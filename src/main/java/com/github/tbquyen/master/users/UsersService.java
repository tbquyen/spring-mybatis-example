package com.github.tbquyen.master.users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.github.tbquyen.datatables.params.DataTablesResponse;
import com.github.tbquyen.entity.Users;

@Service
public class UsersService {
	@Autowired
	private UsersDAO usersDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public Users getUserById(long id) {
		if (id <= 0) {
			return null;
		}

		return usersDAO.getUserById(id);
	}

	public DataTablesResponse loadUsersPage(DataTablesUsersRequest form) {
		long recordsTotal = usersDAO.loadUsersRecordsTotal(form);
		List<Users> users = usersDAO.loadUsersPage(form);

		DataTablesResponse response = new DataTablesResponse();
		response.setDraw(form.getDraw());
		response.setRecordsTotal(recordsTotal);
		response.setRecordsFiltered(users.size());
		response.setData(users);

		return response;
	}

	@Transactional(rollbackFor = Throwable.class)
	public int insert(UsersForm form) {
		String rawPassword = form.getPassword();
		form.setPassword(passwordEncoder.encode(rawPassword));
		return usersDAO.insert(form);
	}

	@Transactional(rollbackFor = Throwable.class)
	public int update(UsersForm form) {
		String rawPassword = form.getPassword();
		if (!StringUtils.isEmpty(rawPassword)) {
			form.setPassword(passwordEncoder.encode(rawPassword));
		}

		return usersDAO.update(form);
	}

	@Transactional(rollbackFor = Throwable.class)
	public int delete(long id) {
		return usersDAO.delete(id);
	}
}
