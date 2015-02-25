package gov.va.escreening.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.persistence.Column;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

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
import static gov.va.escreening.constants.AssessmentConstants.*;

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
	private static final AssessmentVariableType TYPE_ANSWER = new AssessmentVariableType(ASSESSMENT_VARIABLE_TYPE_MEASURE_ANSWER);
	private static final AssessmentVariableType TYPE_MEASURE = new AssessmentVariableType(ASSESSMENT_VARIABLE_TYPE_MEASURE);
	private static final AssessmentVariableType TYPE_CUSTOM = new AssessmentVariableType(ASSESSMENT_VARIABLE_TYPE_CUSTOM);
	
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
	public SelectAVBuilder addSelectOneAV(@Nullable Integer avId, @Nullable String questionText){
		return new SelectAVBuilder(this, MEASURE_TYPE_SELECT_ONE, avId, questionText);
	}
	
	@Override
	public SelectAVBuilder addSelectMultiAV(@Nullable Integer avId, @Nullable String questionText){
		return new SelectAVBuilder(this, MEASURE_TYPE_SELECT_MULTI, avId, questionText);
	}
	
	@Override
	public MatrixAVBuilder addSelectOneMatrixAV(@Nullable Integer avId, @Nullable String questionText){
		return new MatrixAVBuilder(this, MEASURE_TYPE_SELECT_ONE_MATRIX, avId, questionText);
	}
	
	@Override
	public MatrixAVBuilder addSelectMultiMatrixAV(@Nullable Integer avId, @Nullable String questionText){
		return new MatrixAVBuilder(this, MEASURE_TYPE_SELECT_MULTI_MATRIX, avId, questionText);
	}
	
	@Override
	public CustomAvBuilder addCustomAV(int id){
		return new CustomAvBuilder(this, id);
	}
	
	
	//Builder methods 
	
	/**
	 * It is important to note that calling this a second time with the same measure ID will overwrite the previous call.
	 * @param measureId
	 * @param smrs
	 */
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
	
	private void addMeasureResponse(int measureId, SurveyMeasureResponse response){
		List<SurveyMeasureResponse> responseList = surveyResponseRepo.getForVeteranAssessmentAndMeasure(4, measureId);
		if(responseList == null){
			responseList = new LinkedList<>();
		}
		responseList.add(response);
		when(surveyResponseRepo.getForVeteranAssessmentAndMeasure(anyInt(), eq(measureId))).thenReturn(responseList);
		
	}
	
	private AssessmentVariable newAssessmentVariable(@Nullable Integer id, AssessmentVariableType type){
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
	
//	//resolveTableAssessmentVariableQuestion
//	createTableAV(children)
//	addNone(isSelected)

	
	public class SelectAVBuilder extends ForwardingAssessmentVariableBuilder{
		private final Measure measure;
		private final AssessmentVariable av;
		private Double defaultCalculationValue = 0.0;
		
		private SelectAVBuilder(AssessmentVariableBuilder rootBuilder, Integer measureTypeId, 
				@Nullable Integer avId, @Nullable String questionText){
			super(rootBuilder);
			
			if(measureTypeId != MEASURE_TYPE_SELECT_ONE 
					&& measureTypeId != MEASURE_TYPE_SELECT_MULTI){
				throw new IllegalArgumentException("measure type ID must be a select");
			}
			
			measure = newMeasure(measureTypeId, questionText);
			
			av = newAssessmentVariable(avId, TYPE_MEASURE);
			av.setMeasure(measure);
			avMap.put(avId, av);
		}
		
		private Measure getMeasure(){
			return measure;
		}
		
		/**
		 * 
		 * @param avId
		 * @param answerText
		 * @param answerType should be "other", "none", or null
		 * @param calculationValue 
		 * @param response
		 * @param otherTextResponse if the type of this answer is other this response is the veteran's entered text
		 * @return
		 */
		public SelectAVBuilder addAnswer(@Nullable Integer avId, 
				@Nullable String answerText, 
				@Nullable String answerType, 
				@Nullable Double calculationValue,
				@Nullable Boolean response,
				@Nullable String otherTextResponse){
			
			MeasureAnswer answer = newMeasureAnswer();
			answer.setAnswerText(answerText);
			answer.setAnswerType(answerType);
			answer.setCalculationValue(calculationValue != null ? calculationValue.toString() : defaultCalculationValue.toString());
			defaultCalculationValue++;
			
			measure.getMeasureAnswerList().add(answer);
			
			//create av for answer
			AssessmentVariable aav = newAssessmentVariable(avId, TYPE_ANSWER);
			aav.setMeasureAnswer(answer);
			answer.getAssessmentVariableList().add(aav);
			measureAnswerHash.put(answer.getMeasureAnswerId(), aav);
			
			//create response
			if(response != null){
				SurveyMeasureResponse srm = new SurveyMeasureResponse();
				srm.setBooleanValue(response);
				srm.setMeasure(measure);
				srm.setMeasureAnswer(answer);
				srm.setOtherValue(otherTextResponse);
				addMeasureResponse(measure.getMeasureId(), srm);
			}
			
			return this;
		}
		
		//TODO: add the following methods
//		//from resolveSelectOneAssessmentVariableQuestion
//		createSelectOneAV();
//		addOption(displayValue, calculationValue, isSelected)
//		addNone(isSelected)
//		addOther(isSelected, otherText)
	//	
//		//resolveSelectMultiAssessmentVariableQuestion
//		createSelectMultiAV();
//		addOption(isSelected)
//		addNone(isSelected)
//		addOther(isSelected, otherText)
	}
	
	
	public class MatrixAVBuilder extends ForwardingAssessmentVariableBuilder{
		private final Measure matrixMeasure;
		private final AssessmentVariable av;
		private final List<SelectAVBuilder> childBuilders = new LinkedList<>();
		private final List<MeasureAnswer> columns = new LinkedList<>();

		/**
		 * 
		 * @param rootBuilder
		 * @param measureTypeId should be either Assessment
		 * @param avId assessment variable ID for the matrix
		 * @param questionText
		 */
		private MatrixAVBuilder(AssessmentVariableBuilder rootBuilder, Integer measureTypeId, 
				@Nullable Integer avId, @Nullable String questionText){
			super(rootBuilder);
			
			if(measureTypeId != MEASURE_TYPE_SELECT_ONE_MATRIX 
					&& measureTypeId != MEASURE_TYPE_SELECT_MULTI_MATRIX){
				throw new IllegalArgumentException("measure type ID must be a matrix");
			}
			
			matrixMeasure = newMeasure(measureTypeId, questionText);
			matrixMeasure.setChildren(new HashSet<Measure>());
			
			av = newAssessmentVariable(avId, TYPE_MEASURE);
			av.setMeasure(matrixMeasure);
			avMap.put(avId, av);
		}
		
		/**
		 * Adds a new child question to the matrix.  If this is called after a column was added, answers will be added but
		 * no responses will be set.
		 * @param childQuestionAvId
		 * @param questionText
		 * @return
		 */
		public MatrixAVBuilder addChildQuestion(@Nullable Integer childQuestionAvId, @Nullable String questionText){
			Integer childMeasureTypeId = matrixMeasure.getMeasureType().getMeasureTypeId() ==  MEASURE_TYPE_SELECT_ONE_MATRIX 
					?  MEASURE_TYPE_SELECT_ONE :  MEASURE_TYPE_SELECT_MULTI;
			
			SelectAVBuilder selectBuilder = new SelectAVBuilder(getRootBuilder(), childMeasureTypeId, childQuestionAvId, questionText);
			childBuilders.add(selectBuilder);
			
			//add any answers that have been defined
			for(MeasureAnswer col : columns){
				selectBuilder.addAnswer(null, col.getAnswerText(), null, 
						col.getCalculationValue() == null ? null: Double.valueOf(col.getCalculationValue()), null, null);
			}
			
			//associate child with this parent
			Measure childMeasure = selectBuilder.getMeasure();
			childMeasure.setParent(matrixMeasure);
			matrixMeasure.getChildren().add(childMeasure);
			
			return this;
		}
		
		/**
		 * Adds a new column to this matrix which means that every question which was added will have a new answer added.
		 * If responses should be set then all questions should be added first and then columns.
		 * @param answerText the column's (answer's) text shown to the veteran
		 * @param answerType should be null, "none", or "other"
		 * @param calculationValue 
		 * @param responses this should be null or have a response for each question that was added to this matrix. The pair is 
		 * a boolean (whether the option is selected), and an optional string (can be null) representing the "other" text value
		 * the veteran entered into the answer. In the context of a matrix question, other answers are used to allow the 
		 * veteran to set the question they are answering (because there are added topics that the other matrix questions didn't 
		 * cover). So be careful with the other type.
		 * @return
		 */
		public MatrixAVBuilder addColumn(
				@Nullable String answerText,  
				@Nullable String answerType, 
				@Nullable Double calculationValue, 
				@Nullable Iterable<Pair<Boolean,String>> responses){
			
			//save in case a new question is added after this
			MeasureAnswer ma = new MeasureAnswer();
			ma.setAnswerText(answerText);
			ma.setCalculationValue(calculationValue.toString());
			columns.add(ma);
			
			Iterator<Pair<Boolean,String>> responseIter = responses == null ? null : responses.iterator();
			for(SelectAVBuilder childBuilder : childBuilders){
				Pair<Boolean,String> response = responseIter != null && responseIter.hasNext() ? responseIter.next() : null; 
				childBuilder.addAnswer(null, answerText, answerType, calculationValue, response.getLeft(), response.getRight());
			}
			
			return this;
		}
		
	}
	
	public class FreeTextAvBuilder extends ForwardingAssessmentVariableBuilder{
		private final Measure measure;
		
		private FreeTextAvBuilder(AssessmentVariableBuilder rootBuilder, int avId, String measureText, @Nullable String response){
			super(rootBuilder);
		
			//create a measure
			measure = newFreeTextMeasure(measureText);
			MeasureAnswer answer = measure.getMeasureAnswerList().get(0);
			
			//create av for measure
			AssessmentVariable av = newAssessmentVariable(avId, TYPE_MEASURE);
			av.setMeasure(measure);
			avMap.put(avId, av);
			
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
				setMeasureResponses(measure.getMeasureId(), srm);
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
			Measure m = newMeasure(MEASURE_TYPE_FREE_TEXT, text, newMeasureAnswer());
			m.setMeasureValidationList(new ArrayList<MeasureValidation>());
			return m;
		}
	}
	
	public class CustomAvBuilder extends ForwardingAssessmentVariableBuilder{
		
		private CustomAvBuilder(AssessmentVariableBuilder rootBuilder, Integer customAvId){
			super(rootBuilder);
			
			AssessmentVariable av = newAssessmentVariable(customAvId, TYPE_CUSTOM);
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
		
		/**
		 * Adds an appointment used by the AV with ID: {@link CustomAssessmentVariableResolver.CUSTOM_VETERAN_APPOINTMENTS}
		 * @param clinicName
		 * @param date
		 * @param status
		 * @return
		 */
		public CustomAvBuilder addAppointment(String clinicName, Date date, String status){
			VistaVeteranAppointment appointment = new VistaVeteranAppointment();
			appointment.setClinicName(clinicName);
			appointment.setAppointmentDate(date);
			appointment.setStatus(status);
			
			addVeteranAppointment(appointment);
			return this;
		}
		
		/**
		 * Set the assigned clinician for this assessment (used by custom variables)
		 * @param user
		 * @return this builder
		 */
		public CustomAvBuilder setClinician(User user){
			when(assessment.getClinician()).thenReturn(user);
			return this;
		}
		
		/**
		 * Set the duration of the assessment (used by custom variables)
		 * @param durationSeconds
		 * @return this builder
		 */
		public CustomAvBuilder setAssessmentDuration(int durationSeconds){
			when(assessment.getDuration()).thenReturn(durationSeconds);
			return this;
		}

		/**
		 * Set the veteran who took this assessment (used by custom variables)
		 * @param vet
		 * @return this builder
		 */
		public  CustomAvBuilder setVeteran(Veteran vet){
			when(assessment.getVeteran()).thenReturn(vet);
			return this;
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
