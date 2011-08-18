/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:MrpSUserinfo.java  11-6-8 下午3:43 poplar.mumu ]
 */
package org.mumu.build.db;
/**
 * 用户详细信息表 实体
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 2011-06-08 15:45:06
 * @since JDK 1.0
 */
public class MrpSUserinfo{
    /*
     *主键
     */
    private long id;
    /*
     *用户
     */
    private long usr;
    /*
     *用户名称
     */
    private String userName;
    /*
     *员工工号
     */
    private String userWorkno;
    /*
     *部门，多个以;号隔开
     */
    private String deps;
    /*
     *出生日期(YYYYMMDD)
     */
    private int birtday;
    /*
     *性别0:女1:男
     */
    private boolean gender;
    /*
     *移动电话
     */
    private String mobile;
    /*
     *固定电话
     */
    private String handfreePhone;
    /*
     *联系地址
     */
    private String address;
    /*
     *Email
     */
    private String email;
    /*
     *备注
     */
    private String remark;
    /*
     *QQ
     */
    private String qq;
    /*
     *状态0,正常;1,禁用,2,删除
     */
    private int states;
    /**
     * @return 主键
     */
    public long getId(){
         return id;
    }

    /**
      * 设置 ID 属性
      * @param id 主键
      */
    public void setId(long id) {
        this.id=id;
    }

    /**
     * @return 用户
     */
    public long getUsr(){
         return usr;
    }

    /**
      * 设置 USR 属性
      * @param usr 用户
      */
    public void setUsr(long usr) {
        this.usr=usr;
    }

    /**
     * @return 用户名称
     */
    public String getUserName(){
         return userName;
    }

    /**
      * 设置 USERNAME 属性
      * @param userName 用户名称
      */
    public void setUserName(String userName) {
        this.userName=userName;
    }

    /**
     * @return 员工工号
     */
    public String getUserWorkno(){
         return userWorkno;
    }

    /**
      * 设置 USERWORKNO 属性
      * @param userWorkno 员工工号
      */
    public void setUserWorkno(String userWorkno) {
        this.userWorkno=userWorkno;
    }

    /**
     * @return 部门，多个以;号隔开
     */
    public String getDeps(){
         return deps;
    }

    /**
      * 设置 DEPS 属性
      * @param deps 部门，多个以;号隔开
      */
    public void setDeps(String deps) {
        this.deps=deps;
    }

    /**
     * @return 出生日期(YYYYMMDD)
     */
    public int getBirtday(){
         return birtday;
    }

    /**
      * 设置 BIRTDAY 属性
      * @param birtday 出生日期(YYYYMMDD)
      */
    public void setBirtday(int birtday) {
        this.birtday=birtday;
    }

    /**
     * @return 性别0:女1:男
     */
    public boolean isGender(){
         return gender;
    }

    /**
      * 设置 GENDER 属性
      * @param gender 性别0:女1:男
      */
    public void setGender(boolean gender) {
        this.gender=gender;
    }

    /**
     * @return 移动电话
     */
    public String getMobile(){
         return mobile;
    }

    /**
      * 设置 MOBILE 属性
      * @param mobile 移动电话
      */
    public void setMobile(String mobile) {
        this.mobile=mobile;
    }

    /**
     * @return 固定电话
     */
    public String getHandfreePhone(){
         return handfreePhone;
    }

    /**
      * 设置 HANDFREEPHONE 属性
      * @param handfreePhone 固定电话
      */
    public void setHandfreePhone(String handfreePhone) {
        this.handfreePhone=handfreePhone;
    }

    /**
     * @return 联系地址
     */
    public String getAddress(){
         return address;
    }

    /**
      * 设置 ADDRESS 属性
      * @param address 联系地址
      */
    public void setAddress(String address) {
        this.address=address;
    }

    /**
     * @return Email
     */
    public String getEmail(){
         return email;
    }

    /**
      * 设置 EMAIL 属性
      * @param email Email
      */
    public void setEmail(String email) {
        this.email=email;
    }

    /**
     * @return 备注
     */
    public String getRemark(){
         return remark;
    }

    /**
      * 设置 REMARK 属性
      * @param remark 备注
      */
    public void setRemark(String remark) {
        this.remark=remark;
    }

    /**
     * @return QQ
     */
    public String getQq(){
         return qq;
    }

    /**
      * 设置 QQ 属性
      * @param qq QQ
      */
    public void setQq(String qq) {
        this.qq=qq;
    }

    /**
     * @return 状态0,正常;1,禁用,2,删除
     */
    public int getStates(){
         return states;
    }

    /**
      * 设置 STATES 属性
      * @param states 状态0,正常;1,禁用,2,删除
      */
    public void setStates(int states) {
        this.states=states;
    }

}
