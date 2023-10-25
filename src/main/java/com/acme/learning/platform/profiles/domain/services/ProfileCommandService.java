package com.acme.learning.platform.profiles.domain.services;

import com.acme.learning.platform.profiles.domain.model.commands.CreateProfileCommand;

public interface ProfileCommandService {
    Long handle(CreateProfileCommand command);
}
