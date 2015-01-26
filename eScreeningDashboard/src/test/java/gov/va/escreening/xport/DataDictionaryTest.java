package gov.va.escreening.xport;

import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.va.escreening.service.export.DataDictionaryService;
import gov.va.escreening.util.DataExportAndDictionaryUtil;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

@Transactional
// this is to ensure all tests do not leave trace, so they are repeatable.
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class DataDictionaryTest {
    Logger logger = LoggerFactory.getLogger(DataDictionaryTest.class);

    @Resource(type = DDCache.class)
    DDCache ddCache;

    @Resource(name="dataExportAndDictionaryUtil")
    DataExportAndDictionaryUtil dedUtil;

    @Test
    public void createDataDictionary() throws Exception {
        Map<String, Table<String, String, String>> dataDictionary = (Map<String, Table<String, String, String>>)ddCache.getDDCache();
    }

    @Test
    public void saveDDAsExcel() throws Exception {
        Map<String, Table<String, String, String>> dataDictionary = (Map<String, Table<String, String, String>>)ddCache.getDDCache();
        String tstDirName=System.getProperty("user.home") + File.separator + "Documents" + File.separator+"escrTestOut";
        dedUtil.saveDataDictionaryAsExcel(tstDirName, dataDictionary, new Date());
    }


    private Map<String, Table<String, String, String>> reconstructDataDictionary(String ddAsString) {
        Gson gson1 = new GsonBuilder().create();
        Map raw = gson1.fromJson(ddAsString, Map.class);

        Map<String, Table<String, String, String>> dd = Maps.newLinkedHashMap();

        for (Object o : raw.keySet()) {
            String moduleName = (String) o;
            Table<String, String, String> t = TreeBasedTable.create();
            Map m = (Map) raw.get(moduleName);
            Map m1 = (Map) m.get("backingMap");
            for (Object k : m1.keySet()) {
                String key = (String) k;
                Map<String, String> m2 = (Map<String, String>) m1.get(key);
                for (String key1 : m2.keySet()) {
                    t.put(key, key1, m2.get(key1));
                }
            }
            dd.put(moduleName, t);
        }
        return dd;
    }

//    @Test
//    public void zipDirTest() throws Exception {
//        String documentDirName = System.getProperty("user.home") + File.separator + "Documents";
//        String zipFileName = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "ddPlusExport_" + ISODateTimeFormat.dateTime().print(DateTime.now()) + ".zip";
//
//        dedUtil.zipDirectory(documentDirName, zipFileName, response);
//    }


    @Test
    public void removeTernaryTest() {
        String ternary = "(([tbi_week_memory]?1:0) + ([tbi_week_balance]?1:0) + ([tbi_week_light]?1:0) + ([tbi_week_irritable]?1:0) + ([tbi_week_headache]?1:0) + ([tbi_week_sleep]?1:0))";
        String refined = ternary.replaceAll("[?]", "").replaceAll("1:0", "");
        int i = 0;
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