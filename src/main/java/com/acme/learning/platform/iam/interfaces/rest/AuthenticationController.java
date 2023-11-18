package com.acme.learning.platform.iam.interfaces.rest;

import com.acme.learning.platform.iam.domain.services.UserCommandService;
import com.acme.learning.platform.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.acme.learning.platform.iam.interfaces.rest.resources.SignInResource;
import com.acme.learning.platform.iam.interfaces.rest.resources.SignUpResource;
import com.acme.learning.platform.iam.interfaces.rest.resources.UserResource;
import com.acme.learning.platform.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.acme.learning.platform.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.acme.learning.platform.iam.interfaces.rest.transform.SignUpCommandFromResourceAssembler;
import com.acme.learning.platform.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Authentication Controller
 * This controller is responsible for handling the authentication endpoints
 * <p>
 *     The authentication endpoints are:
 *     <ul>
 *         <li>Sign In</li>
 *         <li>Sign Up</li>
*     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;


    public AuthenticationController(UserCommandService userCommandService) {
        this.userCommandService = userCommandService;
    }

    /** Sign In
     * This endpoint is responsible for handling the sign-in request
     * @param signInResource The sign-in resource
     * @return The authenticated user resource
     */
    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource signInResource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(signInResource);
        var authenticatedUser = userCommandService.handle(signInCommand);

        if (authenticatedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(
                authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());

        return ResponseEntity.ok(authenticatedUserResource);
    }

    /** Sign Up
     * This endpoint is responsible for handling the sign-up request
     * @param signUpResource The sign-up resource
     * @return The user resource
     */
    @PostMapping("/sign-up")
    public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
        var signUpCommand = SignUpCommandFromResourceAssembler.toCommandFromResource(signUpResource);
        var user = userCommandService.handle(signUpCommand);
        if (user.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }
}
