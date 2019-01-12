package com.ss.vv.ss.controller.base;
import java.io.IOException;
import java.util.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ss.vv.common.WebResponse;
import com.ss.vv.ss.domain.Tab;
import com.ss.vv.ss.domain.User;
import com.ss.vv.ss.mapper.IUserMapper;
import com.ss.vv.ss.service.IUserService;
/** 
* 
* @author MrPeng
* @data 2018年12月23日 00:03:48  
*/
@Controller
@RequestMapping("/user")

public class UserController {
	

	@Autowired
    private IUserMapper userMapper;
	
	@Autowired
    private HttpSession httpSession;
	
	@Autowired
	protected WebResponse webResponse;

	@Resource
	protected IUserService userService;

	@RequestMapping(value = "/addOrEditUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse addOrEditUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, String user_id, @RequestParam(required = false) String username, @RequestParam(required = false) String user_email, @RequestParam(required = false) String user_phone, @RequestParam(required = false) String password) throws IOException {
		
		Object data = null;
		String statusMsg = "";
		Integer statusCode = 200;
		if (user_id == null || user_id.length() == 0) {
			
			
			this.addUser(request, response, session, username,user_email,user_phone, password);
			response.sendRedirect("/signin.html");
			return webResponse.getWebResponse(statusCode, statusMsg, data);
			
		} else {
			
			response.sendRedirect("/register.html");
			return webResponse.getWebResponse(statusCode, statusMsg, data);
			
			
		}
	}
	
	
	

