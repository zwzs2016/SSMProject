package com.bamboo.validation;

import com.bamboo.annotation.ImageBase64Size;
import com.uwan.common.util.FileUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckImageSize implements ConstraintValidator<ImageBase64Size,String> {
    @Override
    public void initialize(ImageBase64Size validator) {
        ConstraintValidator.super.initialize(validator);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //filesize check
        String base64Str=StringUtils.substringAfter(value,";base64,");
        return FileUtils.isBase64(base64Str) && FileUtils.checkBase64Size(base64Str,64,"K");
    }
}
