package gov.va.escreening.expressionevaluator;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.exception.ReferencedFormulaMissingException;
import gov.va.escreening.exception.ReferencedVariableMissingException;
import gov.va.escreening.formula.AvMapTypeEnum;
import gov.va.escreening.formula.FormulaHandler;
import gov.va.escreening.repository.AssessmentVariableRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionEvaluatorServiceImpl implements ExpressionEvaluatorService {

    private static final Logger logger = LoggerFactory.getLogger(ExpressionEvaluatorServiceImpl.class);

    @Resource(type = AssessmentVariableRepository.class)
    AssessmentVariableRepository avr;

    Pattern formulaTokenPattern = Pattern.compile("\\[(.*?)\\]");
    Pattern formulaRefPattern = Pattern.compile("[$](.*?)[$]");

    @Override
    public String evaluateFormula(FormulaDto formulaDto) throws NoSuchMethodException, SecurityException {
        String workingTemplate = formulaDto.getExpressionTemplate();
        String originalFormulaTemplate = workingTemplate;
        workingTemplate = mergeChildFormulasIntoTemplate(workingTemplate, formulaDto.getChildFormulaMap(), originalFormulaTemplate);
        workingTemplate = mergeVariablesToTemplate(workingTemplate, formulaDto.getVariableValueMap(), originalFormulaTemplate);


        String answer = null;

        //Check to see if this requires some custom logic to evaulate the expression
        if (workingTemplate.contains(CustomCalculations.CALCULATE_AGE)) {
            //Register a static method as CUSTOM function then invoke it.
            StandardEvaluationContext stdContext = new StandardEvaluationContext();
            stdContext.registerFunction("calculateAge", CustomCalculations.class.getDeclaredMethod("calculateAge", new Class[]{String.class}));
            ExpressionParser parser = new SpelExpressionParser();
            answer = parser.parseExpression(workingTemplate).getValue(stdContext, String.class);
        } else {
            answer = evaluateFormulaTemplate(workingTemplate);
        }

        return answer;
    }

    @Override
    public String evaluateFormulaTemplate(String formulaAsStr) {
        ExpressionParser parser = new SpelExpressionParser();
        String testResult = parser.parseExpression(formulaAsStr).getValue(String.class);
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("The result of %s is %s", formulaAsStr, testResult));
        }
        return testResult;
    }

    private String mergeChildFormulasIntoTemplate(String workingTemplate, Map<Integer, String> childFormulas, String originalFormulaTemplate) {
        if (childFormulas == null || childFormulas.size() == 0)
            return workingTemplate;

        int safetyCounter = 0;
        do {
            Iterator<Integer> keysIterator = childFormulas.keySet().iterator();
            while (keysIterator.hasNext()) {
                Integer key = keysIterator.next();
                String childFormula = childFormulas.get(key);
                String variablePattern = String.format("[$%s$]", key);
                workingTemplate = workingTemplate.replace(variablePattern, String.format(" ( %s ) ", childFormula));
            }

            safetyCounter = safetyCounter + 1;
            if (safetyCounter > 10)
                throw new ReferencedFormulaMissingException
                        (String.format("A referenced formula was missing. The original template was: '%s'. The merged template was: '%s'.",
                                originalFormulaTemplate, workingTemplate));

        } while (workingTemplate.contains("$"));

        return workingTemplate;
    }

    private String mergeVariablesToTemplate(String workingTemplate, Map<Integer, String> variableValueMap, String originalFormulaTemplate) {
        if (variableValueMap == null || variableValueMap.size() == 0)
            return workingTemplate;

        Iterator<Integer> keysIterator = variableValueMap.keySet().iterator();
        while (keysIterator.hasNext()) {
            Integer key = keysIterator.next();
            String value = variableValueMap.get(key);
            String variablePattern = String.format("[%s]", key);
            workingTemplate = workingTemplate.replace(variablePattern, value);
        }

        if (workingTemplate.contains("[") || workingTemplate.contains("]"))
            throw new ReferencedVariableMissingException
                    (String.format("A referenced variable was missing. The original template was: '%s'. The merged template was: '%s'.",
                            originalFormulaTemplate, workingTemplate));

        return workingTemplate;
    }


    /**
     * method to verify that the expression template is using assessement variable which can actually produce some mathematical result when put into action
     *
     * @param expressionTemplate expression template in the form of
     *                           <ol>
     *                           <li>([demo_weight] / ( [$demo_totalheightin$] * [$demo_totalheightin$] ) ) * 703</li>
     *                           <li>([es1_listen] + [es2_talk] + [es3_feel] + [es4_bad])</li>
     *                           <li>([health16_hearing] + [health17_tinnitus])</li>
     *                           <li>([health21_wghtgain] + [health22_wghtloss])</li>
     *                           </ol>
     * @return returns either a map with success message, along with the avIds of each displayNames
     */
    @Override
    public Map<String, String> verifyExpressionTemplate(String expressionTemplate, AvMapTypeEnum avMap) {
        try {
            Map<String, String> verifiedTokens = prepareMap(expressionTemplate, avMap);
            verifiedTokens.put("status", "verification_success");
            return verifiedTokens;
        } catch (RuntimeException re) {
            Map<String, String> error = Maps.newHashMap();
            error.put("status", "verification_failed");
            error.put("reason", Throwables.getRootCause(re).getMessage());
            return error;
        }
    }

    @Override
    @Transactional
    public void updateFormula(Integer avId, String formula) {
        verifyExpressionTemplate(formula, AvMapTypeEnum.ID2NAME);

        AssessmentVariable av = findAvById(avId);

        List<AssessmentVarChildren> children = Lists.newArrayList();
        Map<Integer, String> m = (Map<Integer, String>) prepareMap(formula.replaceAll("[$]",""), AvMapTypeEnum.IDONLY);

        for (Integer childId : m.keySet()) {
            AssessmentVarChildren avc = new AssessmentVarChildren();
            avc.setVariableParent(av);
            avc.setVariableChild(findAvById(childId));
            children.add(avc);
        }
        av.setFormulaTemplate(formula);
        av.setAssessmentVarChildrenList(children);

        avr.update(av);
    }

    @Override
    @Transactional(readOnly = true)
    public void extractAllInputs(Integer avId, List<Integer> avIdList) {

        AssessmentVariable av = avr.findOne(avId);
        if (av.getAssessmentVariableTypeId().getAssessmentVariableTypeId() == AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA) {
            for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
                extractAllInputs(avc.getVariableChild().getAssessmentVariableId(), avIdList);
            }
        } else {
            avIdList.add(av.getAssessmentVariableId());
        }
    }

    @Override
    public FormulaDto createFormulaDto(String expressionTemplate, Map<Integer, String> avDataMap) {
        //first step is to verify this template
        String filteredExpTemplate = expressionTemplate.replaceAll("[$]", "");
        Map<String, String> verifiedMap = verifyExpressionTemplate(filteredExpTemplate, AvMapTypeEnum.ID2NAME);
        if (verifiedMap.get("status").equals("verification_failed")) {
            return null;
        }

        FormulaDto formulaDto = new FormulaDto();

        formulaDto.setVariableValueMap(avDataMap);
        formulaDto.setExpressionTemplate(expressionTemplate);

        Map<Integer, String> refFormulaMap = Maps.newHashMap();
        extractDataFromRefFormulas(verifiedMap, refFormulaMap);

        formulaDto.setChildFormulaMap(refFormulaMap);

        return formulaDto;

    }

    private void extractDataFromRefFormulas(Map<String, String> verifiedMap, Map<Integer, String> childFormulaMap) {
        for (Object data : verifiedMap.keySet()) {
            Integer avId = null;
            if (data instanceof Integer) {
                avId = (Integer) data;
            } else {
                continue;
            }
            AssessmentVariable av = findAvById(avId);
            if (av.getAssessmentVariableTypeId().getAssessmentVariableTypeId() == AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA) {
                childFormulaMap.put(av.getAssessmentVariableId(), av.getFormulaTemplate());
            }
        }
    }

    @Override
    public AssessmentVariable findAvById(Integer avId) {
        return avr.findOne(avId);
    }

    @Override
    public void readAllFormulas(FormulaHandler fh) {
        List<AssessmentVariable> allFormulas = avr.findAllFormulae();

        for (AssessmentVariable av : allFormulas) {
            String ft = av.getFormulaTemplate().replaceAll("[$]", "");
            Map<Integer, String> avId2NamesMap = prepareMap(ft, AvMapTypeEnum.ID2NAME);
            String formulaWithDisplayNames = getFormulaWithDisplayName(avId2NamesMap, ft);
            fh.handleFormula(av, formulaWithDisplayNames);
        }
    }

    private Map prepareMap(String formulaTemplate, AvMapTypeEnum type) {
        String ft = formulaTemplate;
        Matcher m = formulaTokenPattern.matcher(ft);
        Map map = Maps.newHashMap();
        while (m.find()) {
            String token = m.group(1);

            if (type == AvMapTypeEnum.NAME2ID) {
                mapName2Id(map, token);
            } else if (type == AvMapTypeEnum.ID2NAME) {
                mapId2Name(map, Integer.parseInt(token));
            } else if (type == AvMapTypeEnum.IDONLY) {
                mapId2Nothing(map, Integer.parseInt(token));
            }
        }
        return map;
    }

    private void mapId2Nothing(Map map, int avId) {
        map.put(avId, "");
    }

    private void mapId2Name(Map<Integer, String> map, Integer token) {
        AssessmentVariable av = findAvById(token);
        if (av != null) {
            map.put(av.getAssessmentVariableId(), getDisplayNameFor(av));
        } else {
            throw new IllegalStateException(String.format("No Assessment Variable found with an assessment var id of %s", token));
        }
    }

    private void mapName2Id(Map<String, String> map, String token) {
        Matcher m = formulaRefPattern.matcher(token);
        if (m.find()) {
            token = m.group(1);
        }
        AssessmentVariable av = avr.findOneByDisplayName(token);
        if (av != null) {
            map.put(getDisplayNameFor(av), String.valueOf(av.getAssessmentVariableId()));
        } else {
            throw new IllegalStateException(String.format("No Assessment Variable found with a display name of %s", token));
        }
    }

    private String getFormulaWithDisplayName(Map<Integer, String> avDisplayNames, String formulaTemplate) {
        for (Map.Entry<Integer, String> entry : avDisplayNames.entrySet()) {
            formulaTemplate = formulaTemplate.replaceAll(String.valueOf(entry.getKey()), entry.getValue());
        }
        return formulaTemplate;
    }

    private String getDisplayNameFor(AssessmentVariable av) {
        boolean formulaAv = av.getFormulaTemplate() != null;
        String displayName = av.getDisplayName();

        // if this AV is a formula itself (if called by reference)
        String formulaIdentifier = formulaAv ? "\\$" : "";
        return String.format("%s%s%s", formulaIdentifier, displayName, formulaIdentifier);
    }
}