package com.bamboo.annotation;

import com.bamboo.validation.CheckImageSize;
import com.bamboo.validation.CheckSensitiveWord;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckSensitiveWord.class)
public @interface SensitiveWord {
    String message() default "含有敏感信息";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
