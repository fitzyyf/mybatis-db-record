/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:GenerateService.java  11-6-8 上午1:14 poplar.mumu ]
 */
package com.nutshell.ntztool.generate;

import com.nutshell.ntztool.db.DbInfoDao;
import com.nutshell.ntztool.model.ColumnInfo;
import com.nutshell.ntztool.model.TableInfo;
import com.nutshell.ntztool.util.DatetimeUtil;
import com.nutshell.ntztool.util.FileUtil;
import com.nutshell.ntztool.util.ResourceUtils;
import com.nutshell.ntztool.util.StringUtil;

import java.util.List;

/**
 * 实体生成工具类.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 上午1:14
 * @since JDK 1.0
 */
public class GenerateService implements IGenerateService {

    private static final String PACKAGE_NAME = ResourceUtils.getProject().getPackageName();

    @Override
    public boolean generateFile(String path) {
        List<TableInfo> tableInfoList = DbInfoDao.getInstance().showTables();
        for (TableInfo tableInfo : tableInfoList) {
            if (!generateCode(tableInfo, path)) {
                throw new RuntimeException("创建实体类出错");
            }
            if (!createMybatis(tableInfo, path)) {
                throw new RuntimeException("创建Mybatis配置文件出错");
            }
        }
        return true;
    }

    @Override
    public boolean generateEntityCode(String codeFilePath) {
        List<TableInfo> tableInfoList = DbInfoDao.getInstance().showTables();
        for (TableInfo tableInfo : tableInfoList) {
            if (!generateCode(tableInfo, codeFilePath)) {
                throw new RuntimeException("创建实体类出错");
            }
        }
        return true;
    }

    @Override
    public boolean generateEntityCode(String tableName, String codeFilePath) {
        TableInfo tableInfo = DbInfoDao.getInstance().showColumns(tableName);
        return generateCode(tableInfo, codeFilePath);
    }

    @Override
    public boolean generateMybatisXml(String xmlFilePath) {
        List<TableInfo> tableInfoList = DbInfoDao.getInstance().showTables();
        for (TableInfo tableInfo : tableInfoList) {
            if (!createMybatis(tableInfo, xmlFilePath)) {
                throw new RuntimeException("创建Mybatis配置文件出错");
            }
        }
        return true;
    }

