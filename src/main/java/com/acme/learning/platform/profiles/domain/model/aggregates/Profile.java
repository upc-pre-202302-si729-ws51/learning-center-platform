package com.acme.learning.platform.profiles.domain.model.aggregates;

import com.acme.learning.platform.profiles.domain.model.valueobjects.EmailAddress;
import com.acme.learning.platform.profiles.domain.model.valueobjects.PersonName;
import com.acme.learning.platform.profiles.domain.model.valueobjects.StreetAddress;
import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Profile extends AbstractAggregateRoot<Profile> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;

    @Embedded
    private PersonName name;

    @Embedded
    private StreetAddress address;

    @Embedded
    private EmailAddress email;

    @CreatedDate
    private Date createdAt;

    @LastModifiedDate
    private Date updatedAt;

    public Profile(String firstName, String lastName, String email, String street, String number, String city, String zipCode, String country) {
        this.name = new PersonName(firstName, lastName);
        this.address = new StreetAddress(street, number, city, zipCode, country);
        this.email = new EmailAddress(email);
    }

    public Profile() {

    }

    public void updateName(String firstName, String lastName) {
        this.name = new PersonName(firstName, lastName);
    }

    public void updateEmail(String email) {
        this.email = new EmailAddress(email);
    }

    public void updateAddress(String streetAddress, String city, String zipCode, String country) {
        this.address = new StreetAddress(streetAddress, city, zipCode, country);
    }

    public String getFullName() {
        return name.getFullName();
    }

    public String getStreetAddress() {
        return address.getStreetAddress();
    }


    public String getEmailAddress() {
        return email.address();
    }
}
