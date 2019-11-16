package com.zhangwei.entity;

public class Student {
    private String stuNum;
    private String name;
    private Integer age;
    private String collegeName;
    private String grade;
    private String majorName;

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    public String getStuNum() {
        return stuNum;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public String getGrade() {
        return grade;
    }

    public String getMajorName() {
        return majorName;
    }

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
