package com.acme.learning.platform.profiles.domain.model.queries;

import com.acme.learning.platform.profiles.domain.model.valueobjects.EmailAddress;

public record GetProfileByEmailQuery(EmailAddress emailAddress) {
}
