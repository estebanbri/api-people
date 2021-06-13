package com.supervielle.people.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "CONTACT_DATA")
@Data
public class ContactData extends AbstractAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @MapsId("contact_data_id")
    private Long id;

    private String phoneNumber;

    @Email
    private String email;

}
