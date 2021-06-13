package com.supervielle.people.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class PersonIdDTO {

    @NonNull
    private Long dni;

    @NonNull
    private String dniType;

    @NonNull
    private String country;

    @NonNull
    private String gender;

}
