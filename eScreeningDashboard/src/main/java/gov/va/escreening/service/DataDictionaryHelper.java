package gov.va.escreening.service;

import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.MeasureAnswer;
import gov.va.escreening.entity.Survey;

import java.util.List;
import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

@Component("dataDictionaryHelper")
public class DataDictionaryHelper implements MessageSourceAware {
	Map<Integer, Resolver> resolverMap;

	private Resolver getVrr(Measure m) {
		return this.resolverMap.get(m.getMeasureType().getMeasureTypeId());
	}

	public void addDictionaryRows(Survey s, Measure m,
			Table<String, String, String> t) {
		getVrr(m).addDictionaryRows(s, m, t);
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.resolverMap = Maps.newHashMap();
		this.resolverMap.put(1, new FreeTextResolver(messageSource));
		this.resolverMap.put(2, new SelectOneResolver(messageSource));
		this.resolverMap.put(3, new MultiSelectResolver(messageSource));
		this.resolverMap.put(4, new TableQuestionResolver(messageSource));
		this.resolverMap.put(5, new FreeTextResolver(messageSource));
		this.resolverMap.put(6, new SelectOneMatrixResolver(messageSource));
		this.resolverMap.put(7, new MultiSelectResolver(messageSource));
		this.resolverMap.put(8, new InstructionResolver(messageSource));
	}
}

abstract class Resolver {
	protected final MessageSource messageSource;

	protected Resolver(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	abstract String getValuesRange(Measure m, MeasureAnswer ma);

	abstract String getValuesDescription(Measure m, MeasureAnswer ma);

	void addDictionaryRows(Survey s, Measure m, Table<String, String, String> t) {
		addSingleRow(s, m, t, m.getMeasureAnswerList().isEmpty() ? null : m.getMeasureAnswerList().iterator().next(), 0, false);
	}

	void addSingleRow(Survey s, Measure m, Table<String, String, String> t,
			MeasureAnswer ma, int index, boolean other) {
		int mId = m.getMeasureId();
		String indexAsStr = String.valueOf(mId) + "_" + index;

		t.put(indexAsStr, messageSource.getMessage("data.dict.column.ques.type", null, null), index == 0 ? m.getMeasureType().getName() : "");
		t.put(indexAsStr, messageSource.getMessage("data.dict.column.ques.desc", null, null), index == 0 ? m.getMeasureText() : ma.getAnswerText());

		if (ma != null) {
			t.put(indexAsStr, messageSource.getMessage("data.dict.column.var.name", null, null), !other ? Strings.nullToEmpty(ma.getExportName()) : Strings.nullToEmpty(ma.getOtherExportName()));
			t.put(indexAsStr, messageSource.getMessage("data.dict.column.vals.range", null, null), getValuesRange(m, ma));
			t.put(indexAsStr, messageSource.getMessage("data.dict.column.vals.desc", null, null), getValuesDescription(m, ma));
			t.put(indexAsStr, messageSource.getMessage("data.dict.column.data.val", null, null), "todo");
			t.put(indexAsStr, messageSource.getMessage("data.dict.column.followup", null, null), "todo");
			t.put(indexAsStr, messageSource.getMessage("data.dict.column.skiplevel", null, null), "todo");
		}
	}
}

class MultiSelectResolver extends Resolver {
	protected MultiSelectResolver(MessageSource messageSource) {
		super(messageSource);
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
	protected SelectOneResolver(MessageSource messageSource) {
		super(messageSource);
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
	protected FreeTextResolver(MessageSource messageSource) {
		super(messageSource);
	}

	@Override
	public String getValuesRange(Measure m, MeasureAnswer ma) {
		return "text";
	}

	@Override
	public String getValuesDescription(Measure m, MeasureAnswer ma) {
		return "text, 999= missing";
	}
}

class InstructionResolver extends Resolver {
	protected InstructionResolver(MessageSource messageSource) {
		super(messageSource);
	}

	@Override
	public String getValuesRange(Measure m, MeasureAnswer ma) {
		return "";
	}

	@Override
	public String getValuesDescription(Measure m, MeasureAnswer ma) {
		return "";
	}
}

class SelectOneMatrixResolver extends Resolver {
	protected SelectOneMatrixResolver(MessageSource messageSource) {
		super(messageSource);
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

class TableQuestionResolver extends Resolver {
	protected TableQuestionResolver(MessageSource messageSource) {
		super(messageSource);
	}

	@Override
	public String getValuesRange(Measure m, MeasureAnswer ma) {
		return "TO-DO";
	}

	@Override
	public String getValuesDescription(Measure m, MeasureAnswer ma) {
		return "TO-DO";
	}
}
