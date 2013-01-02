/*
 * Copyright (c) 2010-2011 GOV.
 * [Id:Db2CreateSql.java  8/24/11 4:43 PM poplar.mumu ]
 */
package org.yfyang.osy.record.sql.impl;

import org.yfyang.osy.record.model.MybatisSqlModal;
import org.yfyang.osy.record.model.SqlModal;
import org.yfyang.osy.record.model.TableInfo;
import org.yfyang.osy.record.sql.CreateSqlFace;

/**
 * DB2的创建SQL脚本的信息.
 * <br/>
 *
 * @author poplar.mumu
 * @version 1.0 8/24/11 4:43 PM
 * @since JDK 1.5
 */
public class Db2CreateSql extends CreateSql implements CreateSqlFace {
    private Db2CreateSql() {
    }

    private static class Inner {
        private static CreateSqlFace _db2 = new Db2CreateSql();
    }

    public static CreateSqlFace getInstance() {
        return Inner._db2;
    }
    @Override
    protected String pageParamSql() {
        return null;
    }

    @Override
    protected String getInsertPrimaryKey() {
        return null;
    }

    @Override
    protected String pageSelectSql(MybatisSqlModal mybatisSqlModal, String tableName) {
        return null;
    }

    @Override
    public SqlModal createSql(TableInfo tableInfo) {
        return null;
    }
}
