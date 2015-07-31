package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.DialogComponent;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DialogComponentListExtractor implements VistaRecordExtractor<List<DialogComponent>> {

    private static final Logger logger = LoggerFactory.getLogger(DialogComponentListExtractor.class);

    @Override
    public List<DialogComponent> extractData(String record) {

        List<DialogComponent> resultList = new ArrayList<DialogComponent>();
        Hashtable<String, DialogComponent> hashTable = new Hashtable<String, DialogComponent>();

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] records = StringUtils.split(record, '\n');

        if (records != null && records.length > 0) {

            DialogComponentExtractor dialogComponentExtractor = new DialogComponentExtractor();

            for (int i = 0; i < records.length; ++i) {
                DialogComponent dialogComponent = dialogComponentExtractor.extractData(records[i]);

                if (dialogComponent == null) {
                    logger.error("Failed to recognize RPC string: {}", records[i]);
                }
                else {
                    if ("1".equalsIgnoreCase(dialogComponent.getMode())) {
                        hashTable.put(dialogComponent.getSequenceString(), dialogComponent);
                        resultList.add(dialogComponent);
                    }
                    else {
                        DialogComponent parent = hashTable.get(dialogComponent.getSequenceString());
                        
                        if (dialogComponent.getDisplayText() != null) {
                            if (parent.getDisplayText() != null) {
                                parent.setDisplayText(parent.getDisplayText() + dialogComponent.getDisplayText());
                            }
                            else {
                                parent.setDisplayText(dialogComponent.getDisplayText());
                            }
                        }
                    }
                }
            }
        }

        return resultList;
    }

}
