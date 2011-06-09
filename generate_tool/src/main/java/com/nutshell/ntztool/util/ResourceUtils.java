package com.nutshell.ntztool.util;

import com.nutshell.ntztool.common.Constants;
import com.nutshell.ntztool.model.JdbcInfo;
import com.nutshell.ntztool.model.ProjectInfo;

import java.io.*;
import java.util.Properties;

/**
 * 国际化资源属性文件读取工具类
 *
 * @author poplar
 * @version 2011-5-3 3:48:58
 * @since JDK 1.0
 */
public class ResourceUtils {
    private static final JdbcInfo DEFAULT_JDBC_INFO;

    private static final ProjectInfo PROJECT_INFO;

    static {
        DEFAULT_JDBC_INFO = new JdbcInfo();
        Properties config = new Properties();
        PROJECT_INFO = new ProjectInfo();
        InputStream in = null;
         String proFilePath = Constants.USER_DIR + "\\default.properties";
        try {
            in = new BufferedInputStream(new FileInputStream(proFilePath));
            config.load(in);
            String driver = config.getProperty("jdbc.driver");
            String url = config.getProperty("jdbc.url");
            String username = config.getProperty("jdbc.username");
            String password = config.getProperty("jdbc.password");
            DEFAULT_JDBC_INFO.setDriver(driver);
            DEFAULT_JDBC_INFO.setUrl(url);
            DEFAULT_JDBC_INFO.setUsername(username);
            DEFAULT_JDBC_INFO.setPassword(password);
            PROJECT_INFO.setProjectName(config.getProperty("project.name"));
            String pkName = config.getProperty("project.package");
            if(pkName.endsWith(".") || pkName.endsWith(",") || pkName.endsWith("。")){
                throw new RuntimeException("包名不正确");
            }
            PROJECT_INFO.setPackageName(pkName);
            PROJECT_INFO.setUser(config.getProperty("project.user"));
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

    public static JdbcInfo getJdbc() {
        return DEFAULT_JDBC_INFO;
    }

    public static ProjectInfo getProject() {
        return PROJECT_INFO;
    }
}
