package com.acme.learning.platform.iam.infrastructure.tokens.jwt;

import com.acme.learning.platform.iam.application.internal.outboundservices.tokens.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    @Override
    public String generateToken(Authentication authentication) {
        return null;
    }

    @Override
    public String getUsernameFromToken(String token) {
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }
}
