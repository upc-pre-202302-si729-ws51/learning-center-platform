package com.acme.learning.platform.iam.application.internal.eventhandlers;

import com.acme.learning.platform.iam.domain.model.commands.SeedRolesCommand;
import com.acme.learning.platform.iam.domain.services.RoleCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * ApplicationReadyEventHandler class.
 * This class is responsible for handling the application ready event and
 * seeding the roles if needed.
 */
@Service
public class ApplicationReadyEventHandler {
    private final RoleCommandService roleCommandService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);

    public ApplicationReadyEventHandler(RoleCommandService roleCommandService) {
        this.roleCommandService = roleCommandService;
    }

    /**
     * This method is responsible for handling the application ready event and
     * seeding the roles if needed.
     *
     * @param event ApplicationReadyEvent object
     *              This event is published when the ApplicationContext is fully
     *              refreshed and ready for use but before any actual work has been
     *              done.
     * @see ApplicationReadyEvent
     */
    @EventListener
    public void on(ApplicationReadyEvent event) {
        var name = event.getApplicationContext().getId();
        LOGGER.info("Starting to seed roles if needed for {} at {}", name, new Timestamp(System.currentTimeMillis()));
        var seedRolesCommand = new SeedRolesCommand();
        roleCommandService.handle(seedRolesCommand);
        LOGGER.info("Finished seeding roles if needed for {} at {}", name, new Timestamp(System.currentTimeMillis()));
    }
}
