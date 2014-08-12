package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.DialogPrompt;
import gov.va.escreening.vista.dto.DialogPromptTypeEnum;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DialogPromptListExtractor implements VistaRecordExtractor<List<DialogPrompt>> {

    private static final Logger logger = LoggerFactory.getLogger(DialogPromptListExtractor.class);

    @Override
    public List<DialogPrompt> extractData(String record) {

        List<DialogPrompt> resultList = new ArrayList<DialogPrompt>();
        Hashtable<String, DialogPrompt> hashTable = new Hashtable<String, DialogPrompt>();

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] records = StringUtils.split(record, '\n');

        if (records != null && records.length > 0) {

            DialogPromptExtractor dialogPromptExtractor = new DialogPromptExtractor();

            for (int i = 0; i < records.length; ++i) {
                DialogPrompt dialogPrompt = dialogPromptExtractor.extractData(records[i]);

                if (dialogPrompt == null) {
                    logger.error("Failed to recognize RPC string: {}", records[i]);
                }
                else {

                    // See if we need to append to previously seen progress note text.
                    if (dialogPrompt.getDialogPromptType() == DialogPromptTypeEnum.PROGRESS_NOTE_TEXT) {

                        DialogPrompt parent = hashTable.get(dialogPrompt.getDialogElementIen());

                        if (parent == null) {
                            resultList.add(dialogPrompt);
                            hashTable.put(dialogPrompt.getDialogElementIen(), dialogPrompt);
                        }
                        else {
                            parent.setRecord(parent.getRecord() + '\n' + dialogPrompt.getRecord());
                            
                            if (dialogPrompt.getProgressNoteText() != null) {
                                if (parent.getProgressNoteText() != null) {
                                    parent.setProgressNoteText(parent.getProgressNoteText() + dialogPrompt.getProgressNoteText());
                                }
                                else {
                                    parent.setProgressNoteText(dialogPrompt.getProgressNoteText());
                                }
                            }
                        }
                    }
                    else {
                        resultList.add(dialogPrompt);
                    }

                }
            }
        }

        return resultList;
    }

}
