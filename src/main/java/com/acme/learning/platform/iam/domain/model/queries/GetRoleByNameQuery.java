package com.acme.learning.platform.iam.domain.model.queries;

import com.acme.learning.platform.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
}
