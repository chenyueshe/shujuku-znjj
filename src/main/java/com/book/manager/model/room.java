package com.book.manager.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;

//房间管理
@Data
public class room {

	// 房间编号
	private int id;

	// 房间楼层
	private String floor;

	// 房间名字
	private String name;

	// 房间类型
	private String type;

	// 房间主人
	private String master;
}

