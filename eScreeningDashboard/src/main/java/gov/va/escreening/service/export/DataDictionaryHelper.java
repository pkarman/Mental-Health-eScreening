package gov.va.escreening.service.export;

import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

@Service("dataDictionaryHelper")
public class DataDictionaryHelper implements MessageSourceAware {
	interface ExportNameExtractor {
		String extractExportName(AssessmentVarChildren avc);
	}

	class MaNameExtractor implements ExportNameExtractor {
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

	public static final String EXPORT_NAME_KEY_PREFIX = "mId_";
	public static final String FORMULA_KEY_PREFIX = "surveyId_";
	MessageSource msgSrc;

	Map<Integer, Resolver> resolverMap;

	private Resolver findResolver(Measure m) {
		return this.resolverMap.get(m.getMeasureType().getMeasureTypeId());
	}

	public void buildDataDictionaryFor(Survey s,
			Table<String, String, String> t, Collection<Measure> smList,
			Multimap mvMap, Collection<AssessmentVariable> avList,
			Set<String> avUsed) {
		for (Measure m : smList) {
			addDictionaryRowsFor(m, s, mvMap, t);
		}

		addFormulaeFor(s, t, smList, avList, avUsed);
	}

	public void addDictionaryRowsFor(Measure m, Survey s, Multimap mvMap,
			Table<String, String, String> t) {
		findResolver(m).addDictionaryRows(s, m, mvMap, t);
	}

	private void addFormulaeFor(Survey s, Table<String, String, String> t,
			Collection<Measure> smList, Collection<AssessmentVariable> avList,
			Set<String> avUsed) {

		Set<String> formulae = Sets.newLinkedHashSet();
		buildFormulaeFor(s, formulae, avUsed, smList, avList);

		if (formulae != null) {
			int i = 0;
			for (String formula : formulae) {
				Iterator<String> formulaTokens = Splitter.on(",").split(formula).iterator();
				String indexAsStr = String.valueOf(FORMULA_KEY_PREFIX + s.getSurveyId()) + "_" + i++;

				t.put(indexAsStr, msg("ques.type"), "formula");
				t.put(indexAsStr, msg("ques.desc"), formulaTokens.next());
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

	void buildFormulaeFor(Survey survey, Set<String> surveyFormulae,
			Set<String> avUsed, Collection<Measure> smList,
			Collection<AssessmentVariable> avList) {

		for (AssessmentVariable av : avList) {
			for (Measure m : smList) {
				for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
					if (compareMeasure(avc.getVariableChild(), m)) {
						surveyFormulae.add(useM2FormulaPlusExportName(av));
						avUsed.add(av.getDisplayName());
					} else if (compareMeasureAnswer(avc.getVariableChild(), m)) {
						surveyFormulae.add(useMa2FormulaPlusExportName(av));
						avUsed.add(av.getDisplayName());
					}
				}
				if (!m.getChildren().isEmpty()) {
					buildFormulaeFor(survey, surveyFormulae, avUsed, m.getChildren(), avList);
				}
			}

		}
	}

	/**
	 * recursively search that AssessmentVariable belongs to the MeasurementAnswer of passed in Measure
	 * 
	 * @param av
	 * @param m
	 * @return
	 */
	private boolean compareMeasureAnswer(AssessmentVariable av, Measure m) {
		if (av == null) {
			return false;
		} else if (av.getMeasureAnswer() != null && m.equals(av.getMeasureAnswer().getMeasure())) {
			return true;
		} else {
			for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
				return compareMeasureAnswer(avc.getVariableChild(), m);
			}
		}
		return false;
	}

	/**
	 * recursively search that AssessmentVariable belongs to the Measure
	 * 
	 * @param av
	 * @param m
	 * @return
	 */
	private boolean compareMeasure(AssessmentVariable av, Measure m) {
		if (av == null) {
			return false;
		} else if (m.equals(av.getMeasure())) {
			return true;
		} else {
			for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
				return compareMeasure(avc.getVariableChild(), m);
			}
		}
		return false;
	}

	private String useMa2FormulaPlusExportName(AssessmentVariable av) {
		String formula = extractFormula(av, new MaNameExtractor());
		return formulaPlusExportName(formula, av);
	}

	// private String extractFormulaUsingMeasureAnswer(AssessmentVariable av) {
	// String dbFormula = av.getFormulaTemplate();
	// String displayableFormula = dbFormula;
	// for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
	// MeasureAnswer ma = avc.getVariableChild().getMeasureAnswer();
	// String exportName = ma != null ? ma.getExportName() : avc.getVariableChild().getDisplayName();
	// String toBeReplaced = String.valueOf(avc.getVariableChild().getAssessmentVariableId());
	// displayableFormula = displayableFormula.replaceAll(toBeReplaced, exportName);
	// }
	// displayableFormula = displayableFormula.replaceAll("[$]", "");
	// if (logger.isDebugEnabled()) {
	// logger.debug(String.format("Formula=%s==>DisplayableFormula=%s", dbFormula, displayableFormula));
	// }
	// return displayableFormula;
	// }

	private String useM2FormulaPlusExportName(AssessmentVariable av) {
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
		displayableFormula = displayableFormula.replaceAll("[$]", "").replaceAll("[?]", "").replaceAll("1:0", "");
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Formula=%s==>DisplayableFormula=%s", dbFormula, displayableFormula));
		}
		return displayableFormula;
	}

	public String msg(String propertySuffix) {
		return msgSrc.getMessage("data.dict.column." + propertySuffix, null, null);
	}

}

abstract class Resolver {
	protected final DataDictionaryHelper ddh;

	protected Resolver(DataDictionaryHelper ddh) {
		this.ddh = ddh;
	}

