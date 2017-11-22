package fr.pinguet62.jsfring.webservice.controller;

import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.service.RightService;
import fr.pinguet62.jsfring.webservice.converter.RightMapper;
import fr.pinguet62.jsfring.webservice.dto.RightDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public RightDto get(@PathVariable String code) {
        Right right = rightService.get(code);
        return converter.toDto(right);
    }

    @GetMapping
    public List<RightDto> list() {
        List<Right> rights = rightService.getAll();
        return converter.toDto(rights);
    }

    @PostMapping
    public void update(@RequestBody RightDto rightDto) {
        Right right = rightService.get(rightDto.getCode());
        converter.updateFromDto(right, rightDto);
        rightService.update(right);
    }

}