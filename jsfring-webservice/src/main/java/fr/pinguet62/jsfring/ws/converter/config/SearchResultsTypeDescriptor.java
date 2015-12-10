package fr.pinguet62.jsfring.ws.converter.config;

import org.springframework.core.ResolvableType;
import org.springframework.core.convert.TypeDescriptor;

import com.mysema.query.SearchResults;

public class SearchResultsTypeDescriptor extends TypeDescriptor {

    private static final long serialVersionUID = 1;

    public SearchResultsTypeDescriptor(Class<?> typeElement) {
        super(ResolvableType.forClassWithGenerics(SearchResults.class, typeElement), null, null);
    }

}