package com.bamboo.validation;

import com.bamboo.annotation.Identical;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckPassword implements ConstraintValidator<Identical,String> {
    @Override
    public void initialize(Identical validator) {
        ConstraintValidator.super.initialize(validator);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}
