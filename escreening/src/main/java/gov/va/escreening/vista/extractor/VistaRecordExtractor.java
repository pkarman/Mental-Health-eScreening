package gov.va.escreening.vista.extractor;

public interface VistaRecordExtractor<T> {

    /**
     * Parses the string and returns an object.
     * @param record
     * @return
     */
    T extractData(String record);

}
