package gov.va.escreening.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.delegate.CreateAssessmentDelegate;
import gov.va.escreening.domain.RoleEnum;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.AssessmentVariableType;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.MeasureAnswerValidation;
import gov.va.escreening.entity.MeasureType;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.SystemProperty;
import gov.va.escreening.entity.User;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.expressionevaluator.ExpressionEvaluatorService;
import gov.va.escreening.repository.SurveyMeasureResponseRepository;
import gov.va.escreening.repository.UserRepository;
import gov.va.escreening.service.SystemPropertyService;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.variableresolver.AssessmentVariableDto;
import gov.va.escreening.variableresolver.AssessmentVariableDtoFactory;
import gov.va.escreening.variableresolver.AssessmentVariableDtoFactoryImpl;
import gov.va.escreening.variableresolver.CustomAssessmentVariableResolver;
import gov.va.escreening.variableresolver.CustomAssessmentVariableResolverImpl;
import gov.va.escreening.variableresolver.FormulaAssessmentVariableResolver;
import gov.va.escreening.variableresolver.FormulaAssessmentVariableResolverImpl;
import gov.va.escreening.variableresolver.MeasureAnswerAssessmentVariableResolver;
import gov.va.escreening.variableresolver.MeasureAnswerAssessmentVariableResolverImpl;
import gov.va.escreening.variableresolver.MeasureAssessmentVariableResolver;
import gov.va.escreening.variableresolver.MeasureAssessmentVariableResolverImpl;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;
import static org.mockito.Mockito.*;

public class AssessmentVariableBuilder {
	private static final AssessmentVariableType TYPE_ANSWER = new AssessmentVariableType(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER);
	private static final AssessmentVariableType TYPE_MEASURE = new AssessmentVariableType(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE);
	
	
	private final Map<Integer, AssessmentVariable> avMap = new HashMap<>();
	private final Map<Integer, AssessmentVariable> measureAnswerHash = new HashMap<>();
	
	//resolvers - used to make sure all business rules are followed
	private final MeasureAnswerAssessmentVariableResolver answerResolver;
	private final MeasureAssessmentVariableResolver measureResolver;
	private final CustomAssessmentVariableResolver customResolver;
	private final FormulaAssessmentVariableResolver formulaResolver;
	private final AssessmentVariableDtoFactory variableResolver;
	
	//mocked supporting objects for resolvers
	private final SurveyMeasureResponseRepository surveyResponseRepo = mock(SurveyMeasureResponseRepository.class);
	private final ExpressionEvaluatorService expressionService = mock(ExpressionEvaluatorService.class);
	private final VeteranAssessment assessment = mock(VeteranAssessment.class);
	private final Veteran vet = mock(Veteran.class);
	private final List<VistaVeteranAppointment>appointments = new ArrayList<>();
	
	
	public AssessmentVariableBuilder(){
		answerResolver = new MeasureAnswerAssessmentVariableResolverImpl(surveyResponseRepo);
		measureResolver = new MeasureAssessmentVariableResolverImpl(answerResolver, surveyResponseRepo);
		customResolver = createCustomResolver();
		formulaResolver = new FormulaAssessmentVariableResolverImpl(expressionService, answerResolver, measureResolver);
		variableResolver = new AssessmentVariableDtoFactoryImpl(customResolver, formulaResolver, answerResolver, measureResolver);
		
	}
	
	private CustomAssessmentVariableResolver createCustomResolver(){
		//setup packet version lookup
		SystemPropertyService sps = mock(SystemPropertyService.class);
		SystemProperty sp = mock(SystemProperty.class);
		when(sp.getTextValue()).thenReturn("test.version.number");
		when(sps.findBySystemPropertyId(anyInt())).thenReturn(sp);
		
		//setup assessment 
		VeteranAssessmentService vas = mock(VeteranAssessmentService.class);
		when(vas.findByVeteranAssessmentId(anyInt())).thenReturn(assessment);		
		when(vet.getVeteranIen()).thenReturn("123");
		when(assessment.getVeteran()).thenReturn(vet);
		
		//setup appointments
		UserRepository ur = mock(UserRepository.class);
		User mockUser = mock(User.class);
		when(ur.findByRoleId(RoleEnum.TECH_ADMIN)).thenReturn(Lists.newArrayList(mockUser));
		
		CreateAssessmentDelegate cad = mock(CreateAssessmentDelegate.class);
		when(cad.getVeteranAppointments(eq(mockUser), anyString())).thenReturn(appointments);
		
		return new CustomAssessmentVariableResolverImpl(sps, vas, ur, cad);
	}
	
