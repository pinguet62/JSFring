package fr.pinguet62.jsfring.ws.converter.config;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;

import fr.pinguet62.jsfring.ws.dto.SearchResultsDto;

public class SearchResultsDtoTypeDescriptor extends TypeDescriptor {

    private static final long serialVersionUID = 1;

    public SearchResultsDtoTypeDescriptor(Class<?> typeElement) {
        super(ResolvableType.forClassWithGenerics(SearchResultsDto.class, typeElement), null, null);
    }

}