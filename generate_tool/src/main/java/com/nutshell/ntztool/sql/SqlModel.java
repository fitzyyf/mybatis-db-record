/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:SqlModel.java  11-8-12 下午6:00 poplar.mumu ]
 */
package com.nutshell.ntztool.sql;

import java.io.Serializable;

/**
 * SQL基本实体.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-8-12 下午6:00
 * @since JDK 1.0
 */
public class SqlModel implements Serializable {
    private static final long serialVersionUID = -7646394029487278442L;

    /**
     * Insert INTO 语句
     */
    private String insertSql;
    /**
     * UPDATE SET 语句
     */
    private String updateSql;
    /**
     * DELETE 语句
     */
    private String deleteSql;
    /**
     * 查询所有的SQL语句
     */
    private String selectAllSql;
    /**
     * 查询语句
     */
    private String selectSql;
    /**
     * 数据总数的SQL语句
     */
    private String countQuerySql;


    public String getInsertSql() {
        return insertSql;
    }

    public void setInsertSql(String insertSql) {
        this.insertSql = insertSql;
    }

    public String getUpdateSql() {
        return updateSql;
    }

    public void setUpdateSql(String updateSql) {
        this.updateSql = updateSql;
    }

    public String getDeleteSql() {
        return deleteSql;
    }

    public void setDeleteSql(String deleteSql) {
        this.deleteSql = deleteSql;
    }

    public String getSelectAllSql() {
        return selectAllSql;
    }

    public void setSelectAllSql(String selectAllSql) {
        this.selectAllSql = selectAllSql;
    }

    public String getSelectSql() {
        return selectSql;
    }

    public void setSelectSql(String selectSql) {
        this.selectSql = selectSql;
    }

    public String getCountQuerySql() {
        return countQuerySql;
    }

    public void setCountQuerySql(String countQuerySql) {
        this.countQuerySql = countQuerySql;
    }
}
