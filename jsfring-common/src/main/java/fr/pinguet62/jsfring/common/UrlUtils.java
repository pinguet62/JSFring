package fr.pinguet62.jsfring.common;

import lombok.experimental.UtilityClass;

import java.util.Base64;

import static java.util.Objects.requireNonNull;

@UtilityClass
public class UrlUtils {

    /**
     * Format the values for HTTP {@code Authorization} header parameter. <br>
     * Username and password are combined into a string separated by a colon {@code ":"}, and result is Base64 encoded.
     *
     * @return The Base64 encoded value.<br>
     * End with {@code "=="}.
     */
    public String formatAuthorization(String username, String password) {
        requireNonNull(username);
        requireNonNull(password);
        return new String(Base64.getEncoder().encode((username + ":" + password).getBytes()));
    }

}