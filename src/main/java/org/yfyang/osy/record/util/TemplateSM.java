package org.yfyang.osy.record.util;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;

import com.google.common.io.Resources;

/**
 * 读取Mapper模板和XML模板信息内容，读取完成后，并实例化到内存中.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 18/08/2011 11:00 上午
 * @since JDK 1.0
 */
public class TemplateSM implements Serializable {
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

	private static final long serialVersionUID = -2380088637065148571L;

	private TemplateSM() {
	}

	/**
	 * 读取Mapper模板信息
	 *
	 * @return Mapper模板信息
	 */
    private static String readMapperTemp() {
		URL url = Resources.getResource("mappertmp.txt");
		try {
			return Resources.toString(url, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

    /**
     * 读取Mybatis的XML模板文件信息
     *
     * @return XML模板信息
     */
    private static String readXmlTemp() {
		URL url = Resources.getResource("mybatis.txt");
		try {
			return Resources.toString(url, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
