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

import javax.validation.Valid;

import java.util.regex.Pattern;
import java.math.BigDecimal;
import org.springframework.util.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;
import vo.UserinfoVo;

import com.book.manager.model.ResponseMessage;
import com.book.manager.service.userinfoService;
import com.book.manager.dao.userinfoDao;
import com.book.manager.model.userinfo;
import com.book.manager.util.CommonUtil;
import com.book.manager.util.DateUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.validation.BindingResult;
import javax.validation.constraints.NotNull;

/**
 * 用户管理 service
 * @author 刘强
 *
 */
@Service
@Slf4j
public class userinfoServiceImpl implements userinfoService{
	
	@Autowired
	private userinfoDao userinfoDao;

	
	@Value("${template.path}")
	private String localpath;
	
	@Override
	public ResponseMessage adduserinfo(@Validated UserinfoVo param, BindingResult bindingResult) {
		ResponseMessage responseMessage=new ResponseMessage();

		try {
			
		    // 检查参数校验结果
		    if (bindingResult.hasErrors()) {
		        responseMessage.setStatus("F");
		        responseMessage.setMessage(bindingResult.getFieldError().getDefaultMessage());
		        return responseMessage;
		    }

			userinfo userinfo=new userinfo();
			userinfo.setId(param.getId());
			userinfo.setUsername(param.getUsername());
			userinfo.setPassword(param.getPassword());
			userinfo.setSex(param.getSex());
			userinfo.setName(param.getName());
			userinfo.setRole(param.getRole());
			userinfo.setPhone(param.getPhone());
			userinfo.setRemark(param.getRemark());
            
			userinfoDao.adduserinfo(userinfo);
			//强制类型转换
			//碰到日期格式，转换成日期
			//以create开头的字段，使用当前的日期
			responseMessage.setStatus("S");
			responseMessage.setMessage("保存成功");
			return responseMessage;
		}
		catch (Exception e) {
			log.info("用户管理保存异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("用户管理保存异常");
			return responseMessage;
		}
	}

	@Override
	public ResponseMessage deleteuserinfo(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			//强制类型转换
			userinfoDao.deleteuserinfo(param);
			responseMessage.setStatus("S");
			responseMessage.setMessage("删除成功");
			return responseMessage;
		}catch (Exception e) {
			log.info("用户管理删除异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("用户管理删除异常");
			return responseMessage;
		}
	}

	@Override
	public ResponseMessage updateuserinfo(UserinfoVo param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			
			
			//强制类型转换
			//碰到日期格式，转换成日期
			//以update开头的字段，使用当前的日期
			userinfo userinfo=new userinfo();
			userinfo.setId(param.getId());
			userinfo.setUsername(param.getUsername());
			userinfo.setPassword(param.getPassword());
			userinfo.setSex(param.getSex());
			userinfo.setName(param.getName());
			userinfo.setRole(param.getRole());
			userinfo.setPhone(param.getPhone());
			userinfo.setRemark(param.getRemark());
			userinfoDao.updateuserinfo(userinfo);
			responseMessage.setStatus("S");
			responseMessage.setMessage("更新成功");
			return responseMessage;
		}catch (Exception e) {
			log.info("用户管理更新异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("用户管理更新异常");
			return responseMessage;
		}
	}

	@Override
	public ResponseMessage queryuserinfoList(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			
			int startPage=Integer.parseInt((String)param.get("startPage"));
			int pageSize=Integer.parseInt((String)param.get("pageSize"));
			int offset = (startPage - 1) * pageSize;
			param.put("offset", offset);
			param.put("pageSize", pageSize);
			if(!StringUtils.isEmpty((String)param.get("username"))){
			param.put("username","%"+param.get("username")+"%");
		}
if(!StringUtils.isEmpty((String)param.get("name"))){
			param.put("name","%"+param.get("name")+"%");
		}

			//查询总数int total
			int total=userinfoDao.queryuserinfoCount(param);

			//查询明细
			List<userinfo> list=userinfoDao.queryuserinfoList(param);
			
			Map<String, Object>resultMap=new HashMap<>();
			resultMap.put("list", list);
			resultMap.put("total", total);
			responseMessage.setData(resultMap);
			responseMessage.setStatus("S");
		}catch (Exception e) {
			log.info("用户管理查询异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("用户管理查询异常");
			return responseMessage;
		}
		return responseMessage;
	}
	
	@Override
	public ResponseMessage queryuserinfoDetail(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			userinfo userinfo =userinfoDao.queryuserinfoDetail(param);
			
			responseMessage.setData(userinfo);
			responseMessage.setStatus("S");
		}catch (Exception e) {
			log.info("用户管理查询异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("用户管理查询异常");
			return responseMessage;
		}
		return responseMessage;
	}
	@Override
	public ResponseMessage uploaduserinfoFile(Map<String, Object> param) {
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
				gridMgrChannelRelation.put("username",rowTerm.get(1).trim());
				gridMgrChannelRelation.put("password",rowTerm.get(2).trim());
				gridMgrChannelRelation.put("sex",rowTerm.get(3).trim());
				gridMgrChannelRelation.put("name",rowTerm.get(4).trim());
				gridMgrChannelRelation.put("role",rowTerm.get(5).trim());
				gridMgrChannelRelation.put("phone",rowTerm.get(6).trim());
				gridMgrChannelRelation.put("remark",rowTerm.get(7).trim());

	                        return gridMgrChannelRelation;
	                    }).collect(Collectors.toList());
	        log.info("读取内容::::::"+gridManagerGroupRelationList.toString());
	        for(int i=0;i<gridManagerGroupRelationList.size();i++) {//开始循环保存数据
	            JSONObject jsonObject=gridManagerGroupRelationList.get(i);
	            //CommonUtil.removeAllBlank(jsonObject.getString("phone"))
	            userinfo userinfo=new userinfo();
			userinfo.setId(Integer.parseInt(CommonUtil.removeAllBlank(jsonObject.getString("id"))));
			userinfo.setUsername(CommonUtil.removeAllBlank(jsonObject.getString("username")));
			userinfo.setPassword(CommonUtil.removeAllBlank(jsonObject.getString("password")));
			userinfo.setSex(CommonUtil.removeAllBlank(jsonObject.getString("sex")));
			userinfo.setName(CommonUtil.removeAllBlank(jsonObject.getString("name")));
			userinfo.setRole(CommonUtil.removeAllBlank(jsonObject.getString("role")));
			userinfo.setPhone(CommonUtil.removeAllBlank(jsonObject.getString("phone")));
			userinfo.setRemark(CommonUtil.removeAllBlank(jsonObject.getString("remark")));
				userinfoDao.adduserinfo(userinfo);

	        }
			responseMessage.setMessage("上传成功");
			responseMessage.setStatus("S");
		}catch (Exception e) {
			log.info("用户管理上传异常",e);
			responseMessage.setStatus("F");
			responseMessage.setMessage("用户管理上传异常");
			return responseMessage;
		}
		return responseMessage;
	}
	@Override
	public ResponseMessage productTxtuserinfo(Map<String, Object> param) {
		ResponseMessage responseMessage=new ResponseMessage();
        try {
        	PrintWriter print=new PrintWriter(new File(localpath+"userinfo"+"_"+DateUtil.formatDate(new Date(), "yyyyMMdd")+".txt"));
        	int pageSize=5000;
        	int startPage = 1;
			// 是否含有下一页标识
			boolean flag = true;
            // 在里面创建一个sheet 名字为工作薄1
            //int total = chargesDao.queryChargesDetailTotal(param);
            int total =userinfoDao.queryuserinfoCount(param);
			log.info("总条数" + total);
			int totalPage = (int) Math.ceil(Double.valueOf(total) / pageSize);
			log.info("总页数" + totalPage);
			int offset = (startPage - 1) * pageSize;
			param.put("pageSize", pageSize);
			param.put("offset", offset);
			while (flag) {
				//List<Map<String, Object>> list = chargesDao.queryChargesDetail(param);
				List<userinfo> list=userinfoDao.queryuserinfoList(param);
				// 写入文件
				for (int i=0;i<list.size();i++) {
					
					// 看你实体类在进行填充
					//printWriter.println(userInfo.getPhone()+"	"+userInfo.getSendMessage());
					print.println(list.get(i).getId()+","+
list.get(i).getUsername()+","+
list.get(i).getPassword()+","+
list.get(i).getSex()+","+
list.get(i).getName()+","+
list.get(i).getRole()+","+
list.get(i).getPhone()+","+
list.get(i).getRemark());
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
        	log.info("用户管理生成txt异常",e);
        }finally {
        	
        }
		return responseMessage;
	}
}

