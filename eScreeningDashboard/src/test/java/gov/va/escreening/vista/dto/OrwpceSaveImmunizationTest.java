package gov.va.escreening.vista.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OrwpceSaveImmunizationTest {

    @Test
    public void toVistaRecord1Test() {

        String immunizationIen = "731";
        String immunizationName = "YELLOW FEAVER";
        Boolean isContraindicated = false;
        String reaction = "RASH ON ARM";
        Boolean removeImmunization = false;
        Integer sequenceNumber = 3;
        String series = "immunseries01";

        String expected = "IMM+^731^^YELLOW FEAVER^immunseries01^^RASH ON ARM^0^^3";
        
        OrwpceSaveImmunization orwpceSaveImmunization = new OrwpceSaveImmunization();
        orwpceSaveImmunization.setImmunizationIen(immunizationIen);
        orwpceSaveImmunization.setImmunizationName(immunizationName);
        orwpceSaveImmunization.setIsContraindicated(isContraindicated);
        orwpceSaveImmunization.setReaction(reaction);
        orwpceSaveImmunization.setRemoveImmunization(removeImmunization);;
        orwpceSaveImmunization.setSequenceNumber(sequenceNumber);
        orwpceSaveImmunization.setSeries(series);

        String actual = orwpceSaveImmunization.toVistaRecord();

        assertEquals(expected, actual);
    }
    
    @Test
    public void toVistaRecord2Test() {

        String immunizationIen = "731";
        String immunizationName = "YELLOW FEAVER";
        Boolean isContraindicated = true;
        String reaction = null;
        Boolean removeImmunization = true;
        Integer sequenceNumber = 4;
        String series = "immunseries03";

        String expected = "IMM-^731^^YELLOW FEAVER^immunseries03^^@^1^^4";
        
        OrwpceSaveImmunization orwpceSaveImmunization = new OrwpceSaveImmunization();
        orwpceSaveImmunization.setImmunizationIen(immunizationIen);
        orwpceSaveImmunization.setImmunizationName(immunizationName);
        orwpceSaveImmunization.setIsContraindicated(isContraindicated);
        orwpceSaveImmunization.setReaction(reaction);
        orwpceSaveImmunization.setRemoveImmunization(removeImmunization);;
        orwpceSaveImmunization.setSequenceNumber(sequenceNumber);
        orwpceSaveImmunization.setSeries(series);

        String actual = orwpceSaveImmunization.toVistaRecord();

        assertEquals(expected, actual);
    }
    
    @Test
    public void toVistaRecord3Test() {

        String immunizationIen = "731";
        String immunizationName = "YELLOW FEAVER";
        Boolean isContraindicated = true;
        String reaction = null;
        Boolean removeImmunization = false;
        Integer sequenceNumber = 5;
        String series = null;

        String expected = "IMM+^731^^YELLOW FEAVER^@^^@^1^^5";
        
        OrwpceSaveImmunization orwpceSaveImmunization = new OrwpceSaveImmunization();
        orwpceSaveImmunization.setImmunizationIen(immunizationIen);
        orwpceSaveImmunization.setImmunizationName(immunizationName);
        orwpceSaveImmunization.setIsContraindicated(isContraindicated);
        orwpceSaveImmunization.setReaction(reaction);
        orwpceSaveImmunization.setRemoveImmunization(removeImmunization);;
        orwpceSaveImmunization.setSequenceNumber(sequenceNumber);
        orwpceSaveImmunization.setSeries(series);

        String actual = orwpceSaveImmunization.toVistaRecord();

        assertEquals(expected, actual);
    }
}
