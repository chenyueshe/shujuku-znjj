package vo;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
//家具管理
@Data
public class ControllVo {

	// 家具编码
    @NotNull(message="编码不能为空")
	private int id;

	// 家具名称
	private String name;

	// 房间编码
    @NotNull
    @Pattern(regexp = "^[0-9]*$", message = "编码只能包含数字")
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
	
	private List<Map<String, Object>> controls;
}

