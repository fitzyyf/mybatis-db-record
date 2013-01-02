/*
 * Copyright (c) 2010-2011 GOV.
 * [Id:ResourceUtilsTest.java  11-9-15 下午4:10 poplar.mumu ]
 */
package org.yfyang.osy.record.util;

import junit.framework.Assert;
import org.junit.Test;
import org.yfyang.osy.record.App;
import org.yfyang.osy.record.model.JdbcInfo;

import java.util.Properties;

/**
 * .
 * <br/>
 *
 * @author poplar.mumu
 * @version 1.0 11-9-15 下午4:10
 * @since JDK 1.5
 */
public class ResourceUtilsTest {

    private String testDriver="com.mysql.jdbc.Driver";

    private String testDataJdbc="java.util.Date";

    @Test
    public void testReadDataJdbcType() throws Exception {
        Properties dataJdbc = App.getInfo().readDataJdbcType();
        Assert.assertEquals(testDataJdbc,dataJdbc.get("date"));
    }

    @Test
    public void testGetJdbc() throws Exception {
       JdbcInfo jdbcInfo= App.getInfo().getJdbc();
        Assert.assertEquals(testDriver,jdbcInfo.getDriver());
    }

    @Test
    public void testGetProject() throws Exception {
        
    }

    @Test
    public void testGetCodeTemplate() throws Exception {

    }
}
