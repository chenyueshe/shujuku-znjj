package com.book.manager.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

//房间管理
@Data
public class room {

	// 房间编号
    @NotNull
	private int id;

	// 房间楼层
    @NotNull
    @Pattern(regexp = "^[0-9]*$", message = "楼层只能包含数字")
	private String floor;

	// 房间名字
	private String name;

	// 房间类型
	private String type;

	// 房间主人
	private String master;
}

