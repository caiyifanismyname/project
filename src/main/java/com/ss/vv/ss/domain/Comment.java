package com.ss.vv.ss.domain;

import lombok.Data;

@Data
public class Comment implements java.io.Serializable{
	private String userName;
	private String comment;
	private String time;

}
