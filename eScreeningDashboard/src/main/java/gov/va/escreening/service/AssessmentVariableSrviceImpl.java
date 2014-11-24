package gov.va.escreening.service;

import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyPageMeasure;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.repository.SurveyPageMeasureRepository;
import gov.va.escreening.repository.SurveyRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

@Service("assessmentVariableService")
public class AssessmentVariableSrviceImpl implements AssessmentVariableService {

	@Resource(name = "filterMeasureTypes")
	Set<Integer> filterMeasureTypes;

	@Resource(type = SurveyRepository.class)
	SurveyRepository sr;

	public class TableTypeAvModelBuilder implements AvBuilder<Table<String, String, Object>> {
		final Table<String, String, Object> assessments;

		public TableTypeAvModelBuilder(Table<String, String, Object> assessments) {
			this.assessments = assessments;
		}

		private void addAv2Table(AssessmentVariable av, Measure m,
				MeasureAnswer ma) {
			Integer avId = av.getAssessmentVariableId();
			String avIdRowKey = String.format("avId_%s", avId);
			this.assessments.put(avIdRowKey, "id", avId);
			this.assessments.put(avIdRowKey, "typeId", av.getAssessmentVariableTypeId().getAssessmentVariableTypeId());
			this.assessments.put(avIdRowKey, "name", av.getDisplayName());
			this.assessments.put(avIdRowKey, "displayName", av.getDescription());
			this.assessments.put(avIdRowKey, "answerId", ma != null ? ma.getMeasureAnswerId() : 0);
			this.assessments.put(avIdRowKey, "measureId", m != null ? m.getMeasureId() : 0);
			this.assessments.put(avIdRowKey, "measureTypeId", m != null ? m.getMeasureType().getMeasureTypeId() : 0);
		}

		@Override
		public Table<String, String, Object> getResult() {
			return assessments;
		}

		@Override
		public void buildFromMeasure(AssessmentVariable av,
				AssessmentVarChildren avc, Measure m) {
			addAv2Table(av, m, null);
		}

		@Override
		public void buildFromMeasureAnswer(AssessmentVariable av,
				AssessmentVarChildren avc, Measure m, MeasureAnswer ma) {
			addAv2Table(av, m, ma);
		}

		@Override
		public void buildFormula(Survey survey, AssessmentVariable av,
				Collection<Measure> smList,
				Collection<AssessmentVariable> avList, boolean ignoreAnswers) {

			handleAvChilren(survey, av, smList, this, avList, ignoreAnswers);
		}
	}

	@Resource(type = AssessmentVariableRepository.class)
	AssessmentVariableRepository avr;

	@Resource(type = SurveyPageMeasureRepository.class)
	SurveyPageMeasureRepository spmr;

	@Override
	public Multimap<Survey, Measure> buildSurveyMeasuresMap() {
		List<SurveyPageMeasure> spmList = spmr.findAll();
		Multimap<Survey, Measure> smMap = ArrayListMultimap.create();
		for (SurveyPageMeasure spm : spmList) {
			smMap.put(spm.getSurveyPage().getSurvey(), spm.getMeasure());
		}
		return smMap;
	}

