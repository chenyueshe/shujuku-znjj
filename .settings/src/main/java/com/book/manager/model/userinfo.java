package com.book.manager.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Pattern;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
//用户管理
@Data
@Validated
public class userinfo {

	// 用户编号
	private int id;

	// 登录用户名
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9@_]*$", message = "用户名只能包含数字、大小写字母、@和_")
	private String username;

	// 登录密码
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9@_]*$", message = "密码只能包含数字、大小写字母、@和_")
	private String password;

	// 性别
    @NotNull
    @Pattern(regexp = "^(男|女)$", message = "性别只能为'男'或'女'")
	private String sex;

	// 姓名
	private String name;

	// 角色管理
	private String role;

	// 手机号
    @NotNull
    @Pattern(regexp = "^\\d{11}$", message = "电话号码必须为11位数字")
	private String phone;

	// 阅读偏好
	private String remark;
}

