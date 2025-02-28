package cn.iocoder.yudao.module.client.controller.admin.logtask;

import cn.iocoder.yudao.framework.common.enums.UserTypeEnum;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;


import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

import cn.iocoder.yudao.module.client.controller.admin.logtask.vo.*;
import cn.iocoder.yudao.module.client.dal.dataobject.logtask.LogTaskDO;
import cn.iocoder.yudao.module.client.service.logtask.LogTaskService;

import javax.validation.Valid;

@Tag(name = "管理后台 - 日志捞取建")
@RestController
@RequestMapping("/client/log-task")
@Validated
public class LogTaskController {

    @Resource
    private LogTaskService logTaskService;

    @PostMapping("/create")
    @Operation(summary = "创建日志捞取建")
    @PreAuthorize("@ss.hasPermission('client:log-task:create')")
    public CommonResult<Integer> createLogTask(@Valid @RequestBody LogTaskSaveReqVO createReqVO) {
        return success(logTaskService.createLogTask(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新日志捞取建")
    @PreAuthorize("@ss.hasPermission('client:log-task:update')")
    public CommonResult<Boolean> updateLogTask(@Valid @RequestBody LogTaskSaveReqVO updateReqVO) {
        logTaskService.updateLogTask(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除日志捞取建")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('client:log-task:delete')")
    public CommonResult<Boolean> deleteLogTask(@RequestParam("id") Integer id) {
        logTaskService.deleteLogTask(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得日志捞取建")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('client:log-task:query')")
    public CommonResult<LogTaskRespVO> getLogTask(@RequestParam("id") Integer id) {
        LogTaskDO logTask = logTaskService.getLogTask(id);
        return success(BeanUtils.toBean(logTask, LogTaskRespVO.class));
    }
    @GetMapping("/get_task_list")
    @Operation(summary = "获取任务列表")
    public CommonResult< List<LogTaskRespVO>> getTaskList() {
        List<LogTaskDO> list = logTaskService.getTaskList();
        List<LogTaskRespVO>  result= BeanUtils.toBean(list, LogTaskRespVO.class);
        return  CommonResult.success(result);
    }



    @GetMapping("/page")
    @Operation(summary = "获得日志捞取建分页")
    @PreAuthorize("@ss.hasPermission('client:log-task:query')")
    public CommonResult<PageResult<LogTaskRespVO>> getLogTaskPage(@Valid LogTaskPageReqVO pageReqVO) {
        PageResult<LogTaskDO> pageResult = logTaskService.getLogTaskPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, LogTaskRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出日志捞取建 Excel")
    @PreAuthorize("@ss.hasPermission('client:log-task:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportLogTaskExcel(@Valid LogTaskPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<LogTaskDO> list = logTaskService.getLogTaskPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "日志捞取建.xls", "数据", LogTaskRespVO.class,
                        BeanUtils.toBean(list, LogTaskRespVO.class));
    }

}