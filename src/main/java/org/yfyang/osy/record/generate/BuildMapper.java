package org.yfyang.osy.record.generate;

import java.io.File;

import org.yfyang.osy.record.common.Constants;
import org.yfyang.osy.record.db.impl.TableInfoDao;
import org.yfyang.osy.record.model.SqlModal;
import org.yfyang.osy.record.model.TableInfo;
import org.yfyang.osy.record.sql.BuildSqlFactory;
import org.yfyang.osy.record.sql.CreateSqlFace;
import org.yfyang.osy.record.util.DatetimeUtil;
import org.yfyang.osy.record.util.FileUtil;
import org.yfyang.osy.record.util.StringUtil;
import org.yfyang.osy.record.util.TemplateSM;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 7:11 下午
 * @since JDK 1.0
 */
public class BuildMapper {

    /**
     * @param tableInfo 数据库表含义
     * @param classPath 类 存放路径
     * @return 是否操作成功
     */
    public static boolean createMybatis(TableInfo tableInfo, String classPath) {
        //XML实体数据库对应名称
        String className = StringUtil.tableNameToClass(tableInfo.getTableName());
        CreateSqlFace createSqlFace = BuildSqlFactory.
                getDBMSBuilderSql(TableInfoDao.AppInstance.DB_POOL.getDbms());
        final SqlModal sql = createSqlFace.createSql(tableInfo);
        String tmpPackage = Constants.PROJECT_INFO.getPackageName();
        String packageName = tmpPackage + ".dao";

        String xmlPath = FileUtil.createXmlFolder(classPath, packageName);

        String packageMapper = packageName + "." + className + Constants.MAPPER;
        String content =
                StringUtil.replace(TemplateSM.XML_TEMP, Constants.XML_TEMP,
                        converSqlArray(sql, packageMapper, className));

        String mapperName = createMapperName(className);
        String filePath = xmlPath + File.separator + mapperName + ".xml";
        if (FileUtil.createClassFile(content, filePath)) {
            classPath = FileUtil.createJavaFolder(classPath, packageName);
            filePath = classPath + File.separator + mapperName + ".java";
            String dataTime = DatetimeUtil.dateTime();
            content = StringUtil.replace(TemplateSM.MAPPER_TEMP, Constants.MAPPER_TEMP,
                    new String[]{
                            tmpPackage + ".domain." + className,
                            packageName,
                            tableInfo.getTableComment(),
                            Constants.PROJECT_INFO.getUser(),
                            dataTime,
                            mapperName,
                            tableInfo.getTableName(),
                            className,
                            StringUtil.toHump(className)
                    });
            return FileUtil.createClassFile(content, filePath);
        }
        return false;
    }

    /**
     * 产生Mapper接口
     *
     * @param className 实体类名
     * @return 实体类名＋Mapper
     */
    private static String createMapperName(String className) {
        return className + Constants.MAPPER;
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
        String[] xmlArray = new String[10];
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
}
