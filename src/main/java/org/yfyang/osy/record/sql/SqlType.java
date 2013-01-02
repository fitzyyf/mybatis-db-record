/*
 * Copyright (c) 2010-2011 GOV.
 * [Id:SqlType.java  11-9-15 下午5:26 poplar.mumu ]
 */
package org.yfyang.osy.record.sql;

/**
 * Sql类型，包括insert、delete、update、select等.
 * <br/>
 *
 * @author poplar.mumu
 * @version 1.0 11-9-15 下午5:26
 * @since JDK 1.5
 */
public enum SqlType {
    /**
     * 写入Insert标记
     */
    insert,
    /**
     * 删除Delete语句
     */
    delete,
    /**
     * 更新Update语句
     */
    update,
    /**
     * 查询单条信息的SQL
     */
    singleSelect,
    /**
     *分页查询sql
     */
    pageSelect,
    /**
     *分页查询种的总计
     */
    countSelect;

    /**
     * 现实对应的标记名称。
     * @param sqlType SQL标记类型
     * @return 具体的SQL标记描述
     */
    public String toValue(SqlType sqlType){
        return null;
    }
}
