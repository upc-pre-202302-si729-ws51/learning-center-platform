package com.acme.learning.platform.profiles.domain.model.commands;

public record CreateProfileCommand(String firstName, String lastName, String email, String street, String number, String city, String zipCode, String country) {
}
