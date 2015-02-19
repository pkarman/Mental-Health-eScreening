package gov.va.escreening.service.export;

import gov.va.escreening.constants.AssessmentConstants;
import gov.va.escreening.entity.*;
import gov.va.escreening.service.AssessmentVariableService;
import gov.va.escreening.service.AvBuilder;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

@Component("dataDictionaryHelper")
public class DataDictionaryHelper implements MessageSourceAware {

	public final String SALT_DEFAULT = "1";

	@Resource(name = "assessmentVariableService")
	AssessmentVariableService avs;

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

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public final String EXPORT_KEY_PREFIX = "EXPORT";
	public final String FORMULA_KEY_PREFIX = "FORMULA";
	MessageSource msgSrc;

	Map<Integer, Resolver> resolverMap;

	private Resolver findResolver(Measure m) {
		return findResolver(m.getMeasureType().getMeasureTypeId());
	}

	public Resolver findResolver(int measureTypeId) {
		return this.resolverMap.get(measureTypeId);
	}

	public String getPlainText(String htmlString) {
		return htmlString != null ? htmlString.replaceAll("\\<.*?>", "") : "";
	}

	public void buildDataDictionaryFor(Survey s,
			Table<String, String, String> t, Collection<Measure> smList,
			Multimap mvMap, Collection<AssessmentVariable> avList,
			Set<String> avUsed) {
		for (Measure m : smList) {
			addDictionaryRowsFor(m, s, mvMap, t, SALT_DEFAULT);
		}

		addFormulaeFor(s, t, smList, avList, avUsed);
	}

	void addDictionaryRowsFor(Measure m, Survey s, Multimap mvMap,
			Table<String, String, String> t, String salt) {
		findResolver(m).addDictionaryRows(s, m, mvMap, t, salt);
	}

	public String createTableResponseVarName(String exportName) {
		int pos = exportName.lastIndexOf("_");
		String tableResponseVarName = (pos > -1 ? exportName.substring(0, exportName.lastIndexOf("_")) : exportName) + "_count";
		return tableResponseVarName;
	}

	private void addFormulaeFor(Survey s, Table<String, String, String> t,
			Collection<Measure> smList, Collection<AssessmentVariable> avList,
			Set<String> avUsed) {

		Set<String> tgtFormulaeSet = Sets.newLinkedHashSet();
		buildFormulaeFor(s, tgtFormulaeSet, avUsed, smList, avList);

		if (tgtFormulaeSet != null) {
			int index = 0;
			for (String formula : tgtFormulaeSet) {
				Iterator<String> formulaTokens = Splitter.on(",").split(formula).iterator();
				String indexAsStr = String.format("%s_%s_%s", FORMULA_KEY_PREFIX, s.getSurveyId(), index++);

				t.put(indexAsStr, msg("ques.type"), "formula");
				t.put(indexAsStr, msg("ques.desc"), getPlainText(formulaTokens.next()));
				t.put(indexAsStr, msg("var.name"), formulaTokens.next());

				if (formulaTokens.hasNext()) {
					t.put(indexAsStr, msg("vals.range"), formulaTokens.next());
				}

				if (formulaTokens.hasNext()) {
					t.put(indexAsStr, msg("vals.desc"), formulaTokens.next());
				}
			}
		}
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.msgSrc = messageSource;
		registerResolversNow();
	}

	private void registerResolversNow() {
		this.resolverMap = Maps.newHashMap();
		this.resolverMap.put(1, new FreeTextResolver(this)); // freeText
		this.resolverMap.put(2, new SelectOneResolver(this)); // selectOne
		this.resolverMap.put(3, new MultiSelectResolver(this)); // selectMulti
		this.resolverMap.put(4, new TableQuestionResolver(this)); // tableQuestion
		this.resolverMap.put(5, new FreeTextResolver(this)); // readOnlyText
		this.resolverMap.put(6, new SelectOneMatrixResolver(this)); // selectOneMatrix
		this.resolverMap.put(7, new SelectMultiMatrixResolver(this)); // selectMultiMatrix
		this.resolverMap.put(8, new InstructionResolver(this)); // instruction
	}

