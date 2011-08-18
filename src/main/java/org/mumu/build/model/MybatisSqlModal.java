package org.mumu.build.model;

import java.util.List;

/**
 * mybatis对应的sql对象信息.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 1:50 下午
 * @since JDK 1.0
 */
public class MybatisSqlModal {
    /**
     * 主键，可能多个
     */
    private List<String> primaryKeys;

    /**
     * 组合后的insert字段
     */
    private String insertColumn;
    /**
     * 组合后的insert的mybatis的组合
     */
    private String insertMybatis;

    /**
     * 组合后的update字段
     */
    private String updateColumn;

    /**
     * 组合后的select字段
     */
    private String selectColumn;



    public List<String> getPrimaryKeys() {
        return primaryKeys;
    }

    public void setPrimaryKeys(List<String> primaryKeys) {
        this.primaryKeys = primaryKeys;
    }

    public String getInsertColumn() {
        return insertColumn;
    }

    public void setInsertColumn(String insertColumn) {
        this.insertColumn = insertColumn;
    }

    public String getUpdateColumn() {
        return updateColumn;
    }

    public void setUpdateColumn(String updateColumn) {
        this.updateColumn = updateColumn;
    }

    public String getSelectColumn() {
        return selectColumn;
    }

    public void setSelectColumn(String selectColumn) {
        this.selectColumn = selectColumn;
    }

    public String getInsertMybatis() {
        return insertMybatis;
    }

    public void setInsertMybatis(String insertMybatis) {
        this.insertMybatis = insertMybatis;
    }
}
