package fr.pinguet62.jsfring.webservice.controller;

import fr.pinguet62.jsfring.model.sql.User;
import fr.pinguet62.jsfring.service.UserService;
import fr.pinguet62.jsfring.webservice.converter.UserMapper;
import fr.pinguet62.jsfring.webservice.dto.UserDto;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static fr.pinguet62.jsfring.webservice.controller.UserController.PATH;
import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping(PATH)
public final class UserController {

    public static final String PATH = "/user";

    private final UserService userService;

    private final UserMapper converter;

    public UserController(UserService userService, UserMapper converter) {
        this.userService = requireNonNull(userService);
        this.converter = requireNonNull(converter);
    }

    @PutMapping
    public void create(@RequestBody UserDto userDto) {
        User user = converter.fromDto(userDto);
        userService.create(user);
    }

    @GetMapping("/{email:.+}")
    public Mono<UserDto> get(@PathVariable String email) {
        return userService
                .get(email)
                .map(converter::toDto);
    }

    @GetMapping
    public Flux<UserDto> list() {
        return userService
                .getAll()
                .map(converter::toDto);
    }

    @PostMapping
    public void update(@RequestBody UserDto userDto) {
        userService
                .get(userDto.getEmail())
                .doOnNext(user -> converter.updateFromDto(user, userDto))
                .doOnNext(userService::update);
    }

}