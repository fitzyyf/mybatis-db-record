package org.mumu.build.util;

import java.io.Serializable;

/**
 * 读取Mapper模板和XML模板信息内容，读取完成后，并实例化到内存中.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 11:00 上午
 * @since JDK 1.0
 */
public class TemplateSM implements Serializable {
    private static final long serialVersionUID = -2380088637065148571L;

    /**
     * Mapper接口的模板信息
     */
    public static final String MAPPER_TEMP;
    /**
     * XML模板信息
     */
    public static final String XML_TEMP;

    static {
        MAPPER_TEMP = readMapperTemp();
        XML_TEMP = readXmlTemp();
    }

    /**
     * 读取Mapper模板信息
     *
     * @return Mapper模板信息
     */
    private static String readMapperTemp() {
        String path = FileUtil.getClassPath("mappertmp.txt");
        return FileUtil.readFileContent(path);
    }

    /**
     * 读取Mybatis的XML模板文件信息
     *
     * @return XML模板信息
     */
    private static String readXmlTemp() {
        String path = FileUtil.getClassPath("mybatis.txt");
        return FileUtil.readFileContent(path);
    }

    private TemplateSM() {
    }
}
