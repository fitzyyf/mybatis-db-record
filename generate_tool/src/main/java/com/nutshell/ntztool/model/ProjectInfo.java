/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:SystemInfo.java  11-6-8 上午1:22 poplar.mumu ]
 */
package com.nutshell.ntztool.model;

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

    @Override
    public String toString() {
        return "项目名称:" + projectName + "：{" +
                "项目包='" + packageName + '\'' +
                ", 开发人员='" + user + '\'' +
                '}';
    }
}
