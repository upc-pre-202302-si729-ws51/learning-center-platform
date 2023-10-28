package com.acme.learning.platform.profiles.interfaces.rest;

import com.acme.learning.platform.profiles.domain.model.queries.GetProfileByIdQuery;
import com.acme.learning.platform.profiles.domain.services.ProfileCommandService;
import com.acme.learning.platform.profiles.domain.services.ProfileQueryService;
import com.acme.learning.platform.profiles.interfaces.rest.resources.CreateProfileResource;
import com.acme.learning.platform.profiles.interfaces.rest.resources.ProfileResource;
import com.acme.learning.platform.profiles.interfaces.rest.transform.CreateProfileCommandFromResourceAssembler;
import com.acme.learning.platform.profiles.interfaces.rest.transform.ProfileResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ProfilesController
 * <p>
 *     Profile Management Endpoints
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Profiles", description = "Profile Management Endpoints")
public class ProfilesController {
    private final ProfileCommandService profileCommandService;
    private final ProfileQueryService profileQueryService;

    public ProfilesController(ProfileCommandService profileCommandService, ProfileQueryService profileQueryService) {
        this.profileCommandService = profileCommandService;
        this.profileQueryService = profileQueryService;
    }

    /**
     * Create a new Profile
     * @param resource Create Profile Resource including the profile data
     * @return Profile Resource if created, otherwise 400
     */
    @PostMapping
    public ResponseEntity<ProfileResource> createProfile(@RequestBody CreateProfileResource resource) {
        var createProfileCommand = CreateProfileCommandFromResourceAssembler.toCommandFromResource(resource);
        var profileId = profileCommandService.handle(createProfileCommand);
        if (profileId == 0L) {
            return ResponseEntity.badRequest().build();
        }
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);

        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return new ResponseEntity<>(profileResource, HttpStatus.CREATED);
    }

    /**
     * Get Profile by Identifier
     * @param profileId the given Profile Identifier
     * @return Profile Resource if found, otherwise 404
     */
    @GetMapping("/{profileId}")
    public ResponseEntity<ProfileResource> getProfileById(@PathVariable Long profileId) {
        var getProfileByIdQuery = new GetProfileByIdQuery(profileId);
        var profile = profileQueryService.handle(getProfileByIdQuery);
        if (profile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var profileResource = ProfileResourceFromEntityAssembler.toResourceFromEntity(profile.get());
        return ResponseEntity.ok(profileResource);
    }
}
