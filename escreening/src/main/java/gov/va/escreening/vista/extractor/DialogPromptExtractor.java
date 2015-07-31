package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.DialogPrompt;
import gov.va.escreening.vista.dto.DialogPromptTypeEnum;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DialogPromptExtractor implements VistaRecordExtractor<DialogPrompt> {

    private static final Logger logger = LoggerFactory.getLogger(DialogPromptExtractor.class);

    @Override
    public DialogPrompt extractData(String record) {

        if (StringUtils.isBlank(record)) {
            return null;
        }

        DialogPrompt dialogPrompt = null;

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        String dialogPromptTypeCode = fields[0];

        // 3 Resolution
        // 4 Additional Prompts
        // 5 Listbox Items
        // 6 Progress Note Text Line * (concatenate following 6's)

        if ("3".equals(dialogPromptTypeCode)) {
            dialogPrompt = extractResolutionPromptData(record);
        }
        else if ("4".equals(dialogPromptTypeCode)) {
            dialogPrompt = extractAdditionalPromptData(record);
        }
        else if ("5".equals(dialogPromptTypeCode)) {
            dialogPrompt = extractListboxItemPromptData(record);
        }
        else if ("6".equals(dialogPromptTypeCode)) {
            dialogPrompt = extractProgressNoteTextPromptData(record);
        }
        else {
            logger.error("Unrecognized 'dialogPromptTypeCode': " + dialogPromptTypeCode);
        }

        return dialogPrompt;
    }

    /**
     * Extracts 'Resolution' types (3).
     * @param record
     * @return
     */
    public DialogPrompt extractResolutionPromptData(String record) {

        DialogPrompt dialogPrompt = new DialogPrompt();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');
        Integer size = fields.length;

        logger.debug("dialogPromptTypeCode {}, Size {}: ", fields[0], size);

        if (size < 1) {
            return null;
        }

        if (!"3".equals(fields[0])) {
            return null;
        }
        
        /**
         * extract -- Health factors 
         * 3^663000687^^HF^^663155^^ALCOHOL - ADVISE TO ABSTAIN^^
         */

        dialogPrompt.setDialogPromptTypeCode(fields[0]);
        dialogPrompt.setDialogPromptType(DialogPromptTypeEnum.RESOLUTION);
        dialogPrompt.setRecord(record);

        if (size < 2) {
            return dialogPrompt;
        }
        dialogPrompt.setDialogElementIen(fields[1]);

        if (size < 3) {
            return dialogPrompt;
        }
        dialogPrompt.setLineId(fields[2]);

        if (size < 4) {
            return dialogPrompt;
        }
        dialogPrompt.setFindingTypeCode(fields[3]);

        // UNKNOWN
        if (size < 5) {
            return dialogPrompt;
        }
        dialogPrompt.setPiece05(fields[4]);

        // UNKNOWN
        if (size < 6) {
            return dialogPrompt;
        }
        dialogPrompt.setItemIen(fields[5]);

        if (size < 7) {
            return dialogPrompt;
        }
        dialogPrompt.setR3Code(fields[6]);

        if (size < 8) {
            return dialogPrompt;
        }
        dialogPrompt.setDisplayText(fields[7]);

        // UNKNOWN
        if (size < 9) {
            return dialogPrompt;
        }
        dialogPrompt.setR3Cat(fields[8]);

        // UNKNOWN
        if (size < 10) {
            return dialogPrompt;
        }
        dialogPrompt.setPiece10(fields[9]);

        // UNKNOWN
        if (size < 12) {
            return dialogPrompt;
        }
        dialogPrompt.setPiece11(fields[10]);

        return dialogPrompt;
    }

    /**
     * Extracts 'Additional Prompt' type (4)
     * @param record
     * @return
     */
    public DialogPrompt extractAdditionalPromptData(String record) {

        DialogPrompt dialogPrompt = new DialogPrompt();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');
        Integer size = fields.length;

        logger.debug("dialogPromptTypeCode {}, Size {}: ", fields[0], size);

        if (size < 1) {
            return null;
        }
        if (!"4".equals(fields[0])) {
            return null;
        }

        dialogPrompt.setDialogPromptTypeCode(fields[0]);
        dialogPrompt.setDialogPromptType(DialogPromptTypeEnum.ADDITIONAL_PROMPT);
        dialogPrompt.setRecord(record);

        if (size < 2) {
            return dialogPrompt;
        }
        dialogPrompt.setDialogElementIen(fields[1]);

        if (size < 3) {
            return dialogPrompt;
        }
        dialogPrompt.setLineId(fields[2]);

        if (size < 4) {
            return dialogPrompt;
        }
        dialogPrompt.setDisplayText(fields[3]);

        return dialogPrompt;
    }

    /**
     * Extracts 'Listbox Item' type (5).
     * @param record
     * @return
     */
    public DialogPrompt extractListboxItemPromptData(String record) {

        DialogPrompt dialogPrompt = new DialogPrompt();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');
        Integer size = fields.length;

        logger.debug("dialogPromptTypeCode {}, Size {}: ", fields[0], size);

        if (size < 1) {
            return null;
        }

        if (!"5".equals(fields[0])) {
            return null;
        }

        dialogPrompt.setDialogPromptTypeCode(fields[0]);
        dialogPrompt.setDialogPromptType(DialogPromptTypeEnum.LISTBOX_ITEM);
        dialogPrompt.setRecord(record);

        dialogPrompt.setDialogPromptTypeCode(fields[0]);

        if (size < 2) {
            return dialogPrompt;
        }
        dialogPrompt.setDialogElementIen(fields[1]);

        if (size < 3) {
            return dialogPrompt;
        }
        dialogPrompt.setLineId(fields[2]);

        if (size < 4) {
            return dialogPrompt;
        }
        dialogPrompt.setCptPovIen(fields[3]);

        if (size < 5) {
            return dialogPrompt;
        }
        dialogPrompt.setCodeDescription(fields[4]);

        return dialogPrompt;
    }

    /**
     * Extracts 'Progress Note' type (6).
     * @param record
     * @return
     */
    public DialogPrompt extractProgressNoteTextPromptData(String record) {

        DialogPrompt dialogPrompt = new DialogPrompt();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');
        Integer size = fields.length;

        logger.debug("dialogPromptTypeCode {}, Size {}: ", fields[0], size);

        if (size < 1) {
            return null;
        }

        if (!"6".equals(fields[0])) {
            return null;
        }

        dialogPrompt.setDialogPromptTypeCode(fields[0]);
        dialogPrompt.setDialogPromptType(DialogPromptTypeEnum.PROGRESS_NOTE_TEXT);
        dialogPrompt.setRecord(record);

        if (size < 2) {
            return dialogPrompt;
        }
        dialogPrompt.setDialogElementIen(fields[1]);

        if (size < 4) {
            return dialogPrompt;
        }
        dialogPrompt.setProgressNoteText(fields[3]);

        return dialogPrompt;
    }

}
