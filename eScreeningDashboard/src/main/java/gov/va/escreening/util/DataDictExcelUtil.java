package gov.va.escreening.util;

import com.google.common.collect.Sets;
import com.google.common.collect.Table;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Created by munnoo on 1/18/15.
 */
@Component("dataDictAsExcelUtil")
public class DataDictExcelUtil implements MessageSourceAware{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private MessageSource messageSource;

    private Set<String> allowedHeaderColumns;
    @PostConstruct
    private void initializeHeaderColumns(){
        String headerColumnKeys=messageSource.getMessage("data.dict.column.show.list", null, null);
        String[] headerColKeyArray=headerColumnKeys.split(",");

        allowedHeaderColumns = Sets.newHashSet();
        for(String colKey:headerColKeyArray){
            allowedHeaderColumns.add(messageSource.getMessage(colKey.trim(), null, null));
        }
    }
    public void buildDdAsExcel(Map model, HSSFWorkbook workbook) {

        Map<String, Table<String, String, String>> dataDictionary = (Map<String, Table<String, String, String>>) model.get("dataDictionary");

        CellStyle csWrapText = workbook.createCellStyle();
        csWrapText.setWrapText(true);

        for (String surveyName : dataDictionary.keySet()) {
            if (logger.isDebugEnabled()) {
                logger.debug("creating excel sheet for module:" + surveyName);
            }

            HSSFSheet sheet = workbook.createSheet(surveyName);

            Table<String, String, String> measuresTable = dataDictionary.get(surveyName);
            setExcelHeader(workbook, surveyName, sheet, measuresTable.columnKeySet());
            setExcelRows(workbook, surveyName, sheet, measuresTable, csWrapText);

            sheet.autoSizeColumn(0, true);
            sheet.setColumnWidth(1, 256 * 40);
            sheet.autoSizeColumn(2, true);
            sheet.autoSizeColumn(3, true);
            sheet.setColumnWidth(4, 256 * 20);
            sheet.autoSizeColumn(5, true);
            sheet.autoSizeColumn(6, true);
            sheet.autoSizeColumn(7, true);
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
            if (isAllowed(header)) {
                HSSFCell cell = headerRow.createCell(columnIndex);
                cell.setCellValue(header.substring(2));
                columnIndex++;
            }
        }
        return headerRow;
    }

    private boolean isAllowed(String header) {
        return this.allowedHeaderColumns.contains(header.trim());
    }

    private HSSFRow createRow_SurveyName(HSSFWorkbook workbook,
                                         String surveyName, HSSFSheet excelSheet, int row) {

        HSSFRow surveyNameWithDateRow = excelSheet.createRow(row);

        HSSFCell cell = surveyNameWithDateRow.createCell(0);
        cell.setCellValue(String.format("%s as of %s", surveyName, LocalDate.now().toString("dd-MMM-yyyy", Locale.US)));

        return surveyNameWithDateRow;
    }

    private void setExcelRows(HSSFWorkbook workbook, String surveyName,
                              HSSFSheet excelSheet, Table<String, String, String> measuresTable,
                              CellStyle cs) {

        int row = 4;
        for (String rowId : measuresTable.rowKeySet()) {
            int columnIndex = 0;
            HSSFRow excelRow = excelSheet.createRow(row++);

            StringBuilder debugString = new StringBuilder();
            for (Map.Entry<String, String> rowData : measuresTable.row(rowId).entrySet()) {
                if (logger.isDebugEnabled()) {
                    debugString.append(String.format("%s:%s:%s$", rowId, rowData.getKey(), rowData.getValue()));
                }

                if (isAllowed(rowData.getKey().trim())) {
                    HSSFCell aCell = excelRow.createCell(columnIndex);
                    aCell.setCellValue(rowData.getValue());

                    if (columnIndex == 1 || columnIndex == 4) {
                        aCell.setCellStyle(cs);
                    }

                    columnIndex++;
                }
            }

            if (logger.isDebugEnabled()) {
                logger.debug(debugString.toString());
            }
        }
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource=messageSource;
    }
}
