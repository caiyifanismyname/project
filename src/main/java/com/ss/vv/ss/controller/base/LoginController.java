package com.ss.vv.ss.controller.base;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.ss.vv.common.WebResponse;
import com.ss.vv.ss.domain.User;
import com.ss.vv.ss.mapper.IUserMapper;

/**
 *
 * @author MrPeng
 * @data 2018年12月23日 00:03:48
 */



@Controller
public class LoginController {
	
	@Resource
	protected IUserMapper iUserMapper ;
	

    @Resource
    protected IUserMapper userMapper;


    @Resource
    private WebResponse webResponse;

    @RequestMapping("/hello")
    public String helloHtml() {
        return "/index";
    }

    @RequestMapping("/hell")
    public String loginHtml() {
        return "/signin";
    }


    @RequestMapping("/loginVerify")
    public String addOrEditTab(HttpServletRequest request, HttpServletResponse response, HttpSession session, String user_id,
                                     @RequestParam(required = false) String user_email, @RequestParam(required = false) String password){



        Map<String,Object>map=new HashMap<String,Object>();
        map.put("user_email",user_email);
        map.put("password",password);
        User user=userMapper.selectLogin(map);
        if(user!=null){
        	
        	//1.10下午添加代码
    		List<User> list = this.iUserMapper.findChangeMessage(user_email, password);
			Cookie email = new Cookie("email",list.get(0).getUser_email());
			Cookie username = new Cookie("username",list.get(0).getUsername());
			Cookie phonenumber = new Cookie("phonenumber",list.get(0).getUser_phone());
			Cookie passwords = new Cookie("passwords",list.get(0).getPassword());

			
			User cusername= this.userMapper.findChangeMessage(user_email, password).get(0);
			String ccc = cusername.getUsername();
			System.out.println("cusername"+ccc);
			session.setAttribute("username",ccc);
			
			
			response.addCookie(email);
			response.addCookie(username);
			response.addCookie(phonenumber);
			response.addCookie(passwords);

            return "/index";

        }else
        {
            return "/signin" ;
        }

    }
}


       