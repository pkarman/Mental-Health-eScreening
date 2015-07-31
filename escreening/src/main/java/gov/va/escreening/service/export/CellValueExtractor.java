package gov.va.escreening.service.export;

import java.util.Map;

public interface CellValueExtractor {

	String apply(String colName, Map<String, String> usrRespMap);

}
