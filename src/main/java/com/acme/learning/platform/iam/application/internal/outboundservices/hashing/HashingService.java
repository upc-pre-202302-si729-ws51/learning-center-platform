package com.acme.learning.platform.iam.application.internal.outboundservices.hashing;

/**
 * Hashing service
 * <p>
 *     This interface represents the hashing service.
 *     It is used to encode a password.
 * </p>
 */
public interface HashingService {
    /**
     * Encode a password
     * @param rawPassword the password to encode
     * @return the encoded password
     */
    String encode(CharSequence rawPassword);

    /**
     * Check if a raw password matches an encoded password
     * @param rawPassword the raw password
     * @param encodedPassword the encoded password
     * @return true if the raw password matches the encoded password, false otherwise
     */
    boolean matches(CharSequence rawPassword, String encodedPassword);

}
