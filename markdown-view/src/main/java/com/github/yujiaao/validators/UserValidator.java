package com.github.yujiaao.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.github.yujiaao.User;
@Component
public class UserValidator implements Validator  {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User)target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "","Username is empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "Password is empty");
		if (user.getName().length()<5) {
			errors.rejectValue("name","", "Username length is less than 5");
		}
	}

}
