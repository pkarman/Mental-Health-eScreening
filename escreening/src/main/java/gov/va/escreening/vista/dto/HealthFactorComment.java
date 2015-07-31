package gov.va.escreening.vista.dto;

/**
 * Created by pouncilt on 5/6/14.
 */
public class HealthFactorComment {
    private static final String dataElementName = "COM";
    private String text = "@";  // The @ Symbol means there is no text for this comment.
    private Integer sequenceNumber = null;

    public HealthFactorComment(String text, Integer sequenceNumber) {
        if(sequenceNumber == null) throw new NullPointerException("Parameter \"sequenceNumber\" cannot be Null.");
        if(sequenceNumber < 1) throw new IllegalArgumentException("Parameter \"sequenceNumber\" must be a positive integer.");
        if(text != null && text.length() > 245) throw new IllegalArgumentException("Parameter \"text\" cannot be longer than 245 characters.");

        if (text != null &&  text.trim().length() > 0) this.text = text;
        this.sequenceNumber = sequenceNumber;
    }

    public static String getDataElementName() {
        return dataElementName;
    }

    public String getText() {
        return text;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public String getSequenceNumberAsString() {
        return (sequenceNumber==null || sequenceNumber < 1)? "": sequenceNumber.toString();
    }

    @Override
    public String toString() {
        return  dataElementName + "^" + getSequenceNumberAsString() + "^" + text;
    }
}
