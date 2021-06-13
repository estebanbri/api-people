package com.supervielle.people.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "PERSON")
@Data
public class Person extends AbstractAuditingEntity {

    @EmbeddedId
    private PersonId personId;

    private Long id;

    private String firstName;

    private String lastName;

    @NotNull
    private LocalDate birthDate;

    @NotNull
    @OneToOne(cascade=CascadeType.ALL)
    private ContactData contactData;

    private Long parentId;

}
