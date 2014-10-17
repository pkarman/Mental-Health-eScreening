package gov.va.escreening.dto.dashboard;

public class DataExportCell {
	String columnName;
	String cellValue;

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

	public DataExportCell() {
	}

	public DataExportCell(String columnName, String cellValue) {
		setColumnName(columnName);
		setCellValue(cellValue);
	}

	@Override
	public String toString() {
		return "name=" + columnName + ", val=" + cellValue;
	}

}