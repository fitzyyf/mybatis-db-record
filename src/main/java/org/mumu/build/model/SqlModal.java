/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:SqlModal.java  11-8-16 上午10:56 poplar.mumu ]
 */
package org.mumu.build.model;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-8-16 上午10:56
 * @since JDK 1.0
 */
public class SqlModal {
    private String insertSql;
    private String updateSql;
    private String deleteSql;
    private String selectAllSql;

    private String pageWhereSql;
    private String selectSql;
    private String countQuerySql;

    public String getPageWhereSql() {
        return pageWhereSql;
    }

    public void setPageWhereSql(String pageWhereSql) {
        this.pageWhereSql = pageWhereSql;
    }

    public String getCountQuerySql() {
        return countQuerySql;
    }

    public void setCountQuerySql(String countQuerySql) {
        this.countQuerySql = countQuerySql;
    }

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
}
