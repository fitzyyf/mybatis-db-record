/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:MapperXmlUtil.java  11-6-11 下午7:49 poplar.mumu ]
 */
package com.nutshell.ntztool.generate.mybatis;

import com.nutshell.ntztool.model.ColumnInfo;
import com.nutshell.ntztool.model.TableInfo;
import com.nutshell.ntztool.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建Mybatis的Mapper xml中的各个脚本.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-11 下午7:49
 * @since JDK 1.0
 */
public class MapperXmlUtil {
    /**
     * 主键列,可能存在多主键
     */
    private List<ColumnInfo> priColumn = new ArrayList<ColumnInfo>();
    /**
     * 查询SQL对应的XML代码
     */
    private String querySql;
    /**
     * 查询单条记录SQL对应的XML代码
     */
    private String queryOneSql;
    /**
     * 写入SQL对应的XML代码
     */
    private String insertSql;
    /**
     * 更新SQL对应的XML代码
     */
    private String updateSql;
    /**
     * 删除SQL对应的XML代码
     */
    private String deleteSql;

    public MapperXmlUtil(TableInfo tableInfo) {
        analysis(tableInfo);
    }

    private void analysis(TableInfo tableInfo) {
        
    }
}
