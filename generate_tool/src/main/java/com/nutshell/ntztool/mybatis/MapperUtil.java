/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:MapperUtil.java  11-6-11 下午7:36 poplar.mumu ]
 */
package com.nutshell.ntztool.mybatis;

import com.nutshell.ntztool.common.Constants;
import com.nutshell.ntztool.generate.SqlModal;
import com.nutshell.ntztool.generate.SqlOperator;
import com.nutshell.ntztool.model.ColumnInfo;
import com.nutshell.ntztool.model.TableInfo;
import com.nutshell.ntztool.util.DatetimeUtil;
import com.nutshell.ntztool.util.FileUtil;
import com.nutshell.ntztool.util.ResourceUtils;
import com.nutshell.ntztool.util.StringUtil;

/**
 * 产生各个Mapper方法，为mybatis的接口类.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-11 下午7:36
 * @since JDK 1.5
 */
public class MapperUtil {

    private MapperUtil() {
    }

    /**
     * @param tableInfo 数据库表含义
     * @param classPath 类 存放路径
     * @return 是否操作成功
     */
    public static boolean createMybatis(TableInfo tableInfo, String classPath) {
        String className = StringUtil.tableNameToClass(tableInfo.getTableName());//XML配置名
        final SqlModal sql = SqlOperator.createSql(tableInfo, className);
        StringBuilder xmlContent = new StringBuilder();
        xmlContent.append(sql.getInsertSql()).append(sql.getUpdateSql()).append(sql.getSelectAllSql()).
                append(sql.getCountQuerySql()).append(sql.getSelectSql()).append(sql.getDeleteSql());
        String packageName = Constants.PROJECT_INFO.getPackageName() + ".persistence";
        String content = Constants.MYBATIS_XML.replace("${mappername}", packageName + "." + className + "Mapper")
                .replace("${sqlconetnt}", xmlContent);
        String xmlPath = FileUtil.createXmlFolder(classPath, packageName);
        String filePath = xmlPath + "/" + className + "Mapper.xml";
        if (FileUtil.createClassFile(content, filePath)) {
            classPath = FileUtil.createJavaFolder(classPath, packageName);
            filePath = classPath + "/" + className + "Mapper.java";
            String dataTime = DatetimeUtil.dateTime();
            String header = ResourceUtils.getInstance().getCodeTemplate().replace("${name}", className + "Mapper").replace("${datetime}", dataTime)
                    .replace("${package}", packageName);
            String importDomain = new StringBuilder().append("\nimport ").append(Constants.PROJECT_INFO.getPackageName()).append(".domain.")
                    .append(className).append(";\n").append("import java.util.List;\nimport java.util.Map;\n").toString();
            return FileUtil.createClassFile(header + importDomain + sql.getMapper(), filePath);
        }
        return false;
    }