    /**
     * 根据表信息创建实体类。
     *
     * @param tableInfo 表格信息
     * @param classPath 文件类存放文件夹
     * @return 是否操作成功
     */
    private static boolean generateCode(TableInfo tableInfo, String classPath) {
        String className = StringUtil.tableNameToClass(tableInfo.getTableName());//实体类名
        StringBuilder builder = new StringBuilder();
        StringBuilder getSet = new StringBuilder();
        String dataTime = DatetimeUtil.dateTime();
        System.out.println("nutshell代码工具:" + dataTime + ",正在生成表：" + tableInfo + "的实体类");
        builder.append("/**\n");
        builder.append(" * ").append(tableInfo.getTableComment()).append(" 实体\n");
        builder.append(" * <br/>\n");
        builder.append(" * \n");
        builder.append(" * @author ").append(ResourceUtils.getProject().getUser()).append("\n");
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
            getSet.append("     * 设置 ").append(columnInfo.getColumnName()).append(" 属性\n");
            getSet.append("     * @param ").append(clsPro).append(" ").append(columnInfo.getColumnComment()).append("\n");
            getSet.append("     */\n");
            getSet.append("    public void ").append(setName).append("(").append(columnInfo.getDataType()).append(" ").append(clsPro).append(") {\n");
            getSet.append("        this.").append(clsPro).append("=").append(clsPro).append(";\n");
            getSet.append("    }\n\n");
        }
        builder.append(getSet);
        builder.append("}");
        String packageName = PACKAGE_NAME + ".domain";
        classPath = FileUtil.createJavaFolder(classPath, packageName);
        String filePath = classPath + "\\" + className + ".java";
        String header = CODE_TEMPLATE.replace("${name}", className).replace("${datetime}", dataTime).replace("${package}", packageName);
        return FileUtil.createClassFile(header + builder.toString(), filePath);
    }

    /**
     * @param tableInfo 数据库表含义
     * @param classPath 类 存放路径
     * @return 是否操作成功
     */
    private static boolean createMybatis(TableInfo tableInfo, String classPath) {
        String className = StringUtil.tableNameToClass(tableInfo.getTableName());//XML配置名
        Sql sql = createSql(tableInfo, className);
        StringBuilder xmlContent = new StringBuilder();
        xmlContent.append(sql.getInsertSql()).append(sql.getUpdateSql()).append(sql.getSelectAllSql()).
                append(sql.getSelectSql()).append(sql.getDeleteSql());
        String packageName = PACKAGE_NAME + ".persistence";
        String content = MYBATIS_XML.replace("${mappername}", packageName + "." + className + "Mapper").replace("${sqlconetnt}", xmlContent);
        String xmlPath = FileUtil.createXmlFolder(classPath, packageName);
        String filePath = xmlPath + "\\" + className + "Mapper.xml";
        if (FileUtil.createClassFile(content, filePath)) {
            classPath = FileUtil.createJavaFolder(classPath, packageName);
            filePath = classPath + "\\" + className + "Mapper.java";
            String dataTime = DatetimeUtil.dateTime();
            String header = CODE_TEMPLATE.replace("${name}", className + "Mapper").replace("${datetime}", dataTime).replace("${package}", packageName);
            String importDomain = "\nimport " + PACKAGE_NAME + ".domain." + className + ";\n";
            return FileUtil.createClassFile(header + importDomain + sql.getMapper(), filePath);
        }
        return false;
    }
    /**
     * MYBATIS的配置文件
     */
    private static final String MYBATIS_XML =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
            + "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \n"
            + "\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n"
            + "<mapper namespace=\"${mappername}\">\n\n"
            + "   <cache />\n\n"
            + "${sqlconetnt}\n</mapper>";

    /**
     * 创建SQL脚本.
     *
     * @param tableInfo 表格属性
     * @param clsName   类型
     * @return sql语句
     */
    private static Sql createSql(TableInfo tableInfo, String clsName) {
        Sql sql = new Sql();
        StringBuilder insert = new StringBuilder();//insert语句
        StringBuilder update = new StringBuilder();//update语句
        StringBuilder delete = new StringBuilder();//delete语句
        StringBuilder selectOne = new StringBuilder();//查询单个语句
        StringBuilder selectAll = new StringBuilder();//查询所有的语句

        StringBuilder mapper = new StringBuilder();
        String tableName = tableInfo.getTableName().toUpperCase();
        //
        String dataTime = DatetimeUtil.dateTime();
        mapper.append("/**\n");
        mapper.append(" * ").append(tableInfo.getTableComment()).append(" Mybatis接口类\n");
        mapper.append(" * <br/>\n");
        mapper.append(" * \n");
        mapper.append(" * @author ").append(ResourceUtils.getProject().getUser()).append("\n");
        mapper.append(" * @version 1.0 ").append(dataTime).append("\n");
        mapper.append(" * @since JDK 1.0\n");
        mapper.append(" */\n");
        mapper.append("public interface ").append(clsName).append("Mapper {\n");
        //插入语句
        String inserId = StringUtil.sqlName("insert", clsName);
        insert.append("    <insert id=\"").append(inserId).append("\" parameterType=\"").append(clsName).append("\">\n");
        insert.append("      INSERT INTO ").append(tableName);
        insert.append("(\n");

        mapper.append("    /**\n");
        mapper.append("     * 向数据库表").append(tableName).append("增加一条信息。\n");
        mapper.append("     * @param ").append(clsName.toLowerCase()).append(" ").append(tableName).append("信息\n");
        mapper.append("     * @return 返回大于0的数字表示数据库新增成功，否则表示新增失败\n");
        mapper.append("     */\n");
        mapper.append("    int ").append(inserId).append("(").append(clsName).append(" ").append(clsName.toLowerCase()).append(");\n");
        //删除语句
        String delteId = StringUtil.sqlName("delete", clsName);
        delete.append("    <delete id=\"").append(delteId).append("\" parameterType=\"");
        //查询语句
        String selectOneId = StringUtil.sqlName("selectOne", clsName);
        String selectOwnId = StringUtil.sqlName("selectOwn", clsName);
        selectOne.append("      <select id=\"").append(selectOneId).append("\" ");
        selectAll.append("      <select id=\"").append(selectOwnId).
                append("\" resultType=\"").append(clsName).append("\">\n");
        mapper.append("    /**\n");
        mapper.append("     * 取得表").append(tableName).append("所有的信息。\n");
        mapper.append("     * @return ").append(tableName).append("的所有信息列表\n");
        mapper.append("     */\n");
        mapper.append("    java.util.List<").append(clsName).append("> ").append(selectOwnId).append("();\n");
        //update语句
        String updateId = StringUtil.sqlName("update", clsName);
        update.append("     <update id=\"").append(updateId).append("\"  parameterType=\"").
                append(clsName).append("\">\n");
        update.append("         UPDATE ").append(tableName).append(" SET \n");

        String clsPro;//类属性名称
        StringBuilder proBuilder = new StringBuilder();
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("          SELECT\n");
        String wherePRI = "", columnName;
        boolean pri = false;
        for (ColumnInfo columnInfo : tableInfo.getColumnList()) {
            clsPro = StringUtil.columnToPropertis(columnInfo.getColumnName());
            columnName = columnInfo.getColumnName().toUpperCase();
            //删除主键
            if (columnInfo.getColumnKey().equals("PRI")) {//主键
                pri = true;
                selectSql.append("          ").append(columnName).append(",\n");
                delete.append(columnInfo.getDataType()).append("\">\n");
                delete.append("         DELETE FROM ").append(tableName).append(" WHERE ").append(columnName).append(" = #{").append(clsPro).append("}\n");
                delete.append("    </delete>");
                selectOne.append(" parameterType=\"").append(columnInfo.getDataType()).append("\">\n");
                wherePRI = "         WHERE " + columnName + "=#{" + clsPro + "}\n";

                mapper.append("    /**\n");
                mapper.append("     * 根据给定的主键值取得表").append(tableName).append("对应的数据信息。\n");
                mapper.append("     * @param ").append(columnInfo.getColumnName().toLowerCase()).append(" 表").append(columnInfo.getColumnName()).append("主键值\n");
                mapper.append("     * @return 主键为<code>").append(columnInfo.getColumnName().toLowerCase()).append("</code>").append("的数据信息\n");
                mapper.append("     */\n");
                mapper.append("    ").append(clsName).append(" ").append(selectOneId).append("(").append(columnInfo.getDataType()).append(" ").append(columnInfo.getColumnName().toLowerCase()).append(");\n");

                mapper.append("    /**\n");
                mapper.append("     * 根据给定的主键值向数据库表").append(tableName).append("删除一条信息。\n");
                mapper.append("     * @param ").append(columnInfo.getColumnName().toLowerCase()).append(" 表").append(columnInfo.getColumnName()).append("主键值\n");
                mapper.append("     * @return 返回大于0的数字表示数据库删除成功，否则表示删除失败\n");
                mapper.append("     */\n");
                mapper.append("    int ").append(delteId).append("(").append(columnInfo.getDataType()).append(" ").append(columnInfo.getColumnName().toLowerCase()).append(");\n");

                mapper.append("    /**\n");
                mapper.append("     * 根据给定的主键值更新数据库表").append(tableName).append("的一条信息。\n");
                mapper.append("     * @param ").append(clsName.toLowerCase()).append(" 包含主键值的").append(tableName).append("数据信息\n");
                mapper.append("     * @return 返回大于0的数字表示数据库更新成功，否则表示更新失败\n");
                mapper.append("     */\n");
                mapper.append("    int ").append(updateId).append("(").append(clsName).append(" ").append(clsName.toLowerCase()).append(");\n");
            } else {
                proBuilder.append("         #{").append(clsPro).append("},\n");

                insert.append("          ").append(columnName).append(",\n");
                //查询语句
                selectSql.append("          ").append(columnName).append(",\n");
                //update语句
                update.append("          ").append(columnName).
                        append("=#{").append(clsPro).append("},\n");
            }
        }
        insert.setCharAt(insert.lastIndexOf(","), ' ');
        proBuilder.setCharAt(proBuilder.lastIndexOf(","), ' ');
        selectSql.setCharAt(selectSql.lastIndexOf(","), ' ');

        selectSql.append("          FROM ").append(tableName).append("\n");
        selectAll.append(selectSql).append("\n").append("       </select>\n");
        if (!pri) { //么有主键
            selectOne = new StringBuilder("");
            update = new StringBuilder("");
            delete = new StringBuilder("");
        } else {
            selectOne.append(selectSql).append(wherePRI).append("       </select>\n");
            update.setCharAt(update.lastIndexOf(","), ' ');
            update.append(wherePRI).append("\n      </update>\n");
        }

        insert.append("         ) VALUES (\n");
        insert.append(proBuilder);
        insert.append("         )\n");
        insert.append("     </insert>\n");


        mapper.append("}");

        sql.setInsertSql(insert.toString());
        sql.setDeleteSql(delete.toString());
        sql.setSelectAllSql(selectAll.toString());
        sql.setSelectSql(selectOne.toString());
        sql.setUpdateSql(update.toString());
        sql.setMapper(mapper.toString());
        return sql;
    }
    /**
     * 头文件
     */
    private static final String CODE_TEMPLATE = "/*\n"
            + " * Copyright (c) 2010-2011 NutShell.\n"
            + " * [Project:" + ResourceUtils.getProject().getProjectName() + ",Id:${name}.java  ${datetime} " + ResourceUtils.getProject().getUser() + " ]\n"
            + " */\n"
            + "package ${package};\n";

    static class Sql {

        private String insertSql;
        private String updateSql;
        private String deleteSql;
        private String selectAllSql;
        private String selectSql;
        private String mapper;

        public String getMapper() {
            return mapper;
        }

        public void setMapper(String mapper) {
            this.mapper = mapper;
        }

        public String getInsertSql() {
            return insertSql;
        }

        public void setInsertSql(String insertSql) {
            this.insertSql = insertSql;
        }

        public String getUpdateSql() {
            return updateSql;
        }

        public void setUpdateSql(String updateSql) {
            this.updateSql = updateSql;
        }

        public String getDeleteSql() {
            return deleteSql;
        }

        public void setDeleteSql(String deleteSql) {
            this.deleteSql = deleteSql;
        }

        public String getSelectAllSql() {
            return selectAllSql;
        }

        public void setSelectAllSql(String selectAllSql) {
            this.selectAllSql = selectAllSql;
        }

        public String getSelectSql() {
            return selectSql;
        }

        public void setSelectSql(String selectSql) {
            this.selectSql = selectSql;
        }
    }
}
