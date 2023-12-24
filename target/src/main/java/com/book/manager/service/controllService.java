package com.book.manager.service;


import java.util.Map;
import java.util.List;
import com.book.manager.model.controll;

import vo.ControllVo;

import com.book.manager.model.ResponseMessage;


public interface controllService{
	
	public ResponseMessage addcontroll(ControllVo param);
	public ResponseMessage deletecontroll(Map<String,Object> param);
	public ResponseMessage updatecontroll(ControllVo param);
	public ResponseMessage querycontrollList(Map<String,Object> param);
	public ResponseMessage querycontrollDetail(Map<String,Object> param);
	public ResponseMessage uploadcontrollFile(Map<String,Object> param);
	public ResponseMessage productTxtcontroll(Map<String,Object> param);
}

