package gov.va.escreening.vista.dto;

import static org.junit.Assert.assertEquals;

import org.joda.time.DateTime;
import org.junit.Test;

public class OrwpceSaveVisitTest {

    @Test
    public void toVistaRecordVisitDateTest() {

        DateTime visitDate = new DateTime(2014, 3, 9, 1, 2, 3);

        String expected = "VST^DT^3140309.010203";

        OrwpceSaveVisit orwpceSaveVisit = new OrwpceSaveVisit();
        orwpceSaveVisit.setVisitType(VisitTypeEnum.ENCOUNTER_DATE);
        orwpceSaveVisit.setVisitDate(visitDate.toDate());

        String actual = orwpceSaveVisit.toVistaRecord();

        assertEquals(expected, actual);
    }

    @Test
    public void toVistaRecordVisitPatientTest() {

        String veteranIen = "1234";

        String expected = "VST^PT^1234";

        OrwpceSaveVisit orwpceSaveVisit = new OrwpceSaveVisit();
        orwpceSaveVisit.setVisitType(VisitTypeEnum.PATIENT);
        orwpceSaveVisit.setPatientIen(veteranIen);

        String actual = orwpceSaveVisit.toVistaRecord();

        assertEquals(expected, actual);
    }

    @Test
    public void toVistaRecordVisitEncounterEventTest() {

        String expected = "VST^VC^E";

        OrwpceSaveVisit orwpceSaveVisit = new OrwpceSaveVisit();
        orwpceSaveVisit.setVisitType(VisitTypeEnum.ENCOUNTER_SERVICE_CATEGORY);
        orwpceSaveVisit.setVistaServiceCategory(VistaServiceCategoryEnum.EVENT);

        String actual = orwpceSaveVisit.toVistaRecord();

        assertEquals(expected, actual);
    }

    @Test
    public void toVistaRecordVisitParentIenTest() {

        String parentVisitIen = "4545";

        String expected = "VST^PR^4545";

        OrwpceSaveVisit orwpceSaveVisit = new OrwpceSaveVisit();
        orwpceSaveVisit.setVisitType(VisitTypeEnum.PARENT_VISIT_IEN_HIST);
        orwpceSaveVisit.setParentVisitIen(parentVisitIen);

        String actual = orwpceSaveVisit.toVistaRecord();

        assertEquals(expected, actual);
    }
    
    @Test
    public void toVistaRecordVisitOutsideLocationTest() {

        String locationIen = "3443";
        String freeText = null;

        String expected = "VST^OL^3443^";

        OrwpceSaveVisit orwpceSaveVisit = new OrwpceSaveVisit();
        orwpceSaveVisit.setVisitType(VisitTypeEnum.OUTSIDE_LOCATION_HIST);
        orwpceSaveVisit.setLocationIen(locationIen);
        orwpceSaveVisit.setFreeText(freeText);

        String actual = orwpceSaveVisit.toVistaRecord();

        assertEquals(expected, actual);
    }
    
    @Test
    public void toVistaRecordVisitOutsideLocationOtherTest() {

        String locationIen = "2558";
        String freeText = "032";

        String expected = "VST^OL^0^032";

        OrwpceSaveVisit orwpceSaveVisit = new OrwpceSaveVisit();
        orwpceSaveVisit.setVisitType(VisitTypeEnum.OUTSIDE_LOCATION_HIST);
        orwpceSaveVisit.setLocationIen(locationIen);
        orwpceSaveVisit.setFreeText(freeText);

        String actual = orwpceSaveVisit.toVistaRecord();

        assertEquals(expected, actual);
    }
}
