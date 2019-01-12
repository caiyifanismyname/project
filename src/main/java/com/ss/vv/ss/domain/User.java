package com.ss.vv.ss.domain;

import java.util.Date;

import lombok.Data;

@Data
public class User implements java.io.Serializable{

	private Integer user_id;
	private String username;
	private String user_email;
	private String user_phone;
	private String password;
	private Date updateDate;
	

	
}
