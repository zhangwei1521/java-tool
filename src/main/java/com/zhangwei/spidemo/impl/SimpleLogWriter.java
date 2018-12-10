package com.zhangwei.spidemo.impl;

import com.zhangwei.spidemo.inteface.LogWriter;

public class SimpleLogWriter implements LogWriter {
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
