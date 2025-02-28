package cn.iocoder.yudao.module.client.controller.admin.logtask.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 日志捞取建分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class LogTaskPageReqVO extends PageParam {

    @Schema(description = "应用名称", example = "赵六")
    private String appName;

    @Schema(description = "用户id", example = "11570")
    private Long userUid;

    @Schema(description = "操作者")
    private String operator;

    @Schema(description = "操作时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] operationTime;

    @Schema(description = "任务状态", example = "0.全部 1.进行中 2.完成 3.失败/超时")
    private Integer taskStatus;

    @Schema(description = "日志结果链接", example = "https://www.iocoder.cn")
    private String resultUrl;

}