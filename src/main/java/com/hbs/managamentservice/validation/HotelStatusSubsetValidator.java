package com.hbs.managamentservice.validation;

import com.hbs.managamentservice.model.HotelStatus;
import com.hbs.managamentservice.validation.annotation.HotelStatusSubset;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Set;

public class HotelStatusSubsetValidator implements ConstraintValidator<HotelStatusSubset, HotelStatus> {

    private Set<HotelStatus> subset;

    @Override
    public void initialize(HotelStatusSubset constraint) {
        this.subset = Set.of(constraint.anyOf());
    }

    @Override
    public boolean isValid(HotelStatus value, ConstraintValidatorContext context) {
        return value == null || subset.contains(value);
    }
}
