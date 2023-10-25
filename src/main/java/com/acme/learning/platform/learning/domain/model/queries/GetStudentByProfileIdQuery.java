package com.acme.learning.platform.learning.domain.model.queries;

import com.acme.learning.platform.learning.domain.model.valueobjects.ProfileId;

public record GetStudentByProfileIdQuery(ProfileId profileId) {
}
