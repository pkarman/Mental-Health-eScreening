package gov.va.escreening.vista.dto;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;

public class OrwpceSaveHeaderTest {

    @Test
    public void toVistaRecordTest() {

        Boolean isInpatient = true;
        String visitLocationIen = "0";
        DateTime visitDate = new DateTime(2014, 3, 9, 1, 2, 3);

        String expected = "HDR^1^^0;3140309.010203;E";

        OrwpceSaveHeader orwpceSaveHeader = new OrwpceSaveHeader();
        orwpceSaveHeader.setIsInpatient(isInpatient);
        orwpceSaveHeader.setVisitLocationIen(visitLocationIen);
        orwpceSaveHeader.setVisitDate(visitDate.toDate());
        orwpceSaveHeader.setVistaServiceCategory(VistaServiceCategoryEnum.EVENT);

        String actual = orwpceSaveHeader.toVistaRecord();

        assertEquals(expected, actual);
    }

    @Test
    public void toVistaRecordTest2() {

        Boolean isInpatient = false;
        String visitLocationIen = "198";
        DateTime visitDate = new DateTime(2014, 3, 9, 1, 2, 3);

        String expected = "HDR^0^^198;3140309.010203;A";

        OrwpceSaveHeader orwpceSaveHeader = new OrwpceSaveHeader();
        orwpceSaveHeader.setIsInpatient(isInpatient);
        orwpceSaveHeader.setVisitLocationIen(visitLocationIen);
        orwpceSaveHeader.setVisitDate(visitDate.toDate());
        orwpceSaveHeader.setVistaServiceCategory(VistaServiceCategoryEnum.AMBULATORY);

        String actual = orwpceSaveHeader.toVistaRecord();

        assertEquals(expected, actual);
    }

}