	void buildFormulaeFor(Survey survey, final Set<String> surveyFormulae,
			final Set<String> avUsed, Collection<Measure> smList,
			Collection<AssessmentVariable> avLstWithFormulae) {

		AvBuilder<Set<String>> avBldr = new AvBuilder<Set<String>>() {
			@Override
			public void buildFromMeasureAnswer(
					AssessmentVariable avWithFormula,
					AssessmentVarChildren avc, Measure m, MeasureAnswer ma) {
				addSurveyFormula(avWithFormula, buildXportNameFromMeasureAnswer(avWithFormula));

			}

			@Override
			public void buildFromMeasure(AssessmentVariable avWithFormula,
					AssessmentVarChildren avc, Measure m) {
				addSurveyFormula(avWithFormula, buildXportNameFromMeasure(avWithFormula));
			}

			private void addSurveyFormula(AssessmentVariable avWithFormula, String formula) {
				// each formula is unique and can only be used only once across all surveys.
				// If it is already used, then do not try to add it again
				if (avUsed.add(avWithFormula.getDisplayName())) {
					surveyFormulae.add(formula);
				}
			}

			@Override
			public Set<String> getResult() {
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
						avs.filterBySurvey(survey, this, m.getChildren(), avList, filterMeasures);
					}
				}
			}
		};

		avs.filterBySurvey(survey, avBldr, smList, avLstWithFormulae, false);
	}

	private String buildXportNameFromMeasureAnswer(AssessmentVariable av) {
		String formula = extractFormula(av, new MeasureAnswerNameExtractor());
		return formulaPlusExportName(formula, av);
	}

	private String buildXportNameFromMeasure(AssessmentVariable av) {
		String formula = extractFormula(av, new MeasureNameExtractor());
		return formulaPlusExportName(formula, av);
	}

	private String formulaPlusExportName(String formula, AssessmentVariable av) {
		return String.format("%s,%s", formula, av.getDisplayName());
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
		// displayableFormula = displayableFormula.replaceAll("[$]", "").replaceAll("[?]", "").replaceAll("1\\s*:\\s*0",
		// "").replaceAll(">=\\s*1", "");
		// displayableFormula = displayableFormula.replaceAll("[$]|[?]|[1\\s*:\\s*0]|[>=\\s*1]", "");
		displayableFormula = displayableFormula.replaceAll("([$])|(\\?\\s*[1]\\s*[:]\\s*[0])", "").replaceAll("(>\\s*=\\s*[1])", " >= 1 then 1 else 0");

		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Formula=%s==>DisplayableFormula=%s", dbFormula, displayableFormula));
		}
		return displayableFormula;
	}

	String msg(String propertySuffix) {
		return msgSrc.getMessage("data.dict.column." + propertySuffix, null, null);
	}
}

abstract class Resolver {
	protected final DataDictionaryHelper ddh;

	protected Resolver(DataDictionaryHelper ddh) {
		this.ddh = ddh;
	}

	String getValidationDescription(Measure m, Multimap mvMap, boolean isOther) {
		return "";
	}

	public final void addDictionaryRows(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t, String salt) {
		addDictionaryRowsNow(s, m, mvMap, t, salt);
	}

	String getValuesRange(Measure m, MeasureAnswer ma, boolean isOther) {
		return "";
	}

	String getValuesDescription(Measure m, MeasureAnswer ma, boolean isOther) {
		return "";
	}

	protected void addDictionaryRowsNow(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t, String salt) {

		int index = 0;
		addSingleRow(s, m, mvMap, t, m.getMeasureAnswerList().isEmpty() ? null : m.getMeasureAnswerList().iterator().next(), index++, false, salt);

		// compensate measure answer of other type
		for (MeasureAnswer ma : m.getMeasureAnswerList()) {
			if ("other".equals(ma.getAnswerType())) {
				addSingleRow(s, m, mvMap, t, ma, index++, true, salt);
			}
		}

	}

