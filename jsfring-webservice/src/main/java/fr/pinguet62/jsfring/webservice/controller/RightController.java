package fr.pinguet62.jsfring.webservice.controller;

import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.webservice.converter.RightMapper;
import fr.pinguet62.jsfring.webservice.dto.RightDto;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static fr.pinguet62.jsfring.webservice.controller.RightController.PATH;
import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping(PATH)
public final class RightController {

    public static final String PATH = "/right";

    private final RightService rightService;

    private final RightMapper converter;

    public RightController(RightService rightService, RightMapper converter) {
        this.rightService = requireNonNull(rightService);
        this.converter = requireNonNull(converter);
    }

    @GetMapping("/{code}")
    public Mono<RightDto> get(@PathVariable String code) {
        return rightService
                .get(code)
                .map(converter::toDto);
    }

    @GetMapping
    public Flux<RightDto> list() {
        return rightService
                .getAll()
                .map(converter::toDto);
    }

    @PostMapping
    public void update(@RequestBody RightDto rightDto) {
        rightService
                .get(rightDto.getCode())
                .doOnNext(right -> converter.updateFromDto(right, rightDto))
                .doOnNext(rightService::update);
    }

}