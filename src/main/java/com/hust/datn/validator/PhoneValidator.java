package com.hust.datn.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhone, String> {

  @Override
  public void initialize(ValidPhone date) {
  }

  @Override
  public boolean isValid(String phone, ConstraintValidatorContext cxt) {
	  return phone != null && phone.matches("[0-9]+") && (phone.length() == 10);
  }

}