	String getValidationDescription(Measure m, Multimap mvMap) {
		return "";
	}

	public final void addDictionaryRows(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t) {
		addDictionaryRowsNow(s, m, mvMap, t);
	}

	String getValuesRange(Measure m, MeasureAnswer ma) {
		return "";
	}

	String getValuesDescription(Measure m, MeasureAnswer ma) {
		return "";
	}

	protected void addDictionaryRowsNow(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t) {
		int index = 0;
		addSingleRow(s, m, mvMap, t, m.getMeasureAnswerList().isEmpty() ? null : m.getMeasureAnswerList().iterator().next(), index++, false);

		// compensate measure answer of other type
		for (MeasureAnswer ma : m.getMeasureAnswerList()) {
			if ("other".equals(ma.getAnswerType())) {
				addSingleRow(s, m, mvMap, t, ma, index++, true);
			}
		}

	}

	protected final void addSingleRow(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t, MeasureAnswer ma, int index,
			boolean other) {
		int mId = m.getMeasureId();
		String indexAsStr = String.valueOf(DataDictionaryHelper.EXPORT_NAME_KEY_PREFIX + mId) + "_" + index;

		t.put(indexAsStr, ddh.msg("ques.type"), index == 0 ? m.getMeasureType().getName() : "");
		t.put(indexAsStr, ddh.msg("ques.desc"), index == 0 ? m.getMeasureText() : ma.getAnswerText());

		if (ma != null) {
			t.put(indexAsStr, ddh.msg("var.name"), !other ? Strings.nullToEmpty(ma.getExportName()) : Strings.nullToEmpty(ma.getOtherExportName()));
			t.put(indexAsStr, ddh.msg("vals.range"), getValuesRange(m, ma));
			t.put(indexAsStr, ddh.msg("vals.desc"), getValuesDescription(m, ma));
			t.put(indexAsStr, ddh.msg("data.val"), getValidationDescription(m, mvMap));
			t.put(indexAsStr, ddh.msg("followup"), "todo");
			t.put(indexAsStr, ddh.msg("skiplevel"), getSkipLevel(m));
		}
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
	public String getValuesRange(Measure m, MeasureAnswer ma) {
		return "0-1,999";
	}

	@Override
	public String getValuesDescription(Measure m, MeasureAnswer ma) {
		return "0= no, 1= yes, 999= missing";
	}

	@Override
	protected void addDictionaryRowsNow(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t) {

		int index = 0;
		addSingleRow(s, m, mvMap, t, null, index++, false);
		for (MeasureAnswer ma : m.getMeasureAnswerList()) {
			addSingleRow(s, m, mvMap, t, ma, index++, false);
			if ("other".equals(ma.getAnswerType())) {
				addSingleRow(s, m, mvMap, t, ma, index++, true);
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
	public String getValuesRange(Measure m, MeasureAnswer unusedMa) {
		List<MeasureAnswer> maList = m.getMeasureAnswerList();
		int calculationType = maList.iterator().next().getCalculationType().getCalculationTypeId();
		if (calculationType == 1) {
			int min = Integer.MAX_VALUE;
			int max = Integer.MIN_VALUE;
			for (MeasureAnswer ma : maList) {
				int val = Integer.parseInt(ma.getCalculationValue());
				min = Math.min(min, val);
				max = Math.max(max, val);
			}
			return String.format("%s-%s,999", min, max);
		}
		return "TO-DO";
	}

	/**
	 * consolidate measure answers' calculationValue wirh the answer text. The output will be in following format
	 * 
	 * 1=English,2=Spanish,3=Tagalog,4=Chinese,5=German,6=Japanese,7=Korean,8=Russian,9=Vietnamese,10=Other, please
	 * specify,999=missing
	 */
	@Override
	public String getValuesDescription(Measure m, MeasureAnswer unusedMa) {
		List<MeasureAnswer> maList = m.getMeasureAnswerList();
		int calculationType = maList.iterator().next().getCalculationType().getCalculationTypeId();
		if (calculationType == 1) {
			StringBuilder sb = new StringBuilder();
			for (MeasureAnswer ma : maList) {
				sb.append(String.format("%s=%s,", ma.getCalculationValue(), ma.getAnswerText()));
			}
			sb.append("999=missing");
			return sb.toString();

		}
		return "TO-DO";
	}
}

class FreeTextResolver extends Resolver {
	protected FreeTextResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}

	@Override
	public String getValuesRange(Measure m, MeasureAnswer ma) {
		return "text";
	}

	@Override
	public String getValuesDescription(Measure m, MeasureAnswer ma) {
		return "text, 999= missing";
	}

	@Override
	String getValidationDescription(Measure m, Multimap mvMap) {
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
			Table<String, String, String> t) {

		super.addDictionaryRowsNow(s, m, mvMap, t);

		// collect all children here. find all measures which (whose parent_id) points to this measure (m)
		// Collection<Measure> children = findChildren(m);
		Collection<Measure> mc = m.getChildren();

		for (Measure cm : mc) {
			// let the framework take care of rest
			ddh.addDictionaryRowsFor(cm, s, mvMap, t);
		}
	}
}

class SelectMultiMatrixResolver extends SelectOneMatrixResolver {
	protected SelectMultiMatrixResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}

	@Override
	protected void addDictionaryRowsNow(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t) {
		super.addDictionaryRowsNow(s, m, mvMap, t);
	}
}

class TableQuestionResolver extends SelectOneMatrixResolver {
	protected TableQuestionResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}

	@Override
	protected void addDictionaryRowsNow(Survey s, Measure m, Multimap mvMap,
			Table<String, String, String> t) {
		super.addDictionaryRowsNow(s, m, mvMap, t);
	}
}
