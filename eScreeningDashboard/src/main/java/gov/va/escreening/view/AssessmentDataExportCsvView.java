package gov.va.escreening.view;

import gov.va.escreening.dto.dashboard.AssessmentDataExport;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gov.va.escreening.util.DataExportAndDictionaryUtil;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.web.servlet.view.AbstractView;

public class AssessmentDataExportCsvView extends AbstractView {

    @Resource(name = "dataExportAndDictionaryUtil")
    DataExportAndDictionaryUtil deUtil;

    private final String defaultErrorMsg = "An error occurred while generating the requested export.  Please contact technical support for assistance.";

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) {

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(response.getWriter());

            AssessmentDataExport dataExport = (AssessmentDataExport) model.get("dataExportList");

            String csvFileName = dataExport.getFilterOptions().getFilePath();
            response.setHeader("Content-Disposition", "attachment; filename=\"" + csvFileName + "\"");

            deUtil.writeExportDataToCsvFile(writer, dataExport, csvFileName);

        } catch (IOException ioe) {
            throw new IllegalStateException(defaultErrorMsg);
        } finally {
            if (writer != null) {
                try {
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    throw new IllegalStateException(defaultErrorMsg);
                }
            }
        }
    }


}