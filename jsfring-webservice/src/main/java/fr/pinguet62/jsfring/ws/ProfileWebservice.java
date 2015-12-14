package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.ws.ProfileWebservice.PATH;
import static org.springframework.core.ResolvableType.forClassWithGenerics;
import static org.springframework.core.convert.TypeDescriptor.collection;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.springframework.core.convert.ConversionService;

import com.mysema.query.SearchResults;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QProfile;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.util.spring.GenericTypeDescriptor;
import fr.pinguet62.jsfring.ws.converter.OrderConverter;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;
import fr.pinguet62.jsfring.ws.dto.SearchResultsDto;

@Path(PATH)
public final class ProfileWebservice extends AbstractWebservice<Profile, Integer> {

    public static final String PATH = "/profile";

    @Inject
    private ConversionService conversionService;

    @Inject
    private ProfileService profileService;

    /**
     * @param page The page index.<br>
     *            Default: the first page is {@code 0}.
     * @param pageSize The page size.
     * @param sortField The field to sort.<br>
     *            Default: {@code null} for default order.
     * @param sortOrder The sort order.<br>
     *            Default: ascending.<br>
     *            Not used if no field to sort.
     * @return The found results.
     */
    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResultsDto<ProfileDto> find(@QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("20") int pageSize, @QueryParam("sortField") String sortField,
            @QueryParam("sortOrder") @DefaultValue(OrderConverter.ASC) String sortOrder) {
        SearchResults<Profile> searchResults = super.find(QProfile.profile, page, pageSize, sortField, sortOrder);
        return (SearchResultsDto<ProfileDto>) conversionService.convert(searchResults,
                new GenericTypeDescriptor(forClassWithGenerics(SearchResults.class, Profile.class)),
                new GenericTypeDescriptor(forClassWithGenerics(SearchResultsDto.class, ProfileDto.class)));
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ProfileDto get(@PathParam("id") int id) {
        Profile profile = profileService.get(id);
        if (profile == null)
            return null;
        return conversionService.convert(profile, ProfileDto.class);
    }

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProfileDto> list() {
        List<Profile> pojos = profileService.getAll();
        return (List<ProfileDto>) conversionService.convert(pojos, collection(List.class, valueOf(Profile.class)),
                collection(List.class, valueOf(ProfileDto.class)));
    }
}