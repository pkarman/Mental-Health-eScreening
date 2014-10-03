package gov.va.escreening.xport;

import gov.va.escreening.service.export.DataDictionaryService;
import gov.va.escreening.view.DataDictionaryExcelView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.support.StaticWebApplicationContext;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.io.Files;

@Transactional
// this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml" })
public class DataDictionaryTest {
	Logger logger = LoggerFactory.getLogger(DataDictionaryTest.class);

	@Resource(type = DataDictionaryService.class)
	DataDictionaryService dds;

	private MockServletContext servletCtx;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private StaticWebApplicationContext webAppCtx;

	@Before
	public void setUp() {
		servletCtx = new MockServletContext("org/springframework/web/servlet/view/document");
		request = new MockHttpServletRequest(servletCtx);
		response = new MockHttpServletResponse();
		webAppCtx = new StaticWebApplicationContext();
		webAppCtx.setServletContext(servletCtx);
	}

	@Test
	public void createDataDictionary() throws Exception {
		Map<String, Table<String, String, String>> dataDictionary = dds.createDataDictionary();

		logDataDictionary(dataDictionary);

		viewDataDictionaryAsExcel(dataDictionary);
	}

	private void viewDataDictionaryAsExcel(
			Map<String, Table<String, String, String>> dataDictionary) throws Exception {

		AbstractExcelView excelView = new DataDictionaryExcelView();

		Map<String, Map<String, Table<String, String, String>>> model = Maps.newHashMap();
		model.put("dataDictionary", dataDictionary);
		excelView.render(model, request, response);

		String documentDirName=System.getProperty("user.home")+File.separator+"Documents";
		writeAsExcelFile(documentDirName+File.separator+"data_dict_test_"+System.nanoTime()+".xls", response);
	}

	private void writeAsExcelFile(String excelFile,
			MockHttpServletResponse response) throws IOException {
		File dest = new File(excelFile);
		FileOutputStream byteSink = new FileOutputStream(dest);
		byteSink.write(response.getContentAsByteArray());
		byteSink.flush();
		byteSink.close();
		//Assert.assertSame(Files.toByteArray(dest), response.getContentAsByteArray());
	}

	private void logDataDictionary(
			Map<String, Table<String, String, String>> dataDictionary) {
		// for each row key
		for (String surveyName : dataDictionary.keySet()) {
			logger.info("Survey name:" + surveyName);
			Table<String, String, String> measuresTable = dataDictionary.get(surveyName);
			for (String measureRowNum : measuresTable.rowKeySet()) {
				StringBuilder measureColumns = new StringBuilder();
				for (Entry<String, String> rowData : measuresTable.row(measureRowNum).entrySet()) {
					measureColumns.append(String.format("%s:%s$", rowData.getKey(), rowData.getValue()));
				}
				logger.info(measureColumns.toString());
			}
		}
	}
}