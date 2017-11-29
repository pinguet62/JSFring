package fr.pinguet62.jsfring.webservice.controller

import fr.pinguet62.jsfring.model.sql.Profile
import fr.pinguet62.jsfring.model.sql.QProfile
import fr.pinguet62.jsfring.service.ProfileService
import fr.pinguet62.jsfring.webservice.controller.ProfileController.Companion.PATH
import fr.pinguet62.jsfring.webservice.converter.ProfileMapper
import fr.pinguet62.jsfring.webservice.converter.toDto
import fr.pinguet62.jsfring.webservice.dto.PageDto
import fr.pinguet62.jsfring.webservice.dto.ProfileDto
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping(PATH)
class ProfileController(
        val profileService: ProfileService,
        val converter: ProfileMapper
) : AbstractController<Profile, Int>() {

    companion object {
        const val PATH = "/profile"
    }

    @PutMapping
    fun create(@RequestBody profileDto: ProfileDto) {
        var profile: Profile = converter.fromDto(profileDto)
        profileService.create(profile)
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
    fun find(
            @RequestParam(value = "page", defaultValue = "0") page: Int,
            @RequestParam(value = "pageSize", defaultValue = "20") pageSize: Int,
            @RequestParam(value = "sortField") sortField: String,
            @RequestParam(value = "sortOrder", defaultValue = "ASC") sortOrder: String
    ): Mono<PageDto<ProfileDto>> {
        return super
                .findAll(profileService, QProfile.profile, page, pageSize, sortField, sortOrder)
                .map { toDto(it, converter::toDto) }
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int) =
            profileService
                    .get(id)
                    .map { converter.toDto(it) }

    @GetMapping
    fun list(): Flux<ProfileDto> =
            profileService
                    .getAll()
                    .map { converter.toDto(it) }

    @PostMapping
    fun update(@RequestBody profileDto: ProfileDto) {
        profileService
                .get(profileDto.id)
                .doOnNext { converter.updateFromDto(it, profileDto) }
                .doOnNext { profileService.update(it) }
    }

}