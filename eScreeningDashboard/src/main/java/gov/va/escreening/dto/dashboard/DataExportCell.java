package gov.va.escreening.dto.dashboard;

public class DataExportCell {
	String columnName;
	String cellValue;
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	
	public String getCellValue() {
		return cellValue;
	}
	public void setCellValue(String cellValue) {
		this.cellValue = cellValue;
	}
	
	public DataExportCell() {}
	
	public DataExportCell(String columnName, String cellValue) {
		this.columnName = columnName;
		this.cellValue = cellValue;
	}
	
	@Override
	public String toString() {
		return "DataExportCell [columnName=" + columnName + ", cellValue="
				+ cellValue + "]";
	}

}