package com.acme.learning.platform.iam.infrastructure.authorization.sfs.pipeline;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class is responsible for handling unauthorized requests
 * and sending the appropriate response to the client.
 * <p>
 *     This class is used by the {@link com.acme.learning.platform.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter} to handle unauthorized requests.
 *     The {@link com.acme.learning.platform.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter} is responsible for filtering the incoming requests and
 *     checking if the request contains a valid bearer token. If the request does not contain a valid bearer token, the {@link com.acme.learning.platform.iam.infrastructure.authorization.sfs.pipeline.UnauthorizedRequestHandlerEntryPoint}
 *     is called to handle the unauthorized request.
 * </p>
 */
@Component
public class UnauthorizedRequestHandlerEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(UnauthorizedRequestHandlerEntryPoint.class);

    /**
     * This method is called by the {@link com.acme.learning.platform.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter} when an unauthorized request is detected.
     * <p>
     *     This method is responsible for sending the appropriate response to the client.
     * </p>
     * @param request The incoming request
     * @param response The response to be sent to the client
     * @param authenticationException The exception thrown by the {@link com.acme.learning.platform.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter}
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException, ServletException {
        LOGGER.error("Unauthorized request: {}", authenticationException.getMessage());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized request detected");

    }
}
