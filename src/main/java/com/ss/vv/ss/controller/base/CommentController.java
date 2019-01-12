package com.ss.vv.ss.controller.base;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ss.vv.common.WebResponse;
import com.ss.vv.ss.domain.Comment;
import com.ss.vv.ss.mapper.ICommentMapper;
import com.ss.vv.ss.service.ICommentService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/comment")
public class CommentController {
	@Resource
	protected ICommentService iCommentService;
	@Autowired
	protected WebResponse webResponse;

	@Autowired
	protected ICommentMapper iCommentMapper;


	
	//根据pageSize和pageNo查询数据
	
	@RequestMapping(value = "/findComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse findComment(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@RequestParam(defaultValue = "1", required = false) Integer pageNo,  
			@RequestParam(defaultValue = "10", required = false) Integer pageSize) {
		
		Object data = null;
		String statusMsg = "插入成功";
		Integer statusCode = 200;
		
		List<Comment> list = this.iCommentMapper.findCommentList(pageNo);
		int size = list.size();
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (size > 0) {
			List<Comment> listFont = new ArrayList<Comment>();
			Comment vo;
			Comment voFont = new Comment(); 
			for (int i = 0; i < size; i++) {
				vo = list.get(i);
				
				BeanUtils.copyProperties(vo, voFont);
				
				listFont.add(voFont);
				voFont = new Comment();
			}
			map.put("list", listFont);
			data = map;
			statusMsg = "根据条件获取分页数据成功！！！";
			System.out.println("test2======="+map.get(voFont));
		} else {
			map.put("list", list);
			data = map;
			statusCode = 202;
			statusMsg = "no record!!!";
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		
		
		
		
		
		return webResponse.getWebResponse(statusCode, statusMsg, data);
		
		
		
		
	}
	

	//新代码。
	
	@RequestMapping(value = "/addComment", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse addComment(HttpServletRequest request, HttpServletResponse response, HttpSession session, 

			@RequestParam(required = false) String category
			) {
		String statusMsg = "";
		Integer statusCode = 200;	
		Comment comment = new Comment();
		Object data = null;
		List<Comment> list = this.iCommentMapper.findAllComment();
		int size = list.size();
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (size > 0) {
			List<Comment> listFont = new ArrayList<Comment>();
			Comment vo;
			Comment voFont = new Comment(); 
			for (int i = 0; i < size; i++) {
				vo = list.get(i);
				
				BeanUtils.copyProperties(vo, voFont);
				
				listFont.add(voFont);
				voFont = new Comment();
			}
			map.put("list", listFont);
			data = map;
			statusMsg = "根据条件获取分页数据成功！！！";

			System.out.println("test2======="+map.get(voFont));
		} else {
			map.put("list", list);
			data = map;
			statusCode = 202;
			statusMsg = "no record!!!";
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		
		

	    if (session.getAttribute("username") == null) {
	    	statusMsg="登录失效";
	      System.out.println("不存在session");
	    } else {
	     System.out.println("存在session");
	   }

	    comment.setUserName(session.getAttribute("username").toString());
		java.util.Date javaDate = new java.util.Date();
		long javaTime = javaDate.getTime();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
		sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		

		System.out.println(new java.sql.Timestamp(javaTime));
		comment.setTime(sdf.format(new Date(javaTime)));
		comment.setComment(category);  
		this.iCommentService.insert(comment);
		if (comment.getComment()!= null && comment.getUserName()!=null) {
				statusMsg = "成功插入！！！";
				
		} else {
			statusCode = 202;
			statusMsg = "insert false";
		}
		System.out.println(statusCode);
		System.out.println(session.getAttribute("username")+"username");
		

		return webResponse.getWebResponse(statusCode, statusMsg, data);
	}



}
