package gov.va.escreening.variableresolver;

import static com.google.common.base.Preconditions.checkNotNull;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.delegate.CreateAssessmentDelegate;
import gov.va.escreening.domain.RoleEnum;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.SystemProperty;
import gov.va.escreening.entity.User;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.exception.AssessmentVariableInvalidValueException;
import gov.va.escreening.exception.CouldNotResolveVariableException;
import gov.va.escreening.repository.UserRepository;
import gov.va.escreening.service.SystemPropertyService;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(noRollbackFor={CouldNotResolveVariableException.class, AssessmentVariableInvalidValueException.class, UnsupportedOperationException.class, Exception.class})
public class CustomAssessmentVariableResolverImpl implements CustomAssessmentVariableResolver {
	
    private static final Logger logger = LoggerFactory.getLogger(CustomAssessmentVariableResolverImpl.class);
    
    //Please add to the constructor and do not use field based @Autowired	
    private final SystemPropertyService systemPropertyService;
    private final VeteranAssessmentService veteranAssessmentService;
    private final UserRepository userRepository;    
    private final CreateAssessmentDelegate createAssessmentDelegate;

    @Autowired
    public CustomAssessmentVariableResolverImpl(
    		SystemPropertyService sps,
    		VeteranAssessmentService vas,
    		UserRepository ur,    
    		CreateAssessmentDelegate cad){
    	
    	systemPropertyService = checkNotNull(sps);
        veteranAssessmentService = checkNotNull(vas);
        userRepository = checkNotNull(ur);    
        createAssessmentDelegate = checkNotNull(cad);
    }
    
