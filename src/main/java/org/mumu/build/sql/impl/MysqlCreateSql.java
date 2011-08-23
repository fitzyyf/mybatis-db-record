package org.mumu.build.sql.impl;

import org.mumu.build.model.MybatisSqlModal;
import org.mumu.build.model.SqlModal;
import org.mumu.build.model.TableInfo;
import org.mumu.build.sql.ICreateSqlFace;

/**
 * Mysql实现创建脚本类.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 1:31 下午
 * @since JDK 1.0
 */
public class MysqlCreateSql extends CreateSql implements ICreateSqlFace {

    private MysqlCreateSql() {
    }

    /**
     * 
     */
    private static class Inner {
        private static  ICreateSqlFace _mysql = new MysqlCreateSql();
    }

    public static ICreateSqlFace getInstance() {
        return Inner._mysql;
    }

    @Override
    public SqlModal createSql(TableInfo tableInfo) {
        SqlModal sql = new SqlModal();
        String tableName = tableInfo.getTableName().toUpperCase();
        MybatisSqlModal mybatisSqlModal = gengerateSql(tableInfo);
        sql.setInsertSql(generateInsertSql(mybatisSqlModal, tableName));
        sql.setDeleteSql(generateDeleteSql(mybatisSqlModal, tableName));
        sql.setSelectAllSql(generateSelectSql(mybatisSqlModal, tableName));
        sql.setSelectSql(generateSelectOneSql(mybatisSqlModal, tableName));
        sql.setUpdateSql(generateUpdateSql(mybatisSqlModal, tableName));
        sql.setCountQuerySql(generateCountSql(mybatisSqlModal, tableName));
        sql.setPageWhereSql(pageSql());
        return sql;
    }

    @Override
    protected String pageSql() {
        return "LIMIT #{start},#{end}";
    }

    //todo
    @Override
    protected String getInsertPrimaryKey() {
        return null;
    }
}
