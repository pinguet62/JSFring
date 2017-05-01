package fr.pinguet62.jsfring.webserivce.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.sql.Right;
import fr.pinguet62.jsfring.webserivce.dto.RightDto;

@Component
public final class RightFromDtoConverter implements Converter<RightDto, Right> {

    @Override
    public Right convert(RightDto dto) {
        Right right = new Right();
        right.setCode(dto.getCode());
        right.setTitle(dto.getTitle());
        return right;
    }

    @Override
    public String toString() {
        return RightDto.class.getName() + " -> " + Right.class.getName() + " : " + super.toString();
    }

}