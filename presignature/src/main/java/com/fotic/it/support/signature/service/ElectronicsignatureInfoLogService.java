package com.fotic.it.support.signature.service;

import com.fotic.it.support.signature.dao.entity.ElectronicsignatureInfoLog;
import com.fotic.it.support.signature.model.SignatureContainer;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 重构后电子签章日志(ElectronicsignatureInfoLog)表服务接口
 *
 * @author makejava-ElectronicSignatrure
 * @since 2019-06-04 15:55:11
 */
@Component
public interface ElectronicsignatureInfoLogService {

    /**
     * 获取全部数据
     *
     * @return
     */
    PageInfo<ElectronicsignatureInfoLog> findAll(int pageNum, int pageSize,
                                      String fileName,
                                      String code,
                                      String createDateStart,
                                      String createDateEnd,
                                      String info,
                                      String inputPath,
                                      String outputPath
    );

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    List<ElectronicsignatureInfoLog> queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ElectronicsignatureInfoLog> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param electronicsignatureInfoLog 实例对象
     * @return 实例对象
     */
    int insert(ElectronicsignatureInfoLog electronicsignatureInfoLog);

    /**
     * 修改数据
     *
     * @param electronicsignatureInfoLog 实例对象
     * @return 实例对象
     */
    List<ElectronicsignatureInfoLog> update(ElectronicsignatureInfoLog electronicsignatureInfoLog);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    /**
     * 记录日志
     * @param statusCode    状态码  [0：正常，99：异常，1：电子印章没有系统权限，2：访问的IP地址没有在系统中授权，3：签章编号不是由纯数字组成，4：电子印章安全码不正确]
     * @param simpleInfo    对状态码的简单描述，比如两条数据的状态码都为99，但失败的原因不一致，那么就可以对失败原因简单描述
     * @param fileName      签章的文件名
     * @param inputPath     输入文件路径
     * @param outputPath    输出文件路径
     * @param sysName       系统名称
     * @param keyPwd        安全码
     * @param signType      签章方式 [0：文字定位，1：像素定位]
     * @param signPosition  签章位置，当签章方式为0时该参数为具体盖章位置的文字信息；为1时该参数为具体的像素信息如（40，40）
     * @param sealIndex     签章编号
     * @param signPage      签章所在页，部分页都盖章时为[1,2,3,4]数组，每页盖章时为[0]，最后一页盖章时为[-1]，首页和最后一页盖章时为[1,-1]
     */
    int recordLog(int statusCode, String simpleInfo,
                  String fileName, String inputPath, String outputPath,
                  String sysName, String keyPwd, String signType, String signPosition, String sealIndex, String signPage);

    /**
     * 记录日志
     * @param statusCode    状态码  [0：正常，99：异常，1：电子印章没有系统权限，2：访问的IP地址没有在系统中授权，3：签章编号不是由纯数字组成，4：电子印章安全码不正确]
     * @param simpleInfo    对状态码的简单描述，比如两条数据的状态码都为99，但失败的原因不一致，那么就可以对失败原因简单描述
     * @param fileName      签章的文件名
     * @param inputPath     输入文件路径
     * @param outputPath    输出文件路径
     * @param sysName       系统名称
     * @param keyPwd        安全码
     * @param signatureContainers 封装了签章相关的信息
     */
    int recordLog(int statusCode, String simpleInfo,
                  String fileName, String inputPath, String outputPath,
                  String sysName, String keyPwd, List<SignatureContainer> signatureContainers);

}