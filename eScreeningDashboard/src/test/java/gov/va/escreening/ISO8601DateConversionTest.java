package gov.va.escreening;

import org.junit.Assert;
import org.junit.Test;

import java.text.SimpleDateFormat;

/**
 * Created by pouncilt on 7/16/14.
 */
public class ISO8601DateConversionTest {

    @Test
    public void testConvertISO8601DateStringToDate() throws Exception{
        String iso8601Date = "2014-07-16T12:18:30.000-0600";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Assert.assertNotNull(dateFormat.parse(iso8601Date));
    }
}
