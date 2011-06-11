/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:MapperUtil.java  11-6-11 下午7:36 poplar.mumu ]
 */
package com.nutshell.ntztool.generate.mybatis;

import com.nutshell.ntztool.model.ColumnInfo;
import com.nutshell.ntztool.util.DatetimeUtil;
import com.nutshell.ntztool.util.ResourceUtils;

/**
 * 产生各个Mapper方法，为mybatis的接口类.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-11 下午7:36
 * @since JDK 1.0
 */
public class MapperUtil {

    private MapperUtil() {
    }

    /**
     * 根据给定的表的描述以及接口名称以及时间，创建mapper接口的命名，注意，此处创建的java代码没有右大括号。<br/>
     *
     * @param tableComment 表的描述
     * @param domainName   对应实体类名称
     * @return mapper接口的Java代码。
     */
    public static String generateFacesMapper(String tableComment, String domainName) {
        StringBuilder mapper = new StringBuilder();
        mapper.append("/**\n");
        mapper.append(" * ").append(tableComment).append(" Mybatis Mapper接口类\n");
        mapper.append(" * <br/>\n");
        mapper.append(" * \n");
        mapper.append(" * @author ").append(ResourceUtils.getProject().getUser()).append("\n");
        mapper.append(" * @version 1.0 ").append(DatetimeUtil.dateTime()).append("\n");
        mapper.append(" * @since JDK 1.0\n");
        mapper.append(" */\n");
        mapper.append("public interface ").append(domainName).append("Mapper {\n");
        return mapper.toString();
    }

    /**
     * 创建一个mapper的insert方法.
     *
     * @param tableName        表名
     * @param domainName       对应实体类名称
     * @param insertMethodName 方法名称
     * @return insert方法的java代码以及注释
     */
    public static String generateInsertMethod(String tableName, String domainName, String insertMethodName) {
        StringBuilder mapper = new StringBuilder();
        String lowerFaces = domainName.toLowerCase();
        mapper.append("    /**\n");
        mapper.append("     * 向数据库表").append(tableName).append("增加一条信息。\n");
        mapper.append("     * @param ").append(lowerFaces).append(" ").append(tableName).append("信息\n");
        mapper.append("     * @return 返回大于0的数字表示数据库新增成功，否则表示新增失败\n");
        mapper.append("     */\n");
        mapper.append("    int ").append(insertMethodName).append("(").append(domainName).append(" ")
                .append(lowerFaces).append(");\n\n");
        return mapper.toString();
    }

    /**
     * 创建mapper中的recordCount总数的方法。
     *
     * @param tableName         表名
     * @param recordCountMethod 记录总数的方法名称
     * @return 记录总数的方法的Java代码以及注释
     */
    public static String generateRecordCountMehod(String tableName, String recordCountMethod) {
        StringBuilder mapper = new StringBuilder();
        mapper.append("    /**\n");
        mapper.append("     * 取得表").append(tableName).append("记录总数。\n");
        mapper.append("     * @return 表").append(tableName).append("记录总数。\n");
        mapper.append("     */\n");
        mapper.append("    long ").append(recordCountMethod).append("();\n\n");
        return mapper.toString();
    }

    /**
     * 创建mapper中的产生统计所有记录的方法。
     *
     * @param tableName       表名
     * @param domainName      对应的实体类名称
     * @param selectOwnMethod 查询所有记录的方法
     * @return 所有记录的方法的Java代码以及注释
     */
    public static String generateSelectOwnRecordMethod(String tableName, String domainName, String selectOwnMethod) {
        StringBuilder mapper = new StringBuilder();
        mapper.append("    /**\n");
        mapper.append("     * 取得表").append(tableName)
                .append("所有的信息。支持分页操作，如果要查询全部，则传递一个空的Map即可。\n");
        mapper.append("     * @param limitParamt 分页中的起始标记,必须包含的Key为start,end.\n");
        mapper.append("     * @return 分页获取表").append(tableName).append("的所有记录<br/>\n");
        mapper.append("     *         如果要获取全部，则传递一个空的Map即可。\n");
        mapper.append("     */\n");
        mapper.append("    List<").append(domainName).append("> ").append(selectOwnMethod)
                .append("(Map<String,Object> limitParamt);\n\n");
        return mapper.toString();
    }

    /**
     * 给定一个主键列以及表的名称及对应实体名称，和查询单个的方法。
     *
     * @param tableName       表名
     * @param domainName      实体名称
     * @param selectOneMethod 单条记录的方法
     * @param priColumn       主键列
     * @return 主键查询单条记录的查询方法的Java代码以及注释。
     */
    public static String generateSelectOneRecordMethod(String tableName, String domainName,
                                                       String selectOneMethod, ColumnInfo priColumn) {
        StringBuilder mapper = new StringBuilder();
        mapper.append("    /**\n");
        mapper.append("     * 根据给定的主键值取得表").append(tableName).append("对应的记录。\n");
        mapper.append("     * @param ").append(priColumn.getColumnName().toLowerCase()).append(" 表")
                .append(priColumn.getColumnName()).append("主键值\n");
        mapper.append("     * @return 主键为<code>").append(priColumn.getColumnName().toLowerCase())
                .append("</code>").append("的数据信息\n");
        mapper.append("     */\n");
        mapper.append("    ").append(domainName).append(" ").append(selectOneMethod).append("(")
                .append(priColumn.getDataType()).append(" ").append(priColumn.getColumnName().toLowerCase())
                .append(");\n\n");
        return mapper.toString();

    }

    /**
     * 生成删除记录的mapper方法。
     *
     * @param tableName    表名
     * @param deleteMethod 单条记录的方法
     * @param priColumn    主键列
     * @return 主键删除单条记录的方法的Java代码以及注释。
     */
    public static String generateDeleteRecordMethod(String tableName, String deleteMethod, ColumnInfo priColumn) {
        StringBuilder mapper = new StringBuilder();
        mapper.append("    /**\n");
        mapper.append("     * 根据给定的主键值向数据库表").append(tableName).append("删除一条信息。\n");
        mapper.append("     * @param ").append(priColumn.getColumnName().toLowerCase()).append(" 表")
                .append(priColumn.getColumnName()).append("主键值\n");
        mapper.append("     * @return 返回大于0的数字表示数据库删除成功，否则表示删除失败\n");
        mapper.append("     */\n");
        mapper.append("    int ").append(deleteMethod).append("(").append(priColumn.getDataType()).append(" ")
                .append(priColumn.getColumnName().toLowerCase()).append(");\n\n");
        return mapper.toString();

    }

    /**
     * 生成更新记录的mapper方法。
     *
     * @param tableName    表名
     * @param domainName   对应实体的名称
     * @param updateMethod 更新方法的名称
     * @return 更新记录方法的Java代码以及注释。
     */
    public static String generateUpdateRecordMethod(String tableName, String domainName,
                                                    String updateMethod) {
        StringBuilder mapper = new StringBuilder();
        mapper.append("    /**\n");
        mapper.append("     * 根据给定的主键值更新数据库表").append(tableName).append("的一条信息。\n");
        mapper.append("     * @param ").append(domainName.toLowerCase()).append(" 包含主键值的").append(tableName).append("数据信息\n");
        mapper.append("     * @return 返回大于0的数字表示数据库更新成功，否则表示更新失败\n");
        mapper.append("     */\n\n");
        mapper.append("    int ").append(updateMethod).append("(").append(domainName).append(" ").append(domainName.toLowerCase()).append(");\n");
        return mapper.toString();

    }
}