    @Override
    public AssessmentVariableDto resolveAssessmentVariable(AssessmentVariable assessmentVariable, ResolverParameters params) {
        logger.debug("Resolving custom variable ID: {}", assessmentVariable.getAssessmentVariableId());
        
        Integer variableId = assessmentVariable.getAssessmentVariableId();
        params.checkUnresolvable(variableId);
        AssessmentVariableDto variableDto = params.getResolvedVariable(variableId);
        
		if(variableDto == null){
		    try{
		        Integer veteranAssessmentId = params.getAssessmentId();
    			if(CUSTOM_PACKET_VERSION_VARIABLE_ID.equals(variableId)){
    				variableDto = packetVersionHandler();
    			}
    			else if(CUSTOM_ASSIGNED_CLINICIAN_VARIABLE_ID.equals(variableId)){
    				variableDto = assignedClinicianHandler(veteranAssessmentId);
    			}
    			else if(CUSTOM_SIGNING_CLINICIAN_VARIABLE_ID.equals(variableId)){
    				variableDto = signingClinicianHandler(veteranAssessmentId);
    			}
    			else if(CUSTOM_TODAYS_DATE.equals(variableId)){
    				variableDto = getCurrentDate();
    			}
    			else if(CUSTOM_ASSESSMENT_DURATION.equals(variableId)){
    				variableDto = getAssessmentDuration(veteranAssessmentId);
    			}
    			else if(CUSTOM_VETERAN_APPOINTMENTS.equals(variableId)){
    				variableDto = getVeteranAppointments(veteranAssessmentId);
    			}
    			else if(CUSTOM_ASSESSMENT_LAST_MODIFIED.equals(variableId)){
    				variableDto = getAssessmentLastModified(veteranAssessmentId);
    			}
    			else{
    				throw new UnsupportedOperationException(
    					String.format("AssessmentVariable of type Custom with an id of %s is not supported.  A handler must be implemented to support the referenced id.", 
    						variableId));
    			}
        		params.addResolvedVariable(variableDto);
		    }
		    catch(Exception e){
		        params.addUnresolvableVariable(variableId);
		        throw e;
		    }
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
		
		VeteranAssessment veteranAssessment = getAssessment(veteranAssessmentId);
		
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
		
		VeteranAssessment veteranAssessment = getAssessment(veteranAssessmentId);
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
	    Date now = new Date();
	    String strDate = TODAYS_DATE_FORMAT.format(now);
		
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
		
		VeteranAssessment veteranAssessment = getAssessment(veteranAssessmentId);
		
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
	
	private AssessmentVariableDto getVeteranAppointments(Integer veteranAssessmentId){
		// Get veteran IEN
		VeteranAssessment veteranAssessment = getAssessment(veteranAssessmentId);
		if(veteranAssessment.getVeteran() == null || veteranAssessment.getVeteran().getVeteranIen() == null
				|| veteranAssessment.getVeteran().getVeteranIen().isEmpty()){
			throw new AssessmentVariableInvalidValueException("Veteran IEN number could not be found. Map veteran to VistA.");
		}
		String vetIen = veteranAssessment.getVeteran().getVeteranIen();
		
		// Get tech admins
		List<User> adminList = userRepository.findByRoleId(RoleEnum.TECH_ADMIN);
		if(adminList.isEmpty()){
			throw new AssessmentVariableInvalidValueException("Could not find a registered tech admin to pull appointments");
		}
		
		// Get appointments
		List<VistaVeteranAppointment> appointments = null;
		for(User adminUser : adminList){
			try{
				appointments = createAssessmentDelegate.getVeteranAppointments(adminUser, vetIen);
				break;
			}
			catch(Exception e){
				logger.warn("Error getting appointments using tech admin {} and veteran IEN {}",  adminUser, vetIen);
			}
		}
		if(appointments == null){
			throw new AssessmentVariableInvalidValueException("Cannot retrieve appointments from VistA.");
		}
		
		// Sort dates from earlier to later and stick nulls at the end.
		Collections.sort(appointments, new Comparator<VistaVeteranAppointment>(){

			@Override
			public int compare(VistaVeteranAppointment left, VistaVeteranAppointment right) {
				if(left.getAppointmentDate() == null){
					return 1;
				}
				if(right.getAppointmentDate() == null){
					return -1;
				}
				return (int)(left.getAppointmentDate().getTime() - right.getAppointmentDate().getTime());
			}
			
		});
		
		// Create DTOs
		String varName = String.format("var%s", CUSTOM_VETERAN_APPOINTMENTS);
		String displayName = String.format("custom_%s", CUSTOM_VETERAN_APPOINTMENTS);
		
		List<AssessmentVariableDto> children = new ArrayList<>(3);
		for(VistaVeteranAppointment appointment : appointments){
			String appointmentText = APPOINTMENT_DATE_FORMAT.format(appointment.getAppointmentDate()) 
									+ appointment.getClinicName();
			
			children.add(new AssessmentVariableDto(CUSTOM_VETERAN_APPOINTMENTS, varName, "string", 
							displayName, appointmentText, "Veteran Appointments", null, null,
							AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN));
			
			//we only want the first 3
			if(children.size() == 3)
				break;
		}
		
		//TODO: this should probably have a type of "list"
		AssessmentVariableDto variableDto = new AssessmentVariableDto(CUSTOM_VETERAN_APPOINTMENTS, varName, "string", displayName, 
				AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN);
		
		variableDto.setChildren(children);
		
        //create an assessment variable dto with one child per appointment 
        return variableDto;
	}
	
	/**
	 * Gets the completion date for the given assessment.
	 * 
	 * @param veteranAssessmentId
	 * @return
	 */
	private AssessmentVariableDto getAssessmentLastModified(Integer veteranAssessmentId){
		
		VeteranAssessment veteranAssessment = getAssessment(veteranAssessmentId);
		
		if(veteranAssessment.getDateUpdated() == null ) {
			throw new CouldNotResolveVariableException(String.format("Assessment last update date was null for VeteranAssessment with id of: %s", veteranAssessmentId));
		}
		
		String strDate = TODAYS_DATE_FORMAT.format(veteranAssessment.getDateUpdated());
		
		String varName = String.format("var%s", CUSTOM_ASSESSMENT_LAST_MODIFIED);
		String displayName = String.format("custom_%s", CUSTOM_ASSESSMENT_LAST_MODIFIED);
		AssessmentVariableDto variableDto = new AssessmentVariableDto(CUSTOM_ASSESSMENT_LAST_MODIFIED, varName, "string", displayName, 
				strDate, strDate, null, null, AssessmentConstants.ASSESSMENT_VARIABLE_DEFAULT_COLUMN);
		    
		return variableDto;
	}
	
	private VeteranAssessment getAssessment(Integer veteranAssessmentId){
		VeteranAssessment veteranAssessment = veteranAssessmentService.findByVeteranAssessmentId(veteranAssessmentId);
		if(veteranAssessment == null) {
			throw new AssessmentVariableInvalidValueException(String.format("Could not find requested VeteranAssessment with id of: %s", veteranAssessmentId));
		}
		return veteranAssessment;
	}
}