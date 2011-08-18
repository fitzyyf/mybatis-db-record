/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:EnglishDict.java  11-6-8 上午1:46 poplar.mumu ]
 */
package com.nutshell.ntztool.util;

import com.nutshell.ntztool.common.Constants;

import java.io.*;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 常用英文字典，通过dict.txt文本读取到。.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 上午1:46
 * @since JDK 1.0
 */
public class EnglishDict {
    private static final Map<String, String> ENGLISH_DICT = new HashMap<String, String>();

    private EnglishDict() {
    }

    static {
        Properties config = new Properties();
        InputStream in = null;
        String proFilePath = Constants.USER_DIR + "/dict.properties";
        try {
            in = new BufferedInputStream(new FileInputStream(proFilePath));
            config.load(in);
            Enumeration e = config.propertyNames();
            String key, value;
            while (e.hasMoreElements()) {
                key = (String) e.nextElement();
                value = config.getProperty(key);
                ENGLISH_DICT.put(key, value);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件不存在" + e.getLocalizedMessage());
        } catch (IOException e) {
            throw new RuntimeException("文件读取异常:" + e.getLocalizedMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            in = null;
            config = null;
        }
    }

    /**
     * 检测给定的字符是否在字典中，并且不是以字典开头的值。然后进行替换返回。
     *
     * @param name 给定的字符。在这里，一般是表的字段
     * @return 替换字段中的字符后的内容 <br/>
     *         如果字典中不存在，这返回字符本身
     */
    public static String dictReplace(String name) {
        for (String s : ENGLISH_DICT.keySet()) {
            if (!name.startsWith(s) && name.contains(s)) {
                return name.replace(s, ENGLISH_DICT.get(s));
            }
        }
        return name;
    }

}
