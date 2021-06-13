package com.supervielle.people.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervielle.people.constraint.age.AgeConstraint;
import com.supervielle.people.constraint.check_at_lest_not_null.CheckAtLeastOneNotNull;
import com.supervielle.people.util.DateUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PersonDTOAdd {

    @NonNull
    private Long dni;

    @NonNull
    private String dniType;

    @NonNull
    private String country;

    @NonNull
    private String gender;

    private String firstName;

    private String lastName;

    @NonNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.DATE_PATTERN)
    @AgeConstraint(min = 18)
    private LocalDate birthDate;

    @NonNull
    @CheckAtLeastOneNotNull
    private ContactDataDTO contactData;

    public String getCountry() {
        return StringUtils.upperCase(this.country);
    }

    public String getDniType() {
        return StringUtils.upperCase(this.dniType);
    }

}
