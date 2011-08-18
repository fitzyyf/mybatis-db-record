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
    @Override
    public SqlModal createSql(TableInfo tableInfo) {
        SqlModal sql = new SqlModal();

        String tableName = tableInfo.getTableName().toUpperCase();

        MybatisSqlModal mybatisSqlModal = gengerateSql(tableInfo);
        String insert = generateInsertSql(mybatisSqlModal, tableName);//insert语句
        StringBuilder update = new StringBuilder();//update语句
        StringBuilder delete = new StringBuilder();//delete语句
        StringBuilder selectOne = new StringBuilder();//查询单个语句
        StringBuilder selectAll = new StringBuilder();//查询所有的语句
        StringBuilder selectCount = new StringBuilder();//查询统计语句


        //删除语句
        update.append("         UPDATE ").append(tableName).append(" SET \n");

        StringBuilder proBuilder = new StringBuilder();
        StringBuilder selectSql = new StringBuilder();
        StringBuilder wherePRI = new StringBuilder();
        proBuilder.setCharAt(proBuilder.lastIndexOf(","), ' ');
        selectSql.setCharAt(selectSql.lastIndexOf(","), ' ');

        selectSql.append("          FROM ").append(tableName);
        selectAll.append(selectSql).append("\n")
                .append("          LIMIT #{start},#{end}\n         ");
//        if (priColumn != null) {
//            selectOne.append(selectSql).append(wherePRI);
//            update.setCharAt(update.lastIndexOf(","), ' ');
//            update.append(wherePRI);
//            //查询统计语句
//            selectCount.append("         SELECT COUNT(")
//                    .append(priColumn.getColumnName().toUpperCase()).append(")\n         FROM ")
//                    .append(tableName).append("\n");
//        } else {
//            selectOne = new StringBuilder("");
//            update = new StringBuilder("");
//            delete = new StringBuilder("");
//            //查询统计语句
//            selectCount.append("         SELECT COUNT(*) \n            FROM ")
//                    .append(tableName).append("\n");
//        }


        sql.setInsertSql(insert);
        sql.setDeleteSql(delete.toString());
        sql.setSelectAllSql(selectAll.toString());
        sql.setSelectSql(selectOne.toString());
        sql.setUpdateSql(update.toString());
        sql.setCountQuerySql(selectCount.toString());
        return sql;
    }

    @Override
    protected String pageSql() {
        return null;
    }

    //todo
    @Override
    protected String getInsertPrimaryKey() {
        return null;
    }
}
