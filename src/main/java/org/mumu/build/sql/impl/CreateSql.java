package org.mumu.build.sql.impl;

import org.mumu.build.model.ColumnInfo;
import org.mumu.build.model.MybatisSqlModal;
import org.mumu.build.model.TableInfo;
import org.mumu.build.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 产生SQL的基本实现类，基本sql.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 1:08 下午
 * @since JDK 1.0
 */
public abstract class CreateSql {
    /**
     * 产生各种sql
     *
     * @return 返回INSERT、UPDATE
     */
    protected static MybatisSqlModal gengerateSql(TableInfo tableInfo) {
        MybatisSqlModal mybatisSqlModal = new MybatisSqlModal();
        String clsPro;//类属性名称
        String columnName;
        StringBuilder selectColumn = new StringBuilder();
        StringBuilder insertColumns = new StringBuilder();//insert语句mybatis的标记
        StringBuilder updateColumns = new StringBuilder();//update语句mybatis的标记
        StringBuilder insertMybatis = new StringBuilder();
        List<String> primaryKeys = new ArrayList<String>();
        for (ColumnInfo column : tableInfo.getColumnList()) {
            clsPro = StringUtil.columnToPropertis(column.getColumnName());
            columnName = column.getColumnName().toUpperCase();
            selectColumn.append("          ").append(columnName).append(",\n");
            if (column.isPri()) {
                primaryKeys.add(column.getColumnName());
            } else {
                insertColumns.append("            ").append(columnName).append(",\n");
                insertMybatis.append("         #{").append(clsPro).append("},\n");
                updateColumns.append("          ").append(columnName).
                        append("=#{").append(clsPro).append("},\n");
            }
        }
        mybatisSqlModal.setPrimaryKeys(primaryKeys);
        mybatisSqlModal.setInsertColumn(insertColumns.toString());
        mybatisSqlModal.setSelectColumn(selectColumn.toString());
        mybatisSqlModal.setUpdateColumn(updateColumns.toString());
        mybatisSqlModal.setInsertMybatis(insertMybatis.toString());
        return mybatisSqlModal;
    }

    /**
     * 根据分析表信息后生成的mybatis想对应的sql信息和表名产生insert的mybtais的insert脚本。
     *
     * @param mybatisSqlModal mybatis信息
     * @param tableName       数据库表名
     * @return insert脚本
     */
    protected static String generateInsertSql(MybatisSqlModal mybatisSqlModal, String tableName) {
        StringBuilder insertSql = new StringBuilder();
        insertSql.append("      INSERT INTO ").append(tableName).append("(\n");
        insertSql.append(mybatisSqlModal.getInsertColumn());//todo 逗号处理
        insertSql.append("          VALUES (\n")
                .append(mybatisSqlModal.getInsertMybatis())
                .append("          )\n");
        return insertSql.toString();
    }

    protected abstract String pageSql();

    /**
     * 产生获取刚写入数据库信息表的主键值的相关sql。
     * 暂时不实现
     * @return 写入获取主键相关sql
     */
    protected abstract  String getInsertPrimaryKey();
}