	protected final void addSingleRow(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t, MeasureAnswer ma, int index,
			boolean other, String salt) {

		String rowId = generateRowId("" + m.getMeasureId(), salt, index);

		String quesType = index == 0 ? m.getMeasureType().getName() : "";
		String quesDesc = ddh.getPlainText(index == 0 ? m.getMeasureText() : ma.getAnswerText());
		boolean addMore = ma != null;
		String varName = addMore ? !other ? Strings.nullToEmpty(ma.getExportName()) : Strings.nullToEmpty(ma.getOtherExportName()) : "";
		String valsRange = addMore ? getValuesRange(m, ma, other) : "";
		String valsDesc = addMore ? getValuesDescription(m, ma, other) : "";
		String dataVal = addMore ? getValidationDescription(m, mvMap, other) : "";
		String followup = addMore ? "todo" : "";
		String skiplevel = addMore ? getSkipLevel(m) : "";

		addRow(t, rowId, quesType, quesDesc, varName, valsRange, valsDesc, dataVal, followup, skiplevel, other);
	}

	protected String generateRowId(String partialRowId, String salt, int index) {
		String mId = String.format("%s", ddh.SALT_DEFAULT.equals(salt) ? ("" + partialRowId + salt) : salt);
		String rowId = String.format("%s_%s_%s", ddh.EXPORT_KEY_PREFIX, mId, index);
		return rowId;
	}

	protected void addRow(Table<String, String, String> t, String rowId,
			String quesType, String quesDesc, String varName, String valsRange,
			String valsDesc, String dataVal, String followup, String skiplevel, boolean other) {

		t.put(rowId, ddh.msg("ques.type"), quesType);
		t.put(rowId, ddh.msg("ques.desc"), quesDesc);
		t.put(rowId, ddh.msg("var.name"), varName);
		t.put(rowId, ddh.msg("vals.range"), valsRange);
		t.put(rowId, ddh.msg("vals.desc"), valsDesc);
		t.put(rowId, ddh.msg("data.val"), dataVal);
		t.put(rowId, ddh.msg("followup"), followup);
		t.put(rowId, ddh.msg("skiplevel"), skiplevel);
		t.put(rowId, ddh.msg("answer.type.other"), String.format("%s$%s", varName, String.valueOf(other)));
	}

	private String getSkipLevel(Measure m) {
		return m.getIsRequired() ? ddh.msg("skip.no") : ddh.msg("skip.yes");
	}
}

class MultiSelectResolver extends Resolver {
	protected MultiSelectResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}

	@Override
	public String getValuesRange(Measure m, MeasureAnswer ma, boolean isOther) {
		return isOther?ddh.findResolver(1).getValuesRange(m,ma,isOther):"0-1,999";
	}

	@Override
	public String getValuesDescription(Measure m, MeasureAnswer ma, boolean isOther) {
		return isOther?ddh.findResolver(1).getValuesDescription(m,ma,isOther):"0= no, 1= yes, 999= missing";
	}

	@Override
	protected void addDictionaryRowsNow(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t, String salt) {

		int index = 0;
		addSingleRow(s, m, mvMap, t, null, index++, false, salt);
		for (MeasureAnswer ma : m.getMeasureAnswerList()) {
			addSingleRow(s, m, mvMap, t, ma, index++, false, salt);
			if ("other".equals(ma.getAnswerType())) {
				addSingleRow(s, m, mvMap, t, ma, index++, true, salt);
			}
		}
	}
}

