package fr.pinguet62.jsfring.ws;

import static java.util.stream.Collectors.toList;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import fr.pinguet62.jsfring.model.User;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.ws.dto.UserDto;

public final class UserWebservice {

    @Inject
    private UserService userService;

    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto get(@PathParam("login") String login) {
        User user = userService.get(login);
        if (user == null)
            return null;
        return new UserDto(user);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> list() {
        return userService.getAll().stream().map(UserDto::new).collect(toList());
    }

}