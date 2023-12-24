package com.book.manager.dao;
import java.util.Map;
import java.util.List;
import com.book.manager.model.sensor;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface sensorDao{
	public void addsensor(sensor sensor);
	public void deletesensor(Map<String,Object> param);
	public void updatesensor(sensor sensor);
	public List<sensor> querysensorList(Map<String,Object> param);
	public sensor querysensorDetail(Map<String,Object> param);
	public int querysensorCount(Map<String,Object> param);
}

