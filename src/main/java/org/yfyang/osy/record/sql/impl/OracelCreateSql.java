package org.yfyang.osy.record.sql.impl;

import org.yfyang.osy.record.model.MybatisSqlModal;
import org.yfyang.osy.record.model.SqlModal;
import org.yfyang.osy.record.model.TableInfo;
import org.yfyang.osy.record.sql.CreateSqlFace;

/**
 * Oracle数据库创建脚本的抽象工厂实现.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 1:32 下午
 * @since JDK 1.0
 */
public class OracelCreateSql extends CreateSql implements CreateSqlFace {

    private OracelCreateSql() {
    }

    private static class Inner {
        private static CreateSqlFace _oracle = new OracelCreateSql();
    }

    public static CreateSqlFace getInstance() {
        return Inner._oracle;
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
    public SqlModal createSql(TableInfo tableInfo) {
        return null;
    }

    @Override
    protected String pageSelectSql(MybatisSqlModal mybatisSqlModal, String tableName) {
        return null;
    }
}