	/**
	 * recursively search that AssessmentVariable belongs to the Measure
	 * 
	 * @param av
	 * @param m
	 * @return
	 */
	@Override
	public boolean compareMeasure(AssessmentVariable av, Measure m) {
		if (av == null) {
			// ignore formula type, thi scan happen if a formula is pointing to another formula (agreegating)
			return false;
		} else if (m.equals(av.getMeasure())) {
			return true;
		} else {
			for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
				return compareMeasure(avc.getVariableChild(), m);
			}
		}
		return false;
	}

	/**
	 * recursively search that AssessmentVariable belongs to the MeasurementAnswer of passed in Measure
	 * 
	 * @param av
	 * @param m
	 * @return
	 */
	@Override
	public boolean compareMeasureAnswer(AssessmentVariable av, Measure m) {
		if (av == null) {
			return false;
		} else if (av.getMeasureAnswer() != null && m.equals(av.getMeasureAnswer().getMeasure())) {
			return true;
		} else {
			for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
				return compareMeasureAnswer(avc.getVariableChild(), m);
			}
		}
		return false;
	}

	@Override
	public void filterBySurvey(Survey survey, AvBuilder avBldr,
			Collection<Measure> smList, Collection<AssessmentVariable> avList,
			boolean useFilteredMeasures) {
		
		boolean ignoreAnswers=useFilteredMeasures;
		
		for (AssessmentVariable av : avList) {
			int avTypeId = av.getAssessmentVariableTypeId().getAssessmentVariableTypeId();
			switch (avTypeId) {
			case 1:
				Collection<Measure> filteredMeasures = useFilteredMeasures ? filterMeasures(smList, filterMeasureTypes) : smList;
				handleMeasureType(av, filteredMeasures, avBldr);
				break;
			case 2:
				// if caller has asked to filter the measures (see case 1) then do not return measure answers
				if (!ignoreAnswers) {
					handleMeasureAnswerType(av, smList, avBldr);
				}
				break;
			case 3:
				handleCustomType(av, smList, avBldr);
				break;
			case 4:
				handleFormulaType(survey, av, smList, avBldr, avList, ignoreAnswers);
				break;
			default:
				throw new IllegalStateException(String.format("The AssessmentVariable type of %s is not supported", avTypeId));
			}
		}
	}

	@Override
	public Collection<AssessmentVariable> findAllFormulae() {
		return avr.findAllFormulae();
	}

	@Override
	@Transactional(readOnly = true)
	/**
	 * return assessment variables as perf following rules
	 * return all Assessments Variables without any filtering except when the AssessmentVariable of type 1 (av_type=1) (Measure). 
	 * If av_type=1 then only return those which belong to Measure Types of 1, 2, or 3
	 */
	public Table<String, String, Object> getAssessmentVarsFor(int surveyId) {

		// by default have empty set of measures assigned to the requested Survey
		Collection<Measure> measures = Lists.newArrayList();
		Survey survey = sr.findOne(surveyId);

		// retrieve a list of all surveys along with their measures
		Multimap<Survey, Measure> surveyMap = buildSurveyMeasuresMap();

		for (Survey s : surveyMap.keySet()) {
			if (surveyId == s.getSurveyId()) {
				measures = surveyMap.get(s);
				survey = s;
				break;
			}
		}

		Table<String, String, Object> assessments = TreeBasedTable.create();

		Collection<AssessmentVariable> avList = avr.findAll();
		AvBuilder avModelBldr = new TableTypeAvModelBuilder(assessments);
		filterBySurvey(survey, avModelBldr, measures, avList, true);
		return (Table<String, String, Object>) avModelBldr.getResult();
	}

	private Collection<Measure> filterMeasures(Collection<Measure> measures,
			final Set<Integer> measureTypes) {

		Predicate<Measure> predicate = new Predicate<Measure>() {
			@Override
			public boolean apply(Measure input) {
				return measureTypes.contains(input.getMeasureType().getMeasureTypeId());
			}
		};
		Collection<Measure> result = Collections2.filter(measures, predicate);
		return result;
	}

	private void handleCustomType(AssessmentVariable av,
			Collection<Measure> smList, AvBuilder avModelBldr) {
		avModelBldr.buildFromMeasureAnswer(av, null, null, null);
	}

	private void handleFormulaType(Survey survey, AssessmentVariable av,
			Collection<Measure> smList, AvBuilder avBldr,
			Collection<AssessmentVariable> avList, boolean ignoreAnswers) {

		avBldr.buildFormula(survey, av, smList, avList, ignoreAnswers);
	}

	private void handleAvChilren(Survey survey, AssessmentVariable avFormula,
			Collection<Measure> smList, AvBuilder avBldr,
			Collection<AssessmentVariable> avList, boolean ignoreAnswers) {

		List<AssessmentVarChildren> avcList = avFormula.getAssessmentVarChildrenList();
		
		for (Measure m : smList) {
			for (AssessmentVarChildren avc : avcList) {
				AssessmentVariable av = avc.getVariableChild();

				boolean isFormulaType = av.getAssessmentVariableTypeId().getAssessmentVariableTypeId() == 4;

				// no need to compare measure or compare measure answer if the av is a formula type
				if (!isFormulaType && compareMeasure(av, m)) {
					avBldr.buildFromMeasure(av, avc, m);
					handleCustomType(avFormula, smList, avBldr);
				} else if (!ignoreAnswers && !isFormulaType && compareMeasureAnswer(av, m)) {
					avBldr.buildFromMeasureAnswer(av, avc, m, avc.getVariableChild().getMeasureAnswer());
					handleCustomType(avFormula, smList, avBldr);
				}
			}
			if (!m.getChildren().isEmpty()) {
				filterBySurvey(survey, avBldr, m.getChildren(), avList, ignoreAnswers);
			}
		}

	}

	private void handleMeasureAnswerType(AssessmentVariable av,
			Collection<Measure> smList, AvBuilder avModelBldr) {
		for (Measure m : smList) {
			if (compareMeasureAnswer(av, m)) {
				avModelBldr.buildFromMeasureAnswer(av, null, m, av.getMeasureAnswer());
				break;
			}
		}
	}

	private void handleMeasureType(AssessmentVariable av,
			Collection<Measure> smList, AvBuilder avModelBldr) {
		for (Measure m : smList) {
			if (compareMeasure(av, m)) {
				avModelBldr.buildFromMeasure(av, null, m);
				break;
			}
		}
	}
}
