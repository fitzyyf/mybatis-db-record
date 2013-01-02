package org.yfyang.osy.record.db;

import org.yfyang.osy.record.model.TableInfo;

import java.util.List;

/**
 * 数据库信息查询DAO接口.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 6:20 下午
 * @since JDK 1.0
 */
public interface ITableInfoDao {
    /**
     * 查询当前连接的库中，包含哪些表的信息。
     *
     * @return 数据库中的表 <br/>
     *         如果返回<code>null</code>表示查询失败
     */
    public List<TableInfo> showTables();

    /**
     * 查询给定表的所有的字段信息.
     *
     * @param tableName 数据库表名称
     * @return 给定的表的所有字段属性 <br/>
     *         如果范围<code>null</code>表示查询失败
     */
    public TableInfo showColumns(String tableName);
}
