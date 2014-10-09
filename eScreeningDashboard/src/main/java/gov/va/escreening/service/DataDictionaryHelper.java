package gov.va.escreening.service;

import gov.va.escreening.entity.AssessmentVarChildren;
import gov.va.escreening.entity.AssessmentVariable;
import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.MeasureValidation;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Validation;
import gov.va.escreening.repository.ValidationRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.common.collect.Table;

@Service("dataDictionaryHelper")
public class DataDictionaryHelper implements MessageSourceAware {

	MessageSource msgSrc;

	// @Resource(type = MeasureRepository.class)
	// MeasureRepository mr;

	@Resource(type = ValidationRepository.class)
	ValidationRepository vr;

	Map<Integer, Resolver> resolverMap;

	/**
	 * on 'first touch' cache of measure with a list of validation
	 * 
	 * Validations for a free text measure (only) are defined in measure_validation table which relate a measure to a
	 * validation. A measure can have more than one validation which is applied.
	 * 
	 * The way validations work is there is a type id (taken from the validation table), combined with some value in the
	 * measure_validation table. Each type only has one valid value in measure_validation table.
	 * 
	 * For example minValue will have an entry in measure_validation.number_value to indicate the minimum allowable
	 * value.
	 * 
	 * the property this{@link #measureValidationsMap} will keep a map of measure id with a list of validation in the
	 * form 'Min Value=1970, Max Value=2020, Exact Number=4' would mean that the measure answer is a date and it should
	 * be between 1970 and 2020 and must contain century too
	 * */
	Multimap<Integer, String> measureValidationsMap;

	private Resolver findResolver(Measure m) {
		return this.resolverMap.get(m.getMeasureType().getMeasureTypeId());
	}

	public void buildDataDictionary(Survey s, Table<String, String, String> t,
			Collection<Measure> smList, Collection<AssessmentVariable> avList) {
		for (Measure m : smList) {
			addDictionaryRowsForMeasure(s, m, t);
		}

		addFormulaeToSurvey(s, t, smList, avList);
	}

	public void addDictionaryRowsForMeasure(Survey s, Measure m,
			Table<String, String, String> t) {
		findResolver(m).addDictionaryRows(s, m, t);
	}

