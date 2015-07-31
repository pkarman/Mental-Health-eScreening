package gov.va.escreening.vista.extractor;


public class StringExtractor implements VistaRecordExtractor<String> {

    @Override
    public String extractData(String record) {
        return record;
    }

}
