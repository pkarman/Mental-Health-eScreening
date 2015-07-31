package gov.va.escreening.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginHelper {

    private static final Logger logger = LoggerFactory.getLogger(LoginHelper.class);

    public static String prepareValueWithSha256(String value) {
        String preparedValue = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Update input string in message digest
            digest.update(value.getBytes(), 0, value.length());

            // Converts message digest value in base 16 (hex)
            preparedValue = new BigInteger(1, digest.digest()).toString(16);

        }
        catch (NoSuchAlgorithmException e) {
            logger.error(e.getMessage());
        }

        return preparedValue;
    }
}
