package fr.pinguet62.jsfring.webservice;

import static fr.pinguet62.jsfring.webservice.UserWebservice.PATH;
import static java.util.Objects.requireNonNull;
import static org.springframework.core.convert.TypeDescriptor.collection;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import java.util.List;

import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.webservice.dto.UserDto;

@RestController
@RequestMapping(PATH)
public final class UserWebservice {

    public static final String PATH = "/user";

    private final ConversionService conversionService;

    private final UserService userService;

    public UserWebservice(ConversionService conversionService, UserService userService) {
        this.conversionService = requireNonNull(conversionService);
        this.userService = requireNonNull(userService);
    }

    @PutMapping
    public void create(@RequestBody UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        userService.create(user);
    }

    @GetMapping("/{email:.+}")
    public UserDto get(@PathVariable String email) {
        User user = userService.get(email);
        if (user == null)
            return null;
        return conversionService.convert(user, UserDto.class);
    }

    @GetMapping
    public List<UserDto> list() {
        List<User> pojos = userService.getAll();
        return (List<UserDto>) conversionService.convert(pojos, collection(List.class, valueOf(User.class)),
                collection(List.class, valueOf(UserDto.class)));
    }

    @PostMapping
    public void update(@RequestBody UserDto userDto) {
        User user = conversionService.convert(userDto, User.class);
        userService.update(user);
    }

}