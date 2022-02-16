package com.algaworks.algafood.validation.validators;

import com.algaworks.algafood.validation.annotations.FileSize;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    private DataSize maxSize;

    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.maxSize = DataSize.parse(constraintAnnotation.maxSize());
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        return  ObjectUtils.isEmpty(file) || file.getSize() <= this.maxSize.toBytes();
    }
}
