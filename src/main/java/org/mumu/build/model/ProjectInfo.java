/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:SystemInfo.java  11-6-8 上午1:22 poplar.mumu ]
 */
package org.mumu.build.model;

/**
 * 项目配置信息.
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 上午1:22
 * @since JDK 1.0
 */
public class ProjectInfo {

    private String projectName;

    /**
     * 业务表标志，用来生成实体和业务服务类时截取类名。
     */
    private String bizTable;
    /**
     * mybatis的缓存机制
     */
    private String cache;

    public String getCache() {
        return cache;
    }

    public void setCache(String cache) {
        this.cache = cache;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    private String packageName;

    private String user;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getBizTable() {
        return bizTable;
    }

    public void setBizTable(String bizTable) {
        this.bizTable = bizTable;
    }

    @Override
    public String toString() {
        return "项目名称:" + projectName + "：{" +
                "项目包='" + packageName + '\'' +
                ", 开发人员='" + user + '\'' +
                ", 业务表标志='" + bizTable + '\'' +
                '}';
    }
}
