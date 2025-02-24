package cn.iocoder.yudao.module.client.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;// TODO 待办：请将下面的错误码复制到 yudao-module-client-api 模块的 ErrorCodeConstants 类中。注意，请给“TODO 补充编号”设置一个错误码编号！！！
// ========== 日志捞取建 TODO 补充编号 ==========

public interface  ErrorCodeConstants {
    ErrorCode LOG_TASK_NOT_EXISTS = new ErrorCode(-99, "日志捞取建不存在");
}