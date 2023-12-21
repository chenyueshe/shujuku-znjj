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
import com.book.manager.service.roomService;
import com.book.manager.dao.roomDao;
import com.book.manager.model.room;
import com.book.manager.util.CommonUtil;
import com.book.manager.util.DateUtil;

/**
 * 房间管理 service
 * @author 刘强
 *
 */
@Service
@Slf4j
public class roomServiceImpl implements roomService{
	
	@Autowired
	private roomDao roomDao;

	
	@Value("${template.path}")
	private String localpath;
	
	@Override
	public ResponseMessage addroom(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			
			
			room room=new room();
			room.setId(Integer.parseInt((String)param.get("id")));
			room.setFloor((String)param.get("floor"));
			room.setName((String)param.get("name"));
			room.setType((String)param.get("type"));
			room.setMaster((String)param.get("master"));
			roomDao.addroom(room);

			//强制类型转换
			//碰到日期格式，转换成日期
			//以create开头的字段，使用当前的日期
			responseMessage.setStatus("S");
			responseMessage.setMessage("保存成功");
			return responseMessage;
		}catch (Exception e) {
			log.info("房间管理保存异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("房间管理保存异常");
			return responseMessage;
		}
	}

	@Override
	public ResponseMessage deleteroom(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			//强制类型转换
			roomDao.deleteroom(param);
			responseMessage.setStatus("S");
			responseMessage.setMessage("删除成功");
			return responseMessage;
		}catch (Exception e) {
			log.info("房间管理删除异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("房间管理删除异常");
			return responseMessage;
		}
	}

	@Override
	public ResponseMessage updateroom(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			
			
			//强制类型转换
			//碰到日期格式，转换成日期
			//以update开头的字段，使用当前的日期
			room room=new room();
			room.setId(Integer.parseInt((String)param.get("id")));
			room.setFloor((String)param.get("floor"));
			room.setName((String)param.get("name"));
			room.setType((String)param.get("type"));
			room.setMaster((String)param.get("master"));
			roomDao.updateroom(room);
			responseMessage.setStatus("S");
			responseMessage.setMessage("更新成功");
			return responseMessage;
		}catch (Exception e) {
			log.info("房间管理更新异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("房间管理更新异常");
			return responseMessage;
		}
	}

	@Override
	public ResponseMessage queryroomList(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			
			int startPage=Integer.parseInt((String)param.get("startPage"));
			int pageSize=Integer.parseInt((String)param.get("pageSize"));
			int offset = (startPage - 1) * pageSize;
			param.put("offset", offset);
			param.put("pageSize", pageSize);

			//查询总数int total
			int total=roomDao.queryroomCount(param);

			//查询明细
			List<room> list=roomDao.queryroomList(param);
			
			Map<String, Object>resultMap=new HashMap<>();
			resultMap.put("list", list);
			resultMap.put("total", total);
			responseMessage.setData(resultMap);
			responseMessage.setStatus("S");
		}catch (Exception e) {
			log.info("房间管理查询异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("房间管理查询异常");
			return responseMessage;
		}
		return responseMessage;
	}
	
	@Override
	public ResponseMessage queryroomDetail(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			room room =roomDao.queryroomDetail(param);
			
			responseMessage.setData(room);
			responseMessage.setStatus("S");
		}catch (Exception e) {
			log.info("房间管理查询异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("房间管理查询异常");
			return responseMessage;
		}
		return responseMessage;
	}
	@Override
	public ResponseMessage uploadroomFile(Map<String, Object> param) {
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
				gridMgrChannelRelation.put("floor",rowTerm.get(1).trim());
				gridMgrChannelRelation.put("name",rowTerm.get(2).trim());
				gridMgrChannelRelation.put("type",rowTerm.get(3).trim());
				gridMgrChannelRelation.put("master",rowTerm.get(4).trim());

	                        return gridMgrChannelRelation;
	                    }).collect(Collectors.toList());
	        log.info("读取内容::::::"+gridManagerGroupRelationList.toString());
	        for(int i=0;i<gridManagerGroupRelationList.size();i++) {//开始循环保存数据
	            JSONObject jsonObject=gridManagerGroupRelationList.get(i);
	            //CommonUtil.removeAllBlank(jsonObject.getString("phone"))
	            room room=new room();
			room.setId(Integer.parseInt(CommonUtil.removeAllBlank(jsonObject.getString("id"))));
			room.setFloor(CommonUtil.removeAllBlank(jsonObject.getString("floor")));
			room.setName(CommonUtil.removeAllBlank(jsonObject.getString("name")));
			room.setType(CommonUtil.removeAllBlank(jsonObject.getString("type")));
			room.setMaster(CommonUtil.removeAllBlank(jsonObject.getString("master")));
				roomDao.addroom(room);

	        }
			responseMessage.setMessage("上传成功");
			responseMessage.setStatus("S");
		}catch (Exception e) {
			log.info("房间管理上传异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("房间管理上传异常");
			return responseMessage;
		}
		return responseMessage;
	}
	@Override
	public ResponseMessage productTxtroom(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
        try {
        	PrintWriter print=new PrintWriter(new File(localpath+"room"+"_"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt"));
        	int pageSize=5000;
        	int startPage = 1;
			// 是否含有下一页标识
			boolean flag = true;
            // 在里面创建一个sheet 名字为工作薄1
            //int total = chargesDao.queryChargesDetailTotal(param);
            int total =roomDao.queryroomCount(param);
			log.info("总条数" + total);
			int totalPage = (int) Math.ceil(Double.valueOf(total) / pageSize);
			log.info("总页数" + totalPage);
			int offset = (startPage - 1) * pageSize;
			param.put("pageSize", pageSize);
			param.put("offset", offset);
			while (flag) {
				//List<Map<String, Object>> list = chargesDao.queryChargesDetail(param);
				List<room> list=roomDao.queryroomList(param);
				// 写入文件
				for (int i=0;i<list.size();i++) {
					
					// 看你实体类在进行填充
					//printWriter.println(userInfo.getPhone()+"	"+userInfo.getSendMessage());
					print.println(list.get(i).getId()+","+
list.get(i).getFloor()+","+
list.get(i).getName()+","+
list.get(i).getType()+","+
list.get(i).getMaster());
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
        	log.info("房间管理生成txt异常",e);
        }finally {
        	
        }
		return responseMessage;
	}
}

