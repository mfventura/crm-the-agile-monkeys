package com.theagilemonkeys.mfventura.crm.infrastructure.controller.requests.validation;

import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

@Component
public class CustomerValidatorImpl implements ConstraintValidator<CustomerValidator, Object> {
  @Value("#{${crm.customers.document.validation}}")
  private Map<String, String> documentValidations;
  private String field;
  private String fieldMatch;
  
  @Override
  public void initialize(CustomerValidator constraintAnnotation) {
    this.field = constraintAnnotation.field();
    this.fieldMatch = constraintAnnotation.fieldMatch();
  }
  
  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    if("document".equals(field)){
      String documentValue = (String) new BeanWrapperImpl(value)
              .getPropertyValue(field);
      String countryValue = (String) new BeanWrapperImpl(value)
              .getPropertyValue(fieldMatch);
      return validateDocument(documentValue, countryValue);
    }
    
    return false;
  }
  
  private boolean validateDocument(String documentValue, String countryValue) {
    if(documentValidations.get(countryValue) != null) {
      return documentValue.matches(documentValidations.get(countryValue));
    } else {
      return StringUtils.hasText(documentValue);
    }
  }
}
