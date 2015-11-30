package fr.pinguet62.jsfring.ws;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;

@Path("/profile")
public final class ProfileWebservice {

    @Inject
    private ProfileService profileService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProfileDto get(@PathParam("id") int id) {
        Profile profile = profileService.get(id);
        if (profile == null)
            return null;
        return new ProfileDto(profile);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProfileDto> list() {
        return profileService.getAll().stream().map(ProfileDto::new).collect(toList());
    }

}