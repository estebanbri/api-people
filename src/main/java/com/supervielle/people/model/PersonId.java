package com.supervielle.people.model;

import com.supervielle.people.model.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class PersonId implements Serializable {

    @NonNull
    private Long dni;

    @NonNull
    private String dniType;

    @NonNull
    private String country;

    @NonNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

}
