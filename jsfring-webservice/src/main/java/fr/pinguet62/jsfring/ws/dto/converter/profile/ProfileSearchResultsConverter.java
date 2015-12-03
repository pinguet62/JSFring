package fr.pinguet62.jsfring.ws.dto.converter.profile;

import org.springframework.core.convert.converter.Converter;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;
import fr.pinguet62.jsfring.ws.dto.converter.SearchResultsConverter;

public final class ProfileSearchResultsConverter extends SearchResultsConverter<Profile, ProfileDto> {

    // TODO Spring Converter @Inject
    private Converter<Profile, ProfileDto> profileConverter = new ProfileConverter();

    @Override
    protected ProfileDto convertPojo(Profile pojo) {
        return profileConverter.convert(pojo);
    }

}