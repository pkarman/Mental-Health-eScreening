package gov.va.escreening.assessments.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gov.va.escreening.controller.dashboard.EditVeteranAssessmentController;
import gov.va.escreening.delegate.BatchBatteryCreateDelegate;
import gov.va.escreening.delegate.CreateAssessmentDelegate;
import gov.va.escreening.domain.BatteryDto;
import gov.va.escreening.domain.BatterySurveyDto;
import gov.va.escreening.domain.SurveyDto;
import gov.va.escreening.domain.VeteranWithClinicalReminderFlag;
import gov.va.escreening.dto.BatchBatteryCreateResult;
import gov.va.escreening.dto.DropDownObject;
import gov.va.escreening.entity.*;
import gov.va.escreening.repository.ClinicRepository;
import gov.va.escreening.security.EscreenUser;
import gov.va.escreening.vista.dto.VistaClinicAppointment;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Transactional
// this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class AssessmentCreateBatchPlusIndivTest extends AssessmentTestBase {
    private static Logger logger = Logger.getLogger(AssessmentCreateBatchPlusIndivTest.class);

    @Resource(name = "editVeteranAssessmentController")
    EditVeteranAssessmentController editVeteranAssessmentController;
    @Resource(type = BatchBatteryCreateDelegate.class)
    private BatchBatteryCreateDelegate batchCreateDelegate;
    @Resource(name = "createAssessmentDelegate")
    private CreateAssessmentDelegate createAssessmentDelegate;
    @Resource(name = "clinicRepository")
    private ClinicRepository clinicRepo;

    @Test
    public void batchCreateScenario1() throws Exception {
        Integer clinicianId = 5;
        String clinicIen = String.valueOf(32);
        DateTime jodaDt = DateTime.now();
        Date sDate = jodaDt.toDate();
        Date eDate = jodaDt.plusYears(1).toDate();
        EscreenUser escreenUser = editVeteranAssessmentController.findEscreenUser(clinicianId);
        Map<String, Object> model = Maps.newHashMap();

        selectVeteransForBatchCreate(escreenUser, clinicianId, clinicIen, sDate, eDate, model);

        assignSurveysToSelectedVeterans(escreenUser, model);

        createAssessmentsForSelectedVeterans(escreenUser, model);
    }

    private void createAssessmentsForSelectedVeterans(EscreenUser escreenUser, Map<String, Object> model) {
        Integer selectedBatteryId = 1;
        Integer selectedClinicId = 23;
        Integer selectedClinicianId = 5;
        Integer selectedNoteTitleId = 9;
        Integer selectedProgramId = 5;

        List<Integer> selectedSurveyIdList = createSelectedSurveyIdList();
        Map<Integer, Set<Integer>> vetSurveyMap = (Map<Integer, Set<Integer>>) model.get("vetSurveyMap");

        List<VeteranWithClinicalReminderFlag> vets = (List<VeteranWithClinicalReminderFlag>) model.get("veterans");
        List<BatchBatteryCreateResult> results = batchCreateDelegate
                .batchCreate(
                        vets,
                        selectedProgramId,
                        selectedClinicId,
                        selectedClinicianId,
                        selectedNoteTitleId,
                        selectedBatteryId,
                        vetSurveyMap,
                        selectedSurveyIdList,
                        escreenUser);
    }

    private void assignSurveysToSelectedVeterans(EscreenUser escreenUser, Map<String, Object> model) {
        Map<Integer, Set<Integer>> vetSurveyMap = (Map<Integer, Set<Integer>>) model.get("vetSurveyMap");
    }

    private List<Integer> createSelectedSurveyIdList() {
        return Arrays.asList(9, 1, 2);
    }

    private void selectVeteransForBatchCreate(EscreenUser escreenUser, Integer clinicianId, String clinicIen, Date sDate, Date eDate, Map<String, Object> model) {
        List<VistaClinicAppointment> appList = batchCreateDelegate.searchVeteranByAppointments(escreenUser, clinicIen, sDate, eDate);
        String[] vetIens = createIensFromAppList(appList);
        List<VeteranWithClinicalReminderFlag> vetList = batchCreateDelegate.getVeteranDetails(vetIens, escreenUser, appList);

        model.put("veteransSize", vetList.size());
        model.put("veterans", vetList);
        Map<Integer, Set<Integer>> vetSurveyMap = new HashMap<Integer, Set<Integer>>();

        // Now getting the list of the surveys per veteran
        for (VeteranWithClinicalReminderFlag v : vetList) {
            Map<Integer, String> autoAssignedSurveyMap = createAssessmentDelegate.getPreSelectedSurveyMap(escreenUser, v.getVeteranIen());
            if (!autoAssignedSurveyMap.isEmpty()) {
                vetSurveyMap.put(v.getVeteranId(),
                        new HashSet(autoAssignedSurveyMap.keySet()));
                v.setDueClinicalReminders(true);
            }
        }

        model.put("vetSurveyMap", vetSurveyMap);
//        batchCreateFormBean.setVetSurveyMap(vetSurveyMap);

        model.put("isCprsVerified", escreenUser.getCprsVerified());
        model.put("isCreateMode", true);

        Clinic c = clinicRepo.findByIen(clinicIen).iterator().next();
//        batchCreateFormBean.setSelectedClinicId(c.getClinicId());
//
        model.put("clinic", c.getName());

        //todo krizvi which program to choose
        if (c.getClinicProgramList().iterator().hasNext()) {
            Program p=c.getClinicProgramList().iterator().next().getProgram();
            int varProgramId = p.getProgramId();
//            batchCreateFormBean.setSelectedProgramId(varProgramId);

            DropDownObject dr = new DropDownObject(String.valueOf(varProgramId), p.getName());
            List<DropDownObject> progList = new ArrayList<DropDownObject>(1);
            progList.add(dr);
            model.put("programList", progList);
            model.put("program", p.getName());
            List<DropDownObject> noteTitleList = createAssessmentDelegate.getNoteTitleList(varProgramId);
            model.put("noteTitleList", noteTitleList);

            // Get all clinician list since we have a clinic.
            List<DropDownObject> clinicianList = createAssessmentDelegate.getClinicianList(varProgramId);
            model.put("clinicianList", clinicianList);

        } else {
            List<DropDownObject> programList = createAssessmentDelegate.getProgramList(escreenUser.getProgramIdList());
            model.put("programList", programList);
        }

        List<DropDownObject> batteryList = createAssessmentDelegate.getBatteryList();

        Map<String, String> pm = new HashMap<String, String>();

        for (DropDownObject b : batteryList) {
            List<Program> ps = createAssessmentDelegate.getProgramsForBattery(Integer.parseInt(b.getStateId()));
            StringBuilder sb = new StringBuilder();
            for (Program p : ps) {
                sb.append("program_" + p.getProgramId()).append(" ");
            }
            pm.put(b.getStateId(), sb.toString());
        }

        model.put("programsMap", pm);
        model.put("batteryList", batteryList);

        List<BatterySurveyDto> batterySurveyList = createAssessmentDelegate.getBatterySurveyList();
        model.put("batterySurveyList", batterySurveyList);

        // 7. Get all the modules (surveys) that can be assigned
        List<SurveyDto> surveyList = createAssessmentDelegate.getSurveyList();

        // 8. Populate survey list with list of batteries it is associated with
        // to make it easier in view.
        for (BatterySurveyDto batterySurvey : batterySurveyList) {

            BatteryDto batteryDto = new BatteryDto(
                    batterySurvey.getBatteryId(),
                    batterySurvey.getBatteryName());

            for (SurveyDto survey : surveyList) {
                if (survey.getSurveyId().intValue() == batterySurvey
                        .getSurveyId().intValue()) {
                    if (survey.getBatteryList() == null) {
                        survey.setBatteryList(new ArrayList<BatteryDto>());
                    }

                    survey.getBatteryList().add(batteryDto);
                    break;
                }
            }
        }

        //Set pre-selected
        Map<Integer, Integer> preselectedSurveys = new HashMap<Integer, Integer>();

        for (Map.Entry<Integer, Set<Integer>> entry : vetSurveyMap.entrySet()) {
            for (Integer surveyId : entry.getValue()) {
                if (!preselectedSurveys.containsKey(surveyId)) {
                    preselectedSurveys.put(surveyId, 1);
                } else {
                    preselectedSurveys.put(surveyId, preselectedSurveys.get(surveyId) + 1);
                }
            }
        }
        model.put("surveyList", surveyList);

        int[] checkIndicator = new int[surveyList.size()];

        for (int i = 0; i < surveyList.size(); i++) {
            int checked = 0;
            SurveyDto s = surveyList.get(i);
            if (preselectedSurveys.containsKey(s.getSurveyId())) {
                int num = preselectedSurveys.get(s.getSurveyId());
                if (num == vetIens.length) {
                    checked = 2;
                } else {
                    checked = 1;
                }
            }
            checkIndicator[i] = checked;
        }
        logger.info("Check indicators: " + checkIndicator);
        model.put("dueClinicalReminders", checkIndicator);
    }

    private String[] createIensFromAppList(List<VistaClinicAppointment> appList) {
        List<String> iens = Lists.newArrayList();
        for (VistaClinicAppointment vca : appList) {
            iens.add(vca.getVeteranIen());
        }
        String[] iensAsArray=new String[iens.size()];
        iens.toArray(iensAsArray);
        return iensAsArray;
    }

}
