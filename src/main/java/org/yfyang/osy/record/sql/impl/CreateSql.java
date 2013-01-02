package org.yfyang.osy.record.sql.impl;

import org.yfyang.osy.record.common.Constants;
import org.yfyang.osy.record.model.ColumnInfo;
import org.yfyang.osy.record.model.MybatisSqlModal;
import org.yfyang.osy.record.model.TableInfo;
import org.yfyang.osy.record.util.StringUtil;

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
@SuppressWarnings({"UnusedDeclaration"})
public abstract class CreateSql {
    /**
     * 产生各种sql
     *
     * @param tableInfo 数据库表信息
     * @return 返回INSERT、UPDATE
     */
    protected static MybatisSqlModal gengerateSql(TableInfo tableInfo) {
        MybatisSqlModal mybatisSqlModal = new MybatisSqlModal();
        String clsPro, columnName;//类属性名称
        StringBuilder selectColumn = new StringBuilder();
        StringBuilder insertColumns = new StringBuilder();//insert语句mybatis的标记
        StringBuilder updateColumns = new StringBuilder();//update语句mybatis的标记
        StringBuilder insertMybatis = new StringBuilder();
        List<String> primaryKeys = new ArrayList<String>();
        for (ColumnInfo column : tableInfo.getColumnList()) {
            clsPro = StringUtil.columnToProperty(column.getColumnName());
            columnName = column.getColumnName().toUpperCase();
            selectColumn.append(Constants.TWOBLANK_SIGN)
                    .append(columnName)
                    .append(",")
                    .append(Constants.WRAP_CHAR);
            if (column.isPri()) {
                primaryKeys.add(column.getColumnName());
            } else {
                insertColumns.append(Constants.TWOBLANK_SIGN)
                        .append(columnName)
                        .append(",")
                        .append(Constants.WRAP_CHAR);
                insertMybatis.append(Constants.TWOBLANK_SIGN)
                        .append(" #{")
                        .append(clsPro)
                        .append("},")
                        .append(Constants.WRAP_CHAR);
                updateColumns.append(Constants.TWOBLANK_SIGN)
                        .append(columnName).
                        append("=#{")
                        .append(clsPro)
                        .append("},")
                        .append(Constants.WRAP_CHAR);
            }
        }
        mybatisSqlModal.setPrimaryKeys(primaryKeys);
        mybatisSqlModal.setInsertColumn(StringUtil.deleteLastChar(insertColumns, ","));
        mybatisSqlModal.setSelectColumn(StringUtil.deleteLastChar(selectColumn, ","));
        mybatisSqlModal.setUpdateColumn(StringUtil.deleteLastChar(updateColumns, ","));
        mybatisSqlModal.setInsertMybatis(StringUtil.deleteLastChar(insertMybatis, ","));
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
        insertSql.append("INSERT INTO ")
                .append(tableName)
                .append("(")
                .append(Constants.WRAP_CHAR)
                .append(mybatisSqlModal.getInsertColumn())
                .append(Constants.WRAP_CHAR)
                .append(Constants.BLANK_SIGN)
                .append("VALUES (")
                .append(Constants.WRAP_CHAR)
                .append(mybatisSqlModal.getInsertMybatis())
                .append(Constants.WRAP_CHAR)
                .append(Constants.BLANK_SIGN)
                .append(")");
        return insertSql.toString();
    }

    /**
     * 根据分析后的mybatis的sql信息和表名产生更给信息的update脚本
     *
     * @param mybatisSqlModal mybatis sql信息
     * @param tableName       数据库表名
     * @return update脚本
     */
    protected static String generateUpdateSql(MybatisSqlModal mybatisSqlModal, String tableName) {
        StringBuilder updateSql = new StringBuilder();
        updateSql.append("UPDATE ")
                .append(tableName)
                .append(Constants.WRAP_CHAR)
                .append(Constants.BLANK_SIGN)
                .append("SET")
                .append(Constants.WRAP_CHAR)
                .append(mybatisSqlModal.getUpdateColumn())
                .append(Constants.WRAP_CHAR)
                .append(Constants.BLANK_SIGN)
                .append(Constants.WHERE_KEY)
                .append(Constants.WRAP_CHAR);
        for (String primary : mybatisSqlModal.getPrimaryKeys()) {
            updateSql.append(Constants.TWOBLANK_SIGN)
                    .append(primary.toUpperCase())
                    .append("=#{")
                    .append(StringUtil.columnToProperty(primary))
                    .append("}")
                    .append(Constants.WRAP_CHAR)
                    .append(Constants.BLANK_SIGN)
                    .append(Constants.AND_SIGN);
        }
        return StringUtil.deleteLastChar(updateSql, Constants.AND_SIGN);
    }


