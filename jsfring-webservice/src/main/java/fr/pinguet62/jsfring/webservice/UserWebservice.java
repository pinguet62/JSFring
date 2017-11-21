package fr.pinguet62.jsfring.webservice;

import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.webservice.converter.UserMapper;
import fr.pinguet62.jsfring.webservice.dto.UserDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fr.pinguet62.jsfring.webservice.UserWebservice.PATH;
import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping(PATH)
public final class UserWebservice {

    public static final String PATH = "/user";

    private final UserService userService;

    private final UserMapper converter;

    public UserWebservice(UserService userService, UserMapper converter) {
        this.userService = requireNonNull(userService);
        this.converter = requireNonNull(converter);
    }

    @PutMapping
    public void create(@RequestBody UserDto userDto) {
        User user = converter.fromDto(userDto);
        userService.create(user);
    }

    @GetMapping("/{email:.+}")
    public UserDto get(@PathVariable String email) {
        User user = userService.get(email);
        return converter.toDto(user);
    }

    @GetMapping
    public List<UserDto> list() {
        List<User> users = userService.getAll();
        return converter.toDto(users);
    }

    @PostMapping
    public void update(@RequestBody UserDto userDto) {
        User user = userService.get(userDto.getEmail());
        converter.updateFromDto(user, userDto);
        userService.update(user);
    }

}