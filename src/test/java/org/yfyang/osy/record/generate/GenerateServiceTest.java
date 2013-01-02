/*
 * Copyright (c) 2010-2011 GOV.
 * [Id:GenerateServiceTest.java  11-9-15 下午4:42 poplar.mumu ]
 */
package org.yfyang.osy.record.generate;

import org.junit.Before;
import org.junit.Test;

/**
 * .
 * <br/>
 *
 * @author poplar.mumu
 * @version 1.0 11-9-15 下午4:42
 * @since JDK 1.5
 */
public class GenerateServiceTest {

    private IGenerateService generateService ;
    @Before
    public void setUp() throws Exception {
        generateService = new GenerateService();
    }

    @Test
    public void testGenerateFile() throws Exception {
        generateService.generateFile("/test");
    }

    @Test
    public void testGenerateEntityCode() throws Exception {

    }


    @Test
    public void testGenerateMybatisXml() throws Exception {

    }
}
