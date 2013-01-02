package org.yfyang.osy.record.db;

import java.util.List;

import org.yfyang.osy.record.db.impl.TableInfoDao;
import org.yfyang.osy.record.db.impl.TableInfoFactory;
import org.yfyang.osy.record.model.TableInfo;

/**
 * 数据库调用对象单例，获取各种数据库类型参数获取具体的表以及列的信息.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 6:36 下午
 * @since JDK 1.5
 */
public enum TableInfoClient implements ITableInfoDao {
    instance;

	/**
	 * 根据当前链接的数据库类型读取数据库信息，取得当前连接的所有的表以及表字段的信息。
	 * @return 当前连接的所有的表以及表字段的信息。
	 */
	public List<TableInfo> showTables() {
		TableInfoDao infoDao = TableInfoFactory.builderTableInfoDao(TableInfoDao.AppInstance.DB_POOL.getDbms());
		return infoDao.showTables();
    }

	/**
	 * 根据给定的数据库表名，取得对应的数据库表和表字段的信息。
	 * @param tableName 数据库表名称
	 * @return 表的信息以及表的字段信息。
	 */
	public TableInfo showColumns(String tableName) {
        TableInfoDao infoDao = TableInfoFactory
				.builderTableInfoDao(TableInfoDao.AppInstance.DB_POOL.getDbms());
		return infoDao.showColumns(tableName);
    }


}
