package com.bamboo.annotation;

import com.bamboo.validation.CheckPassword;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckPassword.class)
public @interface Identical {
    String message() default "两次密码不一样";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
