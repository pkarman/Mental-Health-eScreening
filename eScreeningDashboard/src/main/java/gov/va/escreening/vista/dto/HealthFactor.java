package gov.va.escreening.vista.dto;


/**
 * Created by pouncilt on 5/5/14.
 */
public class HealthFactor {
    public static enum ActionSymbols {
        Plus('+'), Minus('-');

        private final char symbol;

        ActionSymbols(char symbol){
            this.symbol = symbol;
        }
        public char getSymbol() {
            return symbol;
        }
    }
    public enum SeverityLevels {
        Minimal("M"), Moderate("MO"), Severe("H"), Normal("@");
        private final String level;

        SeverityLevels(String level) {
            this.level = level;
        }

        public String getLevel() {return level;}

    }
    private static final String dataElementName = "HF";
    private ActionSymbols actionSymbol = null;
    private String ien = null;
    private String category = ""; // optional
    private String name = null;
    private SeverityLevels level = SeverityLevels.Normal;
    private String unusedField6 = "";
    private String unusedField7 = "";
    private String unusedField8 = "";
    private String unusedField9 = "";
    private Integer sequenceNumber = 0;
    private String genRen = ""; // optional
    private HealthFactorComment healthFactorComment = null; // optional limit to 245 characters
    private boolean historicalHealthFactor = false;

    public HealthFactor(ActionSymbols actionSymbol, String ien, String name, Boolean historicalHealthFactor, Integer sequenceNumber, String commentText) {
        if(actionSymbol == null) throw new NullPointerException("Parameter \"actionSymbol\" cannot be Null.");
        if(ien == null) throw new NullPointerException("Parameter \"ien\" cannot be Null.");
        if(name == null) throw new NullPointerException("Parameter \"name\" cannot be Null.");
        if(this.sequenceNumber == null) throw new NullPointerException("Parameter \"sequenceNumber\" cannot be Null.");

        this.actionSymbol = actionSymbol;
        this.ien = ien;
        this.name = name;
        if(historicalHealthFactor != null) this.historicalHealthFactor = historicalHealthFactor;
        this.sequenceNumber = sequenceNumber;
        this.healthFactorComment = new HealthFactorComment(commentText, sequenceNumber);
    }

    public ActionSymbols getActionSymbol() {
        return actionSymbol;
    }

    public static String getDataElementName() {
        return dataElementName;
    }

    public boolean isHistoricalHealthFactor() {
        return historicalHealthFactor;
    }

    public String getIen() {
        return ien;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level.getLevel();
    }

    public String getUnusedField6() {
        return unusedField6;
    }

    public String getUnusedField7() {
        return unusedField7;
    }

    public String getUnusedField8() {
        return unusedField8;
    }

    public String getUnusedField9() {
        return unusedField9;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public String getSequenceNumberAsString() {
        return (sequenceNumber==null || sequenceNumber < 1)? "": sequenceNumber.toString();
    }

    public String getGenRen() {
        return genRen;
    }

    public HealthFactorComment getHealthFactorComment() {
        return healthFactorComment;
    }

    @Override
    public String toString() {
        return dataElementName + actionSymbol.getSymbol() + "^" + ien + "^" + category + "^" + name + "^" + level.getLevel() + "^" +
                unusedField6 + "^" + unusedField7 + "^" + unusedField8 + "^" + unusedField9 + "^" + getSequenceNumberAsString() +
                "^" + genRen;
    }

}
