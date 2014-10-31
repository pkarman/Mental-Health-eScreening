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

	public class TableTypeAvModelBuilder implements AvModelBuilder {
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
			this.assessments.put(avIdRowKey, "name", av.getDescription());
			this.assessments.put(avIdRowKey, "displayName", av.getDisplayName());
			this.assessments.put(avIdRowKey, "answerId", ma != null ? ma.getMeasureAnswerId() : 0);
			this.assessments.put(avIdRowKey, "measureId", m != null ? m.getMeasureId() : 0);
			this.assessments.put(avIdRowKey, "measureTypeId", m != null ? m.getMeasureType().getMeasureTypeId() : 0);
		}

		@Override
		public Object getResult() {
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
	private boolean compareMeasure(AssessmentVariable av, Measure m) {
		if (av == null) {
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
	private boolean compareMeasureAnswer(AssessmentVariable av, Measure m) {
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
	public void filterBySurvey(Survey survey, AvModelBuilder avModelBldr,
			Collection<Measure> smList, Collection<AssessmentVariable> avList,
			boolean filterMeasures) {

		for (AssessmentVariable av : avList) {
			int avTypeId = av.getAssessmentVariableTypeId().getAssessmentVariableTypeId();
			switch (avTypeId) {
			case 1:
				Collection<Measure> filteredMeasures = filterMeasures ? filterMeasures(smList, filterMeasureTypes) : smList;
				handleMeasureType(av, filteredMeasures, avModelBldr);
				break;
			case 2:
				// if caller has asked to filter the measures (see case 1) then do not return measure answers
				if (!filterMeasures) {
					handleMeasureAnswerType(av, smList, avModelBldr);
				}
				break;
			case 3:
				handleCustomType(av, smList, avModelBldr);
				break;
			case 4:
				handleFormulaType(survey, av, smList, avModelBldr, avList, filterMeasures);
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
		AvModelBuilder avModelBldr = new TableTypeAvModelBuilder(assessments);
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
			Collection<Measure> smList, AvModelBuilder avModelBldr) {
		avModelBldr.buildFromMeasureAnswer(av, null, null, null);
	}

	private void handleFormulaType(Survey survey, AssessmentVariable av,
			Collection<Measure> smList, AvModelBuilder avModelBldr,
			Collection<AssessmentVariable> avList, boolean filterMeasures) {
		for (Measure m : smList) {
			for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
				if (compareMeasure(avc.getVariableChild(), m)) {
					avModelBldr.buildFromMeasure(av, avc, m);
				} else if (compareMeasureAnswer(avc.getVariableChild(), m)) {
					avModelBldr.buildFromMeasureAnswer(av, avc, m, avc.getVariableChild().getMeasureAnswer());
				}
			}
			if (!m.getChildren().isEmpty()) {
				filterBySurvey(survey, avModelBldr, m.getChildren(), avList, filterMeasures);
			}
		}
	}

	private void handleMeasureAnswerType(AssessmentVariable av,
			Collection<Measure> smList, AvModelBuilder avModelBldr) {
		for (Measure m : smList) {
			if (compareMeasureAnswer(av, m)) {
				avModelBldr.buildFromMeasureAnswer(av, null, m, av.getMeasureAnswer());
				break;
			}
		}
	}

	private void handleMeasureType(AssessmentVariable av,
			Collection<Measure> smList, AvModelBuilder avModelBldr) {
		for (Measure m : smList) {
			if (compareMeasure(av, m)) {
				avModelBldr.buildFromMeasure(av, null, m);
				break;
			}
		}
	}
}
