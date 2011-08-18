/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:StringUtil.java  11-6-8 上午1:41 poplar.mumu ]
 */
package org.mumu.build.util;

import org.mumu.build.common.Constants;

/**
 * 字符串工具.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 上午1:41
 * @since JDK 1.0
 */
public class StringUtil {

    private static final String TABLE_SPLIT = "_";

    /**
     * 替换字符串。如果原始字符串和替换的标记为null，返回“”。
     * 注意，替换标记和替换内容按数组下标保持一致
     * @param template 原始字符串
     * @param temp     需要替换的标记字符串数组
     * @param replace  替换的内容 的数组
     * @return 替换后的字符串
     */
    public static String replace(String template, String[] temp, String[] replace) {
        if (template == null || temp == null) {
            return "";
        }
        for (int i = 0; i < temp.length; i++) {
            template = template.replace(temp[i],replace[i]);
        }
        return template;
    }

    /**
     * 删除字符串中最后一个出现的指定的字符串
     * @param stringBuilder 需要删除的原始字符串
     * @param character 指定要删除的字符串
     * @return 删除后的字符串
     */
    public static String deleteLastChar(StringBuilder stringBuilder,String character){
        int last = stringBuilder.lastIndexOf(character);
        return stringBuilder.substring(0, last);
    }

    /**
     * 将表名首字符大写，如果表名的命名方式为 T_c这种，进行间隔首字符大写的方式。
     *
     * @param tableName 表名
     * @return 转换为类名
     */
    public static String tableNameToClass(String tableName) {
        tableName = tableName.toUpperCase();
        tableName = tableName.substring(Constants.PROJECT_INFO.getBizTable().length());
        if (tableName.contains(TABLE_SPLIT)) {
            return toSplitChar(tableName, true);
        } else {
            return toFirstCharUpper(tableName);
        }
    }

    /**
     * 将属性名称以驼峰方式进行转换。
     * @param properties 属性名称
     * @return 驼峰方式
     */
    public static String toHump(String properties){
        properties = properties.toLowerCase();
        return properties.contains(TABLE_SPLIT)?
                toSplitChar(properties,false) :
                EnglishDict.dictReplace(properties);
    }

    /**
     * 转换表的字段名称。
     *
     * @param columnName 表的字段名称
     * @return 字段名称为属性驼峰形式
     */
    public static String columnToPropertis(String columnName) {
        columnName = columnName.toLowerCase();
        if (columnName.contains(TABLE_SPLIT)) {
            return toSplitChar(columnName, false);
        } else {
            return EnglishDict.dictReplace(columnName.toLowerCase());
        }
    }

    /**
     * 生成get set方法。
     *
     * @param columnCls 表字段名称
     * @param getSet    get set is标记
     * @return get set方法名称
     */
    public static String getSetMethod(String columnCls, String getSet) {
        if (columnCls.contains(TABLE_SPLIT)) {
            String[] column = columnCls.split(TABLE_SPLIT);
            StringBuilder builder = new StringBuilder();
            builder.append(getSet);
            for (String s : column) {
                builder.append(toFirstCharUpper(s));
            }
            return builder.toString();
        } else {
            return getSet + toFirstCharUpper(columnCls);
        }
    }

    /**
     * 对数据库中的表的_进行驼峰转换。
     *
     * @param tableName 数据库表名称
     * @param first     true 首字母大写，flase，首字母小写
     * @return 驼峰格式的表名
     */
    private static String toSplitChar(String tableName, boolean first) {
        String[] table = tableName.split(TABLE_SPLIT);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < table.length; i++) {
            String s = table[i];
            builder.append(first ? toFirstCharUpper(s) : (i == 0 ? s : toFirstCharUpper(s)));
        }
        return builder.toString();
    }

    /**
     * 将首字母大写.
     *
     * @param str 字符串
     * @return 首字符大写后的字符串
     */
    private static String toFirstCharUpper(String str) {
        str = str.toLowerCase();
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
