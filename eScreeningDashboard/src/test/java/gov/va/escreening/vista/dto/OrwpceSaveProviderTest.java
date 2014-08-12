package gov.va.escreening.vista.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OrwpceSaveProviderTest {

    @Test
    public void toVistaRecord1Test() {

        Boolean isPrimaryPhysician = false;
        String providerIen = "777";
        String providerName = "Truman,Tom";

        String expected = "PRV^777^^^Truman,Tom^0";

        OrwpceSaveProvider orwpceSaveProvider = new OrwpceSaveProvider();
        orwpceSaveProvider.setIsPrimaryPhysician(isPrimaryPhysician);
        orwpceSaveProvider.setProviderIen(providerIen);
        orwpceSaveProvider.setProviderName(providerName);

        String actual = orwpceSaveProvider.toVistaRecord();

        assertEquals(expected, actual);
    }

    @Test
    public void toVistaRecord2Test() {

        Boolean isPrimaryPhysician = true;
        String providerIen = "888";
        String providerName = "Dune,Tony";

        String expected = "PRV^888^^^Dune,Tony^1";

        OrwpceSaveProvider orwpceSaveProvider = new OrwpceSaveProvider();
        orwpceSaveProvider.setIsPrimaryPhysician(isPrimaryPhysician);
        orwpceSaveProvider.setProviderIen(providerIen);
        orwpceSaveProvider.setProviderName(providerName);

        String actual = orwpceSaveProvider.toVistaRecord();

        assertEquals(expected, actual);
    }

}
