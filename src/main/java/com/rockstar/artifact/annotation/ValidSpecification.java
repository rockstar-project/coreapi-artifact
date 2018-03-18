package com.rockstar.artifact.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.rockstar.artifact.service.SpecificationValidator;

@Documented
@Constraint(validatedBy = SpecificationValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSpecification {
 
     
    String message() default "specification not selected or valid";
     
    Class<?>[] groups() default {};
     
    Class<? extends Payload>[] payload() default {};
      
}