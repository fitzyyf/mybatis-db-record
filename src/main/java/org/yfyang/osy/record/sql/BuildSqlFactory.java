package org.yfyang.osy.record.sql;

import org.yfyang.osy.record.db.DBMS;
import org.yfyang.osy.record.sql.impl.Db2CreateSql;
import org.yfyang.osy.record.sql.impl.MysqlCreateSql;
import org.yfyang.osy.record.sql.impl.OracelCreateSql;

/**
 * 数据库创建sql脚本控制器.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 5:57 下午
 * @since JDK 1.0
 */
public class BuildSqlFactory {
    private BuildSqlFactory() {
    }

    /**
     * 根据数据库类型取得对应产生sql脚本的实现
     *
     * @param dbms 数据库类型
     * @return 数据库对应类型的sql脚本
     */
    public static CreateSqlFace getDBMSBuilderSql(DBMS dbms) {
        switch (dbms) {
            case MSSQL:
                break;
            case MYSQL:
                return MysqlCreateSql.getInstance();
            case ORACLE:
                return OracelCreateSql.getInstance();
            case DB2:
                return Db2CreateSql.getInstance();
            default:
                break;
        }
        return null;
    }
}
