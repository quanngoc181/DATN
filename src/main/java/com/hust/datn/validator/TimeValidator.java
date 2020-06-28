package com.hust.datn.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TimeValidator implements ConstraintValidator<ValidTime, String> {

  @Override
  public void initialize(ValidTime time) {
  }

  @Override
  public boolean isValid(String time, ConstraintValidatorContext cxt) {
      return time != null && time.matches("^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$") && (time.length() == 5);
  }

}
