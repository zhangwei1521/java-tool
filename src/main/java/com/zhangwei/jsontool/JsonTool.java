package com.zhangwei.jsontool;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonTool {
    private final static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }

    public static <T> T getPojoFromJson(String json, Class<T> clazz){
        try {
            return objectMapper.readValue(json,clazz);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static <T> List<T> getListFromJson(String json, Class<T> clazz){
        try {
            List<Map<String, Object>> list = objectMapper.readValue(json, new TypeReference<List<T>>() {});
            List<T> result = new ArrayList<T>();
            for (Map<String, Object> map : list) {
                result.add(map2pojo(map, clazz));
            }
            return result;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T map2pojo(Map<String,Object> map, Class<T> clazz){
        return objectMapper.convertValue(map,clazz);
    }

}
