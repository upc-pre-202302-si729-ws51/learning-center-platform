package com.acme.learning.platform.iam.application.internal.outboundservices.tokens;

import org.springframework.security.core.Authentication;

/**
 * Token service
 * <p>
 *     This interface represents the token service.
 *     It is used to generate and validate a token.
 * </p>
 */
public interface TokenService {

    /**
     * Generate a token
     * @param authentication the authentication object
     * @return the generated token
     */
    String generateToken(Authentication authentication);

    /**
     * Get the username from a token
     * @param token the token
     * @return the username
     */
    String getUsernameFromToken(String token);

    /** Validate a token
     * @param token the token
     * @return true if the token is valid, false otherwise
     */
    boolean validateToken(String token);
}
