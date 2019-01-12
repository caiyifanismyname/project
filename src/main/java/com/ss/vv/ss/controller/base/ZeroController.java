package com.ss.vv.ss.controller.base;

import com.ss.vv.common.WebResponse;
import com.ss.vv.ss.domain.Zero;
import com.ss.vv.ss.service.IZeroService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
/** 
* 
* @author MrPeng
* @data 2018年12月23日 00:03:48  
*/
@Controller
@RequestMapping("/zero")

public class ZeroController {

	@Autowired
	protected WebResponse webResponse;

	@Resource
	protected IZeroService zeroService;

	@RequestMapping(value = "/addOrEditZero", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse addOrEditZero(HttpServletRequest request, HttpServletResponse response, HttpSession session, String a_id, @RequestParam(required = false) String a_name,  @RequestParam(required = false) String a_pass) {
		if (a_id == null || a_id.length() == 0) {
			return this.addZero(request, response, session, a_name, a_pass);

		} else {
			return this.editZero(request, response, session, a_id, a_name,a_pass);
		}
	}

	@RequestMapping(value = "/addZero", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse addZero(HttpServletRequest request, HttpServletResponse response, HttpSession session, String a_name,String a_pass) {
		Object data = null;
		String statusMsg = "";
		Integer statusCode = 200;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("a_name", a_name);
		paramMap.put("a_pass", a_pass);
		data = paramMap;
		if (a_name == null || "".equals(a_name.trim()) || a_pass == null || "".equals(a_pass.trim())) {
			statusMsg = " 参数为空错误！！！！";
			statusCode = 201;
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		if (a_name.length() > 255 ||a_pass.length()>255) {
			statusMsg = " 参数长度过长错误！！！";
			statusCode = 201;
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		Zero zero = new Zero();
		boolean isAdd = true;
		return this.addOrEditZero(request, response, session, data, zero,a_name,a_pass, isAdd);
	}

	@RequestMapping(value = "/editZero", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse editZero(HttpServletRequest request, HttpServletResponse response, HttpSession session, String a_id, @RequestParam(required = false) String a_name,  @RequestParam(required = false) String a_pass) {
		Object data = null;
		String statusMsg = "";
		Integer statusCode = 200;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("a_id", a_id);
		paramMap.put("a_name", a_name);
		paramMap.put("a_pass", a_pass);
		data = paramMap;
		Integer a_idNumeri = a_id.matches("^[0-9]*$") ? Integer.parseInt(a_id) : 0;
		Zero zeroVo = this.zeroService.getById(a_idNumeri);
		Zero zero = new Zero();
		BeanUtils.copyProperties(zeroVo, zero);
		boolean isAdd = false;
		return this.addOrEditZero(request, response, session, data, zero,a_name,a_pass,isAdd);
	}
/*
 * 
 */
private WebResponse addOrEditZero(HttpServletRequest request, HttpServletResponse response, HttpSession session, Object data, Zero zero, String a_name, String a_pass, boolean isAdd) {
		String statusMsg = "";
		Integer statusCode = 200;
		if (a_name != null && !("".equals(a_name.trim()))) {
			zero.setA_name(a_name);
		}
		if (a_pass != null && !("".equals(a_pass.trim()))) {
			zero.setA_pass(a_pass);
		}
		if (isAdd) {
			this.zeroService.insert(zero);
			if (zero.getA_id() > 0) {
				statusMsg = "成功插入！！！";
			} else {
				statusCode = 202;
				statusMsg = "insert false";
			}
			return webResponse.getWebResponse(statusCode, statusMsg, data);
		}
		int num = this.zeroService.update(zero);
		if (num > 0) {
			statusMsg = "成功修改！！！";
		}
		return webResponse.getWebResponse(statusCode, statusMsg, data);
	}

	@RequestMapping(value = "/getZeroList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public WebResponse getZeroList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
								   @RequestParam(defaultValue = "1", required = false) Integer pageNo,
								   @RequestParam(defaultValue = "10", required = false) Integer pageSize,
								   @RequestParam(defaultValue = "正常", required = false) String tbStatus,
								   @RequestParam(required = false) String keyword,
								   @RequestParam(defaultValue = "a_id", required = false) String order,
								   @RequestParam(defaultValue = "desc", required = false) String desc ) {
		Object data = null;
		String statusMsg = "";
		int statusCode = 200;
		LinkedHashMap<String, String> condition = new LinkedHashMap<String, String>();
		if (keyword != null && keyword.length() > 0) {
			StringBuffer buf = new StringBuffer();
			buf.append("(");
			buf.append("a_name like '%").append(keyword).append("%'");
			buf.append(" or ");
			buf.append("a_pass like '%").append(keyword).append("%'");
			buf.append(")");
			condition.put(buf.toString(), "and");
		}
		String field = null;
		if (condition.size() > 0) {
			condition.put(condition.entrySet().iterator().next().getKey(), "");
		}
		int count = this.zeroService.getCount(condition, field);
		if (order != null && order.length() > 0 & "desc".equals(desc)) {
			order = order + " desc";
		}
		List<Zero> list = this.zeroService.getList(condition, pageNo, pageSize, order, field);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("total", count);
		int size = list.size();
		if (size > 0) {
			List<Zero> listFont = new ArrayList<Zero>();
			Zero vo;
			Zero voFont = new Zero();
			for (int i = 0; i < size; i++) {
				vo = list.get(i);
				BeanUtils.copyProperties(vo, voFont);
				listFont.add(voFont);
				voFont = new Zero();
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
