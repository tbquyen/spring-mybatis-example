package com.github.tbquyen.master.users;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.StringUtils;

import com.github.tbquyen.datatables.params.DataTablesOrder;
import com.github.tbquyen.entity.Users;

@Mapper
public interface UsersDAO {
	@Select("SELECT * FROM users WHERE id = #{id}")
	@Results(id = "userMaster", value = { @Result(property = "id", column = "id"),
			@Result(property = "username", column = "username"), @Result(property = "password", column = "password"),
			@Result(property = "role", column = "role"), @Result(property = "status", column = "status"), })
	public Users getUserById(long id);

	@SelectProvider(type = UsersDAOBuilder.class, method = "loadUsersPage")
	@ResultMap(value = "userMaster")
	public List<Users> loadUsersPage(DataTablesUsersRequest form);

	@Select("SELECT COUNT(id) FROM users")
	@ResultType(value = Long.class)
	public long loadUsersRecordsTotal(DataTablesUsersRequest form);

	@Delete("DELETE FROM users WHERE id=#{id}")
	public int delete(long id);

	@Insert("INSERT INTO users (username, password, role, status) VALUES (#{username}, #{password}, #{role}, #{status})")
	public int insert(UsersForm form);

	@UpdateProvider(type = UsersDAOBuilder.class, method = "uploadUser")
	public int update(UsersForm form);

	public class UsersDAOBuilder {
		public String loadUsersPage(DataTablesUsersRequest form) {
			return new SQL() {
				{
					SELECT("*");
					FROM("users");
					if (!StringUtils.isEmpty(form.getUsername())) {
						form.setUsername("%" + form.getUsername() + "%");
						WHERE("username LIKE #{username}");
					}
					if (!StringUtils.isEmpty(form.getRole())) {
						WHERE("role = #{role}");
					}
					OFFSET(form.getStart());
					LIMIT(form.getLength());
					for (DataTablesOrder order : form.getOrder()) {
						ORDER_BY(order.toSQL());
					}
				}
			}.toString();
		}

		public String uploadUser(UsersForm form) {
			return new SQL() {
				{
					UPDATE("users");
					SET("username=#{username}");
					if (!StringUtils.isEmpty(form.getPassword())) {
						SET("password=#{password}");
						SET("status=0");
					}
					SET("role=#{role}");
					WHERE("id=#{id}");
				}
			}.toString();
		}
	}
}
