package com.acme.learning.platform.iam.infrastructure.hashing.bcrypt.services;

import com.acme.learning.platform.iam.application.internal.outboundservices.hashing.HashingService;
import com.acme.learning.platform.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Hashing service implementation.
 * Implementation of {@link HashingService} using BCrypt algorithm.
 */
@Service
public class HashingServiceImpl implements BCryptHashingService {
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Constructor.
     */
    public HashingServiceImpl() {
        passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * This method is used to encode the password.
     * @param rawPassword the password to be encoded.
     * @return the encoded password.
     */
    @Override
    public String encode(CharSequence rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * This method is used to check if the raw password matches the encoded password.
     * @param rawPassword the raw password.
     * @param encodedPassword the encoded password.
     * @return true if the raw password matches the encoded password, false otherwise.
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
