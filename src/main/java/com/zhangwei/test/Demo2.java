package com.zhangwei.test;

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
