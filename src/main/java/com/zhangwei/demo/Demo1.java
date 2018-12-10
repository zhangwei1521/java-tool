package com.zhangwei.demo;

import java.util.ArrayList;
import java.util.List;

public class Demo1 {
    public static void main(String[] args) {
        Integer x = new Integer(55);
        String s = ""+x;
        System.out.println(s);
        List list = new ArrayList();
        System.out.println(list.size());
        System.out.println(String.class);
        System.out.println(Object.class);
        System.out.println(Object[].class);
        //list.stream().forEach(item-> System.out.println(item.toString()));
        int sid =(-1 << 12);
        sid = -1 ^(-1 << 12);
        System.out.println(sid);
        System.out.println(Long.toBinaryString(-1));
        System.out.println(Long.toBinaryString((-1<<12)));
        System.out.println(Long.toBinaryString(Long.valueOf(sid)));
    }
}
