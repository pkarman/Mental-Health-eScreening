package gov.va.escreening.variableresolver;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.SystemProperty;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.repository.VeteranAssessmentRepository;
import gov.va.escreening.service.SystemPropertyService;
import gov.va.escreening.service.VeteranAssessmentService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor={CouldNotResolveVariableException.class, AssessmentVariableInvalidValueException.class, UnsupportedOperationException.class, Exception.class})
public class CustomAssessmentVariableResolverImpl implements CustomAssessmentVariableResolver {
	
    @Autowired
    private SystemPropertyService systemPropertyService;
    @Autowired
    private VeteranAssessmentRepository veteranAssessmentRepository;
    @Autowired
    private VeteranAssessmentService veteranAssessmentService;
    
	public static final int CUSTOM_PACKET_VERSION_VARIABLE_ID = 1;
	public static final int CUSTOM_ASSIGNED_CLINICIAN_VARIABLE_ID = 2;
	public static final int CUSTOM_SIGNING_CLINICIAN_VARIABLE_ID = 3;
	public static final int CUSTOM_TODAYS_DATE = 4;
	public static final int CUSTOM_ASSESSMENT_DURATION = 5;
	
	public static final int SYSTEM_PROPERTIES_ESCREENING_PACKET_VERSION_ID = 1;
	
    private static final Logger logger = LoggerFactory.getLogger(CustomAssessmentVariableResolverImpl.class);

