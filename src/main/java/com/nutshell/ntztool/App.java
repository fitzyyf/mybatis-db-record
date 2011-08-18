package com.nutshell.ntztool;

import com.nutshell.ntztool.generate.GenerateService;
import com.nutshell.ntztool.generate.IGenerateService;
import com.nutshell.ntztool.util.ResourceUtils;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        ResourceUtils.getInstance();
        IGenerateService service = new GenerateService();
        service.generateFile(args[0]);
    }
}