    /**
     * 查询根据主键查询单个信息的脚本
     *
     * @param mybatisSqlModal 分析后的mybatis的sql信息
     * @param tableName       数据库表名
     * @return 查询单个信息的脚本
     */
    protected static String generateSelectOneSql(MybatisSqlModal mybatisSqlModal, String tableName) {
        StringBuilder selectOneSql = new StringBuilder();
        selectOneSql.append(Constants.SELECT_KEY)
                .append(Constants.WRAP_CHAR)
                .append(mybatisSqlModal.getSelectColumn())
                .append(Constants.WRAP_CHAR)
                .append(Constants.BLANK_SIGN)
                .append(Constants.FROM_KEY)
                .append(tableName)
                .append(Constants.WRAP_CHAR)
                .append(Constants.BLANK_SIGN)
                .append(Constants.WHERE_KEY)
                .append(Constants.WRAP_CHAR);
        for (String primary : mybatisSqlModal.getPrimaryKeys()) {
            selectOneSql.append(Constants.TWOBLANK_SIGN)
                    .append(primary.toUpperCase())
                    .append("=#{")
                    .append(StringUtil.columnToProperty(primary))
                    .append("}")
                    .append(Constants.WRAP_CHAR)
                    .append(Constants.BLANK_SIGN)
                    .append(Constants.AND_SIGN);
        }
        return StringUtil.deleteLastChar(selectOneSql, Constants.AND_SIGN);
    }

    /**
     * 根据分析得到的主键信息分析创建求总纪录的脚本。
     *
     * @param mybatisSqlModal 分析数据库列的信息
     * @param tableName       数据库表名
     * @return 求总记录的sql脚本
     */
    protected static String generateCountSql(MybatisSqlModal mybatisSqlModal, String tableName) {
        List<String> primarys = mybatisSqlModal.getPrimaryKeys();
        StringBuilder countSql = new StringBuilder();
        countSql.append(Constants.SELECT_KEY);
        if (primarys.size() > 0) {
            countSql.append("COUNT(")
                    .append(primarys.get(0))
                    .append(")");
        } else {
            countSql.append("COUNT(*)");
        }
        countSql.append(Constants.WRAP_CHAR)
                .append(Constants.BLANK_SIGN)
                .append(Constants.FROM_KEY)
                .append(tableName);
        return countSql.toString();
    }

    /**
     * 根据分析后的mybatis sql列信息，和表名，产生delete脚本
     *
     * @param mybatisSqlModal 分析后的mybatis的sql列信息
     * @param tableName       数据库表名
     * @return delete脚本
     */
    protected static String generateDeleteSql(MybatisSqlModal mybatisSqlModal, String tableName) {
        StringBuilder deleteSql = new StringBuilder();
        deleteSql.append(Constants.DELETE_KEY)
                .append(tableName)
                .append(Constants.WRAP_CHAR);
        for (String primary : mybatisSqlModal.getPrimaryKeys()) {
            deleteSql.append(Constants.TWOBLANK_SIGN)
                    .append(primary.toUpperCase()).append("=#{")
                    .append(StringUtil.columnToProperty(primary))
                    .append("}")
                    .append(Constants.WRAP_CHAR)
                    .append(Constants.BLANK_SIGN)
                    .append(Constants.AND_SIGN);
        }
        return StringUtil.deleteLastChar(deleteSql, Constants.AND_SIGN);
    }

    /**
     * 生成分页sql的Where后信息
     *
     * @return 分页sql的Where
     *         <p>例如：mysql的生成：limit start and end</p>
     */
    protected abstract String pageParamSql();

    /**
     * 产生获取刚写入数据库信息表的主键值的相关sql。
     * 暂时不实现
     *
     * @return 写入获取主键相关sql
     */
    protected abstract String getInsertPrimaryKey();


    /**
     * 产生查询语句。与分页查询有关，所以，在这里需要每个数据库的实现都要重写改方法
     *
     * @param mybatisSqlModal 分析后的mybatis的sql语句
     * @param tableName       数据库表名
     * @return 查询脚本
     */
    protected abstract String pageSelectSql(MybatisSqlModal mybatisSqlModal, String tableName);
}
