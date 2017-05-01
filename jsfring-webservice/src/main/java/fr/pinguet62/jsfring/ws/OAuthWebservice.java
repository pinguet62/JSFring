package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.common.security.UserDetailsUtils.getCurrent;
import static fr.pinguet62.jsfring.ws.OAuthWebservice.PATH;
import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Webservice for OAuth additional utilities. */
@RestController
@RequestMapping(PATH)
public class OAuthWebservice {

    /** @see OAuthWebservice#getAutorities() */
    public static final String AUTORITIES_PATH = "/autorities";

    public static final String PATH = "/oauth";

    /**
     * Get the {@link GrantedAuthority#getAuthority() authoritie}s of current connected user.
     *
     * @return The {@link GrantedAuthority#getAuthority() authoritie}s.
     */
    @GetMapping(AUTORITIES_PATH)
    public List<String> getAutorities() {
        return getCurrent().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList());
    }

}