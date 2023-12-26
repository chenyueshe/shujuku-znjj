package vo;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
//传感器管理
@Data
public class SensorVo {

	// 传感器编码
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "编码只能包含数字、大小写字母")
	private int id;

	// 传感器名称
	private String name;

	// 传感器类型
	private String type;

	// 房间编码
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "编码只能包含数字、大小写字母")
	private String roomId;

	// 房间名称
	private String roomName;

	// 最后一次数值
    @NotNull
    @Pattern(regexp = "^[0-9]*$", message = "数值只能包含数字")
	private String lastNum;

	// 单位
	private String unit;
}

