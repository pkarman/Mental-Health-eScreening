package gov.va.escreening.service.export;

import com.google.common.base.Strings;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.SurveyMeasureResponse;
import gov.va.escreening.entity.VeteranAssessment;
import gov.va.escreening.util.SurveyResponsesHelper;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public interface DataExtractor {
    final Logger logger = LoggerFactory.getLogger(DataExtractor.class);

    Map<String, String> apply(SurveyMeasureResponse smr, SurveyResponsesHelper srh);
}

@Component("smrExportName")
class ExportName implements DataExtractor {
    @Override
    public Map<String, String> apply(SurveyMeasureResponse smr, SurveyResponsesHelper srh) {
        MeasureAnswer ma = smr.getMeasureAnswer();

        // data export column we could be interested in
        String xportName = srh.buildExportName(smr, ma.getIdentifyingText());
        if (xportName == null) {
            logger.warn(String.format("%s export name is null--returning null from here", ma));
            return null;
        }
        // user entered data
        String textValue = smr.getTextValue();
        Long numberValue = smr.getNumberValue();

        // marker to identify that this measure answer record was selected
        Boolean boolValue = smr.getBooleanValue();

        String exportableResponse = testTableQuestionChosen(smr.getVeteranAssessment(), ma, boolValue);

        // bypass all logic if this was a table question and if the table question was marked as YES or NO
        if (exportableResponse == null) {
            if (textValue != null && !textValue.trim().isEmpty()) {
                exportableResponse = textValue;
            } else if (numberValue != null) {
                exportableResponse = String.valueOf(numberValue);
            } else if (boolValue != null && boolValue) {
                exportableResponse = ma.getCalculationValue();
                if (Strings.isNullOrEmpty(exportableResponse)) {
                    exportableResponse = "1";
                }
            }
        }

        if (exportableResponse != null) {
            Map<String, String> m = new HashMap<String, String>();
            m.put("exportName", xportName);
            m.put("exportableResponse", exportableResponse);
            return m;
        } else {
            return null;
        }
    }

    /**
     * Method to evaluate if this is The master Answer of TableQuestion parent.
     * <p/>
     * TableQuestions could be as follows:
     * Question : Hobbies, none (Hobbies and none, both are click able).
     * <p/>
     * If user clicks on Hobbies then the first table question will appear with a hobby name,
     * followed by once again an option to add more hobbies and so forth
     * <p/>
     * If user clicking on 'none'--that would mean that there are no hobbies
     * <p/>
     * Scenarios:
     * <p/>
     * <ol>
     * <li>User clicks on 'none': System will record this intent of the user by setting the SMR->booleanValue as true.
     * But for data export this has to be shown as false, as data export is showing the intent of user from business perspective.
     * And clearly user clicked on 'none' as a response to do u have hobbies, meaning that no I don not have any hobbies
     * </li>
     * <li>User clicks on 'Hobbies': This is a no brainer as when user would click on 'Hobbies' means that user does wan to record
     * its hobbies. But the System is trying to hold the logic based on 'none', so since user has hobbies would mean that none is false.
     * But for data export that Hobbies are true, so data export would once again flip the none
     * </li>
     * <li>Surprisingly when user totally ignores to answer by not clicking on 'Hobbies' or 'none', but clicking on next button--the system
     * still records in SMR->booleanValue a value of false.
     * But once again, for data export has to identify the fact that user actually skipped to respond this question all together by marking this as 999
     * </li>
     * </ol>
     *
     * @param ma
     * @param boolValue returns true, if user has selected answers, ie., if the value of none is false
     * @return
     */
    private String testTableQuestionChosen(VeteranAssessment va, MeasureAnswer ma, Boolean boolValue) {
        if ("none".equals(ma.getAnswerType()) && ma.getMeasure().getMeasureType().getMeasureTypeId() == 4 && ma.getMeasure().getParent() == null) {

            if (boolValue) { // user clicked on 'none'
                return String.valueOf(0);
            } else { // user either clicked on the TableQuestion's Variable Name ***OR*** user totally ignored by not clicking at all
                // if user actually clicked on the Question with an intention to respond at least one table question than VeteranAssessment's
                // list of Survey Measure Response must have those Questions-----if not than User bypassed this question and we return null
                if (findChildMeasures(ma.getMeasure(), va)) {
                    return String.valueOf(1);
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    /**
     * this will simply find any child measures inside the list of SMR of this asessment
     *
     * @param measure
     * @param va
     * @return
     */
    private boolean findChildMeasures(Measure parentMeasure, VeteranAssessment va) {
        for (SurveyMeasureResponse smr : va.getSurveyMeasureResponseList()) {
            final Measure parent = smr.getMeasureAnswer().getMeasure().getParent();
            if (parent!=null && parent.getMeasureId().equals(parentMeasure.getMeasureId())) {
                return true;
            }
        }
        return false;
    }
}

@Component("smrExportOtherName")
class ExportOtherName implements DataExtractor {
    @Override
    public Map<String, String> apply(SurveyMeasureResponse smr, SurveyResponsesHelper srh) {
        MeasureAnswer ma = smr.getMeasureAnswer();

        // data export column we could be interested in
        String xportOtherName = srh.buildExportName(smr, ma.getOtherExportName());

        String otherValue = smr.getOtherValue();

        String exportableResponse = null;
        if ("other".equals(ma.getAnswerType()) && otherValue != null && !otherValue.trim().isEmpty()) {
            exportableResponse = otherValue;
        }

        if (exportableResponse != null) {

            // this is not possible that answer is of 'other' and user has also entered data for other but there is no export name assigned for other export name
            // but if this *does* happen than we will check the name of exportName and if hope that answer exportName ends with other
            if (Strings.isNullOrEmpty(xportOtherName) && Strings.nullToEmpty(ma.getIdentifyingText()).endsWith("other")) {
                xportOtherName = ma.getIdentifyingText();
            }

            Map<String, String> m = new HashMap<String, String>();
            m.put("exportName", xportOtherName);
            m.put("exportableResponse", exportableResponse);
            return m;
        } else {
            return null;
        }
    }
}
