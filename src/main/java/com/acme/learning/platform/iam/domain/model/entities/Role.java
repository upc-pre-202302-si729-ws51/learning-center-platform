package com.acme.learning.platform.iam.domain.model.entities;

import com.acme.learning.platform.iam.domain.model.valueobjects.Roles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;

/**
 * Role entity
 *
 * <p>
 *     This entity represents the role of a user in the system.
 *     It is used to define the permissions of a user.
 * </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@With
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Roles name;

    /**
     * Constructor
     * @param name Role name
     */
    public Role(Roles name) {
        this.name = name;
    }
}
