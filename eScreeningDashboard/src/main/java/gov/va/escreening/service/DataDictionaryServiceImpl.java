package gov.va.escreening.service;

import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.entity.SurveyPageMeasure;
import gov.va.escreening.repository.SurveyPageMeasureRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;

@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService, MessageSourceAware {

	private MessageSource msgSrc;

	@Resource(type = SurveyPageMeasureRepository.class)
	SurveyPageMeasureRepository spmr;

	public Map<String, Table<Integer, String, String>> createDataDictionary() {

		Multimap<Survey, Measure> surveyMeasuresMap = buildSurveyMeasuresMap();

		Map<String, Table<Integer, String, String>> dataDictionary = Maps.newHashMap();

		for (Survey s : surveyMeasuresMap.keySet()) {
			Table<Integer, String, String> table = buildTable(s, surveyMeasuresMap.get(s));
			dataDictionary.put(s.getName(), table);
		}
		return dataDictionary;
	}

	private Multimap<Survey, Measure> buildSurveyMeasuresMap() {
		List<SurveyPageMeasure> spmList = spmr.findAll();
		Multimap<Survey, Measure> smMap = ArrayListMultimap.create();
		for (SurveyPageMeasure spm : spmList) {
			smMap.put(spm.getSurveyPage().getSurvey(), spm.getMeasure());
		}
		return smMap;
	}

	private Table<Integer, String, String> buildTable(Survey s,
			Collection<Measure> surveyMeasures) {

		Table<Integer, String, String> t = HashBasedTable.create();

		// test code
		for (Measure m : surveyMeasures) {
			int mId = m.getMeasureId();
			t.put(mId, msgSrc.getMessage("data.dict.column.part", null, null), "todo");
			t.put(mId, msgSrc.getMessage("data.dict.column.ques.type", null, null), m.getMeasureType().getName());
			t.put(mId, msgSrc.getMessage("data.dict.column.ques.desc", null, null), m.getMeasureText());
			t.put(mId, msgSrc.getMessage("data.dict.column.var.name", null, null), m.getVariableName()==null?"exportName":m.getVariableName());
			t.put(mId, msgSrc.getMessage("data.dict.column.vals.range", null, null), "todo");
			t.put(mId, msgSrc.getMessage("data.dict.column.vals.desc", null, null), "todo");
			t.put(mId, msgSrc.getMessage("data.dict.column.data.val", null, null), "todo");
			t.put(mId, msgSrc.getMessage("data.dict.column.followup", null, null), "todo");
			t.put(mId, msgSrc.getMessage("data.dict.column.skiplevel", null, null), "todo");
		}

		return t;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.msgSrc = messageSource;
	}

}
