/*
 * Copyright (c) 2010-2011 NutShell.
 * [Id:MapperUtilTest.java  11-6-11 下午7:42 poplar.mumu ]
 */
package com.nutshell.ntztool.generate;

import com.nutshell.ntztool.mybatis.MapperUtil;
import org.junit.Test;

/**
 * .
 * <br/>
 *
 * @author poplar_mumu
 * @version 1.0 11-6-11 下午7:42
 * @since JDK 1.0
 */
public class MapperUtilTest {
    @Test
    public void testGenerateInterMapper() throws Exception {
        System.out.println(MapperUtil.generateFacesMapper("表描述", "IFaces"));
    }
}
