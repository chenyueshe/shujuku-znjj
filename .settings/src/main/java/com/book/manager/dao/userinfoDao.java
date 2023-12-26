package com.book.manager.dao;
import java.util.Map;
import java.util.List;
import com.book.manager.model.userinfo;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface userinfoDao{
	public void adduserinfo(userinfo userinfo);
	public void deleteuserinfo(Map<String,Object> param);
	public void updateuserinfo(userinfo userinfo);
	public List<userinfo> queryuserinfoList(Map<String,Object> param);
	public userinfo queryuserinfoDetail(Map<String,Object> param);
	public int queryuserinfoCount(Map<String,Object> param);
}

