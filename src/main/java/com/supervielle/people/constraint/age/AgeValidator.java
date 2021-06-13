package com.supervielle.people.constraint.age;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<AgeConstraint, LocalDate> {

    private int minAge;

    @Override
    public void initialize(AgeConstraint ageConstraint) {
        this.minAge = ageConstraint.min();
    }

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        return calculateAgeFromBirthdate(birthDate) >= minAge;
    }

    private int calculateAgeFromBirthdate(LocalDate birthDate) {
        LocalDate now = LocalDate.now();
        Period diff = Period.between(birthDate, now);
        return diff.getYears();
    }

}
