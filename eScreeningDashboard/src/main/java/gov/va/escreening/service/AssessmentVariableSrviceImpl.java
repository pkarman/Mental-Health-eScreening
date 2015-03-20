package gov.va.escreening.service;

import gov.va.escreening.constants.AssessmentConstants;

import com.google.common.collect.*;

import gov.va.escreening.dto.ae.ErrorBuilder;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Battery;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyPageMeasure;
import gov.va.escreening.exception.EntityNotFoundException;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.repository.BatteryRepository;
import gov.va.escreening.repository.MeasureAnswerRepository;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.SurveyPageMeasureRepository;
import gov.va.escreening.repository.SurveyRepository;

import java.util.*;

import javax.annotation.Resource;

import gov.va.escreening.service.export.FormulaColumnsBldr;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;

@Service("assessmentVariableService")
public class AssessmentVariableSrviceImpl implements AssessmentVariableService {

	@Resource(name = "filterMeasureTypes")
	Set<Integer> filterMeasureTypes;

	@Resource(type = SurveyRepository.class)
	SurveyRepository sr;

	@Resource(type = AssessmentVariableRepository.class)
	AssessmentVariableRepository avr;

	@Resource(type = SurveyPageMeasureRepository.class)
	SurveyPageMeasureRepository spmr;
	
	@Resource(type = BatteryRepository.class)
	BatteryRepository batteryRepo;

	@Resource(type = MeasureRepository.class)
	MeasureRepository measureRepo;

	@Resource(type = MeasureAnswerRepository.class)
	private MeasureAnswerRepository measureAnswerRepo;
	
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
			
			if(av.getDisplayName() != null){
				this.assessments.put(avIdRowKey, "name", av.getDisplayName());
			}
			
			if(av.getDescription() != null){
				this.assessments.put(avIdRowKey, "displayName", av.getDescription());
			}
			this.assessments.put(avIdRowKey, "answerId", ma != null ? ma.getMeasureAnswerId() : 0);
			this.assessments.put(avIdRowKey, "measureId", m != null ? m.getMeasureId() : 0);
			this.assessments.put(avIdRowKey, "measureTypeId", m != null ? m.getMeasureType().getMeasureTypeId() : 0);
			this.assessments.put(avIdRowKey, "parentMeasureId", m != null && m.getParent() != null ? m.getParent().getMeasureId() : 0);
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

