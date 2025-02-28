package cn.iocoder.yudao.module.service.logtask;

import java.util.*;

import cn.iocoder.yudao.module.dal.dataobject.logtask.LogTaskDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.vo.LogTaskPageReqVO;
import cn.iocoder.yudao.module.vo.LogTaskSaveReqVO;

import javax.validation.Valid;

/**
 * 日志捞取建 Service 接口
 *
 * @author 芋道源码
 */
public interface LogTaskService {

    /**
     * 创建日志捞取建
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Integer createLogTask(@Valid LogTaskSaveReqVO createReqVO);

    /**
     * 更新日志捞取建
     *
     * @param updateReqVO 更新信息
     */
    void updateLogTask(@Valid LogTaskSaveReqVO updateReqVO);

    /**
     * 删除日志捞取建
     *
     * @param id 编号
     */
    void deleteLogTask(Integer id);

    /**
     * 获得日志捞取建
     *
     * @param id 编号
     * @return 日志捞取建
     */
    LogTaskDO getLogTask(Integer id);

    /**
     * 获得日志捞取建分页
     *
     * @param pageReqVO 分页查询
     * @return 日志捞取建分页
     */
    PageResult<LogTaskDO> getLogTaskPage(LogTaskPageReqVO pageReqVO);


    List<LogTaskDO> getTaskList();
}