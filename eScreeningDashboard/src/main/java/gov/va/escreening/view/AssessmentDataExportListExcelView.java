package gov.va.escreening.view;

import gov.va.escreening.dto.dashboard.AssessmentDataExport;
import gov.va.escreening.dto.dashboard.DataExportCell;
import gov.va.escreening.exception.DataExportException;
import gov.va.escreening.service.export.ExportDataService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.view.document.AbstractExcelView;

// TODO:
// Note: the current version of AbstractExcelView uses HSSFWorkbook which is for Excel 2007 and only supports 256
// columns
// IF we continue to useAbstractExcelView then we will need to rewrite it to use XSSF which can support 65000 columns.

public class AssessmentDataExportListExcelView extends AbstractExcelView {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentDataExportListExcelView.class);

	private final String defaultErrorMsg = "An error occured while generating the requested export.  Please contact technical support for assistance.";

	@Autowired
	private ExportDataService exportDataService;

	public void setExportDataService(ExportDataService exportDataService) {
		this.exportDataService = exportDataService;
	}

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HSSFSheet excelSheet = workbook.createSheet("Assessment Data Export");

			AssessmentDataExport dataExport = (AssessmentDataExport) model.get("dataExportList");
			if (dataExport == null || dataExport.getTableContent() == null || dataExport.getTableContent().size() == 0) {
				replaceSpreadSheetContentWithMessage(workbook, "No assessments matched the specified filter criteria.");
				logger.debug(String.format("No assessments matched the specified filter criteria of %s.", dataExport.getFilterOptions()));
			} else {
				setExcelHeader(excelSheet, dataExport.getTableContent());
				setExcelRows(excelSheet, dataExport.getTableContent());
				exportDataService.saveDataExport(workbook, dataExport);
				logger.debug(String.format("Export was successful. Created spread sheet with %s rows.  The filter criteria was: %s.", dataExport.getTableContent().size(), dataExport.getFilterOptions().toString()));
			}
		} catch (DataExportException cdnmce) {
			logger.error(cdnmce.getClass().getName() + " " + cdnmce.getMessage());
			replaceSpreadSheetContentWithMessage(workbook, defaultErrorMsg);
		} catch (Exception e) {
			logger.error(e.getClass().getName() + " " + e.getMessage());
			replaceSpreadSheetContentWithMessage(workbook, defaultErrorMsg);
		}
	}

	private void setExcelHeader(HSSFSheet excelSheet,
			List<List<DataExportCell>> exportList) {
		HSSFRow excelHeader = excelSheet.createRow(0);

		List<DataExportCell> firstRow = exportList.get(0);
		int columnIndex = 0;
		for (DataExportCell header : firstRow) {
			excelHeader.createCell(columnIndex).setCellValue(header.getColumnName());
			columnIndex++;
		}
	}

	private void setExcelRows(HSSFSheet excelSheet,
			List<List<DataExportCell>> exportList) {
		HSSFRow header = excelSheet.getRow(0);

		int record = 1;
		for (List<DataExportCell> exportRow : exportList) {
			HSSFRow excelRow = excelSheet.createRow(record++);

			int columnIndex = 0;
			for (DataExportCell cell : exportRow) {
				validateCellMatchesColumn(columnIndex, cell, header);
				excelRow.createCell(columnIndex).setCellValue(cell.getCellValue());
				columnIndex++;
			}
		}
	}

	private void replaceSpreadSheetContentWithMessage(HSSFWorkbook workbook,
			String message) {

		// remove any existing sheets
		int numSheets = workbook.getNumberOfSheets();
		if (numSheets > 0) {
			for (int index = 0; index < numSheets; index++) {
				workbook.removeSheetAt(index);
			}
		}

		HSSFSheet excelSheet = workbook.createSheet("Assessment Data Export");
		HSSFRow excelRow = excelSheet.createRow(0);
		excelRow.createCell(0).setCellValue(message);
	}

	private boolean validateCellMatchesColumn(int columnIndex,
			DataExportCell cell, HSSFRow header) {
		if (header.getCell(columnIndex) == null)
			throw new DataExportException(String.format("A column does not exist for the referenced index of: %s", columnIndex));

		HSSFCell headerColumn = header.getCell(columnIndex);
		String headerName = headerColumn.getStringCellValue().toLowerCase();
		if (!cell.getColumnName().equalsIgnoreCase(headerName))
			throw new DataExportException(String.format("The cell name of: %s did not match the column name of: %s.", cell.getColumnName().toLowerCase(), headerName.toLowerCase()));

		return true;
	}
}