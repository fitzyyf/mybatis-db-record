package com.nutshell.ntztool;

import com.nutshell.ntztool.generate.GenerateService;
import com.nutshell.ntztool.generate.IGenerateService;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        IGenerateService service = new GenerateService();
        service.generateFile(args[0]);
    }
}
