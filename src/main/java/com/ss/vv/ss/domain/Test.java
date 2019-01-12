/** 
* 
* @author bingoWu 
* @data 2018年12月23日 00:03:48  
*/  

package com.ss.vv.ss.domain;
import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
/** 
* 
* @author young 
* @data
*/  

@Data
public class Test implements java.io.Serializable {
	private Integer pig_id; // 猪的ID
	private String pig_name; // 猪的品种
	private Integer pig_sum; // 库存
	private Integer pig_price; // 单价
}

