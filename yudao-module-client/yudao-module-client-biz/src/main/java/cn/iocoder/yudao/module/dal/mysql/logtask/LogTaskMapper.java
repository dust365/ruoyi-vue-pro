package cn.iocoder.yudao.module.dal.mysql.logtask;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.dal.dataobject.logtask.LogTaskDO;
import cn.iocoder.yudao.module.vo.LogTaskPageReqVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志捞取建 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface LogTaskMapper extends BaseMapperX<LogTaskDO> {

    default PageResult<LogTaskDO> selectPage(LogTaskPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<LogTaskDO>()
                .likeIfPresent(LogTaskDO::getAppName, reqVO.getAppName())
                .eqIfPresent(LogTaskDO::getUserUid, reqVO.getUserUid())
                .eqIfPresent(LogTaskDO::getOperator, reqVO.getOperator())
                .betweenIfPresent(LogTaskDO::getOperationTime, reqVO.getOperationTime())
                .eqIfPresent(LogTaskDO::getTaskStatus, reqVO.getTaskStatus())
                .eqIfPresent(LogTaskDO::getResultUrl, reqVO.getResultUrl())
                .orderByDesc(LogTaskDO::getTaskId));
    }

}