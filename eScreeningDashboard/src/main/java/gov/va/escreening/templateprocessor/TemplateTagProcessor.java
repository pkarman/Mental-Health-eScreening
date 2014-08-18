package gov.va.escreening.templateprocessor;

import java.util.regex.Pattern;

import org.apache.commons.lang3.text.WordUtils;

import gov.va.escreening.constants.TemplateConstants.ViewType;
import static gov.va.escreening.templateprocessor.TemplateTags.*;

public class TemplateTagProcessor {
	
	public static String resolveClinicalNoteTags(String noteText, ViewType viewType) {
		switch(viewType) {
			case HTML:
				noteText = resolveHtmlType(noteText);
				break;
			case TEXT:
				noteText = resolveTextType(noteText);
				break;
			default:
				throw new UnsupportedOperationException(String.format("resolveClinicalNoteTags view type: %s is not supported", viewType));
		}
		return noteText;
	}
	
	private static Pattern htmlEndDivReplace = Pattern.compile(createTagRegex(Style.XML, 
			BATTERY_HEADER_END, BATTERY_FOOTER_END, SECTION_TITLE_END, SECTION_END, MODULE_TITLE_END, MODULE_END));
	
	private static String resolveHtmlType(String noteText) {
	    noteText = noteText.replace(BATTERY_HEADER_START.xml(), "<div class='templateHeader'>");
        
        noteText = noteText.replace(BATTERY_FOOTER_START.xml(), "<div class='templateFooter'>");
	    
	    noteText = noteText.replace(SECTION_TITLE_START.xml(), "<div class='templateSectionTitle'>");

	    noteText = noteText.replace(SECTION_START.xml(), "<div class='templateSection'>");
        
		noteText = noteText.replace(MODULE_TITLE_START.xml(), "<div class='moduleTemplateTitle'>");
		
		noteText = noteText.replace(MODULE_START.xml(), "<div class='moduleTemplateText'>");
    	
    	noteText = noteText.replace(LINE_BREAK.xml(), "<br>");
    	noteText = noteText.replace(NBSP.xml(), "&nbsp;");
    	
    	noteText = noteText.replace(MATRIX_TABLE_START.xml(), "<table>");
    	noteText = noteText.replace(MATRIX_TABLE_END.xml(), "</table>");
    	noteText = noteText.replace(MATRIX_TH_START.xml(), "<th class='matrixTableHeader'>");
    	noteText = noteText.replace(MATRIX_TH_END.xml(), "</th>");
    	noteText = noteText.replace(MATRIX_TR_START.xml(), "<tr>");
    	noteText = noteText.replace(TABLE_TR_CTR_START.xml(), "<tr class='justifyCtrTableRow'>");
    	noteText = noteText.replace(TABLE_TR_END.xml(), "</tr>");
    	noteText = noteText.replace(MATRIX_TR_END.xml(), "</tr>");
    	noteText = noteText.replace(MATRIX_TD_START.xml(), "<td>");
    	noteText = noteText.replace(MATRIX_TD_END.xml(), "</td>");
    	noteText = noteText.replace(TABLE_TD_RT_START.xml(), "<td class='justifyRtTableData'>");
    	noteText = noteText.replace(TABLE_TD_CTR_START.xml(), "<td class='justifyCtrTableData'>"); 
    	noteText = noteText.replace(TABLE_TD_LFT_START.xml(), "<td class='justifyLftTableData'>");
    	noteText = noteText.replace(TABLE_TD_END.xml(), "</td>");
    	
    	noteText = noteText.replace(IMG_LOGO_VA_HC.xml(), "<img src='../resources/images/logo_va_veteran_summary.gif'>");
    	noteText = noteText.replace(IMG_CESMITH_BLK_BRDR.xml(), "<img src='../resources/images/cesamh_blk_border.png'>");
    	noteText = noteText.replace(IMG_VA_VET_SMRY.xml(), "<img src='../resources/images/escreening_cdsmith_QR_code_small.png'>");
    	
    	
    	return htmlEndDivReplace.matcher(noteText).replaceAll("</div>");
	}
	
	private static final Pattern textEmptyReplace = Pattern.compile(createTagRegex(Style.XML, 
			BATTERY_HEADER_START,
			MODULE_TITLE_START, MODULE_START, 
			MATRIX_TABLE_START, MATRIX_TABLE_END, MATRIX_TH_START, MATRIX_TH_END, MATRIX_TR_START, MATRIX_TR_END, MATRIX_TD_START, MATRIX_TD_END));
	
	private static final String dashedLine = "--------------------------------------------------------------------------------";
	private static final String equalLine =  "================================================================================";
	
	private static String resolveTextType(String noteText) {
        noteText = noteText.replace(BATTERY_HEADER_END.xml(), equalLine);
        
        noteText = noteText.replace(BATTERY_FOOTER_START.xml(), "\n\n");
        noteText = noteText.replace(BATTERY_FOOTER_END.xml(), "");
	    
	    noteText = noteText.replace(SECTION_TITLE_START.xml(), "\n");
	    noteText = noteText.replace(SECTION_TITLE_END.xml(), "\n" + dashedLine +"\n");
	    
	    noteText = noteText.replace(SECTION_START.xml(), "\n");
	    noteText = noteText.replace(SECTION_END.xml(), dashedLine);
        
        noteText = noteText.replace(MODULE_TITLE_END.xml(), " ");
        noteText = noteText.replace(MODULE_END.xml(), "\n\n");
        
        noteText = noteText.replace(LINE_BREAK.xml(), "\n");
        noteText = noteText.replace(NBSP.xml(), " ");
        
        noteText = textEmptyReplace.matcher(noteText).replaceAll("");

        //remove other tags (this is all we expect in the wysiwyg editor)
        noteText = noteText.replaceAll("</*\\s*[BbIiuUsS(br)(BR)]+\\s*/*>", "");
        
        //wrap to 80 columns
        StringBuilder wrappedText = new StringBuilder();
        String[] lines = noteText.split("\n");
        for(String line : lines){
        	wrappedText.append("\n").append(WordUtils.wrap(line, 80, "\n", true));
        }
        return wrappedText.toString();
    }
}