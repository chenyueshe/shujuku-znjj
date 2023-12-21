package com.book.manager.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;

//用户管理
@Data
public class userinfo {

	// 用户编号
	private int id;

	// 登录用户名
	private String username;

	// 登录密码
	private String password;

	// 性别
	private String sex;

	// 姓名
	private String name;

	// 角色管理
	private String role;

	// 手机号
	private String phone;

	// 阅读偏好
	private String remark;
}

