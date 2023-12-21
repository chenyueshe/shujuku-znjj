package com.book.manager.service;


import java.util.Map;
import java.util.List;
import com.book.manager.model.sensor;
import com.book.manager.model.ResponseMessage;


public interface sensorService{
	
	public ResponseMessage addsensor(Map<String,Object>param);
	public ResponseMessage deletesensor(Map<String,Object> param);
	public ResponseMessage updatesensor(Map<String,Object>param);
	public ResponseMessage querysensorList(Map<String,Object> param);
	public ResponseMessage querysensorDetail(Map<String,Object> param);
	public ResponseMessage uploadsensorFile(Map<String,Object> param);
	public ResponseMessage productTxtsensor(Map<String,Object> param);
}

