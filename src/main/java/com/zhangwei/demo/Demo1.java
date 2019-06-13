package com.zhangwei.demo;

import java.util.Calendar;

public class Demo1 {
    public static void main(String[] args) {
        Calendar today = Calendar.getInstance();
        System.out.println(today.get(Calendar.MONTH));
    }
}
