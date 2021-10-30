package com.github.tbquyen.changepassword;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.github.tbquyen.login.LoginForm;

@Controller
public class ChangePasswordController {
	private static final String SAVED_REQUEST = "SPRING_SECURITY_SAVED_REQUEST";
	@Autowired
	private ChangePasswordValidator validator;
	@Autowired
	private ChangePasswordService changePasswordService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	@RequestMapping(value = { "/changePassword" }, method = RequestMethod.GET)
	public ModelAndView changePasswordGET(@ModelAttribute("ChangePasswordForm") ChangePasswordForm form,
			@RequestAttribute(name = "LoginForm", required = false) LoginForm formForward) {
		if (formForward != null)
			BeanUtils.copyProperties(formForward, form);
		return new ModelAndView("changePassword");
	}

	@RequestMapping(value = { "/changePassword" }, method = RequestMethod.POST)
	public ModelAndView changePasswordPOST(@ModelAttribute("ChangePasswordForm") @Validated ChangePasswordForm form, BindingResult result,
			HttpSession session, RedirectAttributes redirectAttributes) {

		if(result.hasErrors()) {
			ModelAndView mv = new ModelAndView("changePassword");
			mv.setStatus(HttpStatus.BAD_REQUEST);
			return mv;
		}

		int rcUpdate = changePasswordService.updatePassword(form.getUsername(), form.getPassword(),
				form.getNewpassword());

		if(rcUpdate == 0) {
			result.reject("login.001");
			ModelAndView mv = new ModelAndView("changePassword");
			mv.setStatus(HttpStatus.BAD_REQUEST);
			return mv;
		}

		LoginForm loginForm = new LoginForm();
		BeanUtils.copyProperties(form, loginForm);
		redirectAttributes.addFlashAttribute("LoginForm", loginForm);

		DefaultSavedRequest savedRequest = (DefaultSavedRequest) session.getAttribute(SAVED_REQUEST);
		session.removeAttribute(SAVED_REQUEST);

		String redirectPath = savedRequest == null ? "login" : savedRequest.getRequestURI();
		return new ModelAndView(new RedirectView(redirectPath));
	}
}
