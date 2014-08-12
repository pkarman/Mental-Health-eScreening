package gov.va.escreening.dto.ae;

import java.io.Serializable;
import java.util.List;

public class CompletionResponse implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String completionText;
    private List<String> summaryNotes;
    
    public String getCompletionText() {
        return completionText;
    }
    public void setCompletionText(String completionText) {
        this.completionText = completionText;
    }
    public List<String> getSummaryNotes() {
        return summaryNotes;
    }
    public void setSummaryNotes(List<String> summaryNotes) {
        this.summaryNotes = summaryNotes;
    }
}
