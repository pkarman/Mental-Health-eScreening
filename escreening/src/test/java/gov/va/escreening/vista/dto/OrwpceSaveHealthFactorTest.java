package gov.va.escreening.vista.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OrwpceSaveHealthFactorTest {

    @Test
    public void toVistaRecordAddTest() {
        
        String healthFactorIen = "745";
        String healthFactorName = "TMG COLONOSCOPY COMPLETED";
        
        String expected  = "HF+^745^^TMG COLONOSCOPY COMPLETED^@^^^^^1^";
        
        OrwpceSaveHealthFactor orwpceSaveHealthFactor = new OrwpceSaveHealthFactor();
        orwpceSaveHealthFactor.setRemoveHealthFactor(false);
        orwpceSaveHealthFactor.setHealthFactorIen(healthFactorIen);
        orwpceSaveHealthFactor.setHealthFactorName(healthFactorName);
        orwpceSaveHealthFactor.setSequenceNumber(1);
        
        String actual = orwpceSaveHealthFactor.toVistaRecord();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void toVistaRecordRemoveTest() {
        
        String healthFactorIen = "745";
        String healthFactorName = "TMG COLONOSCOPY COMPLETED";
        
        String expected  = "HF-^745^^TMG COLONOSCOPY COMPLETED^@^^^^^1^";
        
        OrwpceSaveHealthFactor orwpceSaveHealthFactor = new OrwpceSaveHealthFactor();
        orwpceSaveHealthFactor.setRemoveHealthFactor(true);
        orwpceSaveHealthFactor.setHealthFactorIen(healthFactorIen);
        orwpceSaveHealthFactor.setHealthFactorName(healthFactorName);
        orwpceSaveHealthFactor.setSequenceNumber(1);
        
        String actual = orwpceSaveHealthFactor.toVistaRecord();
        
        assertEquals(expected, actual);
    }
}
