package fr.pinguet62.jsfring.webservice.controller

import fr.pinguet62.jsfring.model.sql.User
import fr.pinguet62.jsfring.service.UserService
import fr.pinguet62.jsfring.webservice.controller.UserController.Companion.PATH
import fr.pinguet62.jsfring.webservice.converter.UserMapper
import fr.pinguet62.jsfring.webservice.dto.UserDto
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping(PATH)
class UserController(
        val userService: UserService,
        val converter: UserMapper
) {

    companion object {
        const val PATH = "/user"
    }

    @PutMapping
    fun create(@RequestBody userDto: UserDto) {
        var user: User = converter.fromDto(userDto)
        userService.create(user)
    }

    @GetMapping("/{email:.+}")
    fun get(@PathVariable email: String) =
            userService
                    .get(email)
                    .map { converter.toDto(it) }

    @GetMapping
    fun list(): Flux<UserDto> =
            userService
                    .getAll()
                    .map { converter.toDto(it) }

    @PostMapping
    fun update(@RequestBody userDto: UserDto) {
        userService
                .get(userDto.email)
                .doOnNext { converter.updateFromDto(it, userDto) }
                .doOnNext { userService.update(it) }
    }

}