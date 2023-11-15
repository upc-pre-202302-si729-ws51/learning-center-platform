package com.acme.learning.platform.iam.infrastructure.authorization.sfs.model;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

/**
 * This class is a builder for UsernamePasswordAuthenticationToken.
 * It is used to build the token with the principal and the request.
 */
public class UsernamePasswordAuthenticationTokenBuilder {

    /**
     * This method builds the token with the principal and the request.
     * @param principal The principal.
     * @param request The request.
     * @return The token.
     */
    public static UsernamePasswordAuthenticationToken build(UserDetails principal, HttpServletRequest request) {
        var usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(principal,
                        null, principal.getAuthorities());
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return usernamePasswordAuthenticationToken;
    }
}
