package gov.va.escreening.security;

import org.springframework.security.crypto.password.PasswordEncoder;

public final class EscreenPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence rawPassword) {

        return LoginHelper.prepareValueWithSha256(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String expected = encode(rawPassword);

        if (encodedPassword.equals(expected)) {
            return true;
        }
        else {
            return false;
        }
    }
}
