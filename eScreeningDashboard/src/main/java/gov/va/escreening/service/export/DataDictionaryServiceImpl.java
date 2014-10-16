package gov.va.escreening.service.export;

import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureValidation;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyPageMeasure;
import gov.va.escreening.entity.Validation;
import gov.va.escreening.repository.AssessmentVariableRepository;
import gov.va.escreening.repository.SurveyPageMeasureRepository;
import gov.va.escreening.repository.ValidationRepository;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
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
import com.google.common.collect.ArrayListMultimap;
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

	@Resource(type = SurveyPageMeasureRepository.class)
	SurveyPageMeasureRepository spmr;

	@Resource(type = AssessmentVariableRepository.class)
	AssessmentVariableRepository avr;

	private Multimap<Integer, String> buildAndCacheMeasureValidationMap() {

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
		Map<String, Table<String, String, String>> dataDictionary = Maps.newTreeMap();

		// partition all survey with its list of measures
		Multimap<Survey, Measure> surveyMeasuresMap = buildSurveyMeasuresMap();

		// read all AssessmenetVariables having formulae
		Collection<AssessmentVariable> avList = avr.findAllFormulae();

		// read all measures of free text and its validations
		Multimap<Integer, String> ftMvMap = buildAndCacheMeasureValidationMap();

		Set<String> avUsed = Sets.newLinkedHashSet();

		for (Survey s : surveyMeasuresMap.keySet()) {
			Table<String, String, String> sheet = buildSheetFor(s, surveyMeasuresMap.get(s), ftMvMap, avList, avUsed);
			// bind the survey (or module with its sheet)
			dataDictionary.put(s.getName(), sheet);

			if (logger.isDebugEnabled()) {
				logger.debug(String.format("sheet data for Survey=%s =>> %s", s.getName(), sheet));
			}
		}

		if (logger.isDebugEnabled()) {
			List<String> l1 = Lists.newArrayList(avUsed);
			Collections.sort(l1);

			String refAssessmentVars = getRefAssessmentVars(avList);
			List<String> l2 = Lists.newArrayList(Strings.split(refAssessmentVars, ','));
			Collections.sort(l2);

			Preconditions.checkArgument(l1.equals(l2), String.format("(Ref AssessmentVariable List) [%s] is not same as asscoiated AssessmentVariable List [%s]", l2, l1));
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

	private Multimap<Survey, Measure> buildSurveyMeasuresMap() {
		List<SurveyPageMeasure> spmList = spmr.findAll();
		Multimap<Survey, Measure> smMap = ArrayListMultimap.create();
		for (SurveyPageMeasure spm : spmList) {
			smMap.put(spm.getSurveyPage().getSurvey(), spm.getMeasure());
		}
		return smMap;
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
}
