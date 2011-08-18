/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:GenerateService.java  11-6-8 上午1:14 poplar.mumu ]
 */
package com.nutshell.ntztool.generate;

import com.nutshell.ntztool.db.DbInfoDao;
import com.nutshell.ntztool.model.TableInfo;
import com.nutshell.ntztool.mybatis.MapperUtil;

import java.util.List;

/**
 * 实体生成工具类.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 上午1:14
 * @since JDK 1.0
 */
public class GenerateService implements IGenerateService {


    @Override
    public boolean generateFile(String path) {
        List<TableInfo> tableInfoList = DbInfoDao.getInstance().showTables();
        for (TableInfo tableInfo : tableInfoList) {
            if (!MapperUtil.generateCode(tableInfo, path)) {
                throw new RuntimeException("创建实体类出错");
            }
            if (!MapperUtil.createMybatis(tableInfo, path)) {
                throw new RuntimeException("创建Mybatis配置文件出错");
            }
        }
        return true;
    }

    @Override
    public boolean generateEntityCode(String codeFilePath) {
        List<TableInfo> tableInfoList = DbInfoDao.getInstance().showTables();
        for (TableInfo tableInfo : tableInfoList) {
            if (!MapperUtil.generateCode(tableInfo, codeFilePath)) {
                throw new RuntimeException("创建实体类出错");
            }
        }
        return true;
    }

    @Override
    public boolean generateEntityCode(String tableName, String codeFilePath) {
        TableInfo tableInfo = DbInfoDao.getInstance().showColumns(tableName);
        return MapperUtil.generateCode(tableInfo, codeFilePath);
    }

    @Override
    public boolean generateMybatisXml(String xmlFilePath) {
        List<TableInfo> tableInfoList = DbInfoDao.getInstance().showTables();
        for (TableInfo tableInfo : tableInfoList) {
            if (!MapperUtil.createMybatis(tableInfo, xmlFilePath)) {
                throw new RuntimeException("创建Mybatis配置文件出错");
            }
        }
        return true;
    }


}
