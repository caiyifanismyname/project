package com.ss.vv.ss.service.impl;

import com.ss.vv.common.AbstractService;
import com.ss.vv.common.IOperations;
import com.ss.vv.ss.domain.User;
import com.ss.vv.ss.service.IUserService;
import com.ss.vv.ss.mapper.IUserMapper;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;




@Service("userService")
public class UserService extends AbstractService<User, User> implements IUserService{
	

	public UserService() {
		this.setTableName("user");
	}
	@Resource
	private IUserMapper userMapper;

	@Override
	protected IOperations<User, User> getMapper() {
		return userMapper;
	}
	@Override
	public void setTableName(String tableName){
		this.tableName = tableName;
	}

}
