package com.zhangwei.wordtool;

import com.zhangwei.entity.Student;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import sun.misc.BASE64Encoder;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class WordOpTools {
    public static void main(String[] args) {
        //writeToWord();
        //dynamicWrite();
        //newDynamic();
        writeDocx();
    }

    private static void writeDocx(){
        Map<String,Object> dataMap = prepareData();

        try(
                StringWriter writer = new StringWriter();
                FileOutputStream docxOut = new FileOutputStream("dynamicWord.docx");
                ZipOutputStream zipout = new ZipOutputStream(docxOut);
            )
        {
            Configuration config = new Configuration(Configuration.VERSION_2_3_28);
            config.setDefaultEncoding(StandardCharsets.UTF_8.toString());
            URL url = Thread.currentThread().getClass().getResource("/");
            config.setDirectoryForTemplateLoading(new File(url.getFile().substring(1)));

            Template template = config.getTemplate("document.ftl",StandardCharsets.UTF_8.toString());
            template.process(dataMap,writer);

            ByteArrayInputStream documentInput = new ByteArrayInputStream(writer.toString().getBytes(StandardCharsets.UTF_8.toString()));

            File docxFile = new File(WordOpTools.class.getClassLoader().getResource("template.zip").getFile());
            /*if(docxFile.exists()){
                System.out.println(docxFile.getAbsolutePath());
                return;
            }
            if(true){
                System.out.println("fail to load file ");
                return;
            }*/
            ZipFile zipFile = new ZipFile(docxFile);
            Enumeration<? extends ZipEntry> zipEntrys = zipFile.entries();

            int len = -1;
            byte[] buffer = new byte[1024];
            while (zipEntrys.hasMoreElements()) {
                ZipEntry next = zipEntrys.nextElement();
                zipout.putNextEntry(new ZipEntry(next.getName()));
                InputStream zipInput = zipFile.getInputStream(next);
                if ("word/document.xml".equals(next.getName())){
                    while ((len = documentInput.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    documentInput.close();
                }
                else {
                    while ((len = zipInput.read(buffer)) != -1) {
                        zipout.write(buffer, 0, len);
                    }
                    zipInput.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void newDynamic(){
        Map<String,Object> dataMap = prepareData();
        Configuration config = new Configuration();
        config.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        File file = new File("dynamicWord2.doc");
        try(OutputStream fout = Files.newOutputStream(file.toPath());
            Writer writer = new BufferedWriter(new OutputStreamWriter(fout));
        ) {
            //Template template = config.getTemplate("职位申请表.ftl",StandardCharsets.UTF_8.toString());
            URL url = Thread.currentThread().getClass().getResource("/");
            config.setDirectoryForTemplateLoading(new File(url.getFile().substring(1)));
            Template template = config.getTemplate("newTemplate.ftl",StandardCharsets.UTF_8.toString());
            template.process(dataMap,writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Object> prepareData(){
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String currentDate = format.format(now);

        Map<String, Object> dataMap = new HashMap<>();
        List<Map<String,Object>> dataList = new ArrayList<>();
        dataMap.put("alarmArchiveList",dataList);

        Map<String,Object> map1 = new HashMap<>();
        map1.put("studentName","张三");
        map1.put("alarmResultName","2");
        map1.put("alarmYear","2018");
        map1.put("alarmTerm","第1");
        map1.put("collegeName","中文中文");
        map1.put("currentDate",currentDate);
        dataList.add(map1);

        Map<String,Object> map2 = new HashMap<>();
        map2.put("studentName","李四");
        map2.put("alarmResultName","1");
        map2.put("alarmYear","2018");
        map2.put("alarmTerm","第1");
        map2.put("collegeName","中文中文");
        map2.put("currentDate",currentDate);
        dataList.add(map2);

        Map<String,Object> map3 = new HashMap<>();
        map3.put("studentName","王五");
        map3.put("alarmResultName","5");
        map3.put("alarmYear","2018");
        map3.put("alarmTerm","第1");
        map3.put("collegeName","中文中文");
        map3.put("currentDate",currentDate);
        dataList.add(map3);

        return dataMap;
    }

    private static void dynamicWrite(){
        List<List<Student>> lists = new ArrayList<>();
        List<Student> list1 = new ArrayList<>();
        List<Student> list2 = new ArrayList<>();
        lists.add(list1);
        lists.add(list2);

        Student stu1 = new Student();
        stu1.setStuNum("1");
        list1.add(stu1);
        Student stu2 = new Student();
        stu2.setStuNum("2");
        list1.add(stu2);

        Student stu3 = new Student();
        stu3.setStuNum("3");
        list2.add(stu3);
        Student stu4 = new Student();
        stu4.setStuNum("5");
        list2.add(stu4);

        Map<String,List<List<Student>>> dataMap = new HashMap<>();
        dataMap.put("studentList",lists);

        Configuration config = new Configuration();
        config.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        File file = new File("dynamicWord1.doc");
        try(OutputStream fout = Files.newOutputStream(file.toPath());
            Writer writer = new BufferedWriter(new OutputStreamWriter(fout));
        ) {
            //Template template = config.getTemplate("职位申请表.ftl",StandardCharsets.UTF_8.toString());
            URL url = Thread.currentThread().getClass().getResource("/");
            config.setDirectoryForTemplateLoading(new File(url.getFile().substring(1)));
            Template template = config.getTemplate("template.ftl",StandardCharsets.UTF_8.toString());
            template.process(dataMap,writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeToWord(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("name","张三");
        dataMap.put("applyMajor","测试");
        dataMap.put("nowMajor","开发");
        dataMap.put("phoneNum","4568521348");
        dataMap.put("emailAddress","789564@163.com");
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

        try(InputStream imageInputStream = WordOpTools.class.getClassLoader().getResourceAsStream("bridge.jpg");) {
            byte[] imgData = new byte[imageInputStream.available()];
            imageInputStream.read(imgData);
            BASE64Encoder encoder = new BASE64Encoder();
            dataMap.put("myImage",encoder.encode(imgData));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Configuration config = new Configuration();
        config.setDefaultEncoding(StandardCharsets.UTF_8.toString());

        File file = new File("职位申请表_张三.doc");
        try(OutputStream fout = Files.newOutputStream(file.toPath());
            Writer writer = new BufferedWriter(new OutputStreamWriter(fout));
        ) {
            //Template template = config.getTemplate("职位申请表.ftl",StandardCharsets.UTF_8.toString());
            URL url = Thread.currentThread().getClass().getResource("/");
            config.setDirectoryForTemplateLoading(new File(url.getFile().substring(1)));
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
