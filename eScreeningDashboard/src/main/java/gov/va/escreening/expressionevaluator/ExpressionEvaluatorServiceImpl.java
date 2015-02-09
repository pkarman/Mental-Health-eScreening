package gov.va.escreening.expressionevaluator;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.AssessmentVariableType;
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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionEvaluatorServiceImpl implements ExpressionEvaluatorService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(type = AssessmentVariableRepository.class)
    AssessmentVariableRepository avr;

    Pattern formulaTokenPattern = Pattern.compile("\\[(.*?)\\]");
    Pattern formulaRefPattern = Pattern.compile("[$](.*?)[$]");

    @Override
    @Transactional(readOnly = true)
    public Map extractInputsRecursively(String filteredExpTemplate) {
        Map<String, Object> verifiedIds = verifyExpressionTemplate(filteredExpTemplate.replaceAll("[$]", ""), AvMapTypeEnum.ID2NAME);
        if (verifiedIds.get(ExpressionEvaluatorService.key.status.name()).equals(ExpressionEvaluatorService.key.success.name())) {
            List<Map<String, Object>> formulaTokens = (List<Map<String, Object>>) verifiedIds.get(ExpressionEvaluatorService.key.verifiedIds.name());

            Set<Map<String, Object>> recursiveFormulaTokens = Sets.newLinkedHashSet();
            for (Map<String, Object> token : formulaTokens) {
                List<Map<String, Object>> recuriveInputs = Lists.newArrayList();
                extractAllInputs((Integer) token.get("id"), recuriveInputs);
                recursiveFormulaTokens.addAll(recuriveInputs);
            }
            verifiedIds.put(ExpressionEvaluatorService.key.verifiedIds.name(), Lists.newArrayList(recursiveFormulaTokens));
        }
        return verifiedIds;
    }

    @Override
    public String evaluateFormula(Map<String, Object> tgtFormula) {

        List<Map<String, Object>> operands = (List<Map<String, Object>>) tgtFormula.get("f2t");
        String template = (String) tgtFormula.get("template");

        Map<Integer, String> formulaOperands = Maps.newLinkedHashMap();
        for (Map<String, Object> operand : operands) {
            Integer operandId = (Integer) operand.get("id");
            Object operandValue = operand.get("value");
            if (operandValue==null){
                throw new IllegalStateException(String.format("There is no value provided for %s with an id of %s", operand.get("name"), operand.get("id")));
            }
            formulaOperands.put(operandId, (String)operandValue);
        }

        try {
            return evaluateFormula(createFormulaDto(template, formulaOperands));
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

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
            answer = evaluateFormula(workingTemplate);
        }

        return answer;
    }

    @Override
    public String evaluateFormula(String formulaAsStr) {
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

    @Override
    public Map<String, Object> verifyExpressionTemplate(String expressionTemplate, AvMapTypeEnum avMap) {
        Map<String, Object> m = Maps.newHashMap();
        try {
            List<Map<String, Object>> verifiedTokens = parseFormula(expressionTemplate, avMap);
            m.put(key.verifiedIds.name(), verifiedTokens);
            m.put(key.status.name(), key.success.name());
            return m;
        } catch (RuntimeException re) {
            m.put(key.status.name(), key.failed.name());
            m.put(key.reason.name(), Throwables.getRootCause(re).getMessage());
            return m;
        }
    }

    @Override
    @Transactional
    public Integer updateFormula(Map<String, Object> tgtFormula) {

        Integer avId = tgtFormula.get("avId") == null ? null : Integer.parseInt(tgtFormula.get("avId").toString());
        String avName = (String) tgtFormula.get("name");
        String avDesc = (String) tgtFormula.get("description");
        String avTemplate = (String) tgtFormula.get("template");


        verifyExpressionTemplate(avTemplate, AvMapTypeEnum.ID2NAME);

        AssessmentVariable av = avId == null ? new AssessmentVariable() : findAvById(avId);

        List<AssessmentVarChildren> children = Lists.newArrayList();
        List<Map<String, Object>> tokens = (List<Map<String, Object>>) parseFormula(avTemplate.replaceAll("[$]", ""), AvMapTypeEnum.IDONLY);

        for (Map<String, Object> formulaToken : tokens) {
            AssessmentVarChildren avc = new AssessmentVarChildren();
            avc.setVariableParent(av);
            avc.setVariableChild(findAvById((Integer) formulaToken.get("id")));
            children.add(avc);
        }
        av.setFormulaTemplate(avTemplate);
        av.setDisplayName(avName);
        av.setDescription(avDesc);
        av.setAssessmentVariableTypeId(new AssessmentVariableType(AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA));
        av.setAssessmentVarChildrenList(children);

        AssessmentVariable savedAv = avr.update(av);
        return savedAv.getAssessmentVariableId();
    }

    private void extractAllInputs(Integer avId, List<Map<String, Object>> avIdsMap) {

        AssessmentVariable av = avr.findOne(avId);
        if (av.getAssessmentVariableTypeId().getAssessmentVariableTypeId() == AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA) {
            for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
                extractAllInputs(avc.getVariableChild().getAssessmentVariableId(), avIdsMap);
            }
        } else {
            avIdsMap.add(av.getAsMap());
        }
    }

    private FormulaDto createFormulaDto(String expressionTemplate, Map<Integer, String> avDataMap) {
        //first step is to verify this template
        String filteredExpTemplate = expressionTemplate.replaceAll("[$]", "");
        Map<String, Object> verifiedMap = verifyExpressionTemplate(filteredExpTemplate, AvMapTypeEnum.ID2NAME);
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

    private void extractDataFromRefFormulas(Map<String, Object> verifiedMap, Map<Integer, String> childFormulaMap) {
        List<Map<String, Object>> formulaTokens = (List<Map<String, Object>>) verifiedMap.get(key.verifiedIds.name());
        for (Map<String, Object> formulaToken : formulaTokens) {
            Integer avId = (Integer) formulaToken.get("id");
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
            List<Map<String, Object>> avId2NamesMap = parseFormula(ft, AvMapTypeEnum.ID2NAME);
            String formulaWithDisplayNames = getFormulaWithDisplayName(avId2NamesMap, ft);
            fh.handleFormula(av, formulaWithDisplayNames);
        }
    }

    private List<Map<String, Object>> parseFormula(String formulaTemplate, AvMapTypeEnum type) {
        String ft = formulaTemplate;
        Matcher m = formulaTokenPattern.matcher(ft);
        List<Map<String, Object>> tokens = Lists.newArrayList();
        while (m.find()) {
            String token = m.group(1);

            if (type == AvMapTypeEnum.NAME2ID) {
                parseNames(tokens, token);
            } else if (type == AvMapTypeEnum.ID2NAME) {
                parseIds(tokens, Integer.parseInt(token));
            } else if (type == AvMapTypeEnum.IDONLY) {
                parseIdsOnly(tokens, Integer.parseInt(token));
            }
        }
        return tokens;
    }

    private void parseIdsOnly(List<Map<String, Object>> tokens, int avId) {
        AssessmentVariable av = findAvById(avId);
        if (av != null) {
            tokens.add(av.getAsMap());
        } else {
            throw new IllegalStateException(String.format("No Assessment Variable found with an assessment var id of %s", tokens));
        }
    }

    private void parseIds(List<Map<String, Object>> tokens, Integer token) {
        AssessmentVariable av = findAvById(token);
        if (av != null) {
            tokens.add(av.getAsMap());
        } else {
            throw new IllegalStateException(String.format("No Assessment Variable found with an assessment var id of %s", token));
        }
    }

    private void parseNames(List<Map<String, Object>> tokens, String token) {
        Matcher matcher = formulaRefPattern.matcher(token);
        if (matcher.find()) {
            token = matcher.group(1);
        }
        AssessmentVariable av = avr.findOneByDisplayName(token);
        if (av != null) {
            tokens.add(av.getAsMap());
        } else {
            throw new IllegalStateException(String.format("No Assessment Variable found with a display name of %s", token));
        }
    }

    private String getFormulaWithDisplayName(List<Map<String, Object>> tokens, String formulaTemplate) {
        for (Map<String, Object> m : tokens) {
            formulaTemplate = formulaTemplate.replaceAll(m.get("id").toString(), m.get("name").toString());
        }
        return formulaTemplate;
    }
}