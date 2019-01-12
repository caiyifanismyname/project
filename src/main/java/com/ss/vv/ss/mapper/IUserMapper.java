/** 
* 
* @author bingoWu 
* @data 2018年12月23日 00:03:48  
*/  

package com.ss.vv.ss.mapper;
import com.ss.vv.common.IOperations;
import com.ss.vv.ss.domain.User;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface IUserMapper extends IOperations<User, User> {
    public User selectLogin(Map<String,Object>map);
    
    User selectByNameAndPwd(User user);

    int insert(User user);

    int updateFirst(User user);

    int selectIsName(User user);

    String selectPasswordByName(User user);
    
    
	@Select("select username,user_email,user_phone,password from user where user_email = #{user_email} and password = #{password}")
	List<User> findChangeMessage(@Param("user_email")String user_email,@Param("password")String password);
	
	
	@Update("update user set username=#{username},user_email=#{user_email},user_phone=#{user_phone},password=#{password} where user_email=#{user_email1} and password=#{password1}")
	void updateMessage(@Param("username")String username,@Param("user_email")String user_email,
			@Param("user_phone")String user_phone,@Param("password")String password,
			@Param("user_email1")String user_email1,@Param("password1")String password1);
    
}

