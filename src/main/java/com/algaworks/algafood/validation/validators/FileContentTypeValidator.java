package com.algaworks.algafood.validation.validators;

import com.algaworks.algafood.validation.annotations.FileContentType;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {

    private List<String> allowedContentTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowedContentTypes = Arrays.asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return ObjectUtils.isEmpty(file) || allowedContentTypes.contains(file.getContentType());
    }
}
