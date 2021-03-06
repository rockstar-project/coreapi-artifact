package com.rockstar.artifact.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.rockstar.artifact.service.SchemaValidator;

@Documented
@Constraint(validatedBy = SchemaValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSchema {
 
    String message() default "invalid schema content";
     
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
      
}