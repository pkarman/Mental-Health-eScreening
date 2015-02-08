package gov.va.escreening.service.export;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureValidation;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Validation;
import gov.va.escreening.repository.SurveyRepository;
import gov.va.escreening.repository.ValidationRepository;
import gov.va.escreening.service.AssessmentVariableService;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.bouncycastle.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService, MessageSourceAware {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MessageSource msgSrc;

    @Resource(name = "dataDictionaryHelper")
    DataDictionaryHelper ddh;

    @Resource(type = ValidationRepository.class)
    ValidationRepository vr;

    @Resource(type = SurveyRepository.class)
    SurveyRepository sr;

    @Resource(type = AssessmentVariableService.class)
    AssessmentVariableService avs;

    private Multimap<Integer, String> buildMeasureValidationMap() {

        List<Validation> validations = vr.findAll();
        /**
         * Validations for a free text measure (only) are defined in measure_validation table which relate a measure to
         * a validation. A measure can have more than one validation which is applied.
         *
         * The way validations work is there is a type id (taken from the validation table), combined with some value in
         * the measure_validation table. Each type only has one valid value in measure_validation table.
         *
         * For example minValue will have an entry in measure_validation.number_value to indicate the minimum allowable
         * value.
         *
         * the property this{@link #measureValidationsMap} will keep a map of measure id with a list of validation in
         * the form 'Min Value=1970, Max Value=2020, Exact Number=4' would mean that the measure answer is a date and it
         * should be between 1970 and 2020 and must contain century too
         * */
        Multimap<Integer, String> measureValidationsMap = LinkedHashMultimap.create();

        for (Validation v : validations) {
            for (MeasureValidation mv : v.getMeasureValidationList()) {
                measureValidationsMap.put(mv.getMeasure().getMeasureId(), buildMeasureValidation(mv));
            }
        }
        return measureValidationsMap;
    }

    private String buildMeasureValidation(MeasureValidation mv) {
        Validation v = mv.getValidation();
        String mvStr = null;
        switch (v.getDataType()) {
            case "string":
                mvStr = String.format("%s=%s", v.getDescription(), mv.getTextValue());
                break;
            case "number":
                mvStr = String.format("%s=%s", v.getDescription(), mv.getNumberValue());
                break;
            default:
                mvStr = "";
                break;
        }
        return mvStr;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Table<String, String, String>> createDataDictionary() {
        /**
         * <pre>
         *
         * 	pt#1: each survey (also called module) will have its own table (excel sheet)
         * 	pt#2: each table has rows
         * 	pt#3: each row has columns
         * 	pt#4: and each column has values
         *
         * 	each [survey] has a [Table]
         * 		each [Table] has bunch of rows
         * 	  		==> row column=value
         * 	  		==> row column=value
         * 	  		==> row column=value
         * </pre>
         *
         * Perfect data abstraction to capture the above model is to have Google Guava's Table Table <row, col name, col
         * value>
         */
        Map<String, Table<String, String, String>> dataDictionary = Maps.newTreeMap(new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });

        // partition all survey with its list of measures
        Multimap<Survey, Measure> surveyMeasuresMap = avs.buildSurveyMeasuresMap();

        // read all AssessmenetVariables having formulae
        Collection<AssessmentVariable> avList = avs.findAllFormulae();

        // read all measures of free text and its validations
        Multimap<Integer, String> ftMvMap = buildMeasureValidationMap();

        // bookkeeping set to verify that each and every assessment var of formula type has been utilized in creation of
        // the data dictionary
        Set<String> formulaeAvTouched = Sets.newLinkedHashSet();

        for (Survey s : surveyMeasuresMap.keySet()) {
            Table<String, String, String> sheet = buildSheetFor(s, surveyMeasuresMap.get(s), ftMvMap, avList, formulaeAvTouched);

            // bind the survey (or module with its sheet)
            dataDictionary.put(s.getName(), sheet);

            if (logger.isDebugEnabled()) {
                logger.debug(String.format("sheet data for Survey=%s =>> %s", s.getName(), sheet));
            }
        }

        if (logger.isDebugEnabled()) {
            Set<String> avUsedInDataDictionary = Sets.newHashSet(formulaeAvTouched);

            String refAssessmentVars = getRefAssessmentVars(avList);
            Set<String> avReference = Sets.newHashSet(Strings.split(refAssessmentVars, ','));

            Set<String> unusedAv = Sets.difference(avUsedInDataDictionary, avReference);
            logger.debug(String.format("AvSizeUsedInDD:%s==AvReferenceDD:%s==AvUnusedInDD:%s", avUsedInDataDictionary.size(), avReference.size(), unusedAv));
        }
        return dataDictionary;
    }

    private String getRefAssessmentVars(Collection<AssessmentVariable> avList) {
        Iterable<String> displayNames = Iterables.transform(avList, new Function<AssessmentVariable, String>() {
            public String apply(AssessmentVariable input) {
                // extract display names from Assessment Variables
                return (input == null) ? null : input.getDisplayName();
            }
        });
        String joinedDisplayNames = Joiner.on(",").skipNulls().join(displayNames);
        return joinedDisplayNames;
    }

    private Table<String, String, String> buildSheetFor(Survey s,
                                                        Collection<Measure> surveyMeasures, Multimap mvMap,
                                                        Collection<AssessmentVariable> avList, Set<String> avUsed) {

        Table<String, String, String> t = TreeBasedTable.create();
        ddh.buildDataDictionaryFor(s, t, surveyMeasures, mvMap, avList, avUsed);

        return t;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.msgSrc = messageSource;
    }

    @Override
    public String getExportNameKeyPrefix() {
        return ddh.EXPORT_KEY_PREFIX;
    }

    @Override
    public String createTableResponseVarName(String exportName) {
        return ddh.createTableResponseVarName(exportName);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map> askFormulasFor(Object sessionObject, Integer moduleId) {

        Survey survey = sr.findOne(moduleId);
        Map<String, Table<String, String, String>> dataDictionary = (Map<String, Table<String, String, String>>) sessionObject;
        Table<String, String, String> moduleTable = dataDictionary.get(survey.getName());
        Map<String, Map<String, String>> rowMap = moduleTable.rowMap();
        Set<Map.Entry<String, Map<String, String>>> entries = rowMap.entrySet();

        List<Map> formulas = Lists.newArrayList();
        for (Map.Entry<String, Map<String, String>> entry : entries) {
            if (entry.getKey().startsWith(ddh.FORMULA_KEY_PREFIX)) {
                Map<String, String> formula = Maps.newHashMap();
                formula.put("avId", entry.getValue().get(ddh.msg("av.id")));
                formula.put("name", entry.getValue().get(ddh.msg("var.name")));
                formula.put("formula", entry.getValue().get(ddh.msg("ques.desc")));
                formula.put("description", entry.getValue().get(ddh.msg("ques.desc.business")));
                formula.put("template", entry.getValue().get(ddh.msg("formula.template")));
                formula.put("size", entry.getValue().get(ddh.msg("var.size")));
                formulas.add(formula);
            }
        }

        return formulas;
    }

    @Override
    public List<Map> getAllFormulas() {
        return Collections.EMPTY_LIST;
    }
}
