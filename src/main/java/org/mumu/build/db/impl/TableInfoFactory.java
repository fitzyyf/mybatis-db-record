package org.mumu.build.db.impl;

import org.mumu.build.db.DBMS;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 6:30 下午
 * @since JDK 1.0
 */
public class TableInfoFactory {
    private TableInfoFactory() {

    }

    public static TableInfoDao builderTableInfoDao(DBMS dbms) {
        switch (dbms) {
            case MYSQL:
                return Inner.MYSQLI_TABLE_INFO_DAO;
            case ORACLE:
                break;
        }
        return null;
    }


    private static final class Inner {
        private static final TableInfoDao MYSQLI_TABLE_INFO_DAO = new MysqlTableInfoDao();
    }
}
