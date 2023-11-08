package com.acme.learning.platform.iam.infrastructure.hashing.bcrypt.services;

import com.acme.learning.platform.iam.application.internal.outboundservices.hashing.HashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Hashing service implementation.
 * Implementation of {@link HashingService} using BCrypt algorithm.
 */
@Service
public class HashingServiceImpl implements HashingService {
    private final BCryptPasswordEncoder passwordEncoder;

    public HashingServiceImpl() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
