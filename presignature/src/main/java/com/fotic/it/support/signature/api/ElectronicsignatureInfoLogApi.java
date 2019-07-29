package com.fotic.it.support.signature.api;

import com.fotic.it.support.signature.dao.entity.ElectronicsignatureInfoLog;
import com.fotic.it.support.signature.service.ElectronicsignatureInfoLogService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 重构后电子签章日志(ElectronicsignatureInfoLog)表控制层
 *
 * @author makejava-ElectronicSignatrure
 * @since 2019-06-04 15:55:13
 */
@Api(tags = "获取日志信息")
@Controller
public class ElectronicsignatureInfoLogApi {
    /**
     * 服务对象
     */
    @Autowired
    private ElectronicsignatureInfoLogService electronicsignatureInfoLogService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @ApiOperation(value="获取日志信息方法")
    @ResponseBody
    @RequestMapping(value = "/electronicSignature",method = RequestMethod.GET)
    public PageInfo<ElectronicsignatureInfoLog> selectOne(int pageNum, int pageSize, String fileName,
                                                          String code,String createTimeStart,String createTimeStartEnd,String info,
                                                          String inputPath,String outputPath
    ) {
        PageInfo<ElectronicsignatureInfoLog> electronicsignatureInfoLogList = electronicsignatureInfoLogService.findAll(pageNum,pageSize,fileName,code,createTimeStart,createTimeStartEnd,info,inputPath,outputPath);
        return electronicsignatureInfoLogList;
    }

}