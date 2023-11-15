package com.acme.learning.platform.iam.infrastructure.tokens.jwt;

import com.acme.learning.platform.iam.application.internal.outboundservices.tokens.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

/**
 * BearerTokenService
 * <p>
 *     Service to handle bearer token.
 *     This service is used to generate and validate bearer token.
 *     This service is used to extract bearer token from request.
 * </p>
 */
public interface BearerTokenService extends TokenService {

    /**
     * Extract bearer token from request.
     * @param request the http request
     * @return bearer token
     */
    String getBearerTokenFrom(HttpServletRequest request);

    /**
     * Generate bearer token from authentication.
     * @param authentication the authentication object
     * @return bearer token
     */
    String generateToken(Authentication authentication);
}
