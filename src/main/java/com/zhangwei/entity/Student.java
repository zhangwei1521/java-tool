package com.zhangwei.entity;

public class Student {
    private String stuNum;
    private String name;
    private Integer age;
    private String collegeName;
    private String grade;
    private String majorName;

    public Student(String stuNum, String name, Integer age, String collegeName, String grade, String majorName) {
        this.stuNum = stuNum;
        this.name = name;
        this.age = age;
        this.collegeName = collegeName;
        this.grade = grade;
        this.majorName = majorName;
    }

    public Student(){

    }

    @Override
    public String toString() {
        return "Student{" +
                "stuNum='" + stuNum + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", collegeName='" + collegeName + '\'' +
                ", grade='" + grade + '\'' +
                ", majorName='" + majorName + '\'' +
                '}';
    }
}
