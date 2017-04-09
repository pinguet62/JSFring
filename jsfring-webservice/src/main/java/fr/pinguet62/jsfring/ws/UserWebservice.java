package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.ws.UserWebservice.PATH;
import static java.util.Objects.requireNonNull;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.core.convert.TypeDescriptor.collection;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.ws.dto.UserDto;

@Component
@Path(PATH)
public final class UserWebservice {

    public static final String PATH = "/user";

    private final ConversionService conversionService;

    private final UserService userService;

    public UserWebservice(ConversionService conversionService, UserService userService) {
        this.conversionService = requireNonNull(conversionService);
        this.userService = requireNonNull(userService);
    }

    @PUT
    @Path("/")
    @Consumes(APPLICATION_JSON)
    public void create(UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        userService.create(user);
    }

    @GET
    @Path("/{login}")
    @Produces(APPLICATION_JSON)
    public UserDto get(@PathParam("login") String login) {
        User user = userService.get(login);
        if (user == null)
            return null;
        return conversionService.convert(user, UserDto.class);
    }

    @GET
    @Path("/")
    @Produces(APPLICATION_JSON)
    public List<UserDto> list() {
        List<User> pojos = userService.getAll();
        return (List<UserDto>) conversionService.convert(pojos, collection(List.class, valueOf(User.class)),
                collection(List.class, valueOf(UserDto.class)));
    }

    @POST
    @Path("/")
    @Consumes(APPLICATION_JSON)
    public void update(UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        userService.update(user);
    }

}