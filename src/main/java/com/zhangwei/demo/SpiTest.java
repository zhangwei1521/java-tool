package com.zhangwei.demo;

import com.zhangwei.spidemo.inteface.LogWriter;

import java.util.ServiceLoader;

public class SpiTest {
    public static void main(String[] args) {
        ServiceLoader<LogWriter> logWriterList = ServiceLoader.load(LogWriter.class);
        for(LogWriter logWriter : logWriterList){
            logWriter.log("lllllll");
        }
    }
}
