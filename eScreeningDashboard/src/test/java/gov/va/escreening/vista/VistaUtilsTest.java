package gov.va.escreening.vista;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by pouncilt on 4/23/14.
 */
public class VistaUtilsTest {
    @Test
    public void testVistaStringDateConversion() throws Exception {
        String vistaDateString = "32;3140422.165720;X";
        Date expectedDate = new SimpleDateFormat("yyyyMMdd.HHmmss").parse("20140422.165720");
        Date actualDate = VistaUtils.convertVistaDate(vistaDateString);
        Assert.assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testPartialVistaStringDateConversion() throws Exception {
        String vistaDateString = "3140422.165720";
        Date expectedDate = new SimpleDateFormat("yyyyMMdd.HHmmss").parse("20140422.165720");
        Date actualDate = VistaUtils.convertVistaDate(vistaDateString);
        Assert.assertEquals(expectedDate, actualDate);
    }

    @Test
    public void testStringDateConversion() throws Exception {
        String vistaDateString = "3140422";
        Date expectedDate = new SimpleDateFormat("yyyyMMdd").parse("20140422");
        Date actualDate = VistaUtils.convertVistaDate(vistaDateString);
        Assert.assertEquals(expectedDate, actualDate);
    }
}
