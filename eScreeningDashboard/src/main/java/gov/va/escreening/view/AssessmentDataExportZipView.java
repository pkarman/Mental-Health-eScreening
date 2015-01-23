package gov.va.escreening.view;

import gov.va.escreening.dto.dashboard.AssessmentDataExport;
import gov.va.escreening.util.DataExportAndDictionaryUtil;
import org.springframework.web.servlet.view.AbstractView;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.zip.ZipOutputStream;

public class AssessmentDataExportZipView extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map m = (Map) model.get("model");
        String zipFileName = (String) m.get("zipFileName");
        response.setContentType("application/zip");
        response.setHeader("Content-disposition", "attachment; filename=" + zipFileName);

        byte[] bytes = (byte[]) m.get("zippedBytes");
        ServletOutputStream servletOS = response.getOutputStream();
        servletOS.write(bytes);
        servletOS.flush();
        servletOS.close();
    }


}