package gov.va.escreening.service;

import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.MeasureValidation;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.Validation;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.ValidationRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;

@Service("dataDictionaryHelper")
public class DataDictionaryHelper implements MessageSourceAware {

	MessageSource msgSrc;

	@Resource(type = MeasureRepository.class)
	MeasureRepository mr;

	@Resource(type = ValidationRepository.class)
	ValidationRepository vr;

	Map<Integer, Resolver> resolverMap;

	Multimap<Integer, String> measureValidationsMap;

	private Resolver findResolver(Measure m) {
		return this.resolverMap.get(m.getMeasureType().getMeasureTypeId());
	}

	public void buildDataDictionary(Survey s, Measure m,
			Table<String, String, String> t) {
		findResolver(m).addDictionaryRows(s, m, t);
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
}

abstract class Resolver {
	protected final DataDictionaryHelper ddh;

	protected Resolver(DataDictionaryHelper ddh) {
		this.ddh = ddh;
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

	void addDictionaryRows(Survey s, Measure m, Table<String, String, String> t) {
		addSingleRow(s, m, t, m.getMeasureAnswerList().isEmpty() ? null : m.getMeasureAnswerList().iterator().next(), 0, false);
	}

	Collection<Measure> findChildren(Measure m) {
		return ddh.mr.getChildMeasures(m);
	}

	protected final void addSingleRow(Survey s, Measure m,
			Table<String, String, String> t, MeasureAnswer ma, int index,
			boolean other) {
		int mId = m.getMeasureId();
		String indexAsStr = String.valueOf(mId) + "_" + index;

		t.put(indexAsStr, msg("ques.type"), index == 0 ? m.getMeasureType().getName() : "");
		t.put(indexAsStr, msg("ques.desc"), index == 0 ? m.getMeasureText() : ma.getAnswerText());

		if (ma != null) {
			t.put(indexAsStr, msg("var.name"), !other ? Strings.nullToEmpty(ma.getExportName()) : Strings.nullToEmpty(ma.getOtherExportName()));
			t.put(indexAsStr, msg("vals.range"), getValuesRange(m, ma));
			t.put(indexAsStr, msg("vals.desc"), getValuesDescription(m, ma));
			t.put(indexAsStr, msg("data.val"), getValidationDescription(m));
			t.put(indexAsStr, msg("followup"), "todo");
			t.put(indexAsStr, msg("skiplevel"), getSkipLevel(m));
		}
	}

	private String msg(String propertySuffix) {
		return ddh.msgSrc.getMessage("data.dict.column." + propertySuffix, null, null);
	}

	private String getSkipLevel(Measure m) {
		return m.getIsRequired() ? msg("skip.no") : msg("skip.yes");
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
	public void addDictionaryRows(Survey s, Measure m,
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
		String description = "";
		if (validations != null) {
			for (String s : validations) {
				description += s + " ";
			}
		}
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
	public void addDictionaryRows(Survey s, Measure m,
			Table<String, String, String> t) {
		super.addDictionaryRows(s, m, t);
		// collect all children here. find all measures whose parent is pointing to this measure (m)
		Collection<Measure> children = findChildren(m);
		for (Measure cm : children) {
			ddh.buildDataDictionary(s, cm, t);
		}
	}
}

class TableQuestionResolver extends SelectOneMatrixResolver {
	protected TableQuestionResolver(DataDictionaryHelper ddr) {
		super(ddr);
	}
}
