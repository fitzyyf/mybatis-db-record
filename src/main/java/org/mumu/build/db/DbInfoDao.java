/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:DbInfoDao.java  11-6-8 上午12:09 poplar.mumu ]
 */
package org.mumu.build.db;

import org.mumu.build.common.Constants;
import org.mumu.build.model.ColumnInfo;
import org.mumu.build.model.TableInfo;
import org.mumu.build.util.ResourceUtils;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库查询系统信息的DAO单例.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 上午12:09
 * @since JDK 1.0
 */
public class DbInfoDao implements Serializable {
    private static final long serialVersionUID = -8330342089583159920L;
    /**
     * 数据库连接池
     */
    private static ConnectionPool _dbPool = new ConnectionPool(ResourceUtils.getInstance().getJdbc());

    /**
     * 私有
     */
    private DbInfoDao() {
    }

    private static TableInfo showTable(String tableName, final Connection connection, PreparedStatement pstm) throws SQLException {
        String mysql_table_sql = "SELECT TABLE_NAME,TABLE_COMMENT,CREATE_TIME,TABLE_ROWS FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME=? ";
        pstm = connection.prepareStatement(mysql_table_sql);
        pstm.setString(1, _dbPool.getScheme());
        pstm.setString(2, tableName);
        ResultSet rst = pstm.executeQuery();
        TableInfo info = null;
        while (rst.next()) {
            info = new TableInfo();
            info.setTableName(tableName);
            info.setTableComment(rst.getString(2));
            info.setCreateTime(rst.getString(3));
            info.setRows(rst.getInt(4));
            info.setColumnList(getColumns(connection, tableName, pstm));
        }
        return info;
    }

    /**
     * 查询当前连接的库中，包含哪些表的信息。
     *
     * @return 数据库中的表 <br/>
     *         如果返回<code>null</code>表示查询失败
     */
    public List<TableInfo> showTables() {
        Connection connection = _dbPool.getConnection();
        String mysql_table_sql = "SELECT TABLE_NAME,TABLE_COMMENT,CREATE_TIME,TABLE_ROWS" +
                " FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ?";
        ResultSet rst = null;
        PreparedStatement preparedStatement = null;
        try {
            connection.setAutoCommit(false);
            preparedStatement = connection.prepareStatement(mysql_table_sql);
            preparedStatement.setString(1, _dbPool.getScheme());
            rst = preparedStatement.executeQuery();
            List<TableInfo> tableList = new ArrayList<TableInfo>();
            TableInfo info;
            String tableName;
            while (rst.next()) {
                info = new TableInfo();
                tableName = rst.getString(1);
                info.setTableName(tableName);
                info.setTableComment(rst.getString(2));
                info.setCreateTime(rst.getString(3));
                info.setRows(rst.getInt(4));
                info.setColumnList(getColumns(connection, tableName, preparedStatement));
                tableList.add(info);
            }
            return tableList;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (rst != null) {
                    rst.close();
                }
                connection.rollback();
                _dbPool.returnConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 查询给定表的所有的字段信息.
     *
     * @param conn      数据库连接
     * @param tableName 数据库表名称
     * @return 给定的表的所有字段属性 <br/>
     *         如果范围<code>null</code>表示查询失败
     * @throws java.sql.SQLException 数据库异常
     */
    private static TableInfo showColumns(final Connection conn, String tableName) throws SQLException {
        return getTableColumns(conn, tableName);
    }

    /**
     * 查询给定表的所有的字段信息.
     *
     * @param tableName 数据库表名称
     * @return 给定的表的所有字段属性 <br/>
     *         如果范围<code>null</code>表示查询失败
     */
    public TableInfo showColumns(String tableName) {
        Connection connection = _dbPool.getConnection();
        try {
            connection.setAutoCommit(false);
            return getTableColumns(connection, tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            _dbPool.returnConnection(connection);
        }
        return null;
    }

    /**
     * 查询给定表的所有的字段信息.
     *
     * @param connection 数据库连接
     * @param table      数据库表名称
     * @return 给定的表的所有字段属性 <br/>
     *         如果范围<code>null</code>表示查询失败
     * @throws java.sql.SQLException 数据库异常
     */
    private static TableInfo getTableColumns(final Connection connection, String table) throws SQLException {
        PreparedStatement preparedStatement = null;
        TableInfo tableInfo = showTable(table, connection, preparedStatement);
        tableInfo.setColumnList(getColumns(connection, table, preparedStatement));
        return tableInfo;
    }

    private static List<ColumnInfo> getColumns(final Connection connection, String tableName,
                                               PreparedStatement preparedStatement) throws SQLException {
        String mysql_column_sql = "SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,COLUMN_TYPE,COLUMN_KEY,COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
        preparedStatement = connection.prepareStatement(mysql_column_sql);
        preparedStatement.setString(1, tableName);
        ResultSet rst = preparedStatement.executeQuery();
        List<ColumnInfo> tableList = new ArrayList<ColumnInfo>();
        ColumnInfo info;
        String dataType;
        while (rst.next()) {
            info = new ColumnInfo();
            info.setTableName(tableName);
            info.setColumnName(rst.getString(2));
            info.setNullAble(rst.getBoolean(3));
            info.setDataType(rst.getString(4));
            dataType = rst.getString(4);
            info.setDataType(Constants.DATA_TYPE_MAP.get(dataType.toLowerCase()));
            String columnKey = rst.getString(6);
            info.setColumnKey(columnKey);
            info.setFok(Constants.MUI_KEY.equals(columnKey));
            info.setPri(Constants.PRIMARY_KEY.equals(columnKey));
            info.setColumnComment(rst.getString(7));
            tableList.add(info);
        }
        return tableList;
    }

    /**
     * 单例获取数据库连接操作属性。
     *
     * @return 表信息数据库操作对象
     */
    public static DbInfoDao getInstance() {
        return Single.DB_INFO_DAO;
    }

    private abstract interface Single {
        static final DbInfoDao DB_INFO_DAO = new DbInfoDao();
    }
}
