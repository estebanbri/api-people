package com.supervielle.people.dto;

import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class ContactDataDTO {

    private String phoneNumber;

    @Email
    private String email;

}
