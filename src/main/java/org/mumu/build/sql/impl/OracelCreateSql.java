package org.mumu.build.sql.impl;

import org.mumu.build.model.SqlModal;
import org.mumu.build.model.TableInfo;
import org.mumu.build.sql.ICreateSqlFace;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 1:32 下午
 * @since JDK 1.0
 */
public class OracelCreateSql extends CreateSql implements ICreateSqlFace {

    private OracelCreateSql() {
    }

    private static class Inner {
        private static final ICreateSqlFace CREATE_SQL_FACE = new OracelCreateSql();
    }

    public static ICreateSqlFace getInstance() {
        return Inner.CREATE_SQL_FACE;
    }

    @Override
    protected String pageSql() {
        return null;
    }

    @Override
    protected String getInsertPrimaryKey() {
        return null;
    }

    @Override
    public SqlModal createSql(TableInfo tableInfo) {
        return null;
    }
}
