package com.book.manager.model;


import java.util.List;

import lombok.Data;

@Data
public class ResponseMessage {
	private String status;
	
	private String message;
	
	private List  list;
	
	private Object data;
}

