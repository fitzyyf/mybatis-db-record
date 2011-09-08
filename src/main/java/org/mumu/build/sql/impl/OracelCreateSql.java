package org.mumu.build.sql.impl;

import org.mumu.build.model.MybatisSqlModal;
import org.mumu.build.model.SqlModal;
import org.mumu.build.model.TableInfo;
import org.mumu.build.sql.ICreateSqlFace;

/**
 * Oracle数据库创建脚本的抽象工厂实现.
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
        private static  ICreateSqlFace _oracle = new OracelCreateSql();
    }

    public static ICreateSqlFace getInstance() {
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
