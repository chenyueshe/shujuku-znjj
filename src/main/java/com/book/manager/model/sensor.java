package com.book.manager.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;

//传感器管理
@Data
public class sensor {

	// 传感器编码
	private int id;

	// 传感器名称
	private String name;

	// 传感器类型
	private String type;

	// 房间编码
	private String roomId;

	// 房间名称
	private String roomName;

	// 最后一次数值
	private String lastNum;

	// 单位
	private String unit;
}

