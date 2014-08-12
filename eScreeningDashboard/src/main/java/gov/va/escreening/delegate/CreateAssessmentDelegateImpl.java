package gov.va.escreening.delegate;

import gov.va.escreening.domain.BatterySurveyDto;
import gov.va.escreening.domain.ClinicalNoteDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranAssessmentDto;
import gov.va.escreening.domain.VeteranDto;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.dto.SelectVeteranResultDto;
import gov.va.escreening.dto.VeteranAssessmentNoteDto;
import gov.va.escreening.entity.ClinicalReminderSurvey;
import gov.va.escreening.entity.Program;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.service.BatteryService;
import gov.va.escreening.service.BatterySurveyService;
import gov.va.escreening.service.ClinicService;
import gov.va.escreening.service.ClinicalNoteService;
import gov.va.escreening.service.ClinicalReminderSurveyService;
import gov.va.escreening.service.NoteTitleService;
import gov.va.escreening.service.ProgramService;
import gov.va.escreening.service.SurveyService;
import gov.va.escreening.service.UserService;
import gov.va.escreening.service.VeteranAssessmentNoteService;
import gov.va.escreening.service.VeteranAssessmentService;
import gov.va.escreening.service.VeteranService;
import gov.va.escreening.service.VistaService;
import gov.va.escreening.vista.dto.VistaVeteranAppointment;
import gov.va.escreening.vista.dto.VistaVeteranClinicalReminder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CreateAssessmentDelegateImpl implements CreateAssessmentDelegate {

    private static final Logger logger = LoggerFactory.getLogger(CreateAssessmentDelegate.class);

    private String appProxyName;

    private BatteryService batteryService;
    private BatterySurveyService batterySurveyService;
    private ClinicalNoteService clinicalNoteService;
    private ClinicalReminderSurveyService clinicalReminderSurveyService;
    private ClinicService clinicService;
    private NoteTitleService noteTitleService;
    private ProgramService programService;
    private SurveyService surveyService;
    private UserService userService;
    private VeteranAssessmentNoteService veteranAssessmentNoteService;
    private VeteranAssessmentService veteranAssessmentService;
    private VeteranService veteranService;
    @Resource(name="vistaService")
    private VistaService vistaService;

    @Autowired
    public void setBatteryService(BatteryService batteryService) {
        this.batteryService = batteryService;
    }

    @Autowired
    public void setBatterySurveyService(BatterySurveyService batterySurveyService) {
        this.batterySurveyService = batterySurveyService;
    }

    @Autowired
    public void setClinicalNoteService(ClinicalNoteService clinicalNoteService) {
        this.clinicalNoteService = clinicalNoteService;
    }

    @Autowired
    public void setClinicalReminderSurveyService(ClinicalReminderSurveyService clinicalReminderSurveyService) {
        this.clinicalReminderSurveyService = clinicalReminderSurveyService;
    }

    @Autowired
    public void setClinicService(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @Autowired
    public void setNoteTitleService(NoteTitleService noteTitleService) {
        this.noteTitleService = noteTitleService;
    }

    @Autowired
    public void setProgramService(ProgramService programService) {
        this.programService = programService;
    }

    @Autowired
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setVeteranAssessmentNoteService(VeteranAssessmentNoteService veteranAssessmentNoteService) {
        this.veteranAssessmentNoteService = veteranAssessmentNoteService;
    }

    @Autowired
    public void setVeteranAssessmentService(VeteranAssessmentService veteranAssessmentService) {
        this.veteranAssessmentService = veteranAssessmentService;
    }

    @Autowired
    public void setVeteranService(VeteranService veteranService) {
        this.veteranService = veteranService;
    }

    /**
     * The AppProxyName to use for the RPC calls to VistA. This should be configured using Spring.
     * @param appProxyName
     */
    public void setAppProxyName(String appProxyName) {
        this.appProxyName = appProxyName;
    }

    public CreateAssessmentDelegateImpl() {
        // Default constructor.
    }

    @Override
    public List<SelectVeteranResultDto> searchVeterans(EscreenUser escreenUser, String lastName, String ssnLastFour) {

        logger.debug("searchVeterans");

        List<VeteranDto> dbResultList = veteranService.searchVeterans(lastName, ssnLastFour);

        List<VeteranDto> vistaResultList = null;

        if (escreenUser.getCprsVerified()) {
            vistaResultList = vistaService.searchVeteran(escreenUser.getVistaDivision(), escreenUser.getVistaVpid(),
                    escreenUser.getVistaDuz(), appProxyName, lastName, ssnLastFour);
        }

        if (vistaResultList == null) {
            vistaResultList = new ArrayList<VeteranDto>();
        }

        Hashtable<String, String> ht = new Hashtable<String, String>();

        for (int i = 0; i < dbResultList.size(); ++i) {
            if (StringUtils.isNotBlank(dbResultList.get(i).getVeteranIen())) {
                ht.put(dbResultList.get(i).getVeteranIen(), dbResultList.get(i).getVeteranIen());
            }
        }

        for (int i = 0; i < vistaResultList.size(); ++i) {
            if (!ht.containsKey(vistaResultList.get(i).getVeteranIen())) {
                dbResultList.add(vistaResultList.get(i));
            }
        }

        List<SelectVeteranResultDto> searchResultList = new ArrayList<SelectVeteranResultDto>();

        for (int i = 0; i < dbResultList.size(); ++i) {
            SelectVeteranResultDto selectVeteranResultDto = new SelectVeteranResultDto();

            selectVeteranResultDto.setVeteranId(dbResultList.get(i).getVeteranId());
            selectVeteranResultDto.setVeteranIen(dbResultList.get(i).getVeteranIen());
            selectVeteranResultDto.setSsnLastFour(dbResultList.get(i).getSsnLastFour());
            selectVeteranResultDto.setFirstName(dbResultList.get(i).getFirstName());
            selectVeteranResultDto.setMiddleName(dbResultList.get(i).getMiddleName());
            selectVeteranResultDto.setLastName(dbResultList.get(i).getLastName());
            selectVeteranResultDto.setBirthDate(dbResultList.get(i).getBirthDate());

            searchResultList.add(selectVeteranResultDto);
        }

        return searchResultList;
    }

    @Override
    public List<SelectVeteranResultDto> searchVistaForVeterans(EscreenUser escreenUser, String lastName,
            String ssnLastFour) {

        logger.debug("searchVistaForVeterans");

        List<VeteranDto> vistaResultList = new ArrayList<VeteranDto>();

        if (escreenUser.getCprsVerified()) {
            vistaResultList = vistaService.searchVeteran(escreenUser.getVistaDivision(), escreenUser.getVistaVpid(),
                    escreenUser.getVistaDuz(), appProxyName, lastName, ssnLastFour);
        }

        List<SelectVeteranResultDto> searchResultList = new ArrayList<SelectVeteranResultDto>();

        for (int i = 0; i < vistaResultList.size(); ++i) {
            SelectVeteranResultDto selectVeteranResultDto = new SelectVeteranResultDto();

            selectVeteranResultDto.setVeteranIen(vistaResultList.get(i).getVeteranIen());
            selectVeteranResultDto.setSsnLastFour(vistaResultList.get(i).getSsnLastFour());
            selectVeteranResultDto.setFirstName(vistaResultList.get(i).getFirstName());
            selectVeteranResultDto.setMiddleName(vistaResultList.get(i).getMiddleName());
            selectVeteranResultDto.setLastName(vistaResultList.get(i).getLastName());
            selectVeteranResultDto.setBirthDate(vistaResultList.get(i).getBirthDate());

            searchResultList.add(selectVeteranResultDto);
        }

        return searchResultList;
    }

    @Override
    public VeteranDto fetchVeteran(EscreenUser escreenUser, Integer veteranId, String veteranIen, boolean forceRefresh) {

        boolean isStale = false;
        VeteranDto veteranDto = null;

        if (veteranId == null && veteranIen == null) {
            return null;
        }
        else if (veteranId != null) {

            // Get the veteran from the database.
            veteranDto = veteranService.getByVeteranId(veteranId);

            // See if the record is stale.
            if (forceRefresh == false) {
                if (veteranDto.getDateRefreshedFromVista() == null) {
                    isStale = true;
                }
                else {
                    //
                    long age = System.currentTimeMillis() - veteranDto.getDateRefreshedFromVista().getTime();

                    if (age > (3600 * 1000)) {
                        isStale = true;
                        logger.debug("Copy is stale. Refreshing from VistA.");
                    }
                }
            }

            // Read the current assigned IEN and last time it was refreshed.
            veteranIen = veteranDto.getVeteranIen();

            if (escreenUser.getCprsVerified() && veteranIen != null && (forceRefresh || isStale)) {
                VeteranDto vistaVeteranDto = vistaService.getVeteran(escreenUser.getVistaDivision(),
                        escreenUser.getVistaVpid(), escreenUser.getVistaDuz(), appProxyName, veteranIen);

                if (vistaVeteranDto != null) {
                    // Update VistA fields that are stored in the DB.
                    vistaVeteranDto.setVeteranId(veteranId);
                    veteranService.updateMappedVistaFields(vistaVeteranDto);

                    // Get a fresh copy from DB.
                    veteranDto = veteranService.getByVeteranId(veteranId);
                }
            }
        }
        else if (escreenUser.getCprsVerified() && StringUtils.isNotEmpty(veteranIen)) {
            // No record exists in DB. So just go get it from VistA.
            veteranDto = vistaService.getVeteran(escreenUser.getVistaDivision(), escreenUser.getVistaVpid(),
                    escreenUser.getVistaDuz(), appProxyName, veteranIen);
        }

        return veteranDto;
    }

    @Override
    public List<VistaVeteranAppointment> getVeteranAppointments(EscreenUser escreenUser, String veteranIen) {

        List<VistaVeteranAppointment> veteranAppointmentList = vistaService.getVeteranAppointments(
                escreenUser.getVistaDivision(), escreenUser.getVistaVpid(), escreenUser.getVistaDuz(), appProxyName,
                veteranIen);

        return veteranAppointmentList;
    }

    @Override
    public List<VistaVeteranClinicalReminder> getVeteranClinicalReminders(EscreenUser escreenUser, String veteranIen) {

        List<VistaVeteranClinicalReminder> veteranClinicalReminderList = vistaService.getVeteranClinicalReminders(
                escreenUser.getVistaDivision(), escreenUser.getVistaVpid(), escreenUser.getVistaDuz(), appProxyName,
                veteranIen);

        return veteranClinicalReminderList;
    }

    @Override
    public List<VeteranAssessmentDto> getVeteranAssessments(Integer veteranId, List<Integer> programIdList) {

        List<VeteranAssessmentDto> veteranAssessmentList = new ArrayList<VeteranAssessmentDto>();

        veteranAssessmentList = veteranAssessmentService.getVeteranAssessmentsForVeteranAndProgramIdList(veteranId, programIdList);

        return veteranAssessmentList;
    }

    @Override
    public Integer importVistaRecord(EscreenUser escreenUser, String veteranIen) {

        // Get the veteran record from VistA.
        VeteranDto vistaVeteranDto = vistaService.getVeteran(escreenUser.getVistaDivision(),
                escreenUser.getVistaVpid(), escreenUser.getVistaDuz(), appProxyName, veteranIen);

        Integer veteranId = veteranService.importVistaVeteranRecord(vistaVeteranDto);

        return veteranId;
    }

    @Override
    public Integer createVeteranAssessment(EscreenUser escreenUser, int veteranId) {

        return veteranAssessmentService.create(veteranId, escreenUser.getUserId());
    }

    @Override
    public List<ClinicalNoteDto> findAllClinicalNoteForProgramId(Integer programId) {
        // TODO Currently, it doesn't filter on anything yet.
        if (programId == null) {
            programId = 0;
        }

        return clinicalNoteService.findAllForProgramId(programId);
    }

    @Override
    public List<VeteranAssessmentNoteDto> findAllClinicalNoteForVeteranAssessmentId(int veteranAssessmentId) {

        return veteranAssessmentNoteService.findAllByVeteranAssessmentId(veteranAssessmentId);
    }

    public VeteranAssessment getVeteranAssessmentByVeteranAssessmentId(int veteranAssessmentId) {
        VeteranAssessment veteranAssessment = veteranAssessmentService.findByVeteranAssessmentId(veteranAssessmentId);
        return veteranAssessment;
    }

    @Override
    public VeteranDto getVeteranFromDatabase(int veteranId) {
        return veteranService.getByVeteranId(veteranId);
    }

    @Override
    public List<SurveyDto> getSurveyList() {
        return surveyService.getSurveyList();
    }

    @Override
    public List<SurveyDto> getVeteranAssessmentSurveyList(int veteranAssessmentId) {
        return surveyService.getSurveyListForVeteranAssessment(veteranAssessmentId);
    }

    @Override
    public Map<Integer, String> getPreSelectedSurveyMap(
            List<VistaVeteranClinicalReminder> veteranClinicalReminderDtoList) {
        Map<Integer, String> autoAssignedSurveyMap = new HashMap<Integer, String>();

        for (int i = 0; i < veteranClinicalReminderDtoList.size(); ++i) {
            if ("DUE NOW".equalsIgnoreCase(veteranClinicalReminderDtoList.get(i).getDueDateString())) {

                String clinicalReminderIen = veteranClinicalReminderDtoList.get(i).getClinicalReminderIen();
                String clinicalReminderName = veteranClinicalReminderDtoList.get(i).getName();

                // Get all the surveys mapped to this clinical reminder.
                List<ClinicalReminderSurvey> clinicalReminderSurveyList = clinicalReminderSurveyService
                        .findAllByVistaIen(clinicalReminderIen);

                // For each survey mapped to this clinical reminder, add a note entry saying why this survey is
                // preselected. If it already preselected, append a note for this clinical reminder.
                for (int j = 0; j < clinicalReminderSurveyList.size(); ++j) {
                    Integer surveyId = clinicalReminderSurveyList.get(j).getSurvey().getSurveyId();
                    String notes = autoAssignedSurveyMap.get(surveyId);

                    if (notes != null) {
                        notes = notes + ", " + clinicalReminderName;
                    }
                    else {
                        notes = clinicalReminderName;
                    }

                    autoAssignedSurveyMap.put(surveyId, notes);
                }
            }
        }

        return autoAssignedSurveyMap;
    }

    @Override
    public Map<Integer, String> getPreSelectedSurveyMap(EscreenUser escreenUser, String veteranIen) {

        Map<Integer, String> autoAssignedSurveyMap = new HashMap<Integer, String>();

        // Get all the clinical reminders due for this veteran.
        List<VistaVeteranClinicalReminder> clinicalReminderList = getVeteranClinicalReminders(escreenUser, veteranIen);

        // Get all surveys that should be pre selected and the note to as why it is pre selected.
        autoAssignedSurveyMap = getPreSelectedSurveyMap(clinicalReminderList);

        return autoAssignedSurveyMap;
    }

    @Override
    public List<DropDownObject> getProgramList(List<Integer> programIdList) {
        return programService.getProgramDropDownObjects(programIdList);
    }

    @Override
    public List<DropDownObject> getBatteryList() {
        return batteryService.getBatteryList();
    }

    @Override
    public List<BatterySurveyDto> getBatterySurveyList() {
        return batterySurveyService.getBatterySurveyList();
    }

    @Override
    public void updateVeteranIen(int veteranId, String veteranIen) {
        veteranService.updateVeteranIen(veteranId, veteranIen);
    }

    @Override
    public List<DropDownObject> getClinicList(int programId) {
        return clinicService.getDropDownObjectsByProgramId(programId);
    }

    @Override
    public List<DropDownObject> getClinicianList(int programId) {
        return userService.getClinicianDropDownObjects(programId);
    }

    @Override
    public void editVeteranAssessment(EscreenUser escreenUser, Integer veteranAssessmentId, Integer selectedProgramId,
            Integer selectedClinicId, Integer selectedClinicianId, Integer selectedNoteTitleId,
            Integer selectedBatteryId, List<Integer> selectedSurveyIdList) {

        veteranAssessmentService.update(veteranAssessmentId, selectedProgramId, selectedClinicId, selectedClinicianId,
                escreenUser.getUserId(), selectedNoteTitleId, selectedBatteryId, selectedSurveyIdList);

    }

    @Override
    public Integer createVeteranAssessment(EscreenUser escreenUser, Integer veteranId, Integer selectedProgramId,
            Integer selectedClinicId, Integer selectedClinicianId, Integer selectedNoteTitleId,
            Integer selectedBatteryId, List<Integer> selectedSurveyIdList) {

        return veteranAssessmentService.create(veteranId, selectedProgramId, selectedClinicId, selectedClinicianId,
                escreenUser.getUserId(), selectedNoteTitleId, selectedBatteryId, selectedSurveyIdList);
    }

    @Override
    public List<DropDownObject> getNoteTitleList(Integer programId) {
        return noteTitleService.getNoteTitleList(programId);
    }

    @Override
    public boolean isVeteranAssessmentReadOnly(Integer veteranAssessmentId) {
        return veteranAssessmentService.isReadOnly(veteranAssessmentId);
    }

	@Override
	public List<Program> getProgramsForBattery(int batteryId) {
		return batteryService.getProgramsForBattery(batteryId);
	}
}
