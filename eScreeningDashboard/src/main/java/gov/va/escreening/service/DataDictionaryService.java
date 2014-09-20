package gov.va.escreening.service;

import java.util.Map;

import com.google.common.collect.Table;

public interface DataDictionaryService {

	public Map<String, Table<Integer, String, String>> createDataDictionary();

}
