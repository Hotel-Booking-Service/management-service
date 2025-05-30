package com.hbs.managamentservice.validation.annotation;

import com.hbs.managamentservice.model.HotelStatus;
import com.hbs.managamentservice.validation.HotelStatusSubsetValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = HotelStatusSubsetValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HotelStatusSubset {
    HotelStatus[] anyOf();

    String message() default "Status must be either PLANNED or ACTIVE";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
