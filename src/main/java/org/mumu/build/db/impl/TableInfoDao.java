package org.mumu.build.db.impl;

import org.mumu.build.db.pool.ConnectionPool;
import org.mumu.build.model.TableInfo;
import org.mumu.build.util.ResourceUtils;

import java.util.List;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 6:24 下午
 * @since JDK 1.0
 */
public abstract class TableInfoDao {
    /**
     * 数据库连接池
     */
    public static final ConnectionPool DB_POOL = new ConnectionPool(ResourceUtils.getInstance().getJdbc());


    /**
     * 查询当前连接的库中，包含哪些表的信息。
     *
     * @return 数据库中的表 <br/>
     *         如果返回<code>null</code>表示查询失败
     */
    public abstract List<TableInfo> showTables();

    /**
     * 查询给定表的所有的字段信息.
     *
     * @param tableName 数据库表名称
     * @return 给定的表的所有字段属性 <br/>
     *         如果范围<code>null</code>表示查询失败
     */
    public abstract TableInfo showColumns(String tableName);



}
