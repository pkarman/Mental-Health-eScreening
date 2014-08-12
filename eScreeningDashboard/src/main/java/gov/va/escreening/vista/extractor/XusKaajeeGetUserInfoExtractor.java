package gov.va.escreening.vista.extractor;

import gov.va.escreening.vista.dto.VistaBrokerUserInfo;

import org.apache.commons.lang3.StringUtils;

public class XusKaajeeGetUserInfoExtractor implements VistaRecordExtractor<VistaBrokerUserInfo> {

    @Override
    public VistaBrokerUserInfo extractData(String record) {

        // Result(0) is the users DUZ.
        // Result(1) is the user name from the .01 field.
        // Result(2) is the users full name from the name standard file.
        // Result(3) is the FAMILY (LAST) NAME
        // Result(4) is the GIVEN (FIRST) NAME
        // Result(5) is the MIDDLE NAME
        // Result(6) is the PREFIX
        // Result(7) is the SUFFIX
        // Result(8) is the DEGREE
        // Result(9) is station # of the division that the user is working in.
        // Result(10) is the station # of the parent facility for the login division
        // Result(11) is the station # from the KSP site parameters, the parent "computer system"
        // Result(12) is the signon log entry IEN
        // Result(13) = # of permissible divisions
        // Result(14-n) are the permissible divisions for user login, in the format:
        // ; IEN of file 4^Station Name^Station Number^default? (1 or 0)

        if (StringUtils.isBlank(record)) {
            return null;
        }

        String[] records = StringUtils.splitPreserveAllTokens(record, '\n');

        if (records == null) {
            return null;
        }

        VistaBrokerUserInfo vistaBrokerUserInfo = new VistaBrokerUserInfo();

        if (records.length > 0) {
            vistaBrokerUserInfo.setDuz(records[0]);
        }
        else {
            return null;
        }

        if (records.length > 3) {
            vistaBrokerUserInfo.setLastName(records[3]);
        }
        else {
            return vistaBrokerUserInfo;
        }

        if (records.length > 4) {
            vistaBrokerUserInfo.setFirstName(records[4]);
        }
        else {
            return vistaBrokerUserInfo;
        }

        if (records.length > 5) {
            if (!StringUtils.equals("^", records[5])) {
                vistaBrokerUserInfo.setMiddleName(records[5]);
            }
        }
        else {
            return vistaBrokerUserInfo;
        }

        if (records.length > 7) {
            if (!StringUtils.equals("^", records[7])) {
                vistaBrokerUserInfo.setSuffix(records[7]);
            }
        }
        else {
            return vistaBrokerUserInfo;
        }

        return vistaBrokerUserInfo;
    }

}
