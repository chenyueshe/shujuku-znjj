package com.book.manager.service;


import java.util.Map;
import java.util.List;
import com.book.manager.model.userinfo;
import com.book.manager.model.ResponseMessage;


public interface userinfoService{
	
	public ResponseMessage adduserinfo(Map<String,Object>param);
	public ResponseMessage deleteuserinfo(Map<String,Object> param);
	public ResponseMessage updateuserinfo(Map<String,Object>param);
	public ResponseMessage queryuserinfoList(Map<String,Object> param);
	public ResponseMessage queryuserinfoDetail(Map<String,Object> param);
	public ResponseMessage uploaduserinfoFile(Map<String,Object> param);
	public ResponseMessage productTxtuserinfo(Map<String,Object> param);
}

