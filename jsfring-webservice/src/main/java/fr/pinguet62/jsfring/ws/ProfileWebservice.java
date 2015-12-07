package fr.pinguet62.jsfring.ws;

import static java.util.stream.Collectors.toList;

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
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.expr.ComparableExpressionBase;

import fr.pinguet62.jsfring.model.Profile;
import fr.pinguet62.jsfring.model.QProfile;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.ws.dto.ProfileDto;
import fr.pinguet62.jsfring.ws.dto.SearchResultsDto;

@Path("/profile")
public final class ProfileWebservice {

    @Inject
    private ConversionService conversionService;

    @Inject
    private ProfileService profileService;

    /**
     * 
     * @param page The page index.<br>
     *            The first page is {@code 1}.
     * @param pageSize The page size.
     * @param sortField The field to sort.<br>
     *            {@code null} for default order.
     * @param sortOrder The sort order.<br>
     *            Default: ascending.<br>
     *            Not used if no field to sort.
     * @return The found results.
     */
    @GET
    @Path("/find")
    @Produces(MediaType.APPLICATION_JSON)
    public SearchResultsDto<ProfileDto> find(@QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize,
            @QueryParam("sortField") String sortField, @QueryParam("sortOrder") @DefaultValue("asc") String sortOrder) {
        JPAQuery query = new JPAQuery().from(QProfile.profile);
        // Pagination
        if (page != null && pageSize != null) {
            int first = page * pageSize - 1;
            query.offset(first);
            query.limit(pageSize);
        }

        // Sorting
        if (sortField != null) {
            // Field
            ComparableExpressionBase<?> field;
            if (sortField == null)
                field = null;
            else if (sortField.equals("id"))
                field = QProfile.profile.id;
            else if (sortField.equals("title"))
                field = QProfile.profile.title;
            else
                throw new IllegalArgumentException("Unknown sort field: " + sortOrder);

            // Order
            OrderSpecifier<?> order;
            if (sortOrder.equals("asc"))
                order = field.asc();
            else if (sortOrder.equals("desc"))
                order = field.desc();
            else
                throw new IllegalArgumentException("Unknown sort order: " + sortOrder);

            // Apply
            query.orderBy(order);
        }

        // Filtering
        SearchResults<Profile> searchResults = profileService.findPanginated(query);
        return conversionService.convert(searchResults, (Class<SearchResultsDto<ProfileDto>>) (Class<?>) SearchResults.class);
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
        return profileService.getAll().stream().map(x -> conversionService.convert(x, ProfileDto.class)).collect(toList());
    }

}