package org.mumu.build.db;

import org.mumu.build.db.impl.TableInfoDao;
import org.mumu.build.db.impl.TableInfoFactory;
import org.mumu.build.model.TableInfo;

import java.util.List;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 6:36 下午
 * @since JDK 1.0
 */
public enum TableInfoClient implements ITableInfoDao {
    instance;

    @Override
    public List<TableInfo> showTables() {
        TableInfoDao infoDao = TableInfoFactory.builderTableInfoDao(TableInfoDao.DB_POOL.getDbms());
        return infoDao.showTables();
    }

    @Override
    public TableInfo showColumns(String tableName) {
        TableInfoDao infoDao = TableInfoFactory.builderTableInfoDao(TableInfoDao.DB_POOL.getDbms());
        return infoDao.showColumns(tableName);
    }


}
