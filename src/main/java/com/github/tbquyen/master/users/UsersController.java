package com.github.tbquyen.master.users;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.github.tbquyen.datatables.params.DataTablesResponse;
import com.github.tbquyen.entity.Users;

@Controller
@RequestMapping(value = { UsersConst.URL })
public class UsersController {
	@Autowired
	private UsersService usersService;
	@Autowired
	private UsersValidator validator;
	@Autowired
	private MessageSource messageSource;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		if (binder.getTarget() == null)
			return;

		if (binder.getTarget().getClass() == UsersForm.class) {
			binder.addValidators(validator);
		}
	}

	@GetMapping
	public ModelAndView usersGET() {
		return new ModelAndView(UsersConst.VIEW);
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String usersPOST(@ModelAttribute() DataTablesUsersRequest form) {
		DataTablesResponse response = usersService.loadUsersPage(form);
		return response.toString();
	}

	@GetMapping(value = "/user/{id}")
	public ModelAndView usersGET(@ModelAttribute(name = UsersConst.F_NAME) UsersForm form,
			@PathVariable(name = "id") long id) {

		Users user = usersService.getUserById(id);

		if (user != null) {
			BeanUtils.copyProperties(user, form);
		}

		return new ModelAndView(UsersConst.VIEW_EDIT);
	}

	@PostMapping(value = "/user")
	public ModelAndView usersPOST(@ModelAttribute(name = UsersConst.F_NAME) @Validated UsersForm form,
			BindingResult result) {
		if (result.hasErrors()) {
			ModelAndView mv = new ModelAndView(UsersConst.VIEW_EDIT);
			mv.setStatus(HttpStatus.BAD_REQUEST);
			return mv;
		}

		Map<String, Object> object = new HashMap<String, Object>();

		if (form.getId() == 0) {
			int row = usersService.insert(form);
			if (row > 0) {
				object.put("message",
						messageSource.getMessage("app.002", new Object[] { UsersConst.F_USERNAME }, null));
				object.put("length", row);
			}
		} else {
			int row = usersService.update(form);
			if (row > 0) {
				object.put("message",
						messageSource.getMessage("app.003", new Object[] { UsersConst.F_USERNAME }, null));
				object.put("length", row);
			}
		}

		return new ModelAndView(new MappingJackson2JsonView(), object);
	}

	@DeleteMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String usersDELETE(@PathVariable(name = "id") long id) {
		int row = usersService.delete(id);
		JSONObject object = new JSONObject();
		if (row > 0) {
			object.put("message", messageSource.getMessage("app.004", new Object[] { UsersConst.F_USERNAME }, null));
			object.put("length", row);
		}

		return object.toString();
	}
}
