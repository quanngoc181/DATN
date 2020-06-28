package com.hust.datn.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CostValidator implements ConstraintValidator<ValidCost, String> {

  @Override
  public void initialize(ValidCost cost) {
  }

  @Override
  public boolean isValid(String cost, ConstraintValidatorContext cxt) {
      if(cost == null) return false;
      try {
    	  int c = Integer.parseInt(cost);
    	  if(c >= 0) return true;
    	  else return false;
      } catch (Exception e) {
    	  return false;
      }
  }

}
