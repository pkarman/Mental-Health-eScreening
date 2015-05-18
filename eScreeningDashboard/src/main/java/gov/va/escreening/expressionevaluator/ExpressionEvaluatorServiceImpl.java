package gov.va.escreening.expressionevaluator;

import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.AssessmentFormula;
import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.AssessmentVariableType;
import gov.va.escreening.exception.ReferencedVariableMissingException;
import gov.va.escreening.formula.AvMapTypeEnum;
import gov.va.escreening.formula.FormulaHandler;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.service.AssessmentVariableService;
import gov.va.escreening.variableresolver.AssessmentVariableDto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.*;

public class ExpressionEvaluatorServiceImpl implements
        ExpressionEvaluatorService {

    private static final Logger logger = LoggerFactory
            .getLogger(ExpressionEvaluatorServiceImpl.class);

    private static final Pattern formulaTokenPattern = Pattern
            .compile("\\[(.*?)\\]");
    private static final Pattern formulaRefPattern = Pattern
            .compile("[$](.*?)[$]");

    private final AssessmentVariableRepository avr;
    private final AssessmentVariableService avs;
    private final StandardEvaluationContext stdContext;
    private final ExpressionParser parser;

    @Autowired
    public ExpressionEvaluatorServiceImpl(AssessmentVariableRepository avr, AssessmentVariableService avs)
            throws NoSuchMethodException, SecurityException {
        this.avr = checkNotNull(avr);
        this.avs = checkNotNull(avs);
        stdContext = new StandardEvaluationContext();
        parser = new SpelExpressionParser();
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map extractInputsRecursively(String filteredExpTemplate) {
        Map<String, Object> verifiedIds = verifyExpressionTemplate(
                filteredExpTemplate.replaceAll("[$]", ""),
                AvMapTypeEnum.ID2NAME);
        if (verifiedIds.get(ExpressionEvaluatorService.key.status.name())
                .equals(ExpressionEvaluatorService.key.success.name())) {
            List<Map<String, Object>> formulaTokens = (List<Map<String, Object>>) verifiedIds
                    .get(ExpressionEvaluatorService.key.verifiedIds.name());

            Set<Map<String, Object>> recursiveFormulaTokens = Sets
                    .newLinkedHashSet();
            for (Map<String, Object> token : formulaTokens) {
                List<Map<String, Object>> recuriveInputs = Lists.newArrayList();
                extractAllInputs((Integer) token.get("id"), recuriveInputs);
                recursiveFormulaTokens.addAll(recuriveInputs);
            }
            verifiedIds.put(ExpressionEvaluatorService.key.verifiedIds.name(),
                    Lists.newArrayList(recursiveFormulaTokens));
        }
        return verifiedIds;
    }

    @Override
    public String evaluateFormula(Map<String, Object> tgtFormula) {
        // operands means variables. The variable is basically the assessment
        // variable id + its value user has provided
        List<Map<String, Object>> operands = (List<Map<String, Object>>) tgtFormula
                .get("verifiedIds");
        // formula which is a combination of variables and operators
        List<String> tokens = (List<String>) tgtFormula.get("tokens");

        Map<Integer, String> formulaOperands = Maps.newLinkedHashMap();
        if (operands != null) {
            for (Map<String, Object> operand : operands) {
                Integer operandId = (Integer) operand.get("id");
                Object operandValue = operand.get("value");
                if (operandValue == null) {
                    throw new IllegalStateException(
                            String.format(
                                    "There is no value provided for %s with an id of %s",
                                    operand.get("name"), operand.get("id")));
                }
                formulaOperands.put(operandId, (String) operandValue);
            }
        }

        try {
            String formula = buildFormulaFromMin(tokens);
            return evaluateFormula(createFormulaDto(formula, formulaOperands));
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String evaluateFormula(FormulaDto formulaDto)
            throws NoSuchMethodException, SecurityException {
        String workingTemplate = formulaDto.getExpressionTemplate();
        logger.debug("Evaluating unresolved formula: {}", workingTemplate);
        
        String originalFormulaTemplate = workingTemplate;
//        workingTemplate = mergeChildFormulasIntoTemplate(workingTemplate,
//                formulaDto.getChildFormulaMap(), originalFormulaTemplate);
        workingTemplate = mergeVariablesToTemplate(workingTemplate,
                formulaDto.getVariableValueMap(), originalFormulaTemplate);
        
        String answer = null;

        try {
            answer = evaluateFormula(workingTemplate, formulaDto.getAvMap());
        } catch (Exception spe) {
            logger.error(Throwables.getRootCause(spe).getMessage());
            throw new IllegalStateException("error:"
                    + Throwables.getRootCause(spe).getMessage());
        }

        return answer;
    }

    @Override
    public String evaluateFormula(String formulaAsStr,
            Map<Integer, AssessmentVariableDto> variableMap) {

        logger.debug("Evaluating resolved formula: {}", formulaAsStr);
        ExpressionExtentionUtil extentionUtil = new ExpressionExtentionUtil().setVariableMap(variableMap);
        
        String testResult = parser.parseExpression(formulaAsStr).getValue(
                stdContext, extentionUtil, String.class);

        logger.debug("The result of {} is: {}", formulaAsStr, testResult);

        return testResult;
    }

//    private String mergeChildFormulasIntoTemplate(String workingTemplate,
//            Map<Integer, String> childFormulas, String originalFormulaTemplate) {
//        if (childFormulas == null || childFormulas.size() == 0)
//            return workingTemplate;
//
//        int safetyCounter = 0;
//        do {
//            Iterator<Integer> keysIterator = childFormulas.keySet().iterator();
//            while (keysIterator.hasNext()) {
//                Integer key = keysIterator.next();
//                String childFormula = childFormulas.get(key);
//                String variablePattern = String.format("[$%s$]", key);
//                workingTemplate = workingTemplate.replace(variablePattern,
//                        String.format(" ( %s ) ", childFormula));
//            }
//
//            safetyCounter = safetyCounter + 1;
//            if (safetyCounter > 10)
//                throw new ReferencedFormulaMissingException(
//                        String.format(
//                                "A referenced formula was missing. The original template was: '%s'. The merged template was: '%s'.",
//                                originalFormulaTemplate, workingTemplate));
//
//        } while (workingTemplate.contains("$"));
//
//        return workingTemplate;
//    }
    
    private String mergeVariablesToTemplate(String workingTemplate,
            Map<Integer, String> variableValueMap,
            String originalFormulaTemplate) {
        
        if (variableValueMap != null && !variableValueMap.isEmpty()){
            Iterator<Integer> keysIterator = variableValueMap.keySet().iterator();
            while (keysIterator.hasNext()) {
                Integer key = keysIterator.next();
                String value = variableValueMap.get(key);
                String variablePattern = String.format("\\[\\$*%s\\$*\\]", key);
                workingTemplate = workingTemplate.replaceAll(variablePattern, value);
            }
        }
        
        if (workingTemplate.contains("[") || workingTemplate.contains("]"))
            throw new ReferencedVariableMissingException(
                    String.format(
                            "A referenced variable was missing. The original template was: '%s'.\nThe partially merged template was: '%s'.",
                            originalFormulaTemplate, workingTemplate));

        return workingTemplate;
    }

    @Override
    public Map<String, Object> verifyExpressionTemplate(
            String expressionTemplate, AvMapTypeEnum avMap) {
        Map<String, Object> m = Maps.newHashMap();
        try {
            List<Map<String, Object>> verifiedTokens = parseFormula(
                    expressionTemplate, avMap);
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

        Map<String, Object> avMap = (Map<String, Object>) tgtFormula.get("av");

        Integer avId = (Integer) avMap.get("id");
        String avName = (String) avMap.get("name");
        String avDesc = (String) avMap.get("description");

        List<String> tokens = (List<String>) tgtFormula.get("tokens");
        String avTemplate = buildFormulaFromMin(tokens);
        verifyExpressionTemplate(avTemplate.replaceAll("[$]", ""),
                AvMapTypeEnum.ID2NAME);

        AssessmentVariable av = avId == null ? new AssessmentVariable()
                : findAvById(avId);

        List<Map<String, Object>> parsedFormula = (List<Map<String, Object>>) parseFormula(
                avTemplate.replaceAll("[$]", ""), AvMapTypeEnum.IDONLY);

        Set<Integer> childAvIds = Sets.newHashSetWithExpectedSize(parsedFormula.size());
        //collect the variable IDs we need to include
        for (Map<String, Object> formulaToken : parsedFormula) {
            childAvIds.add((Integer) formulaToken.get("id"));
        }
        
        Map<Integer, AssessmentVarChildren> currentChildMap;
        Map<Integer, AssessmentVariable> varMap;
        if(av.getAssessmentVarChildrenList() == null){
            varMap = Collections.emptyMap();
            currentChildMap = Collections.emptyMap();
            av.setAssessmentVarChildrenList(new ArrayList<AssessmentVarChildren>());
        }
        else{
            varMap = Maps.newHashMapWithExpectedSize(av.getAssessmentVarChildrenList().size());
            currentChildMap = Maps.newHashMapWithExpectedSize(av.getAssessmentVarChildrenList().size());
            for(AssessmentVarChildren child : av.getAssessmentVarChildrenList()){
                AssessmentVariable childAv = child.getVariableChild();
                if(childAvIds.contains(childAv.getAssessmentVariableId())){
                    varMap.put(childAv.getAssessmentVariableId(), childAv);
                    currentChildMap.put(childAv.getAssessmentVariableId(), child);
                }
            }
            av.getAssessmentVarChildrenList().clear();
        }
        
        //collect the variable IDs we need to include
        for (AssessmentVariable variable : avs.collectAssociatedVars(childAvIds, varMap)) {
            AssessmentVarChildren avc = currentChildMap.get(variable.getAssessmentVariableId());
            if(avc == null){
                avc = new AssessmentVarChildren();
                avc.setVariableParent(av);
                avc.setVariableChild(variable);
            }
            
            av.getAssessmentVarChildrenList().add(avc);
        }
        
        av.setFormulaTemplate(avTemplate);
        av.setDisplayName(avName.trim());
        av.setDescription((avDesc == null || avDesc.trim().isEmpty()) ? "No Description provided"
                : avDesc.trim());
        av.setAssessmentVariableTypeId(new AssessmentVariableType(
                AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA));

        av.attachFormulaTokens(tokens);

        AssessmentVariable savedAv = avr.update(av);

        //update parent formulas
        avs.updateParentFormulas(savedAv);
        
        return savedAv.getAssessmentVariableId();
    }

    @Override
    public String buildFormulaFromMin(List<String> idList) {
        List<String> tokens = Lists.newArrayList();
        for (Object idObj : idList) {
            tokens.add(buildFromMin(idObj.toString()));
        }

        String newlyBuiltFormula = Joiner.on("").join(tokens);
//        if (logger.isDebugEnabled()) {
//            logger.debug(String.format("Formula Str:%s == Src tokens:%s",
//                    newlyBuiltFormula, idList));
//        }
        return newlyBuiltFormula;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> fetchFormulaFromDbById(Integer formulaId) {
        AssessmentVariable av = findAvById(formulaId);
        final Map<String, Object> asFormulaVar = av.getAsFormulaVar();

        asFormulaVar.put("selectedTokens", buildSelectedTokensFor(av));
        return asFormulaVar;
    }

    private List<Map<String, Object>> buildSelectedTokensFor(
            AssessmentVariable av) {
        List<Map<String, Object>> selectedTokens = Lists.newArrayList();
        List<AssessmentFormula> formulaTokens = av.getAssessmentFormulas();
        if (formulaTokens != null) {
            for (AssessmentFormula af : formulaTokens) {
                if (!af.getUserDefined()) {
                    final AssessmentVariable avById = findAvById(Integer
                            .parseInt(af.getFormulaToken()));
                    selectedTokens
                            .add(systemGenerated(avById.getAsFormulaVar()));
                } else {
                    selectedTokens.add(createUserDefined(af));
                }
            }
        }
        return selectedTokens;
    }

    private Map<String, Object> systemGenerated(Map<String, Object> asFormulaVar) {
        asFormulaVar.put("id", "f|" + asFormulaVar.get("id"));
        return asFormulaVar;
    }

    private Map<String, Object> createUserDefined(AssessmentFormula af) {
        Map<String, Object> selectedToken = Maps.newHashMap();
        selectedToken.put("id", "t|" + af.getFormulaToken());
        selectedToken.put("name", af.getFormulaToken());
        return selectedToken;
    }

    private String buildFromMin(String tokenId) {
        if (tokenId == null || tokenId.trim().isEmpty()) {
            return "";
        }
        // find if this is user defined or not
        boolean isUserDefined = "t".equals(tokenId.substring(0, 1));
        String onlyToken = tokenId.substring(2);

        if (isUserDefined) {
            return onlyToken;
        } else {
            int id = Integer.parseInt(onlyToken);
            AssessmentVariable av = avr.findOne(id);
            switch (av.getAssessmentVariableTypeId()
                    .getAssessmentVariableTypeId()) {
            case 1:
            case 2:
                return "[" + onlyToken + "]";
            case 4:
                return "[$" + onlyToken + "$]";

            case 5:
                Matcher m = formulaTokenPattern.matcher(av.getDescription());
                if (m.find()) {
                    return m.group(1);
                }
            default:
                return "";
            }
        }
    }

    private void extractAllInputs(Integer avId,
            List<Map<String, Object>> avIdsMap) {

        AssessmentVariable av = avr.findOne(avId);
        if (av.getAssessmentVariableTypeId().getAssessmentVariableTypeId() == AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA) {
            for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
                extractAllInputs(avc.getVariableChild()
                        .getAssessmentVariableId(), avIdsMap);
            }
        } else {
            avIdsMap.add(av.getAsMap());
        }
    }

    private FormulaDto createFormulaDto(String expressionTemplate,
            Map<Integer, String> avDataMap) {
        // first step is to verify this template
        String filteredExpTemplate = expressionTemplate.replaceAll("[$]", "");
        Map<String, Object> verifiedMap = verifyExpressionTemplate(
                filteredExpTemplate, AvMapTypeEnum.ID2NAME);
        if (verifiedMap.get(key.status.name()).equals(key.failed.name())) {
            throw new IllegalStateException(verifiedMap.get(key.reason.name())
                    .toString());
        }

        FormulaDto formulaDto = new FormulaDto();

        formulaDto.setVariableValueMap(avDataMap);
        formulaDto.setExpressionTemplate(expressionTemplate);

//        Map<Integer, String> refFormulaMap = Maps.newHashMap();
//        extractDataFromRefFormulas(verifiedMap, refFormulaMap);
//
//        formulaDto.setChildFormulaMap(refFormulaMap);

        return formulaDto;
    }

//    private void extractDataFromRefFormulas(Map<String, Object> verifiedMap,
//            Map<Integer, String> childFormulaMap) {
//        List<Map<String, Object>> formulaTokens = (List<Map<String, Object>>) verifiedMap
//                .get(key.verifiedIds.name());
//        for (Map<String, Object> formulaToken : formulaTokens) {
//            Integer avId = (Integer) formulaToken.get("id");
//            AssessmentVariable av = findAvById(avId);
//            if (av.getAssessmentVariableTypeId().getAssessmentVariableTypeId() == AssessmentConstants.ASSESSMENT_VARIABLE_TYPE_FORMULA) {
//                childFormulaMap.put(av.getAssessmentVariableId(),
//                        av.getFormulaTemplate());
//            }
//        }
//    }

    @Override
    public AssessmentVariable findAvById(Integer avId) {
        return avr.findOne(avId);
    }

    @Override
    public void readAllFormulas(FormulaHandler fh) {
        List<AssessmentVariable> allFormulas = avr.findAllFormulae();

        for (AssessmentVariable av : allFormulas) {
            String ft = av.getFormulaTemplate().replaceAll("[$]", "");
            List<Map<String, Object>> avId2NamesMap = parseFormula(ft,
                    AvMapTypeEnum.ID2NAME);
            String formulaWithDisplayNames = getFormulaWithDisplayName(
                    avId2NamesMap, ft);
            fh.handleFormula(av, formulaWithDisplayNames);
        }
    }

    private List<Map<String, Object>> parseFormula(String formulaTemplate,
            AvMapTypeEnum type) {
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
            throw new IllegalStateException(
                    String.format(
                            "No Assessment Variable found with an assessment var id of %s",
                            tokens));
        }
    }

    private void parseIds(List<Map<String, Object>> tokens, Integer token) {
        AssessmentVariable av = findAvById(token);
        if (av != null) {
            tokens.add(av.getAsMap());
        } else {
            throw new IllegalStateException(
                    String.format(
                            "No Assessment Variable found with an assessment var id of %s",
                            token));
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
            throw new IllegalStateException(String.format(
                    "No Assessment Variable found with a display name of %s",
                    token));
        }
    }

    private String getFormulaWithDisplayName(List<Map<String, Object>> tokens,
            String formulaTemplate) {
        for (Map<String, Object> m : tokens) {
            formulaTemplate = formulaTemplate.replaceAll(
                    m.get("id").toString(), m.get("name").toString());
        }
        return formulaTemplate;
    }
}