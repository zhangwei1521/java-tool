package com.zhangwei.entity;

public class Student {
    private String stuNum;
    private String name;
    private int age;
    private String collegeName;
    private String grade;
    private String majorName;

    public Student(String stuNum, String name, int age, String collegeName, String grade, String majorName) {
        this.stuNum = stuNum;
        this.name = name;
        this.age = age;
        this.collegeName = collegeName;
        this.grade = grade;
        this.majorName = majorName;
    }
}
