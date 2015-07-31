package gov.va.escreening.dto.dashboard;

public class DataExportCell {
    String columnName;
    String cellValue;
    char dataType='r';

    public DataExportCell() {
    }

    public DataExportCell(String columnName, String cellValue) {
        setColumnName(columnName);
        setCellValue(cellValue);
    }

    public DataExportCell(String columnName, String cellValue, char dataType) {
        setColumnName(columnName);
        setCellValue(cellValue);
        setDataType(dataType);
    }

    public char getDataType() {
        return dataType;
    }

    public void setDataType(char dataType) {
        this.dataType = dataType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName == null ? "" : columnName;
    }

    public String getCellValue() {
        return cellValue;
    }

    public void setCellValue(String cellValue) {
        this.cellValue = cellValue == null ? "" : cellValue;
    }

    @Override
    public String toString() {
        return "name=" + columnName + ", val=" + cellValue + ", dataType=" + dataType;
    }

}