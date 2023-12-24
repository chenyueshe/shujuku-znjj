package com.book.manager.dao;
import java.util.Map;
import java.util.List;
import com.book.manager.model.controll;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface controllDao{
	public void addcontroll(controll controll);
	public void deletecontroll(Map<String,Object> param);
	public void updatecontroll(controll controll);
	public List<controll> querycontrollList(Map<String,Object> param);
	public controll querycontrollDetail(Map<String,Object> param);
	public int querycontrollCount(Map<String,Object> param);
	
	public void saveWork(Map<String, Object>param);
	public void deleteWork(Map<String, Object>param);
	public List<Map<String, Object>> queryWorks(Map<String, Object>param);
}

