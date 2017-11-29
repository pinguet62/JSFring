package fr.pinguet62.jsfring.webservice.controller

import fr.pinguet62.jsfring.service.RightService
import fr.pinguet62.jsfring.webservice.controller.RightController.Companion.PATH
import fr.pinguet62.jsfring.webservice.converter.RightMapper
import fr.pinguet62.jsfring.webservice.dto.RightDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(PATH)
class RightController(
        val rightService: RightService,
        val converter: RightMapper
) {

    companion object {
        const val PATH = "/right"
    }

    @GetMapping("/{code}")
    fun get(@PathVariable code: String) =
            rightService
                    .get(code)
                    .map { converter.toDto(it) }

    @GetMapping
    fun list() =
            rightService
                    .getAll()
                    .map { converter.toDto(it) }

    @PostMapping
    fun update(@RequestBody rightDto: RightDto) {
        rightService
                .get(rightDto.code)
                .doOnNext { converter.updateFromDto(it, rightDto) }
                .doOnNext { rightService.update(it) }
    }

}