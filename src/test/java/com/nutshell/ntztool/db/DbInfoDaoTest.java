/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:DbInfoDaoTest.java  11-6-8 上午12:28 poplar.mumu ]
 */
package com.nutshell.ntztool.db;

import com.nutshell.ntztool.model.ColumnInfo;
import com.nutshell.ntztool.model.TableInfo;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * 数据库测试.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 上午12:28
 * @since JDK 1.0
 */
public class DbInfoDaoTest {

    private DbInfoDao infoDao;

    @Before
    public void setUp() throws Exception {
        infoDao = DbInfoDao.getInstance();
    }

    @Test
    public void testShowTables() throws Exception {
        List<TableInfo> tables = infoDao.showTables();
        for (TableInfo table : tables) {
            System.out.println("表名：" + table);
        }
    }

    @Test
    public void testShowColumns() throws Exception {
        TableInfo tableInfo = infoDao.showColumns("mrp_s_action");
        System.out.println(tableInfo);
        for (ColumnInfo columnInfo : tableInfo.getColumnList()) {
            System.out.println(columnInfo);
        }
    }

    @Test
    public void testName() throws Exception {
        String test = System.getProperty("user.dir");
        System.out.println(test);
    }
}
