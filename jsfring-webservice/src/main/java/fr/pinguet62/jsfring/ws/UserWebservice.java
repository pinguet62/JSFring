package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.ws.UserWebservice.PATH;
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
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.ws.dto.UserDto;

@Path(PATH)
public final class UserWebservice {

    public static final String PATH = "/user";

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private UserService userService;

    @PUT
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void create(UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        userService.create(user);
    }

    @GET
    @Path("/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto get(@PathParam("login") String login) {
        User user = userService.get(login);
        if (user == null)
            return null;
        return conversionService.convert(user, UserDto.class);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> list() {
        List<User> pojos = userService.getAll();
        return (List<UserDto>) conversionService.convert(pojos, collection(List.class, valueOf(User.class)),
                collection(List.class, valueOf(UserDto.class)));
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public void update(UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        userService.update(user);
    }

}