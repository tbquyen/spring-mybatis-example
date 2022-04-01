package com.github.tbquyen.master.users;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Service
public class UsersValidator implements Validator {

	/**
	 * This Validator validates *just* ChangePasswordForm instances
	 */
	public boolean supports(Class<?> clazz) {
		return UsersForm.class == clazz;
	}

	public void validate(Object target, Errors errors) {
		UsersForm form = (UsersForm) target;

		ValidationUtils.rejectIfEmpty(errors, UsersConst.F_USERNAME, "app.001", new Object[] { UsersConst.F_USERNAME });
		if(form.getId() == 0) {
			ValidationUtils.rejectIfEmpty(errors, UsersConst.F_PASSWORD, "app.001", new Object[] { UsersConst.F_PASSWORD });
		}
		ValidationUtils.rejectIfEmpty(errors, UsersConst.F_ROLE, "app.001", new Object[] { UsersConst.F_ROLE });
	}
}
