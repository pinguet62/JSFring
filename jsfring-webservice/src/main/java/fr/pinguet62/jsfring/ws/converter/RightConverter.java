package fr.pinguet62.jsfring.ws.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.Right;
import fr.pinguet62.jsfring.ws.dto.RightDto;

@Component
public final class RightConverter implements Converter<Right, RightDto> {

    @Override
    public RightDto convert(Right right) {
        RightDto dto = new RightDto();
        dto.setCode(right.getCode());
        dto.setTitle(right.getTitle());
        return dto;
    }

}