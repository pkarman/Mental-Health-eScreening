package gov.va.escreening.service.export;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;
import com.sun.xml.internal.bind.v2.util.QNameMap;

import java.util.Map;
import java.util.Set;

/**
 * Created by munnoo on 9/7/15.
 */
public class DataDictionarySheet {
    private final Table<String, String, String> sheet;

    public DataDictionarySheet() {
        this.sheet = TreeBasedTable.create();
    }

    public void put(String rowKey, String columnKey, String value) {
        this.sheet.put(rowKey, columnKey, value);
    }

    public Map<String, String> column(String columnKey) {
        return this.sheet.column(columnKey);
    }

    public Set<String> columnKeySet() {
        return this.sheet.columnKeySet();
    }

    public Set<String> rowKeySet() {
        return this.sheet.rowKeySet();
    }

    public Map<String, String> row(String rowId) {
        return this.sheet.row(rowId);
    }

    public Map<String, Map<String, String>> rowMap() {
        return this.sheet.rowMap();
    }
}
