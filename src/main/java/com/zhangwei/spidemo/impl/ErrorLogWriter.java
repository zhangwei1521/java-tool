package com.zhangwei.spidemo.impl;

import com.zhangwei.spidemo.inteface.LogWriter;

public class ErrorLogWriter implements LogWriter {
    @Override
    public void log(String message) {
        System.err.println(message);
    }
}
