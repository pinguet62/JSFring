package fr.pinguet62.jsfring.ws.dto.converter.profile;

import javax.inject.Inject;

import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;
import fr.pinguet62.jsfring.ws.dto.converter.SearchResultsConverter;

@Component
public final class ProfileSearchResultsConverter extends SearchResultsConverter<Profile, ProfileDto> {

    @Inject
    private ConversionService conversionService;

    @Override
    protected ProfileDto convertPojo(Profile pojo) {
        return conversionService.convert(pojo, ProfileDto.class);
    }

}