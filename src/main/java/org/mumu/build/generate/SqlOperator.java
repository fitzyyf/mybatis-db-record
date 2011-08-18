/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:SqlOperator.java  11-8-16 上午10:45 poplar.mumu ]
 */
package org.mumu.build.generate;

import org.mumu.build.model.ColumnInfo;
import org.mumu.build.model.SqlModal;
import org.mumu.build.model.TableInfo;
import org.mumu.build.util.StringUtil;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-8-16 上午10:45
 * @since JDK 1.0
 */
public class SqlOperator {

    /**
     * 创建SQL脚本.
     *
     * @param tableInfo  表格属性
     * @return sql语句
     */
    @SuppressWarnings({"ConstantConditions"})
    public static SqlModal createSql(TableInfo tableInfo) {
        SqlModal sql = new SqlModal();
        StringBuilder insert = new StringBuilder();//insert语句
        StringBuilder update = new StringBuilder();//update语句
        StringBuilder delete = new StringBuilder();//delete语句
        StringBuilder selectOne = new StringBuilder();//查询单个语句
        StringBuilder selectAll = new StringBuilder();//查询所有的语句
        StringBuilder selectCount = new StringBuilder();//查询统计语句
        String tableName = tableInfo.getTableName().toUpperCase();

        //开始创建各个语句的Mapper接口对应的方法*//
        //结束创建mapper方法
        //插入SQL的Mybatis配置语句
        insert.append("      INSERT INTO ").append(tableName);
        insert.append("(\n");

        //删除语句
        update.append("         UPDATE ").append(tableName).append(" SET \n");

        StringBuilder proBuilder = new StringBuilder();
        StringBuilder selectSql = new StringBuilder();
        StringBuilder wherePRI = new StringBuilder();
        ColumnInfo priColumn =
                SqlOperator.createSql(tableInfo, insert, proBuilder, selectSql, update, delete, wherePRI);
        insert.setCharAt(insert.lastIndexOf(","), ' ');
        proBuilder.setCharAt(proBuilder.lastIndexOf(","), ' ');
        selectSql.setCharAt(selectSql.lastIndexOf(","), ' ');

        selectSql.append("          FROM ").append(tableName);
        selectAll.append(selectSql).append("\n")
                .append("          LIMIT #{start},#{end}\n         ");
        if (priColumn != null) {
            selectOne.append(selectSql).append(wherePRI);
            update.setCharAt(update.lastIndexOf(","), ' ');
            update.append(wherePRI);
            //查询统计语句
            selectCount.append("         SELECT COUNT(")
                    .append(priColumn.getColumnName().toUpperCase()).append(")\n         FROM ")
                    .append(tableName).append("\n");
        } else {
            selectOne = new StringBuilder("");
            update = new StringBuilder("");
            delete = new StringBuilder("");
            //查询统计语句
            selectCount.append("         SELECT COUNT(*) \n            FROM ")
                    .append(tableName).append("\n");
        }

        insert.append("         ) VALUES (\n");
        insert.append(proBuilder);
        insert.append("         )\n");

        sql.setInsertSql(insert.toString());
        sql.setDeleteSql(delete.toString());
        sql.setSelectAllSql(selectAll.toString());
        sql.setSelectSql(selectOne.toString());
        sql.setUpdateSql(update.toString());
        sql.setCountQuerySql(selectCount.toString());
        return sql;
    }

    /**
     * 创建SQL。
     *
     * @param tableInfo  数据库表的信息
     * @param insert     插入脚本
     * @param proBuilder WEHERE条件的mybatis的信息
     * @param selectSql  查询全部
     * @param update     update语句
     * @param delete     delete语句
     * @param wherePRI   WHERE信息
     * @return 返回主键对象
     */
    private static ColumnInfo createSql(TableInfo tableInfo, StringBuilder insert, StringBuilder proBuilder,
                                        StringBuilder selectSql, StringBuilder update, StringBuilder delete, StringBuilder wherePRI) {
        String clsPro;//类属性名称
        String columnName;
        ColumnInfo priColumn = null;
        for (ColumnInfo columnInfo : tableInfo.getColumnList()) {
            clsPro = StringUtil.columnToPropertis(columnInfo.getColumnName());
            columnName = columnInfo.getColumnName().toUpperCase();
            if (columnInfo.isPri()) {//主键
                priColumn = columnInfo;//当前为主键
                priColumn.setColumnName(columnName);
                //查询语句
                selectSql.append("          ").append(columnName).append(",\n");

                delete.append(columnInfo.getDataType()).append("\">\n");
                delete.append("         DELETE FROM ").append(tableInfo.getTableName()).append(" WHERE ").append(columnName)
                        .append(" = #{").append(clsPro).append("}\n");
                wherePRI.append("         WHERE ").append(columnName)
                        .append("=#{").append(clsPro).append("}\n");
            } else {
                proBuilder.append("         #{").append(clsPro).append("},\n");

                insert.append("          ").append(columnName).append(",\n");
                //查询语句
                selectSql.append("          ").append(columnName).append(",\n");
                //update语句
                update.append("          ").append(columnName).
                        append("=#{").append(clsPro).append("},\n");

            }
        }
        return priColumn;
    }
}
