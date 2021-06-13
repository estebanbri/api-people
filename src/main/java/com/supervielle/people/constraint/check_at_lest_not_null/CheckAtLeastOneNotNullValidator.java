package com.supervielle.people.constraint.check_at_lest_not_null;

import org.apache.commons.beanutils.PropertyUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

public class CheckAtLeastOneNotNullValidator implements ConstraintValidator<CheckAtLeastOneNotNull, Object> {

    public void initialize(CheckAtLeastOneNotNull constraintAnnotation) {
    }

    public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields){
            Object property = null;
            try {
                property = PropertyUtils.getProperty(object, field.getName());
            }catch(Exception e){
                e.printStackTrace();
            }
            if (property != null) return true;
        }
        return false;
    }
}
