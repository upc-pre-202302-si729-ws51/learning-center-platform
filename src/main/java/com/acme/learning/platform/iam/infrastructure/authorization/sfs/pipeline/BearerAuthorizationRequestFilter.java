package com.acme.learning.platform.iam.infrastructure.authorization.sfs.pipeline;

import com.acme.learning.platform.iam.infrastructure.authorization.sfs.model.UsernamePasswordAuthenticationTokenBuilder;
import com.acme.learning.platform.iam.infrastructure.tokens.jwt.BearerTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter to validate the token and set the authentication in the security context
 * <p>
 *     This filter is executed before the {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}
 *     and after the {@link org.springframework.security.web.authentication.www.BasicAuthenticationFilter}
 *     in the Spring Security Filter Chain
 */
public class BearerAuthorizationRequestFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(BearerAuthorizationRequestFilter.class);
    private final BearerTokenService tokenService;

    @Qualifier("userDetailsServiceImpl")
    private final UserDetailsService userDetailsService;

    public BearerAuthorizationRequestFilter(BearerTokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Filter to validate the token and set the authentication in the security context
     * <p>
     *     This filter is executed before the {@link org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter}
     *     and after the {@link org.springframework.security.web.authentication.www.BasicAuthenticationFilter}
     *     in the Spring Security Filter Chain
     * </p>
     * @param request {@link HttpServletRequest} request
     * @param response {@link HttpServletResponse} response
     * @param filterChain {@link FilterChain} filter chain
     * @throws ServletException {@link ServletException} exception
     * @throws IOException {@link IOException} exception
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = tokenService.getBearerTokenFrom(request);
            LOGGER.info("Token: {}", token);
            if (token != null && tokenService.validateToken(token)) {
                String username = tokenService.getUsernameFromToken(token);
                var userDetails = userDetailsService.loadUserByUsername(username);
                SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationTokenBuilder.build(userDetails, request));
            } else
                LOGGER.info("Token is not valid");
        } catch (Exception e) {
            LOGGER.error("Cannot set user authentication: {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}
