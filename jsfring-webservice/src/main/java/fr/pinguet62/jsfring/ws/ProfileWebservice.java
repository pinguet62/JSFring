package fr.pinguet62.jsfring.ws;

import static fr.pinguet62.jsfring.ws.ProfileWebservice.PATH;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.core.ResolvableType.forClassWithGenerics;
import static org.springframework.core.convert.TypeDescriptor.collection;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;

import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QProfile;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.util.spring.GenericTypeDescriptor;
import fr.pinguet62.jsfring.ws.converter.OrderConverter;
import fr.pinguet62.jsfring.ws.dto.PageDto;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;

@Path(PATH)
public final class ProfileWebservice extends AbstractWebservice<Profile, Integer> {

    public static final String PATH = "/profile";

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private ProfileService profileService;

    @PUT
    @Path("/")
    @Consumes(APPLICATION_JSON)
    public void create(ProfileDto profileDto) {
        Profile profile = conversionService.convert(profileDto, Profile.class);
        profileService.create(profile);
    }

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
    @Produces(APPLICATION_JSON)
    public PageDto<ProfileDto> find(@QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("20") int pageSize, @QueryParam("sortField") String sortField,
            @QueryParam("sortOrder") @DefaultValue(OrderConverter.ASC) String sortOrder) {
        Page<Profile> results = super.findAll(profileService, QProfile.profile, page, pageSize, sortField, sortOrder);
        return (PageDto<ProfileDto>) conversionService.convert(results,
                new GenericTypeDescriptor(forClassWithGenerics(Page.class, Profile.class)),
                new GenericTypeDescriptor(forClassWithGenerics(PageDto.class, ProfileDto.class)));
    }

    @GET
    @Path("/{id}")
    @Produces(APPLICATION_JSON)
    public ProfileDto get(@PathParam("id") int id) {
        Profile profile = profileService.get(id);
        if (profile == null)
            return null;
        return conversionService.convert(profile, ProfileDto.class);
    }

    @GET
    @Path("/")
    @Produces(APPLICATION_JSON)
    public List<ProfileDto> list() {
        List<Profile> pojos = profileService.getAll();
        return (List<ProfileDto>) conversionService.convert(pojos, collection(List.class, valueOf(Profile.class)),
                collection(List.class, valueOf(ProfileDto.class)));
    }

    @POST
    @Path("/")
    @Consumes(APPLICATION_JSON)
    public void update(ProfileDto profileDto) {
        Profile profile = conversionService.convert(profileDto, Profile.class);
        profileService.update(profile);
    }

}