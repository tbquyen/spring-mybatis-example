package com.github.tbquyen.login;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.github.tbquyen.config.MyPrincipal;

@Controller
public class LoginController {
	private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

	@RequestMapping(value = { "/login" }, method = RequestMethod.GET)
	public ModelAndView login(ModelMap modelMap, @ModelAttribute("LoginForm") LoginForm form, BindingResult result,
			HttpSession session, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("login");
		mv.setStatus(HttpStatus.UNAUTHORIZED);

		if (MyPrincipal.getInstance().isAuthenticated()) {
			LOGGER.debug("Is Login with acccount: " + MyPrincipal.getInstance().getName());
			RedirectView rv = new RedirectView("");
			return new ModelAndView(rv);
		}

		Object authenticationException = session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

		if (authenticationException instanceof PasswordExpiredException) {
			LOGGER.debug("Login Fail: PasswordExpiredException");
			PasswordExpiredException exception = (PasswordExpiredException) authenticationException;
			BeanUtils.copyProperties(exception.getLoginForm(), form);
			return new ModelAndView("forward:changePassword");
		}

		if (authenticationException instanceof LoginAuthenticationException) {
			LOGGER.debug("Login Fail: LoginAuthenticationException");
			LoginAuthenticationException exception = (LoginAuthenticationException) authenticationException;
			result.addAllErrors(exception.getErrors());
			BeanUtils.copyProperties(exception.getLoginForm(), form);
			mv.setStatus(HttpStatus.BAD_REQUEST);
		}

		if (authenticationException instanceof SessionAuthenticationException) {
			LOGGER.debug("Login Fail: SessionAuthenticationException");
			result.reject("login.002");
		}

		return mv;
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String home() {
		return "home";
	}
}
