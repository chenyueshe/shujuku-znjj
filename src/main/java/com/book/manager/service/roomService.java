package com.book.manager.service;


import java.util.Map;
import java.util.List;
import com.book.manager.model.room;
import com.book.manager.model.ResponseMessage;


public interface roomService{
	
	public ResponseMessage addroom(Map<String,Object>param);
	public ResponseMessage deleteroom(Map<String,Object> param);
	public ResponseMessage updateroom(Map<String,Object>param);
	public ResponseMessage queryroomList(Map<String,Object> param);
	public ResponseMessage queryroomDetail(Map<String,Object> param);
	public ResponseMessage uploadroomFile(Map<String,Object> param);
	public ResponseMessage productTxtroom(Map<String,Object> param);
}

