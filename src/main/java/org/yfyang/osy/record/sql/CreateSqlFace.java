package org.yfyang.osy.record.sql;

import org.yfyang.osy.record.model.SqlModal;
import org.yfyang.osy.record.model.TableInfo;

/**
 * 创建SQL脚本的接口.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 1:06 下午
 * @since JDK 1.0
 */
public interface CreateSqlFace {

    /**
     * 创建SQL脚本
     * @param tableInfo 数据库表信息。
     * @return SQL对象信息
     */
    SqlModal createSql(TableInfo tableInfo);
}
