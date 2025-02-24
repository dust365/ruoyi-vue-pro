package cn.iocoder.yudao.module.client.dal.dataobject.logtask;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 日志捞取建 DO
 *
 * @author 芋道源码
 */
@TableName("client_log_task")
@KeySequence("client_log_task_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogTaskDO extends BaseDO {

    /**
     * 任务id
     */
    @TableId
    private Integer taskId;
    /**
     * 应用名称
     */
    private String appName;
    /**
     * 用户id
     */
    private Long userUid;
    /**
     * 操作者
     */
    private String operator;
    /**
     * 操作时间
     */
    private LocalDateTime operationTime;
    /**
     * 任务状态
     */
    private Integer taskStatus;
    /**
     * 日志结果链接
     */
    private String resultUrl;

}