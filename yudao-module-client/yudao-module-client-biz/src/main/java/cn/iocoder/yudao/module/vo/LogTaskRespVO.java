package cn.iocoder.yudao.module.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 日志捞取建 Response VO")
@Data
@ExcelIgnoreUnannotated
public class LogTaskRespVO {

    @Schema(description = "任务id", requiredMode = Schema.RequiredMode.REQUIRED, example = "3201")
    @ExcelProperty("任务id")
    private Integer taskId;

    @Schema(description = "应用名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("应用名称")
    private String appName;

    @Schema(description = "用户id", requiredMode = Schema.RequiredMode.REQUIRED, example = "11570")
    @ExcelProperty("用户id")
    private Long userUid;

    @Schema(description = "操作者", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("操作者")
    private String operator;

    @Schema(description = "操作时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("操作时间")
    private LocalDateTime operationTime;

    @Schema(description = "任务状态", example = "0.全部 1.进行中 2.完成 3.失败/超时")
    @ExcelProperty("任务状态")
    private Integer taskStatus;

    @Schema(description = "日志结果链接", example = "https://www.iocoder.cn")
    @ExcelProperty("日志结果链接")
    private String resultUrl;

}