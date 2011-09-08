/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:GenerateService.java  11-6-8 上午1:14 poplar.mumu ]
 */
package org.mumu.build.generate;

import org.mumu.build.db.TableInfoClient;
import org.mumu.build.model.TableInfo;

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
        List<TableInfo> tableInfoList = TableInfoClient.instance.showTables();
        for (TableInfo tableInfo : tableInfoList) {
            if (!BuilderEntity.generateCode(tableInfo, path)) {
                throw new RuntimeException("创建实体类出错");
            }
            if (!BuildMapper.createMybatis(tableInfo, path)) {
                throw new RuntimeException("创建Mybatis配置文件出错");
            }
        }
        return true;
    }


    @Override
    public boolean generateEntityCode(String codeFilePath) {
        List<TableInfo> tableInfoList = TableInfoClient.instance.showTables();
        for (TableInfo tableInfo : tableInfoList) {
            if (!BuilderEntity.generateCode(tableInfo, codeFilePath)) {
                throw new RuntimeException("创建实体类出错");
            }
        }
        return true;
    }

    @Override
    public boolean generateEntityCode(String tableName, String codeFilePath) {
        TableInfo tableInfo = TableInfoClient.instance.showColumns(tableName);
        return BuilderEntity.generateCode(tableInfo, codeFilePath);
    }

    @Override
    public boolean generateMybatisXml(String xmlFilePath) {
        List<TableInfo> tableInfoList = TableInfoClient.instance.showTables();
        for (TableInfo tableInfo : tableInfoList) {
            if (!BuildMapper.createMybatis(tableInfo, xmlFilePath)) {
                throw new RuntimeException("创建Mybatis配置文件出错");
            }
        }
        return true;
    }


}
