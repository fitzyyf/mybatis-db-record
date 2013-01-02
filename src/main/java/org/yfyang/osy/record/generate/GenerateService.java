/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:GenerateService.java  11-6-8 上午1:14 poplar.mumu ]
 */
package org.yfyang.osy.record.generate;

import java.util.List;

import org.yfyang.osy.record.db.TableInfoClient;
import org.yfyang.osy.record.model.TableInfo;

/**
 * 具体产生实体以及mybatis的信息.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 上午1:14
 * @since JDK 1.5
 */
public class GenerateService implements IGenerateService {


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


    public boolean generateEntityCode(String codeFilePath) {
        List<TableInfo> tableInfoList = TableInfoClient.instance.showTables();
        for (TableInfo tableInfo : tableInfoList) {
            if (!BuilderEntity.generateCode(tableInfo, codeFilePath)) {
                throw new RuntimeException("创建实体类出错");
            }
        }
        return true;
    }

    public boolean generateEntityCode(String tableName, String codeFilePath) {
        TableInfo tableInfo = TableInfoClient.instance.showColumns(tableName);
        return BuilderEntity.generateCode(tableInfo, codeFilePath);
    }

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
