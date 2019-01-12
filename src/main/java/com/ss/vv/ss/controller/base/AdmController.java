package com.ss.vv.ss.controller.base;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.vv.common.WebResponse;
import com.ss.vv.ss.domain.User;
import com.ss.vv.ss.domain.Zero;
import com.ss.vv.ss.mapper.IUserMapper;
import com.ss.vv.ss.mapper.IZeroMapper;

/**
 *
 * @author MrPeng
 * @data 2018年12月23日 00:03:48
 */

@Controller
public class AdmController {
	
	@Resource
	protected IUserMapper iUserMapper ;

	@Resource
	protected IZeroMapper zeroMapper;

	@Resource
	private WebResponse webResponse;

	@RequestMapping("/adm")
	public String admHtml() {
		return "/test";
	}

	@RequestMapping("/ads")
	public String adsHtml() {
		return "/signin2";
	}

	@RequestMapping("/adminVerify")
	public String addOrEditTab(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			String a_id, @RequestParam(required = false) String a_name, @RequestParam(required = false) String a_pass) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("a_name", a_name);
		map.put("a_pass", a_pass);
		Zero zero = zeroMapper.selectLogin(map);
		if (zero != null) {
			return "/test";

		} else {
			return "/signin2";
		}
	}

	@RequestMapping(value = "/findchangemessage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse getPageCount (HttpServletRequest request, HttpServletResponse response, HttpSession session
		) {
		Object data = null;
		String statusMsg = "";
		int statusCode = 200;

		String user_email = "1111@qq.com";
		String	passwords = "11";
		List<User> list = this.iUserMapper.findChangeMessage(user_email, passwords);
		data = list;
		return webResponse.getWebResponse(statusCode, statusMsg,data);
	}
	
	
	@RequestMapping(value = "/changemessage", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public void  changeMessage (HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(required = true) String username,@RequestParam(required = true) String email,
			@RequestParam(required = true) String phonenumber,@RequestParam(required = true) String passwords
		) throws IOException {
		Object data = null;
		String statusMsg = "";
		int statusCode = 200;
		Cookie[] cookie = request.getCookies();
		String email1 = null;
		String passwords1=null;
		for (int i = 0; i < cookie.length; i++) {
		Cookie cook = cookie[i];
//		System.out.println("cook.getName"+cook.getName());
		if(cook.getName().equals("email")){ //获取键
			email1=cook.getValue().toString(); //获取值 
		}
		if(cook.getName().equals("passwords")){ //获取键
			passwords1=cook.getValue().toString(); //获取值 
		}
		}
		//测试输出
		System.out.println("oldtest");
		System.out.println("email"+email1);
		System.out.println("passwords"+passwords1);
		System.out.println("修改信息测试");
		this.iUserMapper.updateMessage(username, email, phonenumber, passwords, email1, passwords1);
		
		



		Cookie[] cookies = request.getCookies();
		for (Cookie cookie1 : cookies) {
			System.out.println("cookie1.getName()="+cookie1.getName());			
	            cookie1.setMaxAge(0);
	            response.addCookie(cookie1);

	
		}
		response.sendRedirect("/index.html");
		

	}

}
