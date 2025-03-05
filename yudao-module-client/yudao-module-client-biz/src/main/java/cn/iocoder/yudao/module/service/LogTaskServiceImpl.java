package cn.iocoder.yudao.module.service;

import cn.iocoder.yudao.module.vo.LogTaskPageReqVO;
import cn.iocoder.yudao.module.vo.LogTaskSaveReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import cn.iocoder.yudao.module.dal.dataobject.logtask.LogTaskDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.dal.mysql.logtask.LogTaskMapper;

import javax.annotation.Resource;

import java.util.List;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.enums.ErrorCodeConstants.*;

/**
 * 日志捞取建 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
public class LogTaskServiceImpl implements LogTaskService {

    @Resource
    private LogTaskMapper logTaskMapper;

    @Override
    public Integer createLogTask(LogTaskSaveReqVO createReqVO) {
        // 插入
        LogTaskDO logTask = BeanUtils.toBean(createReqVO, LogTaskDO.class);
        logTaskMapper.insert(logTask);
        // 返回
        return logTask.getTaskId();
    }

    @Override
    public void updateLogTask(LogTaskSaveReqVO updateReqVO) {
        // 校验存在
        validateLogTaskExists(updateReqVO.getTaskId());
        // 更新
        LogTaskDO updateObj = BeanUtils.toBean(updateReqVO, LogTaskDO.class);
        logTaskMapper.updateById(updateObj);
    }

    @Override
    public void deleteLogTask(Integer id) {
        // 校验存在
        validateLogTaskExists(id);
        // 删除
        logTaskMapper.deleteById(id);
    }

    private void validateLogTaskExists(Integer id) {
        if (logTaskMapper.selectById(id) == null) {
            throw exception(LOG_TASK_NOT_EXISTS);
        }
    }

    @Override
    public LogTaskDO getLogTask(Integer id) {
        return logTaskMapper.selectById(id);
    }

    @Override
    public PageResult<LogTaskDO> getLogTaskPage(LogTaskPageReqVO pageReqVO) {
        return logTaskMapper.selectPage(pageReqVO);
    }

    @Override
    public List<LogTaskDO> getTaskList() {
        return logTaskMapper.selectList();
    }

}