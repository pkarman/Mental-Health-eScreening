package gov.va.escreening.view;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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

		for (String surveyName : dataDictionary.keySet()) {
			if (logger.isDebugEnabled()) {
				logger.debug("Survey name:" + surveyName);
			}

			HSSFSheet sheet = workbook.createSheet(surveyName);

			Table<String, String, String> measuresTable = dataDictionary.get(surveyName);
			setExcelHeader(surveyName, sheet, measuresTable.columnKeySet());
			setExcelRows(surveyName, sheet, measuresTable);
		}

	}

	private void setExcelHeader(String surveyName, HSSFSheet excelSheet,
			Set<String> headerColumns) {

		int row = 0;
		HSSFRow excelHeader = excelSheet.createRow(row);
		excelHeader.createCell(0).setCellValue(String.format("%s as of %s",surveyName, new LocalDate().toString("dd-MMM-yyyy", Locale.US)));

		excelHeader = excelSheet.createRow(row + 2);

		int columnIndex = 0;
		for (String header : headerColumns) {
			excelHeader.createCell(columnIndex).setCellValue(header.substring(2));
			columnIndex++;
		}
	}

	private void setExcelRows(String surveyName, HSSFSheet excelSheet,
			Table<String, String, String> measuresTable) {

		int row = 4;
		for (String rowId : measuresTable.rowKeySet()) {
			int columnIndex = 0;
			HSSFRow excelRow = excelSheet.createRow(row++);

			StringBuilder debugString = new StringBuilder();
			for (Entry<String, String> rowData : measuresTable.row(rowId).entrySet()) {
				debugString.append(String.format("%s:%s:%s$", rowId, rowData.getKey(), rowData.getValue()));

				excelRow.createCell(columnIndex).setCellValue(rowData.getValue());
				columnIndex++;
			}

			if (logger.isDebugEnabled()) {
				logger.debug(debugString.toString());
			}
		}
	}
}