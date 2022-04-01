package com.github.tbquyen.changepassword;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class ChangePasswordValidator implements Validator {

	/**
	 * This Validator validates *just* ChangePasswordForm instances
	 */
	public boolean supports(Class<?> clazz) {
		return ChangePasswordForm.class == clazz;
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, ChangePasswordConst.F_USERNAME, "app.001", new Object[] { "userName" });
		ValidationUtils.rejectIfEmpty(errors, ChangePasswordConst.F_PASSWORD, "app.001", new Object[] { "password" });
		ValidationUtils.rejectIfEmpty(errors, ChangePasswordConst.F_NEWPASSWORD, "app.001", new Object[] { "newpassword" });
	}
}
