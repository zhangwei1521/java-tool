package com.zhangwei.demo;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Demo2 {
    public static void main(String[] args) {
        printClassPath();
    }

    private static void printClassPath(){
        String classpath = System.getProperty("java.class.path");
        String [] arr = classpath.split(";");
        for(String path : arr){
            System.out.println(path);
        }
    }
}
