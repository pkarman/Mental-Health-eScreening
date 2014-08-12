package gov.va.escreening.util;

import org.apache.commons.lang3.StringUtils;


public class VeteranUtil {

    /**
     * Returns a full name.
     * @param firstName
     * @param middleName
     * @param lastName
     * @param suffix
     * @param prefix
     * @return
     */
    public static String getFullName(String firstName, String middleName, String lastName, String suffix, String prefix) {

        StringBuilder fullName = new StringBuilder();

        if (!StringUtils.isEmpty(prefix)) {
            fullName.append(prefix);
            fullName.append(" ");
        }

        if (!StringUtils.isEmpty(lastName)) {
            fullName.append(lastName);
            fullName.append(", ");
        }

        if (!StringUtils.isEmpty(suffix)) {
            fullName.append(suffix);
            fullName.append(" ");
        }

        if (!StringUtils.isEmpty(firstName)) {
            fullName.append(firstName);
            fullName.append(" ");
        }

        if (!StringUtils.isEmpty(middleName)) {
            fullName.append(middleName);
            fullName.append(" ");
        }

        return fullName.toString();
    }
}