	@RequestMapping(value = "/addUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse addUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, String username, String user_email,String user_phone,String password) {
		Object data = null;
		String statusMsg = "";
		Integer statusCode = 200;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("username", username);
		paramMap.put("user_email", user_email);
		paramMap.put("user_phone", user_phone);
		paramMap.put("password", password);
		data = paramMap;
		if (username == null || "".equals(username.trim()) || user_email == null || "".equals(user_phone.trim())|| user_phone == null || "".equals(password.trim())|| user_email == null || "".equals(password.trim())) {
			statusMsg = " 参数为空错误！！！！";
			statusCode = 201;
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		if (username.length() > 255 || user_email.length() > 65535||user_phone.length()>255||password.length()>255) {
			statusMsg = " 参数长度过长错误！！！";
			statusCode = 201;
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		User user = new User();
		boolean isAdd = true;
		return this.addOrEditUser(request, response, session, data, user,username,user_email, user_phone,password, isAdd);
	}

	@RequestMapping(value = "/editUser", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse editUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, String user_id, @RequestParam(required = false) String username, @RequestParam(required = false) String user_email, @RequestParam(required = false) String user_phone, @RequestParam(required = false) String password) {
		Object data = null;
		String statusMsg = "";
		Integer statusCode = 200;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("user_id", user_id);
		paramMap.put("username", username);
		paramMap.put("user_email", user_email);
		paramMap.put("user_phone", user_phone);
		paramMap.put("password", password);
		data = paramMap;
		if (user_id == null || "".equals(user_id.trim())) {
			statusMsg = "未获得主键参数错误！！！";
			statusCode = 201;
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		Integer user_idNumeri = user_id.matches("^[0-9]*$") ? Integer.parseInt(user_id) : 0;
		if (user_idNumeri == 0) {
			statusMsg = "主键不为数字错误！！！";
			statusCode = 201;
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		User userVo = this.userService.getById(user_idNumeri);
		User user = new User();
		BeanUtils.copyProperties(userVo, user);
		boolean isAdd = false;
		return this.addOrEditUser(request, response, session, data, user,username,user_email,user_phone,password,isAdd);
	}
/*
 * 
 */
private WebResponse addOrEditUser(HttpServletRequest request, HttpServletResponse response, HttpSession session, Object data, User user, String username, String user_email,String user_phone,String password, boolean isAdd) {
		String statusMsg = "";
		Integer statusCode = 200;
		if (username != null && !("".equals(username.trim()))) {
			if(username.length() > 255) {
				statusMsg = " 参数长度过长错误,testName";
				statusCode = 201;
				return webResponse.getWebResponse(statusCode, statusMsg, data);
			} 
			user.setUsername(username);
		}
		if (user_email != null && !("".equals(user_email.trim()))) {
			if(user_email.length() > 65535) {
				statusMsg = " 参数长度过长错误,info";
				statusCode = 201;
				return webResponse.getWebResponse(statusCode, statusMsg, data);
			} 
			user.setUser_email(user_email);
		}
		if (user_phone != null && !("".equals(user_phone.trim()))) {
			if(user_phone.length() > 255) {
				statusMsg = " 参数长度过长错误,phone";
				statusCode = 201;
				return webResponse.getWebResponse(statusCode, statusMsg, data);
			} 
			user.setUser_phone(user_phone);
		}
		if (password != null && !("".equals(password.trim()))) {
			if(password.length() > 255) {
				statusMsg = " 参数长度过长错误,phone";
				statusCode = 201;
				return webResponse.getWebResponse(statusCode, statusMsg, data);
			} 
			user.setPassword(password);
//			setUser_pass(password);
		}
		if (isAdd) {
			this.userService.insert(user);
			if (user.getUser_id() > 0) {
				statusMsg = "成功插入！！！";
			} else {
				statusCode = 202;
				statusMsg = "insert false";
			} 
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		int num = this.userService.update(user);
		if (num > 0) {
			statusMsg = "成功修改！！！";
		} else {
			statusCode = 202;
			statusMsg = "update false";
		}
		return webResponse.getWebResponse(statusCode, statusMsg, data);
	}

	@RequestMapping(value = "/getUserList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse getUserList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
								   @RequestParam(defaultValue = "1", required = false) Integer pageNo,
								   @RequestParam(defaultValue = "10", required = false) Integer pageSize,
								   @RequestParam(defaultValue = "正常", required = false) String tbStatus,
								   @RequestParam(required = false) String keyword,
								   @RequestParam(defaultValue = "user_id", required = false) String order,
								   @RequestParam(defaultValue = "desc", required = false) String desc ) {
		Object data = null;
		String statusMsg = "";
		int statusCode = 200;
		LinkedHashMap<String, String> condition = new LinkedHashMap<String, String>();
		if (keyword != null && keyword.length() > 0) {
			StringBuffer buf = new StringBuffer();
			buf.append("(");
			buf.append("username like '%").append(keyword).append("%'");
			buf.append(" or ");
			buf.append("user_email like '%").append(keyword).append("%'");
			buf.append(" or ");
			buf.append("user_phone like '%").append(keyword).append("%'");
			buf.append(" or ");
			buf.append("password like '%").append(keyword).append("%'");
			buf.append(")");
			condition.put(buf.toString(), "and");
		}
		String field = null;
		if (condition.size() > 0) {
			condition.put(condition.entrySet().iterator().next().getKey(), "");
		}
		int count = this.userService.getCount(condition, field);
		if (order != null && order.length() > 0 & "desc".equals(desc)) {
			order = order + " desc";
		}
		List<User> list = this.userService.getList(condition, pageNo, pageSize, order, field);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("total", count);
		int size = list.size();
		if (size > 0) {
			List<User> listFont = new ArrayList<User>();
			User vo;
			User voFont = new User();
			for (int i = 0; i < size; i++) {
				vo = list.get(i);
				BeanUtils.copyProperties(vo, voFont);
				listFont.add(voFont);
				voFont = new User();
			}
			map.put("list", listFont);
			data = map;
			statusMsg = "根据条件获取分页数据成功！！！";
		} else {
			map.put("list", list);
			data = map;
			statusCode = 202;
			statusMsg = "no record!!!";
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		return webResponse.getWebResponse(statusCode, statusMsg, data);
	}

}
