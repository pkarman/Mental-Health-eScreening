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
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService, MessageSourceAware {

	private MessageSource msgSrc;

	@Resource(name = "dataDictionaryHelper")
	DataDictionaryHelper ddh;

	@Resource(type = SurveyPageMeasureRepository.class)
	SurveyPageMeasureRepository spmr;

	@Override
	@Transactional(readOnly = true)
	public Map<String, Table<String, String, String>> createDataDictionary() {

		Multimap<Survey, Measure> surveyMeasuresMap = buildSurveyMeasuresMap();

		/**
		 * <pre>
		 * 
		 * 	pt#1: each survey will have its own table 
		 * 	pt#2: each table has rows 
		 * 	pt#3: each row has columns 
		 * 	pt#4: and each column has values
		 * 
		 * 	[survey]
		 * 		[Table]
		 * 	  		==> row column=value
		 * 	  		==> row column=value
		 * 	  		==> row column=value
		 * 
		 * </pre>
		 */
		Map<String, Table<String, String, String>> dataDictionary = Maps.newTreeMap();

		for (Survey s : surveyMeasuresMap.keySet()) {
			Table<String, String, String> surveyTable = buildSurveyTable(s, surveyMeasuresMap.get(s));
			dataDictionary.put(s.getName(), surveyTable);
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

	private Table<String, String, String> buildSurveyTable(Survey s, Collection<Measure> surveyMeasures) {

		Table<String, String, String> t = TreeBasedTable.create();
		for (Measure m : surveyMeasures) {
			ddh.buildDataDictionary(s, m, t);
		}

		return t;
	}

	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.msgSrc = messageSource;
	}
}