class SelectOneResolver extends Resolver {
	protected SelectOneResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}

	/**
	 * consolidate measure answers' ranges. The out put will be in following format 1-10,999
	 */
	@Override
	public String getValuesRange(Measure m, MeasureAnswer unusedMa, boolean isOther) {
		if (isOther){
			return ddh.findResolver(1).getValuesRange(m,unusedMa,isOther);
		}
		
		List<MeasureAnswer> maList = m.getMeasureAnswerList();
        if(m.getMeasureType().getMeasureTypeId() == AssessmentConstants.MEASURE_TYPE_SELECT_ONE){
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			for (MeasureAnswer ma : maList) {
				int val = Integer.parseInt(ma.getCalculationValue());
				min = Math.min(min, val);
				max = Math.max(max, val);
			}
			return String.format("%s-%s,999", min, max);
		}
		return "undefined";
	}

	/**
	 * consolidate measure answers' calculationValue wirh the answer text. The output will be in following format
	 * 
	 * 1=English,2=Spanish,3=Tagalog,4=Chinese,5=German,6=Japanese,7=Korean,8=Russian,9=Vietnamese,10=Other, please
	 * specify,999=missing
	 */
	@Override
	public String getValuesDescription(Measure m, MeasureAnswer unusedMa, boolean isOther) {
		if (isOther){
			return ddh.findResolver(1).getValuesDescription(m,unusedMa,isOther);
		}
		List<MeasureAnswer> maList = m.getMeasureAnswerList();
		if(m.getMeasureType().getMeasureTypeId() == AssessmentConstants.MEASURE_TYPE_SELECT_ONE){
			StringBuilder sb = new StringBuilder();
			for (MeasureAnswer ma : maList) {
				sb.append(String.format("%s=%s,", ma.getCalculationValue(), ma.getAnswerText()));
			}
			sb.append("999=missing");
			return sb.toString();
		}
        return "undefined";
	}
}

class FreeTextResolver extends Resolver {
	protected FreeTextResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}

	@Override
	public String getValuesRange(Measure m, MeasureAnswer ma, boolean isOther) {
		return "text";
	}

	@Override
	public String getValuesDescription(Measure m, MeasureAnswer ma, boolean isOther) {
		return "text, 999= missing";
	}

	@Override
	String getValidationDescription(Measure m, Multimap mvMap, boolean isOther) {
		Collection<String> validations = mvMap.get(m.getMeasureId());
		String description = Joiner.on(", ").skipNulls().join(validations);
		return description;
	}
}

class InstructionResolver extends Resolver {
	protected InstructionResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}
}

class SelectOneMatrixResolver extends Resolver {
	protected SelectOneMatrixResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}

	@Override
	protected void addDictionaryRowsNow(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t, String salt) {

		super.addDictionaryRowsNow(s, m, mvMap, t, salt);

		// collect all children here. find all measures which (whose parent_id) points to this measure (m)
		// Collection<Measure> children = findChildren(m);
		Collection<Measure> mc = m.getChildren();

		int parentId = m.getMeasureId();
		int modifiedSaltAsInt = Integer.parseInt(ddh.SALT_DEFAULT)+1;
		for (Measure cm : mc) {
			// let the framework take care of rest
			// all dependent record's pk must have a number after the parent
			// if not than we have to some how make sure that the id comes after the parent
			int childId = cm.getMeasureId();
			salt = childId < parentId ? String.valueOf(parentId) + modifiedSaltAsInt++ : salt;
			ddh.addDictionaryRowsFor(cm, s, mvMap, t, salt);
		}
	}
}

class SelectMultiMatrixResolver extends SelectOneMatrixResolver {
	protected SelectMultiMatrixResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}

	@Override
	protected void addDictionaryRowsNow(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t, String salt) {
		super.addDictionaryRowsNow(s, m, mvMap, t, salt);
	}
}

class TableQuestionResolver extends SelectOneMatrixResolver {
	protected TableQuestionResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}

	@Override
	protected void addDictionaryRowsNow(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t, String salt) {
	    String saltForResponseRowCounter = m.getMeasureId() + String.valueOf((Integer.parseInt(ddh.SALT_DEFAULT)-1));
		String tableResponsesCounterVarName = ddh.createTableResponseVarName(m.getChildren().iterator().next().getMeasureAnswerList().iterator().next().getExportName());
		addRow(t, generateRowId("", saltForResponseRowCounter, 0), "tableResponseCntr", "total responses of table questions", tableResponsesCounterVarName, "", "", "", "", "", false);
		super.addDictionaryRowsNow(s, m, mvMap, t, salt);
	}
}
