package com.github.tbquyen.changepassword;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ChangePasswordDAO {
	@Update("UPDATE users SET password=#{password}, status='1' WHERE username = #{username}")
	public int updatePassword(@Param("username") String username, @Param("password") String password);
}
