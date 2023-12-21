package com.book.manager.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.awt.Color;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.text.SimpleDateFormat;
import lombok.extern.slf4j.Slf4j;

import com.book.manager.model.ResponseMessage;
import com.book.manager.dao.roomDao;
import com.book.manager.service.roomService;
import com.book.manager.model.room;
import com.book.manager.util.DateUtil;



@RestController
@Slf4j
public class roomController {
	@Value("${template.path}")
	private String localpath;
	
	@Autowired
	private roomDao roomDao;
	
	@Autowired
	private roomService roomService;
	
	@RequestMapping("addroom")
	public ResponseMessage addroom(@RequestParam Map<String, Object>param,HttpServletRequest request) {
		
		return roomService.addroom(param);
	}
	
	@RequestMapping("deleteroom")
	public ResponseMessage deleteroom(@RequestParam Map<String, Object>param,HttpServletRequest request) {
		
		return roomService.deleteroom(param);
		
	}
	
	@RequestMapping("updateroom")
	public ResponseMessage updateroom(@RequestParam Map<String, Object>param,HttpServletRequest request) {
		
		return roomService.updateroom(param);
	}
	
	@RequestMapping("queryroomList")
	public ResponseMessage queryroomList(@RequestParam Map<String, Object>param,HttpServletRequest request) {
		
		return roomService.queryroomList(param);
	}
	
	@RequestMapping("queryroomDetail")
	public ResponseMessage queryroomDetail(@RequestParam Map<String, Object>param,HttpServletRequest request) {
		return roomService.queryroomList(param);
	}
	
	@RequestMapping("uploadroomFile")
	public ResponseMessage uploadroomFile(@RequestParam Map<String, Object>param,HttpServletRequest request) {
		String fileName=(String)request.getSession().getAttribute("filename");
		String batchId=(String)request.getSession().getAttribute("batchId");
		String localfilename=(String)request.getSession().getAttribute("localfilename");
		param.put("filename", fileName);
		param.put("batchId", batchId);
		param.put("localfilename", localfilename);
		return roomService.uploadroomFile(param);
	}
	
	@RequestMapping("uploadroomURL")
	public ResponseMessage uploadroomURL(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		ResponseMessage responseMessage=new ResponseMessage();
		try {
			String batchId=new Date().getTime()+"";
			String []filenames=file.getOriginalFilename().split("\\.");
			log.info("接收到的文件名"+file.getOriginalFilename());
			request.getSession().setAttribute("fileName", file.getOriginalFilename());
			request.getSession().setAttribute("batchId", batchId);
			request.getSession().setAttribute("localfilename", batchId+"."+filenames[1]);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			String format = sdf.format(new Date());
			File file2 =new File(localpath+format+"/");
			if(!file2.exists()) {
				file2.mkdir();
			}
			file.transferTo(new File(localpath+format+"/"+batchId+"."+filenames[1]));
		}catch (Exception e) {
			log.info("创建任务时,文件上传异常",e);
			responseMessage.setMessage("文件上传异常");
			responseMessage.setStatus("F");
			return responseMessage;
		}
		responseMessage.setStatus("S");
		responseMessage.setMessage("文件上传成功");
		return responseMessage;
	}
	
	
	@RequestMapping("downloadroom")
	public void downloadroom(@RequestParam Map<String, Object>param,HttpServletRequest request,HttpServletResponse response) {
		OutputStream opStream = null;
        XSSFWorkbook hssfWorkbook=null;
        try {
        	//生成下载头
        	hssfWorkbook = new XSSFWorkbook();
			XSSFCellStyle borderStyle=hssfWorkbook.createCellStyle();
			borderStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);    //设置填充方案
			borderStyle.setFillForegroundColor(new XSSFColor(new Color(0,198,99)));  //设置填充颜色
		    // 在Excel工作簿中建一工作表，其名为缺省值
		    XSSFSheet sheet = hssfWorkbook.createSheet("Sheet1");
		    // 在索引0的位置创建行（最顶端的行）
		    XSSFRow row = sheet.createRow(0);
		    //替换
		    //String []headList= {"测试1","测试2","测试3","测试4","测试5",};
		    String []headList= {"房间编号","房间楼层","房间名字","房间类型","房间主人",};

	        // 设置excel头（第一行）的头名称
	        for (int i = 0; i < headList.length; i++) {
	 
	            // 在索引0的位置创建单元格（左上端）
	            XSSFCell cell = row.createCell(i);
	            cell.setCellStyle(borderStyle);
	            // 定义单元格为字符串类型
	            // 在单元格中输入一些内容
	            cell.setCellValue(headList[i]);
	        }
        	int pageSize=5000;
        	int startPage = 1;
			// 是否含有下一页标识
			boolean flag = true;
        	opStream=response.getOutputStream();
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
					// 填充数据
					XSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
					// 看你实体类在进行填充
					//dataRow.createCell(0).setCellValue(list.get(i).getName());
					dataRow.createCell(0).setCellValue(list.get(i).getId());
dataRow.createCell(1).setCellValue(list.get(i).getFloor());
dataRow.createCell(2).setCellValue(list.get(i).getName());
dataRow.createCell(3).setCellValue(list.get(i).getType());
dataRow.createCell(4).setCellValue(list.get(i).getMaster());

				}
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
            
           
            String fileName=new Date().getTime()+".xlsx";
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader(
                    "Content-Disposition",
                    URLEncoder.encode(fileName,
                            "UTF-8"));
            hssfWorkbook.write(opStream);
            opStream.flush();
        }catch(Exception e) {
        	log.info("导出信息异常",e);
        }finally {
        	try {
        		hssfWorkbook.close();
				opStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
}

