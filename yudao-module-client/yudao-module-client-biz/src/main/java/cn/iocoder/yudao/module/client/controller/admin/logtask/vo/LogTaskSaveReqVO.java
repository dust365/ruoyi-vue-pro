package cn.iocoder.yudao.module.client.controller.admin.logtask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 日志捞取建新增/修改 Request VO")
@Data
public class LogTaskSaveReqVO {

    @Schema(description = "任务id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3201")
    private Integer taskId;

    @Schema(description = "应用名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "应用名称不能为空")
    private String appName;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11570")
    @NotNull(message = "用户id不能为空")
    private Long userUid;

    @Schema(description = "操作者", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "操作者不能为空")
    private String operator;

    @Schema(description = "操作时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "操作时间不能为空")
    private LocalDateTime operationTime;

    @Schema(description = "任务状态", example = "0.全部 1.进行中 2.完成 3.失败/超时")
    private Integer taskStatus;

    @Schema(description = "日志结果链接", example = "https://www.iocoder.cn")
    private String resultUrl;

}