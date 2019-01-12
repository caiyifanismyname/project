package com.ss.vv.ss.controller.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.vv.common.WebResponse;
import com.ss.vv.ss.domain.Tab;
import com.ss.vv.ss.service.ITabService;
/**
 *
 * @author MrPeng
 * @data 2018年12月23日 00:03:48
 */
@Controller
@RequestMapping("/tab")

public class TabController {

    @Autowired
    protected WebResponse webResponse;

    @Resource
    protected ITabService tabService;





    @RequestMapping("/hel")
    public String hel() {
        return "/hha";
    }

    @RequestMapping(value = "/addOrEditTab", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void  addOrEditTab(HttpServletRequest request, HttpServletResponse response, HttpSession session, String tab_id,
             @RequestParam(required = false) String tab_name, @RequestParam(required = false) String tab_phone,
             @RequestParam(required = false) String tab_email, @RequestParam(required = false,defaultValue="0") Integer num1,
              @RequestParam(required = false,defaultValue="0") Integer num2, @RequestParam(required = false,defaultValue="0") Integer num3,
              @RequestParam(required = false,defaultValue="0") Integer num4, @RequestParam(required = false,defaultValue="0") Integer num5,
@RequestParam(required = false,defaultValue="0") Integer num6, @RequestParam(required = false,defaultValue="0") Integer num7,
@RequestParam(required = false,defaultValue="0") Integer num8   ) throws IOException {
        Tab tab=new Tab(); 

        
        if(num1>0){
            tab.setTab_name(tab_name);
            tab.setTab_phone(tab_phone);
            tab.setTab_email(tab_email);
            tab.setPig_name("大白猪");
            tab.setTab_num(num1);
            tab.setTab_mon(num1*9000);
            this.tabService.insert(tab);
        }
        if(num2>0){
            tab.setTab_name(tab_name);
            tab.setTab_phone(tab_phone);
            tab.setTab_email(tab_email);
            tab.setPig_name("长白猪");
            tab.setTab_num(num2);
            tab.setTab_mon(num2*8500);
            this.tabService.insert(tab);
        }
        if(num3>0){
            tab.setTab_name(tab_name);
            tab.setTab_phone(tab_phone);
            tab.setTab_email(tab_email);
            tab.setPig_name("杜洛克猪白猪");
            tab.setTab_num(num3);
            tab.setTab_mon(num3*7000);
            this.tabService.insert(tab);
        }
        if(num4>0){
            tab.setTab_name(tab_name);
            tab.setTab_phone(tab_phone);
            tab.setTab_email(tab_email);
            tab.setPig_name("八眉猪");
            tab.setTab_num(num4);
            tab.setTab_mon(num4*9700);
            this.tabService.insert(tab);
        }
        if(num5>0){
            tab.setTab_name(tab_name);
            tab.setTab_phone(tab_phone);
            tab.setTab_email(tab_email);
            tab.setPig_name("汉普夏猪");
            tab.setTab_num(num4);
            tab.setTab_mon(num4*9700);
            this.tabService.insert(tab);
        }
        if(num6>0){
            tab.setTab_name(tab_name);
            tab.setTab_phone(tab_phone);
            tab.setTab_email(tab_email);
            tab.setPig_name("皮特兰猪");
            tab.setTab_num(num6);
            tab.setTab_mon(num6*8000);
            this.tabService.insert(tab);
        }
        if(num7>0){
            tab.setTab_name(tab_name);
            tab.setTab_phone(tab_phone);
            tab.setTab_email(tab_email);
            tab.setPig_name("苏白猪");
            tab.setTab_num(num7);
            tab.setTab_mon(num7*6000);
            this.tabService.insert(tab);
        }
        if(num8>0){
            tab.setTab_name(tab_name);
            tab.setTab_phone(tab_phone);
            tab.setTab_email(tab_email);
            tab.setPig_name("太湖猪");
            tab.setTab_num(num8);
            tab.setTab_mon(num8*10000);
            this.tabService.insert(tab);
     
        }
        
        response.sendRedirect("/products.html");

    }

    @RequestMapping(value = "/getTabList", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public WebResponse getTabList(HttpServletRequest request, HttpServletResponse response, HttpSession session,
                                   @RequestParam(defaultValue = "1", required = false) Integer pageNo,
                                   @RequestParam(defaultValue = "10", required = false) Integer pageSize,
                                   @RequestParam(defaultValue = "正常", required = false) String tbStatus,
                                   @RequestParam(required = false) String keyword,
                                   @RequestParam(defaultValue = "tab_id", required = false) String order,
                                   @RequestParam(defaultValue = "desc", required = false) String desc ) {
        Object data = null;
        String statusMsg = "";
        int statusCode = 200;
        LinkedHashMap<String, String> condition = new LinkedHashMap<String, String>();
        if (keyword != null && keyword.length() > 0) {
            StringBuffer buf = new StringBuffer();
            buf.append("(");
            buf.append("tab_name like '%").append(keyword).append("%'");
            buf.append(" or ");
            buf.append("tab_phone like '%").append(keyword).append("%'");
            buf.append(" or ");
            buf.append("tab_email like '%").append(keyword).append("%'");
            buf.append(" or ");
            buf.append("pig_name like '%").append(keyword).append("%'");
            buf.append(" or ");
            buf.append("tab_num like '%").append(keyword).append("%'");
            buf.append(" or ");
            buf.append("tab_mon like '%").append(keyword).append("%'");
            buf.append(")");
            condition.put(buf.toString(), "and");
        }
        String field = null;
        if (condition.size() > 0) {
            condition.put(condition.entrySet().iterator().next().getKey(), "");
        }
        int count = this.tabService.getCount(condition, field);
        if (order != null && order.length() > 0 & "desc".equals(desc)) {
            order = order + " desc";
        }
        List<Tab> list = this.tabService.getList(condition, pageNo, pageSize, order, field);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map.put("total", count);
        int size = list.size();
        if (size > 0) {
            List<Tab> listFont = new ArrayList<Tab>();
            Tab vo;
            Tab voFont = new Tab();
            for (int i = 0; i < size; i++) {
                vo = list.get(i);
                BeanUtils.copyProperties(vo, voFont);
                listFont.add(voFont);
                voFont = new Tab();
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


