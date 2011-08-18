/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:MapperUtil.java  11-6-11 下午7:36 poplar.mumu ]
 */
package org.mumu.build.mybatis;

import org.mumu.build.common.Constants;
import org.mumu.build.generate.SqlOperator;
import org.mumu.build.model.ColumnInfo;
import org.mumu.build.model.SqlModal;
import org.mumu.build.model.TableInfo;
import org.mumu.build.util.*;

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
        String className = StringUtil.tableNameToClass(tableInfo.getTableName());//XML实体数据库对应名称
        final SqlModal sql = SqlOperator.createSql(tableInfo, className);
        String tmpPakcage = Constants.PROJECT_INFO.getPackageName();
        String packageName = tmpPakcage + ".persistence";

        String xmlPath = FileUtil.createXmlFolder(classPath, packageName);

        String mpaaerName = packageName + "." + className + "Mapper";
        String content =
                StringUtil.replace(TemplateSM.XML_TEMP, Constants.XML_TEMP,
                        converSqlArray(sql, mpaaerName, className));


        String filePath = xmlPath + "/" + mpaaerName + ".xml";
        if (FileUtil.createClassFile(content, filePath)) {
            classPath = FileUtil.createJavaFolder(classPath, packageName);
            filePath = classPath + "/" + mpaaerName + ".java";
            String dataTime = DatetimeUtil.dateTime();
            content = StringUtil.replace(TemplateSM.MAPPER_TEMP, Constants.MAPPER_TEMP, new String[]{
                    tmpPakcage + ".domain" + className,
                    packageName,
                    tableInfo.getTableComment(),
                    Constants.PROJECT_INFO.getUser(),
                    dataTime,
                    className + "Mapper",
                    tableInfo.getTableName(),
                    className,
                    StringUtil.toHump(className)
            });
            return FileUtil.createClassFile(content, filePath);
        }
        return false;
    }


    /**
     * 将SQL对象转换为数组对象。
     *
     * @param sqlModal    SQL对象
     * @param packageName 包名称，也是xml的名空间
     * @param className   实体类名
     * @return 与模板对象数组
     */
    private static String[] converSqlArray(SqlModal sqlModal, String packageName, String className) {
        String[] xmlArray = new String[9];
        xmlArray[0] = packageName;
        xmlArray[1] = Constants.PROJECT_INFO.getCache();
        xmlArray[2] = sqlModal.getInsertSql();
        xmlArray[3] = sqlModal.getUpdateSql();
        xmlArray[4] = sqlModal.getSelectAllSql();
        xmlArray[5] = sqlModal.getPageWhereSql();
        xmlArray[6] = sqlModal.getCountQuerySql();
        xmlArray[7] = sqlModal.getSelectSql();
        xmlArray[8] = sqlModal.getDeleteSql();
        xmlArray[9] = className;
        return xmlArray;
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
}
