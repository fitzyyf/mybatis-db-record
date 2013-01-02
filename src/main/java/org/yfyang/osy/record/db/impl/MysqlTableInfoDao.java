package org.yfyang.osy.record.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.yfyang.osy.record.common.Constants;
import org.yfyang.osy.record.model.ColumnInfo;
import org.yfyang.osy.record.model.TableInfo;

/**
 * Mysql查询数据库系统表以及字段信息的生产者.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 6:21 下午
 * @since JDK 1.5
 */
public class MysqlTableInfoDao extends TableInfoDao {
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
		tableInfo.setColumnList(getColumns(connection, table, AppInstance.DB_POOL.getScheme(), preparedStatement));
		return tableInfo;
    }

    /**
     * 取得数据库某个表的信息。
     *
     * @param tableName  数据库表名
     * @param connection 数据库链接对象
     * @param pstm       数据库预编译对象
     * @return 某个表的具体信息
     * @throws SQLException 数据库异常
     */
    private static TableInfo showTable(String tableName, final Connection connection, PreparedStatement pstm) throws SQLException {
        String mysql_table_sql = "SELECT TABLE_NAME,TABLE_COMMENT,CREATE_TIME,TABLE_ROWS FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME=? ";
        pstm = connection.prepareStatement(mysql_table_sql);
		pstm.setString(1, AppInstance.DB_POOL.getScheme());
		pstm.setString(2, tableName);
        ResultSet rst = pstm.executeQuery();
        TableInfo info = null;
        while (rst.next()) {
            info = new TableInfo();
            info.setTableName(tableName);
            info.setTableComment(rst.getString(2));
            info.setCreateTime(rst.getString(3));
            info.setRows(rst.getInt(4));
			info.setColumnList(getColumns(connection, tableName, AppInstance.DB_POOL.getScheme(), pstm));
		}
        return info;
    }

    /**
     * 查询给定表的所有的字段信息.
     *
     * @param connection        数据库连接
     * @param tableName         数据库表名称
     * @param schema            数据库架构
     * @param preparedStatement 数据库预编译对象
     * @return 给定的表的所有字段属性 <br/>
     *         如果范围<code>null</code>表示查询失败
     * @throws java.sql.SQLException 数据库异常
     */
    private static List<ColumnInfo> getColumns(final Connection connection, String tableName, String schema,
                                               PreparedStatement preparedStatement) throws SQLException {
        String mysql_column_sql =
                "SELECT TABLE_NAME,COLUMN_NAME,IS_NULLABLE,DATA_TYPE,COLUMN_TYPE,COLUMN_KEY,COLUMN_COMMENT FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? AND TABLE_SCHEMA=?";
        preparedStatement = connection.prepareStatement(mysql_column_sql);
        preparedStatement.setString(1, tableName);
        preparedStatement.setString(2,schema);
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
			info.setDataType(String.valueOf(Constants.DATA_TYPE_JDBC.get(dataType.toLowerCase())));
			String columnKey = rst.getString(6);
            info.setColumnKey(columnKey);
            info.setFok(Constants.MUI_KEY.equals(columnKey));
            info.setPri(Constants.PRIMARY_KEY.equals(columnKey));
            info.setColumnComment(rst.getString(7));
            tableList.add(info);
        }
        return tableList;
	}

	@Override
	public List<TableInfo> showTables() {
		Connection connection = AppInstance.DB_POOL.getConnection();
		String mysql_table_sql = "SELECT TABLE_NAME,TABLE_COMMENT,CREATE_TIME,TABLE_ROWS" +
				" FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ?";
		ResultSet rst = null;
		PreparedStatement preparedStatement = null;
		try {
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement(mysql_table_sql);
			preparedStatement.setString(1, AppInstance.DB_POOL.getScheme());
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
				info.setColumnList(getColumns(connection, tableName, AppInstance.DB_POOL.getScheme(), preparedStatement));
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
				AppInstance.DB_POOL.returnConnection(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public TableInfo showColumns(String tableName) {
		Connection connection = AppInstance.DB_POOL.getConnection();
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
			AppInstance.DB_POOL.returnConnection(connection);
		}
		return null;
	}
}
