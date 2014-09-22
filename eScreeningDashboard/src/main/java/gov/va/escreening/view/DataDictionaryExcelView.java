package gov.va.escreening.view;

import gov.va.escreening.dto.dashboard.DataExportCell;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.google.common.collect.Table;

public class DataDictionaryExcelView extends AbstractExcelView {

	private static final Logger logger = LoggerFactory.getLogger(DataDictionaryExcelView.class);

	private final String defaultErrorMsg = "An error occured while generating the requested export.  Please contact technical support for assistance.";

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Table<Integer, String, String>> dataDictionary = (Map<String, Table<Integer, String, String>>) model.get("dataDictionary");

		for (String surveyName : dataDictionary.keySet()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Survey name:" + surveyName);
			}

			HSSFSheet sheet = workbook.createSheet(surveyName);

			Table<Integer, String, String> measuresTable = dataDictionary.get(surveyName);
			setExcelHeader(surveyName, sheet, measuresTable.columnKeySet());
			setExcelRows(surveyName, sheet, measuresTable);
		}

	}

	private void setExcelHeader(String surveyName, HSSFSheet excelSheet,
			Set<String> headerColumns) {

		int row = 0;
		HSSFRow excelHeader = excelSheet.createRow(row);
		excelHeader.createCell(0).setCellValue(surveyName);

		excelHeader = excelSheet.createRow(row + 2);

		int columnIndex = 0;
		for (String header : headerColumns) {
			excelHeader.createCell(columnIndex).setCellValue(header);
			columnIndex++;
		}
	}

	private void setExcelRows(String surveyName, HSSFSheet excelSheet,
			Table<Integer, String, String> measuresTable) {

		int row = 4;
		for (Integer measureRowNum : measuresTable.rowKeySet()) {
			StringBuilder debugString = new StringBuilder();
			int columnIndex = 0;
			HSSFRow excelRow = excelSheet.createRow(row++);

			for (Entry<String, String> rowData : measuresTable.row(measureRowNum).entrySet()) {
				debugString.append(String.format("%s:%s$", rowData.getKey(), rowData.getValue()));

				excelRow.createCell(columnIndex).setCellValue(rowData.getValue());
				columnIndex++;
			}

			if (logger.isDebugEnabled()) {
				logger.debug(debugString.toString());
			}
		}
	}
}