    /**
     * 根据表信息创建实体类。
     *
     * @param tableInfo 表格信息
     * @param classPath 文件类存放文件夹
     * @return 是否操作成功
     */
    public static boolean generateCode(TableInfo tableInfo, String classPath) {
        String className = StringUtil.tableNameToClass(tableInfo.getTableName());//实体类名
        StringBuilder builder = new StringBuilder();
        StringBuilder getSet = new StringBuilder();
        String dataTime = DatetimeUtil.dateTime();
        System.out.println("nutshell代码工具:" + dataTime + ",正在生成表：" + tableInfo + "的实体类");
        builder.append("/**\n");
        builder.append(" * ").append(tableInfo.getTableComment()).append(" 实体\n");
        builder.append(" * <br/>\n");
        builder.append(" * \n");
        builder.append(" * @author ").append(ResourceUtils.getInstance().getProject().getUser()).append("\n");
        builder.append(" * @version 1.0 ").append(dataTime).append("\n");
        builder.append(" * @since JDK 1.0\n");
        builder.append(" */\n");
        builder.append("public class ").append(className).append("{\n");
        String clsPro;//类属性名称
        String setName, getName;//get set名称;
        for (ColumnInfo columnInfo : tableInfo.getColumnList()) {
            clsPro = StringUtil.columnToPropertis(columnInfo.getColumnName());
            setName = StringUtil.getSetMethod(clsPro, "set");
            getName = columnInfo.getDataType().equals("boolean") ? StringUtil.getSetMethod(clsPro, "is") : StringUtil.getSetMethod(clsPro, "get");
            builder.append("    /*\n");
            builder.append("     *").append(columnInfo.getColumnComment()).append("\n");
            builder.append("     */\n");
            builder.append("    private ").append(columnInfo.getDataType()).append(" ").append(clsPro).append(";\n");
            //get
            getSet.append("    /**\n");
            getSet.append("     * @return ").append(columnInfo.getColumnComment()).append("\n");
            getSet.append("     */\n");
            getSet.append("    public ").append(columnInfo.getDataType()).append(" ").append(getName).append("(){\n");
            getSet.append("         return ").append(clsPro).append(";\n");
            getSet.append("    }\n\n");
            //set
            getSet.append("    /**\n");
            getSet.append("     * 设置 ").append(columnInfo.getColumnComment()).append("[")
                    .append(columnInfo.getColumnName()).append("]\n");
            getSet.append("     * @param ").append(clsPro).append(" ").append(columnInfo.getColumnComment()).append("\n");
            getSet.append("     */\n");
            getSet.append("    public void ").append(setName).append("(").append(columnInfo.getDataType()).append(" ").append(clsPro).append(") {\n");
            getSet.append("        this.").append(clsPro).append("=").append(clsPro).append(";\n");
            getSet.append("    }\n\n");
        }
        builder.append(getSet);
        builder.append("}");
        String packageName = Constants.PROJECT_INFO.getPackageName() + ".domain";
        classPath = FileUtil.createJavaFolder(classPath, packageName);
        String filePath = classPath + "/" + className + ".java";
        String header = ResourceUtils.getInstance().getCodeTemplate().replace("${name}", className).replace("${datetime}", dataTime).replace("${package}", packageName);
        return FileUtil.createClassFile(header + builder.toString(), filePath);
    }

    /**
     * 根据给定的表的描述以及接口名称以及时间，创建mapper接口的命名，注意，此处创建的java代码没有右大括号。<br/>
     *
     * @param tableComment 表的描述
     * @param domainName   对应实体类名称
     * @return mapper接口的Java代码。
     */
    public static String generateFacesMapper(String tableComment, String domainName) {

        String[] facesName = new String[]{
                "/**\n", " * ", tableComment, " Mybatis Mapper接口类\n", " * <br/>\n",
                " * \n", " * @author ", ResourceUtils.getInstance().getProject().getUser(), "\n",
                " * @version 1.0 ", DatetimeUtil.dateTime(), "\n",
                " * @since JDK 1.5\n", " */\n", "public interface ", domainName, "Mapper {\n"
        };

        return generateAppendString(facesName);

//        StringBuilder mapper = new StringBuilder();
//        mapper.append("/**\n");
//        mapper.append(" * ").append(tableComment).append(" Mybatis Mapper接口类\n");
//        mapper.append(" * <br/>\n");
//        mapper.append(" * \n");
//        mapper.append(" * @author ").append(ResourceUtils.getProject().getUser()).append("\n");
//        mapper.append(" * @version 1.0 ").append(DatetimeUtil.dateTime()).append("\n");
//        mapper.append(" * @since JDK 1.0\n");
//        mapper.append(" */\n");
//        mapper.append("public interface ").append(domainName).append("Mapper {\n");
//        return mapper.toString();
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

    /**
     * 生成字符串，给定的字符串数组。
     *
     * @param strs 字符串数组
     * @return 链接各个字符串，生成一个新的字符串
     */
    private static String generateAppendString(String[] strs) {
        StringBuilder mapper = new StringBuilder();
        for (String str : strs) {
            mapper.append(str);
        }
        return mapper.toString();
    }
}
