package org.yfyang.osy.record.db.impl;

import org.yfyang.osy.record.db.DBMS;

/**
 * 对外工厂类，内部产生具体的生产DAO.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 6:30 下午
 * @since JDK 1.5
 */
public class TableInfoFactory {
    private TableInfoFactory() {

    }

    /**
     * 根据数据库类型产生对应的工厂类.
     * @param dbms 数据库类型
     * @return 数据库生成操作的生产类。
     */
    public static TableInfoDao builderTableInfoDao(DBMS dbms) {
        switch (dbms) {
            case MYSQL:
                return Inner.MYSQLI_TABLE_INFO_DAO;
            case ORACLE:
                break;
        }
        return null;
    }

    /**
     * 内部单利类
     */
    private static  class Inner {
        private static  TableInfoDao MYSQLI_TABLE_INFO_DAO = new MysqlTableInfoDao();
    }
}
