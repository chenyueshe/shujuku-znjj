package com.book.manager.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
//控制器管理
@Data
public class controll {

	// 控制器编码
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "密码只能包含数字、大小写字母")
	private int id;

	// 控制器名称
	private String name;

	// 房间编码
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "密码只能包含数字、大小写字母")
	private String roomId;

	// 房间名称
	private String roomName;

	// 状态
	private String status;

	// 操作人
	private String oprator;

	// 操作时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date opratorTime;
}

