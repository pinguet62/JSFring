package fr.pinguet62.jsfring.webservice.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.webservice.dto.RightDto;

@Component
public final class RightConverter implements Converter<Right, RightDto> {

    @Override
    public RightDto convert(Right right) {
        RightDto dto = new RightDto();
        dto.setCode(right.getCode());
        dto.setTitle(right.getTitle());
        return dto;
    }

    @Override
    public String toString() {
        return Right.class.getName() + " -> " + RightDto.class.getName() + " : " + super.toString();
    }

}