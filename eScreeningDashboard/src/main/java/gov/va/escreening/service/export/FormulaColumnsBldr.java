package gov.va.escreening.service.export;

import com.google.common.collect.Lists;
import gov.va.escreening.entity.*;
import gov.va.escreening.service.AssessmentVariableService;
import gov.va.escreening.service.AvBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by munnoo on 2/15/15.
 */
public class FormulaColumnsBldr implements AvBuilder<Set<List<String>>> {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Set<List<String>> surveyFormulae;
    private final Set<String> avUsed;

    private final AssessmentVariableService avs;

    public FormulaColumnsBldr(Set<List<String>> surveyFormulae, Set<String> avUsed, AssessmentVariableService avs) {
        this.surveyFormulae = surveyFormulae;
        this.avUsed = avUsed;
        this.avs = avs;
    }

    @Override
    public void buildFromMeasureAnswer(
            AssessmentVariable avWithFormula,
            AssessmentVarChildren avc, Measure m, MeasureAnswer ma) {
        addSurveyFormula(avWithFormula, buildXportNameFromMeasureAnswer(avWithFormula));
    }

    private String extractFormula(AssessmentVariable av,
                                  ExportNameExtractor extractor) {
        String dbFormula = av.getFormulaTemplate();
        String displayableFormula = dbFormula;
        for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
            String exportName = extractor.extractExportName(avc);
            String toBeReplaced = String.valueOf(avc.getVariableChild().getAssessmentVariableId());
            displayableFormula = displayableFormula.replaceAll(toBeReplaced, exportName);
        }
        displayableFormula = displayableFormula.replaceAll("([$])|(\\?\\s*[1]\\s*[:]\\s*[0])", "").replaceAll("(>\\s*=\\s*[1])", " >= 1 then 1 else 0");

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("Formula=%s==>DisplayableFormula=%s", dbFormula, displayableFormula));
        }
        return displayableFormula;
    }

    private List<String> buildXportNameFromMeasureAnswer(AssessmentVariable av) {
        String formula = extractFormula(av, new MeasureAnswerNameExtractor());
        return formulaPlusExportName(formula, av);
    }

    private List<String> buildXportNameFromMeasure(AssessmentVariable av) {
        String formula = extractFormula(av, new MeasureNameExtractor());
        return formulaPlusExportName(formula, av);
    }

    private List<String> formulaPlusExportName(String formula, AssessmentVariable av) {
        List<String> formulaDetails = Lists.newArrayList(formula);
        formulaDetails.addAll(av.getAsList());
        return formulaDetails;
    }

    @Override
    public void buildFromMeasure(AssessmentVariable avWithFormula,
                                 AssessmentVarChildren avc, Measure m) {
        addSurveyFormula(avWithFormula, buildXportNameFromMeasure(avWithFormula));
    }

    private void addSurveyFormula(AssessmentVariable avWithFormula, List<String> formulaDetails) {
        // each formula is unique and can only be used only once across all surveys.
        // If it is already used, then do not try to add it again
        if (avUsed.add(avWithFormula.getDisplayName())) {
            surveyFormulae.add(formulaDetails);
        }
    }

    @Override
    public Set<List<String>> getResult() {
        return surveyFormulae;
    }

    @Override
    public void buildFormula(Survey survey, AssessmentVariable av,
                             Collection<Measure> smList,
                             Collection<AssessmentVariable> avList,
                             boolean filterMeasures) {

        for (Measure m : smList) {
            for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
                AssessmentVariable av1 = avc.getVariableChild();
                if (avs.compareMeasure(av1, m)) {
                    buildFromMeasure(av, avc, m);
                } else if (avs.compareMeasureAnswer(av1, m)) {
                    buildFromMeasureAnswer(av, avc, m, av1.getMeasureAnswer());
                }
            }
            if (!m.getChildren().isEmpty()) {
                avs.filterBySurvey(survey, this, m.getChildren(), avList, filterMeasures, false);
            }
        }
    }

    interface ExportNameExtractor {
        String extractExportName(AssessmentVarChildren avc);
    }

    class MeasureAnswerNameExtractor implements ExportNameExtractor {
        public String extractExportName(AssessmentVarChildren avc) {
            MeasureAnswer ma = avc.getVariableChild().getMeasureAnswer();
            String exportName = ma != null ? ma.getExportName() : avc.getVariableChild().getDisplayName();
            return exportName;
        }
    }

    class MeasureNameExtractor implements ExportNameExtractor {
        public String extractExportName(AssessmentVarChildren avc) {
            Measure m = avc.getVariableChild().getMeasure();
            String exportName = m != null ? m.getMeasureAnswerList().iterator().next().getExportName() : avc.getVariableChild().getDisplayName();
            return exportName;
        }
    }
}
