package com.zhangwei.demo;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Demo2 {
    public static void main(String[] args) {
        URL url = Demo2.class.getClassLoader().getResource("职位申请表.ftl");
        try {
            System.out.println(URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
