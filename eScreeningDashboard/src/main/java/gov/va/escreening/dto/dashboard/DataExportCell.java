package gov.va.escreening.dto.dashboard;

public class DataExportCell {
	String columnName;
	String cellValue;
	boolean other;

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

	public DataExportCell(String columnName, String cellValue, boolean other) {
		setColumnName(columnName);
		setCellValue(cellValue);
		setOther(other);
	}

	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	@Override
	public String toString() {
		return "name=" + columnName + ", val=" + cellValue+", other="+other;
	}

}