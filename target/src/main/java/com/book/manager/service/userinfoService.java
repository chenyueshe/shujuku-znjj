package com.book.manager.service;


import java.util.Map;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import com.book.manager.model.userinfo;

import vo.UserinfoVo;

import com.book.manager.model.ResponseMessage;


public interface userinfoService{
	
	public ResponseMessage adduserinfo(UserinfoVo param, BindingResult bindingResult);
	public ResponseMessage deleteuserinfo(Map<String,Object> param);
	public ResponseMessage updateuserinfo(UserinfoVo param);
	public ResponseMessage queryuserinfoList(Map<String,Object> param);
	public ResponseMessage queryuserinfoDetail(Map<String,Object> param);
	public ResponseMessage uploaduserinfoFile(Map<String,Object> param);
	public ResponseMessage productTxtuserinfo(Map<String,Object> param);
}

