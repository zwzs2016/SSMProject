package com.bamboo.annotation;

import com.bamboo.validation.CheckImageSize;
import com.bamboo.validation.CheckPassword;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckImageSize.class)
public @interface ImageBase64Size {
    String message() default "图片大小超出";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
