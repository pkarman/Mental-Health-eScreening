package gov.va.escreening.service.export;

import com.google.common.collect.Maps;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

/**
 * Created by munnoo on 9/7/15.
 */
enum DataDictionaryState {
    ready, not_ready
}

@Component("theDataDictionary")
public class DataDictionary {

    private final Map<String, DataDictionarySheet> ddObject;
    private HSSFWorkbook workbook;
    private DataDictionaryState state;

    public DataDictionary() {
        this.ddObject = Maps.newTreeMap(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.toLowerCase().compareTo(o2.toLowerCase());
            }
        });
    }

    @Override
    public String toString() {
        return state.toString();
    }

    @PostConstruct
    private void init() {
        this.state = DataDictionaryState.not_ready;
    }

    public void markReady() {
        this.state = DataDictionaryState.ready;
    }

    public void markNotReady() {
        this.state = DataDictionaryState.not_ready;
    }

    public boolean isReady() {
        return state == DataDictionaryState.ready;
    }

    public void put(String surveyName, DataDictionarySheet sheet) {
        ddObject.put(surveyName, sheet);
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public Set<String> getModuleNames() {
        return this.ddObject.keySet();
    }

    public DataDictionarySheet findSheet(String surveyName) {
        return this.ddObject.get(surveyName);
    }
}