	public List<AssessmentVariableDto> getDTOs(){
		List<AssessmentVariableDto> dtoList = new ArrayList<>(avMap.size());
		for(AssessmentVariable av : avMap.values()){
			dtoList.add(variableResolver.createAssessmentVariableDto(av, 123, measureAnswerHash));
		}
		
		return dtoList;
	}
	
	public void setMeasureResponses(int measureId, SurveyMeasureResponse... smrs){
		List<SurveyMeasureResponse> responseList;
		if(smrs == null){ 
			responseList = Collections.emptyList();
		}
		else{ 
			responseList = Arrays.asList(smrs); 
		}
		
		when(surveyResponseRepo.getForVeteranAssessmentAndMeasure(anyInt(), eq(measureId))).thenReturn(responseList);
	}

	/**
	 * 
	 * @param id should be a number under 100
	 * @param measureText
	 * @param response
	 * @return
	 */
	public AssessmentVariableBuilder addFreeTextAV(int id, String measureText, String response){
		//create a measure
		Measure measure = newFreeTextMeasure(measureText);
		MeasureAnswer answer = measure.getMeasureAnswerList().get(0);
		
		//create av for measure
		AssessmentVariable av = newAssessmentVariable(id, TYPE_MEASURE);
		av.setMeasure(measure);
		avMap.put(id, av);
		
		//create av for answer
		AssessmentVariable aav = newAssessmentVariable(null, TYPE_ANSWER);
		aav.setMeasureAnswer(answer);
		answer.getAssessmentVariableList().add(aav);
		measureAnswerHash.put(answer.getMeasureAnswerId(), aav);
		
		//create response
		SurveyMeasureResponse srm = new SurveyMeasureResponse();
		srm.setTextValue(response);
		srm.setMeasure(measure);
		srm.setMeasureAnswer(answer);
		
		setMeasureResponses(av.getMeasure().getMeasureId(), srm);
		
		return this;
	}
	
	
	private AssessmentVariable newAssessmentVariable(Integer id, AssessmentVariableType type){
		AssessmentVariable var = id == null ? new AssessmentVariable(avId++) : new AssessmentVariable(id);
		List<VariableTemplate> vtList = new ArrayList<>();
		vtList.add(new VariableTemplate());
		var.setVariableTemplateList(vtList);
		var.setAssessmentVarChildrenList(new ArrayList<AssessmentVarChildren>());
		var.setAssessmentVariableTypeId(type);
		return var;
	}
	
	private int measureId = 1;
	private int answerId = 1;
	private int avId = 100;
	private Measure newMeasure(Integer type, String text, MeasureAnswer... maList){
		Measure m = new Measure(measureId++);
		m.setMeasureType(new MeasureType(type));
		m.setMeasureText(text);
		m.setMeasureAnswerList(new ArrayList<MeasureAnswer>());
		
		for(MeasureAnswer ma : maList){
			ma.setMeasure(m);
			m.getMeasureAnswerList().add(ma);
		}
		return m;
	}
	
	private Measure newFreeTextMeasure(String text){
		return newMeasure(AssessmentConstants.MEASURE_TYPE_FREE_TEXT, text, newMeasureAnswer());
	}
	
	private MeasureAnswer newMeasureAnswer(){
		MeasureAnswer ma = new MeasureAnswer(answerId++);
		ma.setAssessmentVariableList(new ArrayList<AssessmentVariable>());
		ma.setMeasureAnswerValidationList(new ArrayList<MeasureAnswerValidation>());
		return ma;
	}
	
//	
//	//from resolveSelectOneAssessmentVariableQuestion
//	createSelectOneAV();
//	addOption(displayValue, calculationValue, isSelected)
//	addNone(isSelected)
//	addOther(isSelected, otherText)
//	
//	//resolveSelectMultiAssessmentVariableQuestion
//	createSelectMultiAV();
//	addOption(isSelected)
//	addNone(isSelected)
//	addOther(isSelected, otherText)
//	
//	//resolveTableAssessmentVariableQuestion
//	createTableAV(children)
//	addNone(isSelected)
//	
//	
//	createMatrixAV(children)
//	
//	
//	
//	createCustomAV()
//	addChild --> look at examples CustomAssessmentVariableResolverImpl.getVeteranAppointments
//	//sets the clinician's name in assessment.getClinician	
//	setClinicianName(String first, String last)
//  //sets getDuration
//	setAssessmentDuration(int durationSeconds)
	//addAppointments(VistaVeteranAppointment appointment...){
	// appointments.add(

	//should this be at a higher level?
	//  setVeteran()
	
	
}
