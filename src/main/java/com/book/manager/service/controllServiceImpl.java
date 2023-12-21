package com.book.manager.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.InputStream;
import java.io.FileInputStream;
import com.alibaba.fastjson.JSONObject;
import java.io.PrintWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.regex.Pattern;
import java.math.BigDecimal;
import org.springframework.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import com.book.manager.model.ResponseMessage;
import com.book.manager.service.controllService;
import com.book.manager.dao.controllDao;
import com.book.manager.model.controll;
import com.book.manager.util.CommonUtil;
import com.book.manager.util.DateUtil;

/**
 * 控制器管理 service
 * @author 刘强
 *
 */
@Service
@Slf4j
public class controllServiceImpl implements controllService{
	
	@Autowired
	private controllDao controllDao;

	
	@Value("${template.path}")
	private String localpath;
	
	@Override
	public ResponseMessage addcontroll(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			
			String productId=new Date().getTime()+"";
			controll controll=new controll();
			controll.setId(Integer.parseInt((String)param.get("id")));
			controll.setName((String)param.get("name"));
			controll.setRoomId((String)param.get("roomId"));
			controll.setRoomName((String)param.get("roomName"));
			controll.setStatus(productId);
			controll.setOprator((String)param.get("oprator"));
			controll.setOpratorTime(new Date());
			controllDao.addcontroll(controll);

			List<Map<String, Object>> list=(List<Map<String, Object>>)param.get("controls");
			for(Map<String, Object>temp:list) {
				temp.put("id", productId);
				controllDao.saveWork(temp);
			}
			//强制类型转换
			//碰到日期格式，转换成日期
			//以create开头的字段，使用当前的日期
			responseMessage.setStatus("S");
			responseMessage.setMessage("保存成功");
			return responseMessage;
		}catch (Exception e) {
			log.info("控制器管理保存异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("控制器管理保存异常");
			return responseMessage;
		}
	}

	@Override
	public ResponseMessage deletecontroll(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			//强制类型转换
			controllDao.deletecontroll(param);
			responseMessage.setStatus("S");
			responseMessage.setMessage("删除成功");
			return responseMessage;
		}catch (Exception e) {
			log.info("控制器管理删除异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("控制器管理删除异常");
			return responseMessage;
		}
	}

	@Override
	public ResponseMessage updatecontroll(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			
			
			//强制类型转换
			//碰到日期格式，转换成日期
			//以update开头的字段，使用当前的日期
			controll controll=new controll();
			controll.setId((Integer)param.get("id"));
			controll.setName((String)param.get("name"));
			controll.setRoomId((String)param.get("roomId"));
			controll.setRoomName((String)param.get("roomName"));
			controll.setStatus((String)param.get("status"));
			controll.setOprator((String)param.get("oprator"));
			SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
			Date date2=sdf2.parse((String)param.get("opratorTime"));
			controll.setOpratorTime(date2);
			controllDao.updatecontroll(controll);
			
			String status=(String)param.get("status");
			Map<String, Object>deleteMap=new HashMap<>();
			deleteMap.put("id", status);
			controllDao.deleteWork(deleteMap);
			
			List<Map<String, Object>> list=(List<Map<String, Object>>)param.get("controls");
			for(Map<String, Object>temp:list) {
				temp.put("id", status);
				controllDao.saveWork(temp);
			}
			responseMessage.setStatus("S");
			responseMessage.setMessage("更新成功");
			return responseMessage;
		}catch (Exception e) {
			log.info("控制器管理更新异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("控制器管理更新异常");
			return responseMessage;
		}
	}

	@Override
	public ResponseMessage querycontrollList(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			
			int startPage=Integer.parseInt((String)param.get("startPage"));
			int pageSize=Integer.parseInt((String)param.get("pageSize"));
			int offset = (startPage - 1) * pageSize;
			param.put("offset", offset);
			param.put("pageSize", pageSize);
			if(!StringUtils.isEmpty((String)param.get("name"))){
			param.put("name","%"+param.get("name")+"%");
		}
if(!StringUtils.isEmpty((String)param.get("roomName"))){
			param.put("roomName","%"+param.get("roomName")+"%");
		}

			//查询总数int total
			int total=controllDao.querycontrollCount(param);

			//查询明细
			List<controll> list=controllDao.querycontrollList(param);
			
			Map<String, Object>resultMap=new HashMap<>();
			resultMap.put("list", list);
			resultMap.put("total", total);
			responseMessage.setData(resultMap);
			responseMessage.setStatus("S");
		}catch (Exception e) {
			log.info("控制器管理查询异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("控制器管理查询异常");
			return responseMessage;
		}
		return responseMessage;
	}
	
