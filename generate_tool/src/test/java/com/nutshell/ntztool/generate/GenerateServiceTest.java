/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:GenerateServiceTest.java  11-6-8 上午1:37 poplar.mumu ]
 */
package com.nutshell.ntztool.generate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-8 上午1:37
 * @since JDK 1.0
 */
public class GenerateServiceTest {
    IGenerateService service ;
    @Before
    public void setUp() throws Exception {
        service = new GenerateService();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGenerateCode() throws Exception {
        service.generateEntityCode("mrp_s_userinfo", "D:\\");
    }
}
