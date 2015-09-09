package gov.va.escreening.async.exec;

import com.google.common.base.Throwables;
import gov.va.escreening.service.export.DataDictionary;
import gov.va.escreening.service.export.DataDictionaryService;
import junit.framework.AssertionFailedError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.concurrent.*;

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
    public void callDataDictionary() {
        dds.tryPrepareDataDictionary(false);
        try {
            System.out.println("Sleeping..." + Thread.currentThread().getName());
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Shutting now..."+dd);
    }
}