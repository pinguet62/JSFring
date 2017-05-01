package fr.pinguet62.jsfring.webserivce;

import static fr.pinguet62.jsfring.webserivce.ProfileWebservice.PATH;
import static fr.pinguet62.jsfring.webserivce.converter.OrderConverter.ASC;
import static java.util.Objects.requireNonNull;
import static org.springframework.core.ResolvableType.forClassWithGenerics;
import static org.springframework.core.convert.TypeDescriptor.collection;
import static org.springframework.core.convert.TypeDescriptor.valueOf;

import java.util.List;

import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.pinguet62.jsfring.common.spring.GenericTypeDescriptor;
import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QProfile;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.webserivce.dto.PageDto;
import fr.pinguet62.jsfring.webserivce.dto.ProfileDto;

@RestController
@RequestMapping(PATH)
public final class ProfileWebservice extends AbstractWebservice<Profile, Integer> {

    public static final String PATH = "/profile";

    private final ConversionService conversionService;

    private final ProfileService profileService;

    public ProfileWebservice(ConversionService conversionService, ProfileService profileService) {
        super(conversionService);
        this.conversionService = requireNonNull(conversionService);
        this.profileService = requireNonNull(profileService);
    }

    @PutMapping
    public void create(ProfileDto profileDto) {
        Profile profile = conversionService.convert(profileDto, Profile.class);
        profileService.create(profile);
    }

    /**
     * @param page
     *            The page index.<br>
     *            Default: the first page is {@code 0}.
     * @param pageSize
     *            The page size.
     * @param sortField
     *            The field to sort.<br>
     *            Default: {@code null} for default order.
     * @param sortOrder
     *            The sort order.<br>
     *            Default: ascending.<br>
     *            Not used if no field to sort.
     * @return The found results.
     */
    @GetMapping("/find")
    public PageDto<ProfileDto> find(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "sortField") String sortField,
            @RequestParam(value = "sortOrder", defaultValue = ASC) String sortOrder) {
        Page<Profile> results = super.findAll(profileService, QProfile.profile, page, pageSize, sortField, sortOrder);
        return (PageDto<ProfileDto>) conversionService.convert(results,
                new GenericTypeDescriptor(forClassWithGenerics(Page.class, Profile.class)),
                new GenericTypeDescriptor(forClassWithGenerics(PageDto.class, ProfileDto.class)));
    }

    @GetMapping("/{id}")
    public ProfileDto get(@PathVariable int id) {
        Profile profile = profileService.get(id);
        if (profile == null)
            return null;
        return conversionService.convert(profile, ProfileDto.class);
    }

    @GetMapping
    public List<ProfileDto> list() {
        List<Profile> pojos = profileService.getAll();
        return (List<ProfileDto>) conversionService.convert(pojos, collection(List.class, valueOf(Profile.class)),
                collection(List.class, valueOf(ProfileDto.class)));
    }

    @PostMapping
    public void update(ProfileDto profileDto) {
        Profile profile = conversionService.convert(profileDto, Profile.class);
        profileService.update(profile);
    }

}