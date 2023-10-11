package com.acme.learning.platform.learning.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProfileId(Long profileId) {

    public ProfileId() {
        this(0L);
    }

    public ProfileId {
        if (profileId < 0) {
            throw new IllegalArgumentException("Profile Id cannot be negative");
        }
    }
}
