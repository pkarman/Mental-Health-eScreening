package gov.va.escreening.service.export;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Table;

public interface DataDictionaryService {


    public boolean tryPrepareDataDictionary(boolean force);

    public String getExportNameKeyPrefix();

    public String createTableResponseVarName(String exportName);

    List<String> findAllFormulas(String surveyName);
}
