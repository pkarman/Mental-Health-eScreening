package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.DialogComponent;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DialogComponentExtractor implements VistaRecordExtractor<DialogComponent> {

    private static final Logger logger = LoggerFactory.getLogger(DialogComponentExtractor.class);

    @Override
    public DialogComponent extractData(String record) {

        if (StringUtils.isBlank(record)) {
            return null;
        }

        DialogComponent dialogComponent = null;

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');

        String mode = fields[0];

        if ("2".equals(mode)) {
            dialogComponent = extractModeTwoData(record);
        }
        else if ("1".equals(mode)) {
            dialogComponent = extractModeOneData(record);
        }
        else if ("0".equals(mode)) {
            logger.debug("Encountered 0 mode: {}", record);
        }
        else {
            logger.error("Unrecognized 'mode': " + mode);
        }

        return dialogComponent;
    }

    public DialogComponent extractModeTwoData(String record) {

        DialogComponent dialogComponent = new DialogComponent();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');
        Integer size = fields.length;

        logger.debug("Mode {}, Size {}: ", fields[0], size);

        if (size < 1) {
            return null;
        }
        dialogComponent.setMode(fields[0]);

        if (size < 2) {
            return dialogComponent;
        }
        dialogComponent.setDialogComponentIen(fields[1]);
        
        if (size < 3) {
            return dialogComponent;
        }
        dialogComponent.setSequenceString(fields[2]);
        
        if (size < 4) {
            return dialogComponent;
        }
        dialogComponent.setDisplayText(fields[3]);

        return dialogComponent;
    }

    public DialogComponent extractModeOneData(String record) {

        DialogComponent dialogComponent = new DialogComponent();

        String[] fields = StringUtils.splitPreserveAllTokens(record, '^');
        Integer size = fields.length;

        logger.debug("Mode {}, Size {}: ", fields[0], size);

        if (size < 1) {
            return null;
        }
        dialogComponent.setMode(fields[0]);

        if (size < 2) {
            return dialogComponent;
        }
        dialogComponent.setDialogComponentIen(fields[1]);

        if (size < 3) {
            return dialogComponent;
        }
        dialogComponent.setSequenceString(fields[2]);

        if (size < 4) {
            return dialogComponent;
        }
        dialogComponent.setInputType(fields[3]);

        if (size < 5) {
            return dialogComponent;
        }
        if ("1".equals(fields[4])) {
            dialogComponent.setExcludeFromProgressNote(true);
        }
        else {
            dialogComponent.setExcludeFromProgressNote(false);
        }

        if (size < 6) {
            return dialogComponent;
        }
        if (StringUtils.isNotEmpty(fields[5]) && StringUtils.isNumeric(fields[5])) {
            dialogComponent.setIndentSize(Integer.valueOf(fields[5]));
        }

        if (size < 7) {
            return dialogComponent;
        }
        dialogComponent.setFindingTypeString(fields[6]);

        if (size < 8) {
            return dialogComponent;
        }
        if ("1".equals(fields[7])) {
            dialogComponent.setDoneElsewhereHistorical(true);
        }
        else {
            dialogComponent.setDoneElsewhereHistorical(false);
        }

        if (size < 9) {
            return dialogComponent;
        }
        if ("1".equals(fields[8])) {
            dialogComponent.setExcludeMentalHealthTestFromProgressNote(true);
        }
        else {
            dialogComponent.setExcludeMentalHealthTestFromProgressNote(false);
        }

        if (size < 10) {
            return dialogComponent;
        }
        dialogComponent.setResultGroupListString(fields[9]);

        if (size < 11) {
            return dialogComponent;
        }
        dialogComponent.setDcount(fields[10]);

        if (size < 15) {
            return dialogComponent;
        }
        if ("1".equals(fields[14])) {
            dialogComponent.setHideUntilChecked(true);
        }
        else {
            dialogComponent.setHideUntilChecked(false);
        }

        if (size < 16) {
            return dialogComponent;
        }
        if (StringUtils.isNotEmpty(fields[15]) && StringUtils.isNumeric(fields[15])) {
            dialogComponent.setChildrenIndentSize(Integer.valueOf(fields[15]));
        }

        if (size < 17) {
            return dialogComponent;
        }
        if ("1".equals(fields[16])) {
            dialogComponent.setShareCommonPrompt(true);
        }
        else {
            dialogComponent.setShareCommonPrompt(false);
        }

        if (size < 18) {
            return dialogComponent;
        }
        if (StringUtils.isNotEmpty(fields[17]) && StringUtils.isNumeric(fields[17])) {
            dialogComponent.setGroupEntry(Integer.valueOf(fields[17]));
        }

        if (size < 19) {
            return dialogComponent;
        }
        if ("1".equals(fields[18]) || "Y".equals(fields[18])) {
            dialogComponent.setDrawBox(true);
        }
        else {
            dialogComponent.setDrawBox(false);
        }

        if (size < 20) {
            return dialogComponent;
        }
        dialogComponent.setCaption(fields[19]);

        if (size < 21) {
            return dialogComponent;
        }
        if ("1".equals(fields[20])) {
            dialogComponent.setIndentProgressNote(true);
        }
        else {
            dialogComponent.setIndentProgressNote(false);
        }

        return dialogComponent;
    }

}
