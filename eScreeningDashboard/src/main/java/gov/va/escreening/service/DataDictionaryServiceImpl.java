package gov.va.escreening.service;

import gov.va.escreening.entity.Measure;
import gov.va.escreening.entity.Survey;
import gov.va.escreening.repository.MeasureRepository;
import gov.va.escreening.repository.SurveyRepository;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService, MessageSourceAware {

	private MessageSource msgSrc;

	@Resource(type = MeasureRepository.class)
	MeasureRepository mr;

	@Resource(type = SurveyRepository.class)
	SurveyRepository sr;

	public Map<String, Table<Integer, String, String>> createDataDictionary() {
		Map<String, Table<Integer, String, String>> dataDictionary = Maps.newHashMap();

		List<Measure> allMeasures = mr.findAll();
		List<Survey> allSurvey = sr.findAll();

		for (Survey s : allSurvey) {
			Table<Integer, String, String> table = buildTable(s, allMeasures);
			dataDictionary.put(s.getName(), table);
		}
		return dataDictionary;
	}

	private Table<Integer, String, String> buildTable(Survey s,
			List<Measure> allMeasures) {

		Table<Integer, String, String> t = HashBasedTable.create();

		
		// test code
		for (int validMsrId = 0; validMsrId < 3; validMsrId++) {
			t.put(validMsrId, msgSrc.getMessage("data.dict.column.part", null, null), "part" + validMsrId);
			t.put(validMsrId, msgSrc.getMessage("data.dict.column.ques.type", null, null), "type" + validMsrId);
			t.put(validMsrId, msgSrc.getMessage("data.dict.column.ques.desc", null, null), "desc" + validMsrId);
			t.put(validMsrId, msgSrc.getMessage("data.dict.column.var.name", null, null), "var.name" + validMsrId);
			t.put(validMsrId, msgSrc.getMessage("data.dict.column.vals.range", null, null), "vals.range" + validMsrId);
			t.put(validMsrId, msgSrc.getMessage("data.dict.column.vals.desc", null, null), "vals.desc" + validMsrId);
			t.put(validMsrId, msgSrc.getMessage("data.dict.column.data.val", null, null), "data.val" + validMsrId);
			t.put(validMsrId, msgSrc.getMessage("data.dict.column.followup", null, null), "followup" + validMsrId);
			t.put(validMsrId, msgSrc.getMessage("data.dict.column.skiplevel", null, null), "skiplevel" + validMsrId);
		}

		return t;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.msgSrc = messageSource;
	}

}
