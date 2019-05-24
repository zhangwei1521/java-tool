package com.zhangwei.wordtool;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WordOpTools {
    public static void main(String[] args) {
        writeToWord();
    }

    private static void writeToWord(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("name","");
        dataMap.put("applyMajor","");
        dataMap.put("nowMajor","");
        dataMap.put("phoneNum","");
        dataMap.put("emailAddress","");
        dataMap.put("workExperienceList",new ArrayList<>());
        for(int i=1;i<3;i++){
            ArrayList list = (ArrayList)dataMap.get("workExperienceList");
            Map<String,String> item = new HashMap<>();
            item.put("workName","user"+i);
            item.put("workTime",String.valueOf(2017+i));
            item.put("workSumary","sumary"+i);
            item.put("targetName","target"+i);
            item.put("targetTime",String.valueOf(2020+i));
            item.put("targetMethod","method"+i);
            list.add(item);
        }

        Configuration config = new Configuration();
        config.setDefaultEncoding(StandardCharsets.UTF_8.toString());

        File file = new File("职位申请表_张三.doc");
        try(OutputStream fout = Files.newOutputStream(file.toPath());
            Writer writer = new BufferedWriter(new OutputStreamWriter(fout));
        ) {
            Template template = config.getTemplate("职位申请表.ftl",StandardCharsets.UTF_8.toString());
            template.process(dataMap,writer);
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }
}
