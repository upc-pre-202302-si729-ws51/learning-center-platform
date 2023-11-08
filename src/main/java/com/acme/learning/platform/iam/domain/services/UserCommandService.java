package com.acme.learning.platform.iam.domain.services;

import com.acme.learning.platform.iam.domain.model.aggregates.User;
import com.acme.learning.platform.iam.domain.model.commands.SignInCommand;
import com.acme.learning.platform.iam.domain.model.commands.SignUpCommand;

import java.util.Optional;

/**
 * User command service.
 * <p>
 *     This service is responsible for handling user aggregate commands.
 * </p>
 */
public interface UserCommandService {

    /**
     * Handle the sign-up command.
     * <p>
     *     This method is responsible for handling the sign-up command.
     * </p>
     * @param command The sign-up command.
     * @return The user aggregate.
     * @see SignUpCommand
     */
    Optional<User> handle(SignUpCommand command);

    /**
     * Handle the sign-in command.
     * <p>
     *     This method is responsible for handling the sign-in command.
     * </p>
     * @param command The sign-in command.
     * @return The user aggregate.
     * @see SignInCommand
     */
    Optional<User> handle(SignInCommand command);
}
