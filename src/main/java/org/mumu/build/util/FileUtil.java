/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:FileUtil.java  11-6-8 下午3:46 poplar.mumu ]
 */
package org.mumu.build.util;

import java.io.*;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 下午3:46
 * @since JDK 1.0
 */
@SuppressWarnings({"ResultOfMethodCallIgnored"})
public class FileUtil {
    /**
     * 创建类文件。
     *
     * @param content 文件内容
     * @param path    文件存放地址，不是文件夹，是这次的文件
     * @return 文件产生成功返回true，否则返回false
     */
    public static boolean createClassFile(String content, String path) {
        try {
            FileOutputStream fileoutputstream = new FileOutputStream(path);// 建立文件输出流
            byte tag_bytes[] = content.getBytes("UTF-8");
            fileoutputstream.write(tag_bytes);
            fileoutputstream.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("地址无法找到:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("创建文件出错:" + e.getMessage());
        }
        return false;
    }

    /**
     * 如果java文件夹不存在，则创建.
     *
     * @param folderPath  文件夹地址
     * @param packageName 包名
     * @return 路径
     */
    public static String createJavaFolder(String folderPath, String packageName) {
        folderPath = generatPackageFileFloder(folderPath + "/java/", packageName);
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return folderPath;
    }

    /**
     * 如果xml文件夹不存在，则创建.
     *
     * @param folderPath  文件夹地址
     * @param packageName 包名
     * @return 路径
     */
    public static String createXmlFolder(String folderPath, String packageName) {
        folderPath = generatPackageFileFloder(folderPath + "/resources/", packageName);
        File file = new File(folderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        return folderPath;
    }

    /**
     * 根据包名，创建包文件夹.
     *
     * @param floderPath  存放地址
     * @param packageName 包名
     * @return 包路径
     */
    public static String generatPackageFileFloder(String floderPath, String packageName) {
        String[] pgs = packageName.split("[.]");
        for (String pg : pgs) {
            floderPath += ("/" + pg);
        }
        return floderPath;
    }


    /**
     * 获取资源文件的绝对路径。
     *
     * @param resource 资源文件名称
     * @return 资源文件的文件系统路径
     */
    public static String getClassPath(String resource) {
        String result = FileUtil.class.getClassLoader().getResource(resource).getPath();
        int location = result.indexOf("!/");
        return location != -1 ? result.substring(0, location) : result;
    }

    /**
     * 读取某个文件的内容信息。
     * @param path 文件地址
     * @return 文件内容
     */
    public static String readFileContent(String path) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(path);
            byte[] bit = new byte[in.available()];
            in.read(bit);
            in.close();
            return (new String(bit));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
