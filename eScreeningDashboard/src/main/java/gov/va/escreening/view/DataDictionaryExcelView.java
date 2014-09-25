package gov.va.escreening.view;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.google.common.collect.Table;

public class DataDictionaryExcelView extends AbstractExcelView {

	private static final Logger logger = LoggerFactory.getLogger(DataDictionaryExcelView.class);

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Table<String, String, String>> dataDictionary = (Map<String, Table<String, String, String>>) model.get("dataDictionary");

		CellStyle cs = workbook.createCellStyle();
		cs.setWrapText(true);

		for (String surveyName : dataDictionary.keySet()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Survey name:" + surveyName);
			}

			HSSFSheet sheet = workbook.createSheet(surveyName);

			Table<String, String, String> measuresTable = dataDictionary.get(surveyName);
			setExcelHeader(workbook, surveyName, sheet, measuresTable.columnKeySet());
			setExcelRows(workbook, surveyName, sheet, measuresTable, cs);
		}
	}

	private void setExcelHeader(HSSFWorkbook workbook, String surveyName,
			HSSFSheet excelSheet, Set<String> headerColumns) {

		int row = 0;
		HSSFRow surveyRow = createRow_SurveyName(workbook, surveyName, excelSheet, row);

		HSSFRow headerRow = createRow_Header(workbook, excelSheet, headerColumns, row++);
	}

	private HSSFRow createRow_Header(HSSFWorkbook workbook,
			HSSFSheet excelSheet, Set<String> headerColumns, int row) {

		HSSFRow headerRow = excelSheet.createRow(row + 2);

		int columnIndex = 0;
		for (String header : headerColumns) {
			HSSFCell cell = headerRow.createCell(columnIndex);
			cell.setCellValue(header.substring(2));
			columnIndex++;
		}
		return headerRow;
	}

	private HSSFRow createRow_SurveyName(HSSFWorkbook workbook,
			String surveyName, HSSFSheet excelSheet, int row) {

		HSSFRow surveyNameWithDateRow = excelSheet.createRow(row);

		HSSFCell cell = surveyNameWithDateRow.createCell(0);
		cell.setCellValue(String.format("%s as of %s", surveyName, new LocalDate().toString("dd-MMM-yyyy", Locale.US)));

		return surveyNameWithDateRow;
	}

	
	private void setExcelRows(HSSFWorkbook workbook, String surveyName,
			HSSFSheet excelSheet, Table<String, String, String> measuresTable, CellStyle cs) {

		int row = 4;
		for (String rowId : measuresTable.rowKeySet()) {
			int columnIndex = 0;
			HSSFRow excelRow = excelSheet.createRow(row++);

			StringBuilder debugString = new StringBuilder();
			for (Entry<String, String> rowData : measuresTable.row(rowId).entrySet()) {
				if (logger.isDebugEnabled()) {
					debugString.append(String.format("%s:%s:%s$", rowId, rowData.getKey(), rowData.getValue()));
				}

				HSSFCell aCell = excelRow.createCell(columnIndex);
				aCell.setCellValue(rowData.getValue());

				if (columnIndex == 1) {
					aCell.setCellStyle(cs);
				}
				
				columnIndex++;
			}

			if (logger.isDebugEnabled()) {
				logger.debug(debugString.toString());
			}
		}
	}
}