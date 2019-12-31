package com.zhangwei.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class JsonDemo {
    public static void main(String[] args) {
       //test01();
       test02();
    }

    private static void test01(){
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "[{\"courseName\":\"生物信息学\",\"courseTypeName\":\"专必\",\"credit\":\"3\",\"period\":\"54\",\"schoolYearSemester\":\"2018-2\",\"gradePoint\":0.0,\"totalCredit\":0.0}]";
        try {
            List<Map<String,String>> list = objectMapper.readValue(json,List.class);
            Map<String,String> obj = list.get(0);
            System.out.println(obj.get("finishRate"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void test02() {
        Object a = (Object)null;
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String,String>> list = null;
        try {
            list = objectMapper.readValue("[]",List.class);
        } catch (Exception e){
            e.printStackTrace();
        }
        if(list==null || list.size()<1){
            System.out.println("empty list");
        }
        else {
            Map<String,String> obj = list.get(0);
            if(obj!=null){
                System.out.println(obj);
            }
        }
    }
}
