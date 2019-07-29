package com.fotic.it.support.signature.dao.mapper;

import com.fotic.it.support.signature.dao.entity.EosDictEntry;
import com.fotic.it.support.signature.dao.entity.SignConfigurationInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (EosDictEntry)表数据库访问层对应的Mapper类
 *
 * @author makejava-ElectronicSignatrure
 * @since 2019-06-13 13:40:45
 */
@Mapper
@Component
public interface EosDictEntryMapper {

    /**
     *
     * @param typeId
     * @param id
     * @return
     */
    String getDictName(@Param(value = "typeId") String typeId, @Param(value = "id") String id);

    SignConfigurationInfoEntity getSignConfigurationInfoEntity();
}