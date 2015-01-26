package gov.va.escreening.util;

import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import gov.va.escreening.dto.dashboard.AssessmentDataExport;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by munnoo on 1/15/15.
 */
@Component("dataExportAndDictionaryUtil")
public class DataExportAndDictionaryUtil implements MessageSourceAware {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    public SimpleDateFormat dateFormatter = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");
    MessageSource messageSource;
    @Resource(name = "dataDictAsExcelUtil")
    DataDictExcelUtil ddeutil;


    public String createZipFor(Map<String, Table<String, String, String>> dd, AssessmentDataExport de, OutputStream os) {

        Date now = new Date();

        try {
            // get the date as MM_DD_YY_HH_MM_SS
            String nowAsString = dateFormatter.format(now);
            // create a directory as user.home>Documents>MM_DD_YY_HH_MM_SS
            String dirName = System.getProperty("user.home") + File.separator + "Documents" + File.separator + nowAsString;

            // if dd is passed than create a file inside user.home>Documents>MM_DD_YY_HH_MM_SS representing Data Dictionary
            if (dd != null) {
                saveDataDictionaryAsExcel(dirName, dd, now);
            }

            // if dd is passed than create a file inside user.home>Documents>MM_DD_YY_HH_MM_SS representing Data Export
            if (de != null) {
                saveDataExportAsCsv(dirName, de, now);
            }

            // create a zip file name as data_archive_DD_MM_YY_HH_MM_SS.zip
            String zipFileName = "data_archive_" + nowAsString + ".zip";

            //create a zip file inside the dirName
            return zipDirectory(dirName, zipFileName, os);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Zip the contents of the directory, and save it in the zipfile
     */
    public String zipDirectory(String dir, String zipfile, OutputStream os)
            throws IOException, IllegalArgumentException {
        // Check that the directory is a directory, and get its contents
        File d = new File(dir);
        if (!d.isDirectory())
            throw new IllegalArgumentException("Not a directory:  "
                    + dir);
        String[] entries = d.list();
        byte[] buffer = new byte[4096]; // Create a buffer for copying
        int bytesRead;

        ZipOutputStream zipOut = new ZipOutputStream(os);

        for (int i = 0; i < entries.length; i++) {
            File f = new File(d, entries[i]);
            if (f.isDirectory())
                continue;//Ignore directory
            FileInputStream in = new FileInputStream(f); // Stream to read file
            ZipEntry entry = new ZipEntry(f.getName()); // Make a ZipEntry
            zipOut.putNextEntry(entry); // Store entry
            while ((bytesRead = in.read(buffer)) != -1)
                zipOut.write(buffer, 0, bytesRead);
            in.close();
        }
        zipOut.flush();
        zipOut.close();
        return zipfile;
    }

    private void saveDataDictionaryAsExcel(
            String dirName, Map<String, Table<String, String, String>> dataDictionary, Date now) throws Exception {

        Map<String, Map<String, Table<String, String, String>>> model = Maps.newHashMap();
        model.put("dataDictionary", dataDictionary);
        HSSFWorkbook workbook = new HSSFWorkbook();
        logger.debug("Created Excel Workbook from scratch");
        ddeutil.buildDdAsExcel(model, workbook);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        workbook.write(baos);
        baos.flush();
        baos.close();

        if (!(new File(dirName)).exists()) {
            new File(dirName).mkdirs();
        }
        String excelFileName = dirName + File.separator + "data_dict_" + dateFormatter.format(now) + ".xls";
        File excelFile = new File(excelFileName);
        FileOutputStream outStream = null;
        try {
            outStream = new FileOutputStream(excelFile);
            outStream.write(baos.toByteArray());
        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        } finally {
            if (outStream != null) {
                try {
                    outStream.flush();
                    outStream.close();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }
    }

    private void saveDataExportAsCsv(
            String dirName, AssessmentDataExport dataExport, Date now) throws Exception {

        BufferedWriter writer = null;
        try {
            String csvFileName = dirName + File.separator + "data_export_" + dateFormatter.format(now) + ".csv";
            writer = new BufferedWriter(new FileWriter(csvFileName));

            writeExportDataToCsvFile(writer, dataExport, csvFileName);

        } catch (IOException ioe) {
            throw new IllegalStateException(ioe);
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }


    }

    public void writeExportDataToCsvFile(BufferedWriter writer,
                                         AssessmentDataExport dataExport, String csvFileName) throws IOException {
        if (dataExport.getHeader() != null && !dataExport.getHeader().isEmpty()) {
            setCsvHeader(writer, dataExport.getHeader(), csvFileName);
            setCsvRows(writer, dataExport.getData(), csvFileName);
        } else {
            String missingDataMsg = messageSource.getMessage("export.data.assessments.missing", null, null);
            String displableMissingDataMsg = String.format("%s for Search Criteria as follows \n%s", missingDataMsg, dataExport.getFilterOptions());
            setCsvHeader(writer, displableMissingDataMsg, csvFileName);
            logger.warn(displableMissingDataMsg);
        }

    }

    private void setCsvRows(BufferedWriter writer, List<String> data,
                            String fileName) throws IOException {
        for (String row : data) {
            writer.write(row);
            writer.newLine();
            if (logger.isDebugEnabled()) {
                logger.debug(String.format("row written for %s [%s]", fileName, row));
            }
        }
    }

    private void setCsvHeader(BufferedWriter writer, String header,
                              String fileName) throws IOException {
        writer.write(String.format("%s dated %s", messageSource.getMessage("export.data.assessments.msg.intro", null, null), DateTime.now().toString("MM/dd/yy -- HH:mm:ss")));
        writer.newLine();
        writer.write(header);
        writer.newLine();
        if (logger.isDebugEnabled()) {
            logger.debug(String.format("header written for %s [%s]", fileName, header));
        }
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

}
