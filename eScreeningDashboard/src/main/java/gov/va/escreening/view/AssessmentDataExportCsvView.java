package gov.va.escreening.view;

import gov.va.escreening.dto.dashboard.AssessmentDataExport;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.web.servlet.view.AbstractView;

public class AssessmentDataExportCsvView extends AbstractView implements MessageSourceAware {

	MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(AssessmentDataExportCsvView.class);

	private final String defaultErrorMsg = "An error occured while generating the requested export.  Please contact technical support for assistance.";

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response) {

		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(response.getWriter());

			AssessmentDataExport dataExport = (AssessmentDataExport) model.get("dataExportList");

			String csvFileName = dataExport.getFilterOptions().getFilePath();
			response.setHeader("Content-Disposition", "attachment; filename=\"" + csvFileName + "\"");

			writeExportDataToCsvFile(writer, dataExport, csvFileName);

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

	private void writeExportDataToCsvFile(BufferedWriter writer,
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
		writer.write(String.format("%s %s dated %s", messageSource.getMessage("export.data.assessments.msg.intro", null, null), messageSource.getMessage("export.data.assessments.version", null, null), ISODateTimeFormat.dateTime().print(new DateTime())));
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