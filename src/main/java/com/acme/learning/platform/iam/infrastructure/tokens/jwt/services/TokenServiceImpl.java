package com.acme.learning.platform.iam.infrastructure.tokens.jwt.services;

import com.acme.learning.platform.iam.application.internal.outboundservices.tokens.TokenService;
import com.acme.learning.platform.iam.infrastructure.tokens.jwt.BearerTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

/**
 * Implementation of the {@link TokenService} interface
 * This class is responsible for generating and validating JWTs
 * @see TokenService
 */
@Service
public class TokenServiceImpl implements BearerTokenService {

    private final Logger LOGGER = LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final String AUTHORIZATION_PARAMETER_NAME = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    private static final int TOKEN_BEGIN_INDEX = 7;

    @Value("${authorization.jwt.secret}")
    private String secret;

    @Value("${authorization.jwt.expiration.days}")
    private int expirationDays;

    /**
     * Gets a secret key to sign the JWT
     * @return a secret key to sign the JWT
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Builds a JWT with default parameters
     * @param username the username to be included in the JWT
     * @return a JWT with default parameters
     */
    private String buildTokenWithDefaultParameters(String username) {
        var issuedAt = new Date();
        var expiration = DateUtils.addDays(issuedAt, expirationDays);
        var key = getSigningKey();
        return Jwts.builder()
                .subject(username)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .signWith(key)
                .compact();

    }

    /**
     * Generates a JWT with default parameters
     * @param authentication the authentication object
     * @return a JWT with default parameters
     */
    @Override
    public String generateToken(Authentication authentication) {
        return buildTokenWithDefaultParameters(authentication.getName());
    }

    /**
     * Generates a JWT with default parameters
     * @param username the username to be included in the JWT
     * @return a JWT with default parameters
     */
    @Override
    public String generateToken(String username) {
        return buildTokenWithDefaultParameters(username);
    }

    /**
     * Extracts all claims from a JWT
     * @param token the JWT
     * @return all claims from a JWT
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }

    /**
     * Extracts a claim from a JWT
     * @param token the JWT
     * @param claimsResolver the claims resolver
     * @param <T> the type of the claim
     * @return a claim from a JWT
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the username from a JWT
     * @param token the JWT
     * @return the username from a JWT
     */
    @Override
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Validates a JWT
     * @param token the JWT
     * @return boolean indicating if the JWT is valid
     */
    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
            LOGGER.info("Token is valid");
            return true;
        } catch (SignatureException e) {
            LOGGER.error("Invalid JSON Web Token Signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOGGER.error("Invalid JSON Web Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JSON Web Token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOGGER.error("JSON Web Token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("JSON Web Token claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Validates if a JWT is present in the authorization header parameter
     */
    private boolean isTokenPresentIn(String authorizationParameter) {
        return StringUtils.hasText(authorizationParameter);
    }

    /**
     * Validates if a JWT is a bearer token
     */
    private boolean isBearerTokenIn(String authorizationParameter) {
        return authorizationParameter.startsWith(BEARER_TOKEN_PREFIX);
    }

    /**
     * Extracts the JWT from the authorization header parameter
     */
    private String extractTokenFrom(String authorizationHeaderParameter) {
        return authorizationHeaderParameter.substring(TOKEN_BEGIN_INDEX);
    }

    /**
     * Gets the authorization header parameter from the request
     */
    private String getAuthorizationParameterFrom(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_PARAMETER_NAME);
    }

    /**
     * Gets the bearer token from the request
     * @param request the request
     * @return the bearer token from the request
     */
    @Override
    public String getBearerTokenFrom(HttpServletRequest request) {
        String parameter = getAuthorizationParameterFrom(request);
        if (isTokenPresentIn(parameter) && isBearerTokenIn(parameter))
            return extractTokenFrom(parameter);
        return null;
    }

}
