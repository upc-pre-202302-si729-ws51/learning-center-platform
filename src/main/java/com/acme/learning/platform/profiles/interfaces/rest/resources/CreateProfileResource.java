package com.acme.learning.platform.profiles.interfaces.rest.resources;

public record CreateProfileResource(String firstName, String lastName, String email, String street, String number, String city, String zipCode, String country) {
}
