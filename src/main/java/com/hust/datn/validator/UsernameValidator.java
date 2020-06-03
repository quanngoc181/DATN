package com.hust.datn.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

  @Override
  public void initialize(ValidUsername username) {
  }

  @Override
  public boolean isValid(String username, ConstraintValidatorContext cxt) {
	  return username != null && username.matches("[A-Za-z0-9_]+") && (username.length() >= 8);
  }

}