	@Override
	public ResponseMessage querycontrollDetail(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			controll controll =controllDao.querycontrollDetail(param);
			
			responseMessage.setData(controll);
			responseMessage.setStatus("S");
		}catch (Exception e) {
			log.info("控制器管理查询异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("控制器管理查询异常");
			return responseMessage;
		}
		return responseMessage;
	}
	@Override
	public ResponseMessage uploadcontrollFile(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			String localfilename=(String)param.get("localfilename");
		   	InputStream inputStream=null;
	   	    SimpleDateFormat sdfs=new SimpleDateFormat("yyyy-MM-dd");
			String format = sdfs.format(new Date());
			inputStream=new FileInputStream(localpath+format+"/"+localfilename);
			List<Object>list =CommonUtil.readChannelExcel2(inputStream);
			list.remove(0);
			List<JSONObject> gridManagerGroupRelationList = list.stream()
	                    .map(relations -> {
	                        ArrayList<String> rowTerm = (ArrayList<String>) relations;
	                        JSONObject gridMgrChannelRelation = new JSONObject();
	                        //循环获取数据
	                       				gridMgrChannelRelation.put("id",rowTerm.get(0).trim());
				gridMgrChannelRelation.put("name",rowTerm.get(1).trim());
				gridMgrChannelRelation.put("roomId",rowTerm.get(2).trim());
				gridMgrChannelRelation.put("roomName",rowTerm.get(3).trim());
				gridMgrChannelRelation.put("status",rowTerm.get(4).trim());
				gridMgrChannelRelation.put("oprator",rowTerm.get(5).trim());
				gridMgrChannelRelation.put("opratorTime",rowTerm.get(6).trim());

	                        return gridMgrChannelRelation;
	                    }).collect(Collectors.toList());
	        log.info("读取内容::::::"+gridManagerGroupRelationList.toString());
	        for(int i=0;i<gridManagerGroupRelationList.size();i++) {//开始循环保存数据
	            JSONObject jsonObject=gridManagerGroupRelationList.get(i);
	            //CommonUtil.removeAllBlank(jsonObject.getString("phone"))
	            controll controll=new controll();
			controll.setId(Integer.parseInt(CommonUtil.removeAllBlank(jsonObject.getString("id"))));
			controll.setName(CommonUtil.removeAllBlank(jsonObject.getString("name")));
			controll.setRoomId(CommonUtil.removeAllBlank(jsonObject.getString("roomId")));
			controll.setRoomName(CommonUtil.removeAllBlank(jsonObject.getString("roomName")));
			controll.setStatus(CommonUtil.removeAllBlank(jsonObject.getString("status")));
			controll.setOprator(CommonUtil.removeAllBlank(jsonObject.getString("oprator")));
			SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
			Date date2=sdf2.parse((String)param.get("opratorTime"));
			controll.setOpratorTime(date2);
				controllDao.addcontroll(controll);

	        }
			responseMessage.setMessage("上传成功");
			responseMessage.setStatus("S");
		}catch (Exception e) {
			log.info("控制器管理上传异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("控制器管理上传异常");
			return responseMessage;
		}
		return responseMessage;
	}
	@Override
	public ResponseMessage productTxtcontroll(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
        try {
        	PrintWriter print=new PrintWriter(new File(localpath+"controll"+"_"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt"));
        	int pageSize=5000;
        	int startPage = 1;
			// 是否含有下一页标识
			boolean flag = true;
            // 在里面创建一个sheet 名字为工作薄1
            //int total = chargesDao.queryChargesDetailTotal(param);
            int total =controllDao.querycontrollCount(param);
			log.info("总条数" + total);
			int totalPage = (int) Math.ceil(Double.valueOf(total) / pageSize);
			log.info("总页数" + totalPage);
			int offset = (startPage - 1) * pageSize;
			param.put("pageSize", pageSize);
			param.put("offset", offset);
			while (flag) {
				//List<Map<String, Object>> list = chargesDao.queryChargesDetail(param);
				List<controll> list=controllDao.querycontrollList(param);
				// 写入文件
				for (int i=0;i<list.size();i++) {
					
					// 看你实体类在进行填充
					//printWriter.println(userInfo.getPhone()+"	"+userInfo.getSendMessage());
					print.println(list.get(i).getId()+","+
list.get(i).getName()+","+
list.get(i).getRoomId()+","+
list.get(i).getRoomName()+","+
list.get(i).getStatus()+","+
list.get(i).getOprator()+","+
DateUtil.formatDate(list.get(i).getOpratorTime(), "yyyy-MM-dd HH:MM:ss"));
				}
				print.flush();
				startPage++;
				// 判断是否含有下一页
				flag = startPage <= totalPage;
				if (startPage == totalPage) {
					param.put("offset", (startPage - 1) * pageSize);
					pageSize = total - (startPage - 1) * pageSize;
					param.put("pageSize", pageSize);
				} else {
					param.put("pageSize", pageSize);
					param.put("offset", (startPage - 1) * pageSize);
				}
			}
            
           
           
        }catch(Exception e) {
        	log.info("控制器管理生成txt异常",e);
        }finally {
        	
        }
		return responseMessage;
	}
}

