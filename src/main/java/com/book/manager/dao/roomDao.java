package com.book.manager.dao;
import java.util.Map;
import java.util.List;
import com.book.manager.model.room;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface roomDao{
	public void addroom(room room);
	public void deleteroom(Map<String,Object> param);
	public void updateroom(room room);
	public List<room> queryroomList(Map<String,Object> param);
	public room queryroomDetail(Map<String,Object> param);
	public int queryroomCount(Map<String,Object> param);
}

