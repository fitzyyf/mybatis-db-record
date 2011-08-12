/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:GenerateService.java  11-6-8 上午1:14 poplar.mumu ]
 */
package com.nutshell.ntztool.generate;

import com.nutshell.ntztool.db.DbInfoDao;
import com.nutshell.ntztool.mybatis.MapperUtil;
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
                append(sql.getCountQuerySql()).append(sql.getSelectSql()).append(sql.getDeleteSql());
        String packageName = PACKAGE_NAME + ".persistence";
        String content = MYBATIS_XML.replace("${mappername}", packageName + "." + className + "Mapper")
                .replace("${sqlconetnt}", xmlContent);
        String xmlPath = FileUtil.createXmlFolder(classPath, packageName);
        String filePath = xmlPath + "\\" + className + "Mapper.xml";
        if (FileUtil.createClassFile(content, filePath)) {
            classPath = FileUtil.createJavaFolder(classPath, packageName);
            filePath = classPath + "\\" + className + "Mapper.java";
            String dataTime = DatetimeUtil.dateTime();
            String header = CODE_TEMPLATE.replace("${name}", className + "Mapper").replace("${datetime}", dataTime)
                    .replace("${package}", packageName);
            String importDomain = new StringBuilder().append("\nimport ").append(PACKAGE_NAME).append(".domain.")
                    .append(className).append(";\n").append("import java.util.List;\nimport java.util.Map;\n").toString();
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
     * @param tableInfo  表格属性
     * @param domainName 对应实体类
     * @return sql语句
     */
    private static Sql createSql(TableInfo tableInfo, String domainName) {
        Sql sql = new Sql();
        StringBuilder insert = new StringBuilder();//insert语句
        StringBuilder update = new StringBuilder();//update语句
        StringBuilder delete = new StringBuilder();//delete语句
        StringBuilder selectOne = new StringBuilder();//查询单个语句
        StringBuilder selectAll = new StringBuilder();//查询所有的语句
        StringBuilder selectCount = new StringBuilder();//查询统计语句
        StringBuilder mapper = new StringBuilder(); //mapper接口代码
        String tableName = tableInfo.getTableName().toUpperCase();

        //开始创建各个语句的Mapper接口对应的方法*//
        String insertMethod = StringUtil.sqlName("insert", domainName);
        String delteId = StringUtil.sqlName("delete", domainName);
        String selectOneId = StringUtil.sqlName("selectOne", domainName);
        String selectOwnMethod = StringUtil.sqlName("selectOwn", domainName);
        String updateId = StringUtil.sqlName("update", domainName);
        String recordCountMethod = StringUtil.sqlName("count", domainName);
        //结束创建mapper方法
        mapper.append(MapperUtil.generateFacesMapper(tableInfo.getTableComment(), domainName));
        //插入SQL的Mybatis配置语句
        insert.append("    <insert id=\"").append(insertMethod).append("\" parameterType=\"").append(domainName).append("\">\n");
        insert.append("      INSERT INTO ").append(tableName);
        insert.append("(\n");

        mapper.append(MapperUtil.generateInsertMethod(tableName, domainName, insertMethod));
        //删除语句
        delete.append("    <delete id=\"").append(delteId).append("\" parameterType=\"");
        //查询语句
        selectOne.append("      <select id=\"").append(selectOneId).append("\" ");
        selectAll.append("      <select id=\"").append(selectOwnMethod).
                append("\" resultType=\"").append(domainName)
                .append("\" parameterType=\"map\">\n");
        selectCount.append("      <select id=\"").append(recordCountMethod).append("\" resultType=\"long\">\n");

        mapper.append(MapperUtil.generateRecordCountMehod(tableName, recordCountMethod));

        mapper.append(MapperUtil.generateSelectOwnRecordMethod(tableName, domainName, selectOwnMethod));
        //update语句
        update.append("     <update id=\"").append(updateId).append("\"  parameterType=\"").
                append(domainName).append("\">\n");
        update.append("         UPDATE ").append(tableName).append(" SET \n");

        String clsPro;//类属性名称
        StringBuilder proBuilder = new StringBuilder();
        StringBuilder selectSql = new StringBuilder();
        selectSql.append("          SELECT\n");
        String wherePRI = "", columnName;
        ColumnInfo priColumn = null;//主键列
        for (ColumnInfo columnInfo : tableInfo.getColumnList()) {
            clsPro = StringUtil.columnToPropertis(columnInfo.getColumnName());
            columnName = columnInfo.getColumnName().toUpperCase();
            if (columnInfo.getColumnKey().equals("PRI")) {//主键
                priColumn = columnInfo;//当前为主键
                priColumn.setColumnName(columnName);
                //查询语句
                selectSql.append("          ").append(columnName).append(",\n");

                delete.append(columnInfo.getDataType()).append("\">\n");
                delete.append("         DELETE FROM ").append(tableName).append(" WHERE ").append(columnName).append(" = #{").append(clsPro).append("}\n");
                delete.append("    </delete>");
                selectOne.append(" parameterType=\"").append(columnInfo.getDataType()).append("\">\n");
                wherePRI = "         WHERE " + columnName + "=#{" + clsPro + "}\n";
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

        selectSql.append("          FROM ").append(tableName);
        selectAll.append(selectSql).append("\n")
                .append("          <if test=\"start !=null\">\n             LIMIT #{start},#{end}\n          </if>\n")
                .append("       </select>\n");
        if (priColumn != null) {
            mapper.append(MapperUtil.generateDeleteRecordMethod(tableName, delteId, priColumn));
            mapper.append(MapperUtil.generateUpdateRecordMethod(tableName, domainName, updateId));
            mapper.append(MapperUtil.generateSelectOneRecordMethod(tableName, domainName, selectOneId, priColumn));
            selectOne.append(selectSql).append(wherePRI).append("       </select>\n");
            update.setCharAt(update.lastIndexOf(","), ' ');
            update.append(wherePRI).append("\n      </update>\n");
             //查询统计语句
            selectCount.append("         SELECT COUNT(")
                    .append(priColumn.getColumnName().toUpperCase()).append(")\n         FROM ")
                    .append(tableName).append("\n");
        } else {
            selectOne = new StringBuilder("");
            update = new StringBuilder("");
            delete = new StringBuilder("");
            //查询统计语句
            selectCount.append("         SELECT COUNT(*) \n            FROM ")
                    .append(tableName).append("\n");
        }

        insert.append("         ) VALUES (\n");
        insert.append(proBuilder);
        insert.append("         )\n");
        insert.append("     </insert>\n");

        selectCount.append("       </select>\n");

        mapper.append("}");

        sql.setInsertSql(insert.toString());
        sql.setDeleteSql(delete.toString());
        sql.setSelectAllSql(selectAll.toString());
        sql.setSelectSql(selectOne.toString());
        sql.setUpdateSql(update.toString());
        sql.setCountQuerySql(selectCount.toString());
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
        private String countQuerySql;
        private String mapper;

        public String getCountQuerySql() {
            return countQuerySql;
        }

        public void setCountQuerySql(String countQuerySql) {
            this.countQuerySql = countQuerySql;
        }

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
