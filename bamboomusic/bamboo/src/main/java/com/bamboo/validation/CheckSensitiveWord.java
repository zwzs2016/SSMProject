package com.bamboo.validation;

import com.bamboo.annotation.ImageBase64Size;
import com.bamboo.annotation.SensitiveWord;
import com.bamboo.util.sensitivewdfilter.WordFilter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckSensitiveWord implements ConstraintValidator<SensitiveWord,String> {
    @Override
    public void initialize(SensitiveWord validator) {
        ConstraintValidator.super.initialize(validator);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !WordFilter.isContains(value);
    }
}
