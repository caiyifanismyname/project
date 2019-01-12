package com.ss.vv.ss.controller.base;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.ss.vv.common.WebResponse;
import com.ss.vv.ss.service.ITestService;
import com.ss.vv.ss.domain.Test;
/**
* 
* @author MrPeng
* @data 2018年12月23日 00:03:48  
*/
@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	protected WebResponse webResponse;

	@Resource
	protected ITestService testService;

	@RequestMapping(value = "/addOrEditTest", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse addOrEditTest(HttpServletRequest request, HttpServletResponse response, HttpSession session, String pig_id, @RequestParam(required = false) String pig_name, @RequestParam(required = false) Integer pig_sum, @RequestParam(required = false) Integer pig_price) {
		if (pig_id == null || pig_id.length() == 0) {
			return this.addTest(request, response, session, pig_name, pig_sum, pig_price);
		} else {
			return this.editTest(request, response, session, pig_id, pig_name, pig_sum, pig_price);
		}
	}
	
	@RequestMapping(value = "/addTest", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse addTest(HttpServletRequest request, HttpServletResponse response, HttpSession session, String pig_name, Integer pig_sum, Integer pig_price) {
		Object data = null;
		String statusMsg = "";
		Integer statusCode = 200;
		Map<String, String> paramMap = new HashMap<String, String>();
		String s1=pig_sum+"";
		String s2=pig_price+"";
		paramMap.put("pig_name", pig_name);
		paramMap.put("pig_sum", s1);
		paramMap.put("pig_price", s2);
		data = paramMap;
		Test test = new Test();
		boolean isAdd = true;
		return this.addOrEditTest(request, response, session, data, test,pig_name, pig_sum, pig_price, isAdd);
	}

	@RequestMapping(value = "/editTest", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse editTest(HttpServletRequest request, HttpServletResponse response, HttpSession session, String pig_id, @RequestParam(required = false) String pig_name, @RequestParam(required = false) Integer pig_sum, @RequestParam(required = false) Integer pig_price) {
		Object data = null;
		Map<String, String> paramMap = new HashMap<String, String>();
		String s1=pig_sum+"";
		String s2=pig_price+"";
		paramMap.put("pig_id", pig_id);
		paramMap.put("pig_name", pig_name);
		paramMap.put("pig_sum", s1);
		paramMap.put("pig_price", s2);
		data = paramMap;
		Integer testIdNumeri = pig_id.matches("^[0-9]*$") ? Integer.parseInt(pig_id) : 0;
		Test testVo = this.testService.getById(testIdNumeri);
		Test test = new Test();
		BeanUtils.copyProperties(testVo, test);
		boolean isAdd = false;
		return this.addOrEditTest(request, response, session, data, test, pig_name, pig_sum, pig_price, isAdd);
	}
/*
 * 
 */																							
private WebResponse addOrEditTest(HttpServletRequest request, HttpServletResponse response, HttpSession session, Object data, Test test, String pig_name, Integer pig_sum, Integer pig_price, boolean isAdd) {
		String statusMsg = "";
		Integer statusCode = 200;
		if (pig_name != null && !("".equals(pig_name.trim()))) {
			test.setPig_name(pig_name);
		}
		if (pig_sum != null) {
			test.setPig_sum(pig_sum);
		}
		if (pig_price != null) {
			test.setPig_price(pig_price);;
		}
		if (isAdd) {
			this.testService.insert(test);
			if (test.getPig_id() > 0) {
				statusMsg = "成功插入！！！";
			} else {
				statusCode = 202;
				statusMsg = "insert false";
			} 
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		int num = this.testService.update(test);
		if (num > 0) {
			statusMsg = "成功修改！！！";
		} else {
			statusCode = 202;
			statusMsg = "update false";
		}
		return webResponse.getWebResponse(statusCode, statusMsg, data);
	}

	@RequestMapping(value = "/getTestList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse getTestList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
		@RequestParam(defaultValue = "1", required = false) Integer pageNo,
		@RequestParam(defaultValue = "10", required = false) Integer pageSize,
		@RequestParam(defaultValue = "正常", required = false) String tbStatus,
		@RequestParam(required = false) String keyword,
		@RequestParam(defaultValue = "pig_id", required = false) String order,
		@RequestParam(defaultValue = "desc", required = false) String desc ) {
		Object data = null;
		String statusMsg = "";
		int statusCode = 200;
		LinkedHashMap<String, String> condition = new LinkedHashMap<String, String>();
		if (keyword != null && keyword.length() > 0) {
			StringBuffer buf = new StringBuffer();
			buf.append("(");
			buf.append("pig_name like '%").append(keyword).append("%'");
			buf.append(" or ");
			buf.append("pig_sum like '%").append(keyword).append("%'");
			buf.append(" or ");
			buf.append("pig_price like '%").append(keyword).append("%'");
			buf.append(")");
			condition.put(buf.toString(), "and");
		}
		String field = null;
		if (condition.size() > 0) {
			condition.put(condition.entrySet().iterator().next().getKey(), "");
		}
		int count = this.testService.getCount(condition, field);
		if (order != null && order.length() > 0 & "desc".equals(desc)) {
			order = order + " desc";
		}
		List<Test> list = this.testService.getList(condition, pageNo, pageSize, order, field);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("total", count);
		int size = list.size();
		if (size > 0) {
			List<Test> listFont = new ArrayList<Test>();
			Test vo;
			Test voFont = new Test();
			for (int i = 0; i < size; i++) {
				vo = list.get(i);
				BeanUtils.copyProperties(vo, voFont);
				listFont.add(voFont);
				voFont = new Test();
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

