package org.mumu.build.generate;

import org.mumu.build.common.Constants;
import org.mumu.build.model.ColumnInfo;
import org.mumu.build.model.TableInfo;
import org.mumu.build.util.DatetimeUtil;
import org.mumu.build.util.FileUtil;
import org.mumu.build.util.ResourceUtils;
import org.mumu.build.util.StringUtil;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 7:08 下午
 * @since JDK 1.0
 */
public class BuilderEntity {

    private BuilderEntity() {
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
        System.out.println("NOO代码工具:" + dataTime + ",正在生成表：" + tableInfo + "的实体类");
        builder.append("/**\n");
        builder.append(" * ").append(tableInfo.getTableComment()).append(" 实体\n");
        builder.append(" * <br/>\n");
        builder.append(" * \n");
        builder.append(" * @author ").append(Constants.PROJECT_INFO.getUser()).append("\n");
        builder.append(" * @version 1.0 ").append(dataTime).append("\n");
        builder.append(" * @since JDK 1.5\n");
        builder.append(" */\n");
        builder.append("public class ").append(className).append("{\n");
        String clsPro;//类属性名称
        String setName, getName;//get set名称;
        for (ColumnInfo columnInfo : tableInfo.getColumnList()) {
            System.out.println(columnInfo);
            clsPro = StringUtil.columnToPropertis(columnInfo.getColumnName());
            setName = StringUtil.getSetMethod(clsPro, "set");
            getName = columnInfo.getDataType().equals("boolean") ?
                    StringUtil.getSetMethod(clsPro, "is") :
                    StringUtil.getSetMethod(clsPro, "get");
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
            getSet.append("     * @param ").append(clsPro).append(" ").
                    append(columnInfo.getColumnComment()).append("\n");
            getSet.append("     */\n");
            getSet.append("    public void ").append(setName).append("(").
                    append(columnInfo.getDataType()).append(" ").append(clsPro).append(") {\n");
            getSet.append("        this.").append(clsPro).append("=").append(clsPro).append(";\n");
            getSet.append("    }\n\n");
        }
        builder.append(getSet);
        builder.append("}");
        String packageName = Constants.PROJECT_INFO.getPackageName() + ".domain";
        classPath = FileUtil.createJavaFolder(classPath, packageName);
        String filePath = classPath + "/" + className + ".java";
        String header = ResourceUtils.getInstance().getCodeTemplate()
                .replace("${name}", className).replace("${datetime}", dataTime)
                .replace("${package}", packageName);
        return FileUtil.createClassFile(header + builder.toString(), filePath);
    }
}