	private void addFormulaeToSurvey(Survey s, Table<String, String, String> t,
			Collection<Measure> smList, Collection<AssessmentVariable> avList) {

		Set<String> formulae = Sets.newLinkedHashSet();
		buildSurveyFormulae(formulae, s, smList, avList);

		if (formulae != null) {
			int i = 0;
			for (String formula : formulae) {
				Iterator<String> formulaTokens = Splitter.on(",").split(formula).iterator();
				String indexAsStr = String.valueOf("surveyId_" + s.getSurveyId()) + "_" + i++;

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

	public void buildAndCacheMeasureValidationMap() {
		List<Validation> validations = vr.findAll();

		this.measureValidationsMap = LinkedHashMultimap.create();

		for (Validation v : validations) {
			for (MeasureValidation mv : v.getMeasureValidationList()) {
				this.measureValidationsMap.put(mv.getMeasure().getMeasureId(), buildMeasureValidation(mv));
			}
		}
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

	private void registerResolversNow() {
		this.resolverMap = Maps.newHashMap();
		this.resolverMap.put(1, new FreeTextResolver(this));
		this.resolverMap.put(2, new SelectOneResolver(this));
		this.resolverMap.put(3, new MultiSelectResolver(this));
		this.resolverMap.put(4, new TableQuestionResolver(this));
		this.resolverMap.put(5, new FreeTextResolver(this));
		this.resolverMap.put(6, new SelectOneMatrixResolver(this));
		this.resolverMap.put(7, new SelectOneMatrixResolver(this));
		this.resolverMap.put(8, new InstructionResolver(this));
	}

	void buildSurveyFormulae(Set<String> surveyFormulae, Survey s,
			Collection<Measure> smList, Collection<AssessmentVariable> avList) {

		// find AssessVariable with any AssessmentVarChild whose Measure matches with any of Measure in this Survey
		for (AssessmentVariable av : avList) {
			boolean found = false;
			for (Measure m : smList) {
				for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
					if (m.equals(avc.getVariableChild().getMeasure())) {
						surveyFormulae.add(extractFormulaPlusExportName(av));
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

	private String extractFormulaPlusExportName(AssessmentVariable av) {
		String formula = extractFormula(av);
		return String.format("%s, %s", formula, av.getDisplayName());
	}

	private String extractFormula(AssessmentVariable av) {
		String formula = av.getFormulaTemplate();
		for (AssessmentVarChildren avc : av.getAssessmentVarChildrenList()) {
			Measure m = avc.getVariableChild().getMeasure();
			String exportName = m != null ? m.getMeasureAnswerList().iterator().next().getExportName() : avc.getVariableChild().getDisplayName();
			String toBeReplaced = String.valueOf(avc.getVariableChild().getAssessmentVariableId());
			formula = formula.replaceAll(toBeReplaced, exportName);
		}
		return formula.replaceAll("[$]", "");
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

	public final void addDictionaryRows(Survey s, Measure m,
			Table<String, String, String> t) {
		addDictionaryRowsNow(s, m, t);
	}

	String getValuesRange(Measure m, MeasureAnswer ma) {
		return "";
	}

	String getValuesDescription(Measure m, MeasureAnswer ma) {
		return "";
	}

	String getValidationDescription(Measure m) {
		return "";
	}

	protected void addDictionaryRowsNow(Survey s, Measure m,
			Table<String, String, String> t) {
		addSingleRow(s, m, t, m.getMeasureAnswerList().isEmpty() ? null : m.getMeasureAnswerList().iterator().next(), 0, false);
	}

	// Collection<Measure> findChildren(Measure m) {
	// return ddh.mr.getChildMeasures(m);
	// }

	protected final void addSingleRow(Survey s, Measure m,
			Table<String, String, String> t, MeasureAnswer ma, int index,
			boolean other) {
		int mId = m.getMeasureId();
		String indexAsStr = String.valueOf("mId_" + mId) + "_" + index;

		t.put(indexAsStr, ddh.msg("ques.type"), index == 0 ? m.getMeasureType().getName() : "");
		t.put(indexAsStr, ddh.msg("ques.desc"), index == 0 ? m.getMeasureText() : ma.getAnswerText());

		if (ma != null) {
			t.put(indexAsStr, ddh.msg("var.name"), !other ? Strings.nullToEmpty(ma.getExportName()) : Strings.nullToEmpty(ma.getOtherExportName()));
			t.put(indexAsStr, ddh.msg("vals.range"), getValuesRange(m, ma));
			t.put(indexAsStr, ddh.msg("vals.desc"), getValuesDescription(m, ma));
			t.put(indexAsStr, ddh.msg("data.val"), getValidationDescription(m));
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
	protected void addDictionaryRowsNow(Survey s, Measure m,
			Table<String, String, String> t) {

		int index = 0;
		addSingleRow(s, m, t, null, index++, false);
		for (MeasureAnswer ma : m.getMeasureAnswerList()) {
			addSingleRow(s, m, t, ma, index++, false);
			if ("other".equals(ma.getAnswerType())) {
				addSingleRow(s, m, t, ma, index++, true);
			}
		}
	}
}

class SelectOneResolver extends Resolver {
	protected SelectOneResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}

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
	String getValidationDescription(Measure m) {
		// the measureValidationsMap should have been initialized inside DataDictionayHelper class, but using
		// @PostContruct or calling this from setMeasureSource which spring calls we get Hibernate Lazy Initialization
		// error
		if (ddh.measureValidationsMap == null) {
			ddh.buildAndCacheMeasureValidationMap();
		}

		Collection<String> validations = ddh.measureValidationsMap.get(m.getMeasureId());
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
	protected void addDictionaryRowsNow(Survey s, Measure m,
			Table<String, String, String> t) {

		super.addDictionaryRowsNow(s, m, t);

		// collect all children here. find all measures which (whose parent_id) points to this measure (m)
		// Collection<Measure> children = findChildren(m);
		Collection<Measure> mc = m.getChildren();

		for (Measure cm : mc) {
			// let the framework take care of rest
			ddh.addDictionaryRowsForMeasure(s, cm, t);
		}
	}
}

class TableQuestionResolver extends SelectOneMatrixResolver {
	protected TableQuestionResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}
}
