package com.bamboo.validation;

import com.bamboo.annotation.Identical;
import com.bamboo.dto.UserDTO;
import org.apache.kafka.common.protocol.types.Field;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckPassword implements ConstraintValidator<Identical, UserDTO> {
    @Override
    public void initialize(Identical validator) {
        ConstraintValidator.super.initialize(validator);
    }

    @Override
    public boolean isValid(UserDTO value, ConstraintValidatorContext context) {
        //校验两次输入的密码是否一致
        String password=value.getPassword();
        String repassword=value.getRepassword();
        if (password.equals(repassword)){
            return true;
        }
        return false;
    }
}
