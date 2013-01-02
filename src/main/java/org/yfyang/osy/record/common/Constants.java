/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:Constants.java  11-6-8 下午1:59 poplar.mumu ]
 */
package org.yfyang.osy.record.common;

import java.util.Properties;

import org.yfyang.osy.record.App;
import org.yfyang.osy.record.model.ProjectInfo;

/**
 * 系统常量.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 下午1:59
 * @since JDK 1.5
 */
public class Constants {
    /**
     * 数据库与JDBCJava类型
     */
    public static final Properties DATA_TYPE_JDBC;
    /**
     * 资源配置种的工程信息
     */
    public static final ProjectInfo PROJECT_INFO;

    static {
        PROJECT_INFO = App.getInfo().getProject();
        DATA_TYPE_JDBC = App.getInfo().readDataJdbcType();
    }

    /**
     * 空白符号
     */
    public static final String BLANK_SIGN = "        ";
    /**
     * 双号空白符号
     */
    public static final String TWOBLANK_SIGN = "          ";
    /**
     * 换行符号
     */
    public static final String WRAP_CHAR = "\n";

    /**
     * SQL的AND标记
     */
    public static final String AND_SIGN = "AND";

    /**
     * 当前工作目录
     */
    public static final String USER_DIR = "/Projects/mumu/myopen/genCode";

    /**
     * Mysql主键标志
     */
    public static final String PRIMARY_KEY = "PRI";
    /**
     * Mysql外健标志
     */
    public static final String MUI_KEY = "MUL";
    /**
     * sql Select
     */
    public static final String SELECT_KEY = "SELECT ";
    /**
     * FROM sql key
     */
    public static final String FROM_KEY = "FROM ";


    public static final String DELETE_KEY = "DELETE FROM ";


    public static final String WHERE_KEY = "WHERE ";
    /**
     * Mapper接口的文件名称的结尾标志
     */
    public static final String MAPPER = "Mapper";


    /**
     * Mapper接口的模板标记，按照数组下标依次为：
     * <p>0:Mapper接口对应的实体名称,包括包名</p>
     * <p>1:Mapper接口的的包名</p>
     * <p>2:Mapper接口的描述</p>
     * <p>3:MAPPER接口的JAVADOC的作者</p>
     * <p>4:MAPPER接口的JAVADO的时间</p>
     * <p>5:MAPPER接口的接口名称</p>
     * <p>6:MAPPER接口的对应的数据库名称</p>
     * <p>7:MAPPER接口的对应的实体对象名称</p>
     * <p>8:MAPPER接口的对应的实体对象参数方法名称</p>
     */
    public static final String[] MAPPER_TEMP = new String[]{
            "${domain}",
            "${package}",
            "${mapperdesc}",
            "${author}",
            "${datatime}",
            "${className}",
            "${tableName}",
            "${domainName}",
            "${domainParam}"
    };


    /**
     * XML模板标记 按照下标依次为：
     * <p>0 Mybatis配置文件中的名空间，对应的是Mapper接口的名称，包含包名</p>
     * <p>1 Mybatis配置文件中的缓存机制</p>
     * <p>2 Mybatis配置文件中的Insert语句</p>
     * <p>3 Mybatis配置文件中的update语句</p>
     * <p>4 Mybatis配置文件中的select 所有语句</p>
     * <p>5 Mybatis配置文件中的分页函数的 WHERE条件，包含WHERE关键字</p>
     * <p>6 Mybatis配置文件中的求总纪录的SQL</p>
     * <p>7 Mybatis配置文件中的查询当个的SQL</p>
     * <p>8 Mybatis配置文件中的删除某条信息的语句</p>
     * <p>9 实体名称</p>
     */
    public static final String[] XML_TEMP = new String[]{
            "${namespace}",
            "${cache}",
            "${insertSQL}",
            "${updateSQL}",
            "${queryAllSQL}",
            "${pageWHERESQL}",
            "${countSQL}",
            "${queryOneSQL}",
            "${deleteSQL}",
            "${domainName}"
    };
}
