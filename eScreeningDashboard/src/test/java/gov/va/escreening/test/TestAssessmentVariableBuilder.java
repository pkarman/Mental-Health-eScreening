package gov.va.escreening.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.persistence.Column;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import gov.va.escreening.entity.MeasureValidation;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.SystemProperty;
import gov.va.escreening.entity.User;
import gov.va.escreening.entity.Validation;
import gov.va.escreening.entity.VariableTemplate;
import gov.va.escreening.entity.Veteran;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.exception.CouldNotResolveVariableException;
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
import static com.google.common.base.Preconditions.*;

/**
 * Root {@link AssessmentVariableBuilder} used for integration testing.
 * 
 * @author Robin Carnow
 *
 */
public class TestAssessmentVariableBuilder implements AssessmentVariableBuilder{
	//if this value changes in ClinicalNoteTemplateFunctions.ftl then it has to change here.
	public static final String DEFAULT_VALUE = "notset";
	
	private static final Logger logger = LoggerFactory.getLogger(TestAssessmentVariableBuilder.class); 
	private static final AssessmentVariableType TYPE_ANSWER = new AssessmentVariableType(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER);
	private static final AssessmentVariableType TYPE_MEASURE = new AssessmentVariableType(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_MEASURE);
	private static final AssessmentVariableType TYPE_CUSTOM = new AssessmentVariableType(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_CUSTOM);
	
	//Tracks what is added/built
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
	
	public TestAssessmentVariableBuilder(){
		answerResolver = new MeasureAnswerAssessmentVariableResolverImpl(surveyResponseRepo);
		measureResolver = new MeasureAssessmentVariableResolverImpl(answerResolver, surveyResponseRepo);
		customResolver = createCustomResolver();
		formulaResolver = new FormulaAssessmentVariableResolverImpl(expressionService, answerResolver, measureResolver);
		variableResolver = new AssessmentVariableDtoFactoryImpl(customResolver, formulaResolver, answerResolver, measureResolver); 
	}
	
	@Override
	public List<AssessmentVariableDto> getDTOs(){
		List<AssessmentVariableDto> dtoList = new ArrayList<>(avMap.size());
		for(AssessmentVariable av : avMap.values()){
			try{
				dtoList.add(variableResolver.createAssessmentVariableDto(av, 123, measureAnswerHash));
			}
			catch(CouldNotResolveVariableException e){
				/*ignored*/
				logger.warn(e.getLocalizedMessage());
			}
		}
		return dtoList;
	}
	
	@Override
	public FreeTextAvBuilder addFreeTextAV(int id, String measureText, @Nullable String response){
		return new FreeTextAvBuilder(this, id, measureText, response);
	}

	@Override
	public CustomAvBuilder addCustomAV(int id){
		return new CustomAvBuilder(this, id);
	}
	
	private void setMeasureResponses(int measureId, SurveyMeasureResponse... smrs){
		List<SurveyMeasureResponse> responseList;
		if(smrs == null){ 
			responseList = Collections.emptyList();
		}
		else{ 
			responseList = Arrays.asList(smrs); 
		}
		
		when(surveyResponseRepo.getForVeteranAssessmentAndMeasure(anyInt(), eq(measureId))).thenReturn(responseList);
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
	
	private MeasureAnswer newMeasureAnswer(){
		MeasureAnswer ma = new MeasureAnswer(answerId++);
		ma.setAssessmentVariableList(new ArrayList<AssessmentVariable>());
		ma.setMeasureAnswerValidationList(new ArrayList<MeasureAnswerValidation>());
		return ma;
	}
	
	private void addVeteranAppointment(VistaVeteranAppointment vva){
		appointments.add(vva);
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

	
	public class CustomAvBuilder extends ForwardingAssessmentVariableBuilder{
		private final AssessmentVariable av;
		
		private CustomAvBuilder(AssessmentVariableBuilder rootBuilder, Integer customAvId){
			super(rootBuilder);
			
			av = newAssessmentVariable(customAvId, TYPE_CUSTOM);
			avMap.put(customAvId, av);
			
		}
		
		public CustomAvBuilder addAppointments(VistaVeteranAppointment... appointments){
			for(VistaVeteranAppointment appointment : appointments){
				addAppointment(appointment);
			}
			return this;
		}

		public CustomAvBuilder addAppointment(VistaVeteranAppointment appointment){
			addVeteranAppointment(appointment);
			return this;
		}
		
		public CustomAvBuilder addAppointment(String clinicName, Date date, String status){
			VistaVeteranAppointment appointment = new VistaVeteranAppointment();
			appointment.setClinicName(clinicName);
			appointment.setAppointmentDate(date);
			appointment.setStatus(status);
			
			addVeteranAppointment(appointment);
			return this;
		}
		
		//TODO: implemented the following methods
		//addChild --> look at examples CustomAssessmentVariableResolverImpl.getVeteranAppointments (not sure if this is needed)
		
		//sets the clinician's name in assessment.getClinician	
//		setClinicianName(String first, String last)
		
	    //sets getDuration
//		setAssessmentDuration(int durationSeconds)

		//should this be at a higher level?
		//  setVeteran()

	}
	
	public class FreeTextAvBuilder extends ForwardingAssessmentVariableBuilder{
		private final Measure measure;
		
		private FreeTextAvBuilder(AssessmentVariableBuilder rootBuilder, int id, String measureText, @Nullable String response){
			super(rootBuilder);
			
			//create a measure
			measure = newFreeTextMeasure(measureText);
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
			if(response != null){
				SurveyMeasureResponse srm = new SurveyMeasureResponse();
				srm.setTextValue(response);
				srm.setMeasure(measure);
				srm.setMeasureAnswer(answer);
				setMeasureResponses(av.getMeasure().getMeasureId(), srm);
			}
		}
		
		public FreeTextAvBuilder setDataTypeValidation(String format){
			if(format != "email" && format != "number" && format != "date"){
				throw new IllegalArgumentException("Format is invalid. Should be email, number of date");
			}
			Validation validation = new Validation(1, "dataType", "", "string", null);
			MeasureValidation mv = new MeasureValidation();
			mv.setValidation(validation);
			mv.setMeasure(measure);
			mv.setTextValue(format);
			measure.getMeasureValidationList().add(mv);
			
			return this;
		}
		
		private Measure newFreeTextMeasure(String text){
			Measure m = newMeasure(AssessmentConstants.MEASURE_TYPE_FREE_TEXT, text, newMeasureAnswer());
			m.setMeasureValidationList(new ArrayList<MeasureValidation>());
			return m;
		}
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
}
