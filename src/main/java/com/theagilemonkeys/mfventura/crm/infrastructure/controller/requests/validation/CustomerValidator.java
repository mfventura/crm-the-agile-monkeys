package com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = CustomerValidatorImpl.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomerValidator {
  String field();
  String fieldMatch();
  String message();
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  @Target({ ElementType.TYPE })
  @Retention(RetentionPolicy.RUNTIME)
  @interface List {
    CustomerValidator[] value();
  }
}