            handleAvChilren(survey, av, smList, this, avList, ignoreAnswers, false);
		}
	}
	
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
    @Transactional(readOnly = true)
    public List<Map<String, String>> askFormulasFor(Integer moduleId) {
        Survey survey = sr.findOne(moduleId);
        List<AssessmentVariable> allFormulas = avr.findAllFormulae();

        // by default have empty set of measures assigned to the requested Survey
        Collection<Measure> measures = Lists.newArrayList();
        // retrieve a list of all surveys along with their measures
        Multimap<Survey, Measure> surveyMap = buildSurveyMeasuresMap();

        for (Survey s : surveyMap.keySet()) {
            if (moduleId == s.getSurveyId()) {
                measures = surveyMap.get(s);
                survey = s;
                break;
            }
        }

        Set<String> avUsed = Sets.newHashSet();
        Set<List<String>> formulaColumnsSet = Sets.newLinkedHashSet();
        AvBuilder<Set<List<String>>> fbBldr = new FormulaColumnsBldr(formulaColumnsSet, avUsed, this);
        filterBySurvey(survey, fbBldr, measures, allFormulas, true, false);

        List<Map<String, String>> formulas = Lists.newArrayList();
        for (List<String> formulaColumns : fbBldr.getResult()) {
            Iterator<String> formulaColumnsIter = formulaColumns.iterator();
            Map<String, String> formulaMap = Maps.newHashMap();
            formulaMap.put("formula", formulaColumnsIter.next());
            formulaMap.put("name", formulaColumnsIter.next());
            formulaMap.put("id", formulaColumnsIter.next());
            formulaMap.put("description", formulaColumnsIter.next());
            formulas.add(formulaMap);
        }
        return formulas;
    }

    @Override
    public String getPlainText(String htmlText) {
        return htmlText != null ? htmlText.replaceAll("\\<.*?>", "") : "";
    }

    @Override
    public void filterBySurvey(Survey survey, AvBuilder<?> avBldr,
                               Collection<Measure> smList, Collection<AssessmentVariable> avList,
                               boolean useFilteredMeasures, boolean includeFormulaTokens) {
		
		boolean ignoreAnswers=useFilteredMeasures;
		Collection<Measure>filteredMeasures = null;
		if(useFilteredMeasures){
			filteredMeasures = filterMeasures(smList, filterMeasureTypes);
		}
		
		for (AssessmentVariable av : avList) {
			int avTypeId = av.getAssessmentVariableTypeId().getAssessmentVariableTypeId();
			switch (avTypeId) {
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE:
				Collection<Measure> measures = useFilteredMeasures ? filteredMeasures : smList;
				handleMeasureType(av, measures, avBldr);
				break;
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER:
				// if caller has asked to filter the measures (see case 1) then do not return measure answers
				if (!ignoreAnswers) {
					handleMeasureAnswerType(av, smList, avBldr);
				}
				break;
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_CUSTOM:
				handleCustomType(av, avBldr);
				break;
			case AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA:
				handleFormulaType(survey, av, smList, avBldr, avList, ignoreAnswers);
				break;
			case 5:
				if (includeFormulaTokens) {
					handleCustomType(av, avBldr);
				}
				break;
			default:
				throw new IllegalStateException(String.format("The AssessmentVariable type of %s is not supported", avTypeId));
			}
		}
	}

	@Override
    public Collection<AssessmentVariable> findAllFormulas() {
		return avr.findAllFormulae();
	}
	
	//TODO: all of these getAssessment* method need to be refactored to be more efficient

	@Override
    @Transactional(readOnly = true)
	public Table<String, String, Object> getAssessmentAllVars(boolean ignoreAnswers, boolean includeFormulaTokens){
	    Table<String, String, Object> assessments = TreeBasedTable.create();
        
        List<Survey> surveys = sr.findAll();
        Collection<AssessmentVariable> avList = avr.findAll();
        AvBuilder<Table<String, String, Object>>  avModelBldr = new TableTypeAvModelBuilder(assessments);
        
        for(Survey survey : surveys){
            List<Measure> measures = survey.createMeasureList();
            //TODO: the implementation of filterBySurvey is not very efficient; it should be updated.
            filterBySurvey(survey, avModelBldr, measures, avList, true, false);
        }
        return avModelBldr.getResult();
	}
	
	@Override
	@Transactional(readOnly = true)
	/**
	 * return assessment variables as perf following rules
	 * return all Assessments Variables without any filtering except when the AssessmentVariable of type 1 (av_type=1) (Measure). 
	 * If av_type=1 then only return those which belong to Measure Types of 1, 2, or 3
	 */
    public Table<String, String, Object> getAssessmentVarsForSurvey(int surveyId, boolean ignoreAnswers, boolean includeFormulaTokens) {

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
		AvBuilder<Table<String, String, Object>>  avModelBldr = new TableTypeAvModelBuilder(assessments);
        filterBySurvey(survey, avModelBldr, measures, avList, ignoreAnswers, includeFormulaTokens);
		return avModelBldr.getResult();
	}
	
	@Override
	@Transactional(readOnly = true)
	/**
	 * @return assessment variables for the given battery
	 */
	public Table<String, String, Object> getAssessmentVarsForBattery(int batteryId) {
		Table<String, String, Object> assessments = TreeBasedTable.create();
		
		if(batteryId > -1){
			Battery battery = batteryRepo.findOne(batteryId);
			if(battery != null){
				Collection<AssessmentVariable> avList = avr.findAll();
				AvBuilder<Table<String, String, Object>>  avModelBldr = new TableTypeAvModelBuilder(assessments);
				
				for(Survey survey : battery.getSurveys()){
					List<Measure> measures = survey.createMeasureList();
					//TODO: the implementation of filterBySurvey is not very efficient; it should be updated.
                    filterBySurvey(survey, avModelBldr, measures, avList, true, false);
				}
				assessments = avModelBldr.getResult();
			}
		}
		return assessments;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Map<Integer, AssessmentVariable> getAssessmentVarsForMeasure(
			Integer measureId) {
		
		Measure m = null;
		if(measureId != null){
			m = measureRepo.findOne(measureId);
		}
		if(m == null){
			ErrorBuilder.throwing(EntityNotFoundException.class)
			.toAdmin("An invalid or null measure ID was given: " + measureId)
			.toUser("An invalid ID for a question was sent to the server. Please contact support.")
			.throwIt();
		}
		
		Map<Integer, AssessmentVariable> avMap = new HashMap<>();
		AssessmentVariable av = m.getAssessmentVariable();
		avMap.put(av.getAssessmentVariableId(), av);
		
		//get AVs for children
		for(Measure child : m.getChildren()){
			av = child.getAssessmentVariable();
			avMap.put(av.getAssessmentVariableId(), av);
		}
		
		return avMap;
	}

	@Override
	@Transactional(readOnly = true)
	public AssessmentVariable getAssessmentVarsForAnswer(Integer measureAnswerId){
		MeasureAnswer ma = null;
		if(measureAnswerId != null){
			ma = measureAnswerRepo.findOne(measureAnswerId);
		}
		if(ma == null){
			ErrorBuilder.throwing(EntityNotFoundException.class)
			.toAdmin("An invalid or null measure answer ID was given: " + measureAnswerId)
			.toUser("An invalid ID for an answer was sent to the server. Please contact support.")
			.throwIt();
		}
		
		//TODO: update this to just return the single AV (when the following method is removed and replaced with getAssessmentVariable())
		List<AssessmentVariable> avs = ma.getAssessmentVariableList();
		
		if(avs == null || avs.isEmpty()){
			ErrorBuilder.throwing(EntityNotFoundException.class)
				.toAdmin("There is not assessment variable associated with measure answer with ID: " + measureAnswerId + ". This should never happen.")
				.toUser("No vairable was found for an answer. Please report this to support.")
				.throwIt();
		}
		return avs.get(0);
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

	private void handleCustomType(AssessmentVariable av, AvBuilder<?> avModelBldr) {
		avModelBldr.buildFromMeasureAnswer(av, null, null, null);
	}

	private void handleFormulaType(Survey survey, AssessmentVariable av,
			Collection<Measure> smList, AvBuilder<?> avBldr,
			Collection<AssessmentVariable> avList, boolean ignoreAnswers) {

		avBldr.buildFormula(survey, av, smList, avList, ignoreAnswers);
	}

	private void handleAvChilren(Survey survey, AssessmentVariable avFormula,
			Collection<Measure> smList, AvBuilder<?> avBldr,
                                 Collection<AssessmentVariable> avList, boolean ignoreAnswers, boolean includeFormulaTokens) {

		List<AssessmentVarChildren> avcList = avFormula.getAssessmentVarChildrenList();
		
		for (Measure m : smList) {
			for (AssessmentVarChildren avc : avcList) {
				AssessmentVariable av = avc.getVariableChild();

				boolean isFormulaType = av.getAssessmentVariableTypeId().getAssessmentVariableTypeId() == 4;

				// no need to compare measure or compare measure answer if the av is a formula type
				if (!isFormulaType && compareMeasure(av, m)) {
					avBldr.buildFromMeasure(av, avc, m);
					handleCustomType(avFormula, avBldr);
				} else if (!ignoreAnswers && !isFormulaType && compareMeasureAnswer(av, m)) {
					avBldr.buildFromMeasureAnswer(av, avc, m, avc.getVariableChild().getMeasureAnswer());
					handleCustomType(avFormula, avBldr);
				}
			}
			if (!m.getChildren().isEmpty()) {
                filterBySurvey(survey, avBldr, m.getChildren(), avList, ignoreAnswers, includeFormulaTokens);
			}
		}

	}

	private void handleMeasureAnswerType(AssessmentVariable av,
			Collection<Measure> smList, AvBuilder<?> avModelBldr) {
		for (Measure m : smList) {
			if (compareMeasureAnswer(av, m)) {
				avModelBldr.buildFromMeasureAnswer(av, null, m, av.getMeasureAnswer());
				break;
			}
		}
	}

	private void handleMeasureType(AssessmentVariable av,
			Collection<Measure> smList, AvBuilder<?> avModelBldr) {
		for (Measure m : smList) {
			if (compareMeasure(av, m)) {
				avModelBldr.buildFromMeasure(av, null, m);
				break;
			}
		}
	}
}
