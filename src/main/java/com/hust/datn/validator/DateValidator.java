package com.hust.datn.validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

  @Override
  public void initialize(ValidDate date) {
  }

  @Override
  public boolean isValid(String date, ConstraintValidatorContext cxt) {
	  DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
      sdf.setLenient(false);
      try {
          sdf.parse(date);
      } catch (ParseException e) {
          return false;
      }
      return true;
  }

}
