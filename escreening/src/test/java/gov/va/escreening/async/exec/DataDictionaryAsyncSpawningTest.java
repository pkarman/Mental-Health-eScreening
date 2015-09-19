package gov.va.escreening.async.exec;

import gov.va.escreening.service.export.DataDictionary;
import gov.va.escreening.service.export.DataDictionaryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.assertTrue;

/**
 * Created by munnoo on 9/8/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})

public class DataDictionaryAsyncSpawningTest {
    @Resource(name = "dataDictionaryService")
    DataDictionaryService dds;
    @Resource(name = "theDataDictionary")
    DataDictionary dd;

    @Test
    public void noForceCreateDataDictionary() {
        dd.markNotReady();
        dds.tryPrepareDataDictionary(false);
        assertTrue("Data Dictionary should have initialized as this was a blocking call, because future was waiting", dd.isReady());
    }

    @Test
    public void forceCreateDataDictionary() {
        dd.markNotReady();
        dds.tryPrepareDataDictionary(true);
        assertTrue("Data Dictionary should have initialized as this was a blocking call", dd.isReady());
    }
}