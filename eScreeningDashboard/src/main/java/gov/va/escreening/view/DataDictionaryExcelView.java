package gov.va.escreening.view;

import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.va.escreening.util.DataDictExcelUtil;
import gov.va.escreening.util.DataExportAndDictionaryUtil;
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

	@Resource(name="dataDictAsExcelUtil")
	DataDictExcelUtil ddutil;

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ddutil.buildDdAsExcel(model, workbook);
	}
}