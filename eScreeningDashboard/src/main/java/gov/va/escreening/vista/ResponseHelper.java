package gov.va.escreening.vista;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ResponseHelper {

	public static Map<String, Map<String, String>> createConsultInfoMap(
			List<String> resAry) {

		Iterator<String> iter = resAry.iterator();
		iter.next(); // skip ~ShortList
		iter.next(); // skip ~Inpt Cslt Urgencies

		Map<String, Map<String, String>> resMap = new HashMap<String, Map<String, String>>();

		resMap.put("inpatient_urgencies", createMap(iter));
		resMap.put("outpatient_urgencies", createMap(iter));
		resMap.put("inpatient_places", createMap(iter));
		resMap.put("outpatient_places", createMap(iter));

		return resMap;
	}

	private static Map<String, String> createMap(Iterator<String> iter) {

		boolean finished = false;
		Map<String, String> m = new HashMap<String, String>();
		while (iter.hasNext() && !finished) {
			String row = iter.next();
			if (row.startsWith("~")) {
				finished = true;
			} else {
				String[] tokens = row.split("\\^");
				m.put(tokens[1].trim(), tokens[0].substring(1).trim());
			}
		}
		return m;
	}
}
