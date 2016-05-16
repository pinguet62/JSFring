package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.ws.OAuthWebservice.PATH;
import static fr.pinguet62.jsfring.ws.config.security.UserDetailsUtils.getCurrent;
import static java.util.stream.Collectors.toList;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.security.core.GrantedAuthority;

/** Webservice for OAuth additional utilities. */
@Path(PATH)
public class OAuthWebservice {

    /** @see OAuthWebservice#getAutorities() */
    public static final String AUTORITIES_PATH = "/autorities";

    public static final String PATH = "/oauth";

    /**
     * Get the {@link GrantedAuthority#getAuthority() authoritie}s of current connected user.
     *
     * @return The {@link GrantedAuthority#getAuthority() authoritie}s.
     */
    @GET
    @Path(AUTORITIES_PATH)
    @Produces(APPLICATION_JSON)
    public List<String> getAutorities() {
        return getCurrent().getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(toList());
    }

}