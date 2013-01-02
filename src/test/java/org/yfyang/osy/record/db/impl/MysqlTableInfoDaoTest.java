/*
 * Copyright (c) 2010-2011 GOV.
 * [Id:MysqlTableInfoDaoTest.java  11-9-15 下午4:23 poplar.mumu ]
 */
package org.yfyang.osy.record.db.impl;

import org.junit.Before;
import org.junit.Test;
import org.yfyang.osy.record.model.ColumnInfo;
import org.yfyang.osy.record.model.TableInfo;

import java.util.List;

/**
 * .
 * <br/>
 *
 * @author poplar.mumu
 * @version 1.0 11-9-15 下午4:23
 * @since JDK 1.5
 */
public class MysqlTableInfoDaoTest {
    private MysqlTableInfoDao mysqlTableInfoDao ;

    @Before
    public void setUp() throws Exception {
        mysqlTableInfoDao = new MysqlTableInfoDao();
    }

    /**
     * 测试现实表格方法。
     * @throws Exception 异常
     */
    @Test
    public void testShowTables() throws Exception {
        List<TableInfo> tableInfos = mysqlTableInfoDao.showTables();
        for (TableInfo tableInfo : tableInfos) {
            System.out.println(tableInfo.getTableName());
            for (ColumnInfo columnInfo : tableInfo.getColumnList()) {
                System.out.println("   "+columnInfo.getColumnName());
            }
        }
    }

    /**
     * 测试现实表格字段信息。
     * @throws Exception 异常信息
     */
    @Test
    public void testShowColumns() throws Exception {
        TableInfo tableInfo = mysqlTableInfoDao.showColumns("PF_ACTION_LOG");
        for (ColumnInfo columnInfo : tableInfo.getColumnList()) {
            System.out.println(columnInfo.getColumnName());
        }
    }
}