    @Override
    public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable, Integer veteranAssessmentId) {
		AssessmentVariableDto variableDto = null;
		
		Integer variableId = assessmentVariable.getAssessmentVariableId();
		switch(variableId) {
			case CUSTOM_PACKET_VERSION_VARIABLE_ID:
				variableDto = packetVersionHandler();
				break;
			case CUSTOM_ASSIGNED_CLINICIAN_VARIABLE_ID:
				variableDto = assignedClinicianHandler(veteranAssessmentId);
				break;
			case CUSTOM_SIGNING_CLINICIAN_VARIABLE_ID:
				variableDto = signingClinicianHandler(veteranAssessmentId);
				break;
			case CUSTOM_TODAYS_DATE:
				variableDto = getCurrentDate();
				break;
			case CUSTOM_ASSESSMENT_DURATION:
				variableDto = getAssessmentDuration(veteranAssessmentId);
				break;
			default:
				throw new UnsupportedOperationException(
					String.format("AssessmentVariable of type Custom with an id of %s is not supported.  A handler must be implemented to support the referenced id.", 
						variableId));
		}
		
		return variableDto;
	}
	
	/* new AssessmentVariable(1, "var1", "string", "custom_1", "1.1.04", "1.1.04", null, null) */
	private AssessmentVariableDto packetVersionHandler() {
		
		AssessmentVariableDto variableDto = null;
		
		SystemProperty systemProperty = systemPropertyService.findBySystemPropertyId(SYSTEM_PROPERTIES_ESCREENING_PACKET_VERSION_ID);
		
		if(systemProperty == null) {
			throw new CouldNotResolveVariableException(String.format("Could not find system_property with an id of %s, returned system property was null.", SYSTEM_PROPERTIES_ESCREENING_PACKET_VERSION_ID));
		}
		if(systemProperty.getTextValue() == null || systemProperty.getTextValue().isEmpty()) {
			throw new AssessmentVariableInvalidValueException(String.format("Could not find system_property with an id of %s, returned system property was null.", SYSTEM_PROPERTIES_ESCREENING_PACKET_VERSION_ID));
		}
		else {
			String varName = String.format("var%s", CUSTOM_PACKET_VERSION_VARIABLE_ID);
			String displayName = String.format("custom_%s", CUSTOM_PACKET_VERSION_VARIABLE_ID);
			variableDto = new AssessmentVariableDto(CUSTOM_PACKET_VERSION_VARIABLE_ID, varName, "string", displayName, 
					systemProperty.getTextValue(), systemProperty.getTextValue(), null, null, AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN );
		}
		
		return variableDto;
	}
	
	/* new AssessmentVariable(2, "var2", "string", "custom_2", "Clinician Assigned", "Clinician Assigned", null, null) */
	private AssessmentVariableDto assignedClinicianHandler(Integer veteranAssessmentId) {

		AssessmentVariableDto variableDto = null;
		
		VeteranAssessment veteranAssessment = veteranAssessmentService.findByVeteranAssessmentId(veteranAssessmentId);
		if(veteranAssessment == null) {
			throw new AssessmentVariableInvalidValueException(String.format("Could not find requested VeteranAssessment with id of: %s", veteranAssessmentId));
		}
		if(veteranAssessment.getClinician() == null ) {
			throw new CouldNotResolveVariableException(String.format("Clinician was null for VeteranAssessment with id of: %s", veteranAssessmentId));
		}
		else {
			String administeredBy = ContentUtilty.getFormattedName(veteranAssessment.getClinician().getFirstName(), 
					veteranAssessment.getClinician().getLastName());
			String varName = String.format("var%s", CUSTOM_ASSIGNED_CLINICIAN_VARIABLE_ID);
			String displayName = String.format("custom_%s", CUSTOM_ASSIGNED_CLINICIAN_VARIABLE_ID);
			variableDto = new AssessmentVariableDto(CUSTOM_ASSIGNED_CLINICIAN_VARIABLE_ID, varName, "string", displayName, 
					administeredBy, administeredBy, null, null, AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN);
		}
		
		return variableDto;
	}
	
	/* new AssessmentVariable(3, "var3", "string", "custom_3", "Clinician Signing", "Clinician Signing", null, null) */
	private AssessmentVariableDto signingClinicianHandler(Integer veteranAssessmentId) {

		AssessmentVariableDto variableDto = null;
		
		VeteranAssessment veteranAssessment = veteranAssessmentService.findByVeteranAssessmentId(veteranAssessmentId);
		if(veteranAssessment == null) {
			throw new AssessmentVariableInvalidValueException(String.format("Could not find requested VeteranAssessment with id of: %s", veteranAssessmentId));
		}
		if(veteranAssessment.getSignedByUser() == null ) {
			throw new CouldNotResolveVariableException(String.format("SignedByUser was null for VeteranAssessment with id of: %s", veteranAssessmentId));
		}
		else {
			String signedByUser = ContentUtilty.getFormattedName(veteranAssessment.getSignedByUser().getFirstName(), 
					veteranAssessment.getClinician().getLastName());
			String varName = String.format("var%s", CUSTOM_SIGNING_CLINICIAN_VARIABLE_ID);
			String displayName = String.format("custom_%s", CUSTOM_SIGNING_CLINICIAN_VARIABLE_ID);
			variableDto = new AssessmentVariableDto(CUSTOM_SIGNING_CLINICIAN_VARIABLE_ID, varName, "string", displayName, 
					signedByUser, signedByUser, null, null, AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN);
		}
		
		return variableDto;
	}
	
	//TODO need to add export_name of "demo_date"
	/* new AssessmentVariable(4, "var4", "string", "custom_4", "05-14-2014", "05-14-2014", null, null) */
	private AssessmentVariableDto getCurrentDate() {
	    SimpleDateFormat sdfDate = new SimpleDateFormat("MM-dd-yyyy");
	    Date now = new Date();
	    String strDate = sdfDate.format(now);
		
		String varName = String.format("var%s", CUSTOM_TODAYS_DATE);
		String displayName = String.format("custom_%s", CUSTOM_TODAYS_DATE);
		AssessmentVariableDto variableDto = new AssessmentVariableDto(CUSTOM_TODAYS_DATE, varName, "string", displayName, 
				strDate, strDate, null, null, AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN);
	    
		return variableDto;
	}
	
	//TODO need to add export_name of "duration"
	/* new AssessmentVariable(5, "var5", "string", "custom_5", "01:10:15", "01:10:15", null, null) */
	private AssessmentVariableDto getAssessmentDuration(Integer veteranAssessmentId) {
		
		AssessmentVariableDto variableDto = null;
		
		VeteranAssessment veteranAssessment = veteranAssessmentService.findByVeteranAssessmentId(veteranAssessmentId);
		if(veteranAssessment == null) {
			throw new AssessmentVariableInvalidValueException(String.format("Could not find requested VeteranAssessment with id of: %s", veteranAssessmentId));
		}
		if(veteranAssessment.getDuration() == null ) {
			throw new CouldNotResolveVariableException(String.format("Duration was null for VeteranAssessment with id of: %s", veteranAssessmentId));
		}
		else {
			Integer durationInt = veteranAssessment.getDuration();
			long millis = durationInt * 1000; 
			
			String duration = String.format("%02d:%02d:%02d", 
					TimeUnit.MILLISECONDS.toHours(millis),
					TimeUnit.MILLISECONDS.toMinutes(millis) -  
					TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), 
					TimeUnit.MILLISECONDS.toSeconds(millis) - 
					TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
						
			String varName = String.format("var%s", CUSTOM_ASSESSMENT_DURATION);
			String displayName = String.format("custom_%s", CUSTOM_ASSESSMENT_DURATION);
			variableDto = new AssessmentVariableDto(CUSTOM_ASSESSMENT_DURATION, varName, "string", displayName, 
					duration, duration, null, null, AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN);
		}
		
		return variableDto;
	}
}