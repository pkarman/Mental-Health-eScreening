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
	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static final String EXPORT_NAME_KEY_PREFIX = "mId_";
	public static final String FORMULA_KEY_PREFIX = "surveyId_";

	MessageSource msgSrc;

	Map<Integer, Resolver> resolverMap;

	private Resolver findResolver(Measure m) {
		return this.resolverMap.get(m.getMeasureType().getMeasureTypeId());
	}

	public void buildDataDictionary(Survey s, Table<String, String, String> t,
			Collection<Measure> smList, Multimap mvMap,
			Collection<AssessmentVariable> avList) {
		for (Measure m : smList) {
			addDictionaryRowsForMeasure(s, m, mvMap, t);
		}

		addFormulaeToSurvey(s, t, smList, avList);
	}

	public void addDictionaryRowsForMeasure(Survey s, Measure m,
			Multimap mvMap, Table<String, String, String> t) {
		findResolver(m).addDictionaryRows(s, m, mvMap, t);
	}

	private void addFormulaeToSurvey(Survey s, Table<String, String, String> t,
			Collection<Measure> smList, Collection<AssessmentVariable> avList) {

		Set<String> formulae = Sets.newLinkedHashSet();
		buildSurveyFormulae(formulae, s, smList, avList);

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

	void buildSurveyFormulae(Set<String> surveyFormulae, Survey s,
			Collection<Measure> smList, Collection<AssessmentVariable> avList) {

		// find AssessVariable with any AssessmentVarChild whose Measure matches with any of Measure in this Survey
		for (AssessmentVariable av : avList) {
			boolean found = false;
			for (Measure m : smList) {
				for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
					AssessmentVariable avChild = avc.getVariableChild();
					Measure childM = avChild.getMeasure();
					MeasureAnswer childMa = avChild.getMeasureAnswer();
					if (childM != null && m.equals(childM)) {
						surveyFormulae.add(useM2FormulaPlusExportName(av));
						found = true;
						break;
					} else if (childM == null && childMa != null && childMa.getMeasure().equals(m) && avChild.getFormulaTemplate() == null) {
						logger.warn(String.format("Measure missing AssessmentVarChildren [%s]. Child of AssessmentVariable [%s] ", avc.getVariableChild().getDisplayName(), av.getDisplayName()));
						surveyFormulae.add(useMa2FormulaPlusExportName(av));
						found = true;
						break;
					}
				}
				if (found) {
					break;
				}
				if (!m.getChildren().isEmpty()) {
					buildSurveyFormulae(surveyFormulae, s, m.getChildren(), avList);
				}
			}
		}
	}

	private String useMa2FormulaPlusExportName(AssessmentVariable av) {
		String formula = extractFormulaUsingMeasureAnswer(av);
		return formulaPlusExportName(formula, av);
	}

	private String extractFormulaUsingMeasureAnswer(AssessmentVariable av) {
		String dbFormula = av.getFormulaTemplate();
		String displayableFormula = dbFormula;
		for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
			MeasureAnswer ma = avc.getVariableChild().getMeasureAnswer();
			String exportName = ma.getExportName();
			String toBeReplaced = String.valueOf(avc.getVariableChild().getAssessmentVariableId());
			displayableFormula = displayableFormula.replaceAll(toBeReplaced, exportName);
		}
		displayableFormula = displayableFormula.replaceAll("[$]", "");
		if (logger.isDebugEnabled()) {
			logger.debug(String.format("Formula=%s==>DisplayableFormula=%s", dbFormula, displayableFormula));
		}
		return displayableFormula;
	}

	private String useM2FormulaPlusExportName(AssessmentVariable av) {
		String formula = extractFormulaUsingMeasure(av);
		return formulaPlusExportName(formula, av);
	}

	private String formulaPlusExportName(String formula, AssessmentVariable av) {
		return String.format("%s,%s", formula, av.getDisplayName());
	}

	private String extractFormulaUsingMeasure(AssessmentVariable av) {
		String dbFormula = av.getFormulaTemplate();
		String displayableFormula = dbFormula;
		for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
			Measure m = avc.getVariableChild().getMeasure();
			String exportName = m != null ? m.getMeasureAnswerList().iterator().next().getExportName() : avc.getVariableChild().getDisplayName();
			String toBeReplaced = String.valueOf(avc.getVariableChild().getAssessmentVariableId());
			displayableFormula = displayableFormula.replaceAll(toBeReplaced, exportName);
		}
		displayableFormula = displayableFormula.replaceAll("[$]", "");
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
		addSingleRow(s, m, mvMap, t, m.getMeasureAnswerList().isEmpty() ? null : m.getMeasureAnswerList().iterator().next(), 0, false);
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
			ddh.addDictionaryRowsForMeasure(s, cm, mvMap, t);
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
