package gov.va.escreening.vista.dto;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OrwpceSaveCommentTest {

    @Test
    public void toVistaRecordNoCommentTest() {
        
        String expected = "COM^1^@";
        
        OrwpceSaveComment orwpceSaveComment = new OrwpceSaveComment();
        orwpceSaveComment.setSequenceNumber(1);
        String actual = orwpceSaveComment.toVistaRecord();
        
        assertEquals(expected, actual);
    }
    
    @Test
    public void toVistaRecordCommentTest() {
        
        String expected = "COM^2^the quick brown fox";
        
        OrwpceSaveComment orwpceSaveComment = new OrwpceSaveComment();
        orwpceSaveComment.setSequenceNumber(2);
        orwpceSaveComment.setCommentText("the quick brown fox");
        String actual = orwpceSaveComment.toVistaRecord();
        
        assertEquals(expected, actual);
    }

}
