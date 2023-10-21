package com.acme.learning.platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class LearningCenterPlatformApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearningCenterPlatformApplication.class, args);
    }

}
