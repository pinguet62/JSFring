package fr.pinguet62.jsfring.webservice.controller;

import fr.pinguet62.jsfring.model.sql.Profile;
import fr.pinguet62.jsfring.model.sql.QProfile;
import fr.pinguet62.jsfring.service.ProfileService;
import fr.pinguet62.jsfring.webservice.converter.PageMapper;
import fr.pinguet62.jsfring.webservice.converter.ProfileMapper;
import fr.pinguet62.jsfring.webservice.dto.PageDto;
import fr.pinguet62.jsfring.webservice.dto.ProfileDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static fr.pinguet62.jsfring.webservice.controller.ProfileController.PATH;
import static java.util.Objects.requireNonNull;

@RestController
@RequestMapping(PATH)
public final class ProfileController extends AbstractController<Profile, Integer> {

    public static final String PATH = "/profile";

    private final ProfileService profileService;

    private final ProfileMapper converter;

    public ProfileController(ProfileService profileService, ProfileMapper converter) {
        this.profileService = requireNonNull(profileService);
        this.converter = requireNonNull(converter);
    }

    @PutMapping
    public void create(@RequestBody ProfileDto profileDto) {
        Profile profile = converter.fromDto(profileDto);
        profileService.create(profile);
    }

    /**
     * @param page      The page index.<br>
     *                  Default: the first page is {@code 0}.
     * @param pageSize  The page size.
     * @param sortField The field to sort.<br>
     *                  Default: {@code null} for default order.
     * @param sortOrder The sort order.<br>
     *                  Default: ascending.<br>
     *                  Not used if no field to sort.
     * @return The found results.
     */
    @GetMapping("/find")
    public PageDto<ProfileDto> find(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "sortField") String sortField,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") String sortOrder
    ) {
        Page<Profile> results = super.findAll(profileService, QProfile.profile, page, pageSize, sortField, sortOrder);
        return PageMapper.toDto(results, converter::toDto);
    }

    @GetMapping("/{id}")
    public ProfileDto get(@PathVariable int id) {
        Profile profile = profileService.get(id);
        return converter.toDto(profile);
    }

    @GetMapping
    public List<ProfileDto> list() {
        List<Profile> profiles = profileService.getAll();
        return converter.toDto(profiles);
    }

    @PostMapping
    public void update(@RequestBody ProfileDto profileDto) {
        Profile profile = profileService.get(profileDto.getId());
        converter.updateFromDto(profile, profileDto);
        profileService.update(profile);
    }

}