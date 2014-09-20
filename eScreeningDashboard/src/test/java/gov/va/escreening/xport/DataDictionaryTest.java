package gov.va.escreening.xport;

import gov.va.escreening.service.DataDictionaryService;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Table;

@Transactional
// this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class DataDictionaryTest {
	Logger logger = LoggerFactory.getLogger(DataDictionaryTest.class);

	@Resource(type = DataDictionaryService.class)
	DataDictionaryService dds;

	@Test
	public void createDataDictionary() {
		Map<String, Table<Integer, String, String>> dataDictionary = dds.createDataDictionary();
		logDataDictionary(dataDictionary);
		viewDataDictionaryAsExcel(dataDictionary);
	}

	private void viewDataDictionaryAsExcel(
			Map<String, Table<Integer, String, String>> dataDictionary) {

	}

	private void logDataDictionary(
			Map<String, Table<Integer, String, String>> dataDictionary) {
		// for each row key
		for (String surveyName : dataDictionary.keySet()) {
			logger.info("Survey name:" + surveyName);
			Table<Integer, String, String> measuresTable = dataDictionary.get(surveyName);
			for (Integer measureRowNum : measuresTable.rowKeySet()) {
				StringBuilder measureColumns = new StringBuilder();
				for (Entry<String, String> rowData : measuresTable.row(measureRowNum).entrySet()) {
					measureColumns.append(String.format("%s:%s$", rowData.getKey(), rowData.getValue()));
				}
				logger.info(measureColumns.toString());
			}
		}
	}
}