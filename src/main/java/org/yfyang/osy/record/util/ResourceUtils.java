package org.yfyang.osy.record.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.yfyang.osy.record.model.JdbcInfo;
import org.yfyang.osy.record.model.ProjectInfo;

/**
 * 国际化资源属性文件读取工具类
 *
 * @author poplar
 * @version 2011-5-3 3:48:58
 * @since JDK 1.0
 */
@SuppressWarnings({"UnusedAssignment"})
public final class ResourceUtils {
    /**
     * 头描述文件
     */
	private static final String CODE_TEMPLATE = "////////////////////////////////////////////////////////////////////////////////\n" +
			"// Copyright (c) 2012-2012 www.iflytek.com. All Rights Reserved.\n" +
			"//  This software for customer relationship management system, developed by cdms team.\n" +
			"//  Software code and design for the team, copy rights reserved.\n" +
			"////////////////////////////////////////////////////////////////////////////////\n"
			+ "\n"
			+ "package ${package};\n";
	private final JdbcInfo DEFAULT_JDBC_INFO;
	private final ProjectInfo PROJECT_INFO;

	public ResourceUtils(JdbcInfo default_jdbc_info, ProjectInfo project_info) {
		DEFAULT_JDBC_INFO = default_jdbc_info;
		PROJECT_INFO = project_info;
		String pkName = project_info.getPackageName();
		if (pkName.endsWith(".") || pkName.endsWith(",") || pkName.endsWith("。")) {
			throw new RuntimeException("包名不正确");
		}
	}

	/**
	 * 读取数据库类型与JDBC类型对应关照配置文件信息。
	 *
	 * @return 数据库数据类型与JDBC数据类型对照关系
	 */
	public Properties readDataJdbcType() {
		Properties config = new Properties();
		InputStream in = null;
		try {
			in = this.getClass().getClassLoader().getResourceAsStream("datatype.properties");
			config.load(in);
			return config;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}

	public JdbcInfo getJdbc() {
		return DEFAULT_JDBC_INFO;
	}

    public ProjectInfo getProject() {
        return PROJECT_INFO;
    }

    public String getCodeTemplate() {
        return CODE_TEMPLATE;
	}


}
