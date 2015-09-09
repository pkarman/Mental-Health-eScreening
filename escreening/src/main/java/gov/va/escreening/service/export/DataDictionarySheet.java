package gov.va.escreening.service.export;

import com.google.common.base.Objects;
import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

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

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("sheet", sheet)
                .toString();
    }

    public int size() {
        return sheet.size();
    }
}
