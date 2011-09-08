package org.mumu.build.db;

import org.mumu.build.db.impl.TableInfoDao;
import org.mumu.build.db.impl.TableInfoFactory;
import org.mumu.build.model.TableInfo;

import java.util.List;

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

    @Override
    public List<TableInfo> showTables() {
        TableInfoDao infoDao = TableInfoFactory
                .builderTableInfoDao(TableInfoDao.DB_POOL.getDbms());
        return infoDao.showTables();
    }

    @Override
    public TableInfo showColumns(String tableName) {
        TableInfoDao infoDao = TableInfoFactory
                .builderTableInfoDao(TableInfoDao.DB_POOL.getDbms());
        return infoDao.showColumns(tableName);
    }


}
