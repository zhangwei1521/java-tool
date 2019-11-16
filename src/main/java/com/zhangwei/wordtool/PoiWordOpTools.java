package com.zhangwei.wordtool;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PoiWordOpTools {
    public static void main(String[] args) {
        Map<String,List<XWPFParagraph>> map = read();
        //write(map);
    }


    private static Map<String,List<XWPFParagraph>> read(){
        try (InputStream is = new FileInputStream("e:/tem_file/等线模板.docx");){
            XWPFDocument doc = new XWPFDocument(is);
            List<XWPFParagraph> paragraphList = doc.getParagraphs();
            int i=0;
            boolean flag=true;
            List<XWPFParagraph> newList = new ArrayList<>();
            Map<String,List<XWPFParagraph>> map = new HashMap<>();
            for (XWPFParagraph paragraph : paragraphList) {
                if(StringUtils.endsWith(paragraph.getText(),"预警通知书：")){
                    flag=false;
                }
                if(flag){
                    continue;
                }
                newList.add(paragraph);
                if(StringUtils.endsWith(paragraph.getText(),"xxxx年xx月xx日")){
                    flag=true;
                    map.put(newList.get(0).getText(),new ArrayList<>(newList));
                    newList=new ArrayList<>();
                }
            }
            for(List<XWPFParagraph> list : map.values()){
                list.forEach(item-> System.out.println(item.getText()));
                System.out.println("**************");
            }
            return map;
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    private static void write(Map<String,List<XWPFParagraph>> map){
        XWPFDocument document= new XWPFDocument();

        try (FileOutputStream out = new FileOutputStream(new File("e:/tem_file/PoiWordDemo.docx"));){

            for (List<XWPFParagraph> paragraphList : map.values()) {
                paragraphList.forEach(item->{
                    XWPFParagraph newParagraph = copyParagraph(document,item);
                });
               document.createParagraph().setPageBreak(true);
            }
            /*String title="五级预警通知书：";
            XWPFParagraph paragraph1 = createParagraph1(document,title);

            String studentName="**同学：";
            XWPFParagraph paragraph2 = createParagraph2(document,studentName);

            document.createParagraph().setPageBreak(true);

            String content = "中文数据中文数据中文数据，中文数据《中文数据中文数据》中文数据，中文数据中文数据中文数据。中文数据中文数据中文数据中文数据。中文数据中文数据中文数据，中文数据《中文数据中文数据》中文数据，中文数据中文数据中文数据。中文数据中文数据中文数据中文数据中文数据中文数据中文数据，中文数据《中文数据中文数据》中文数据，中文数据中文数据中文数据。中文数据中文数据中文数据中文数据中文数据中文数据中文数据，中文数据《中文数据中文数据》中文数据，中文数据中文数据中文数据。中文数据中文数据中文数据中文数据";
            XWPFParagraph paragraph3 = createParagraph3(document,content);
*/
            document.write(out);
            out.close();
            System.out.println("write success.");

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static XWPFParagraph copyParagraph(XWPFDocument document,XWPFParagraph source){
        XWPFParagraph paragraph1 = document.createParagraph();
        try {
            source.getRuns().forEach(item->{
                item.getCTR();
                XWPFRun paragraph1Run = paragraph1.createRun();

            });

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        //BeanUtils.copyProperties(source,paragraph1,"wordWrap");
        //paragraph1.setWordWrap(source.isWordWrap());
        //XWPFRun paragraph1Run = paragraph1.createRun();
        //paragraph1Run.setText(source.getText());

        //BeanUtils.copyProperties(source.getRuns().get(0),paragraph1Run);
        return source;
    }

    private static XWPFParagraph createParagraph1(XWPFDocument document,String text){
        XWPFParagraph paragraph1 = document.createParagraph();
        paragraph1.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun paragraph1Run = paragraph1.createRun();
        paragraph1Run.setColor("000000");
        paragraph1Run.setFontSize(16);
        paragraph1Run.setBold(true);
        paragraph1Run.setUnderline(UnderlinePatterns.SINGLE);
        paragraph1Run.setText(text);

        paragraph1Run.setFontFamily("15");
        return paragraph1;
    }

    private static XWPFParagraph createParagraph2(XWPFDocument document,String text){
        XWPFParagraph paragraph2 = document.createParagraph();
        XWPFRun paragraph2Run = paragraph2.createRun();
        paragraph2Run.setText(text);
        paragraph2Run.setColor("000000");
        paragraph2Run.setFontSize(12);
        return paragraph2;
    }

    private static XWPFParagraph createParagraph3(XWPFDocument document,String text){
        XWPFParagraph paragraph3 = document.createParagraph();
        paragraph3.setIndentationFirstLine(2);
        paragraph3.setSpacingAfterLines(10);

        XWPFRun paragraph3Run = paragraph3.createRun();
        paragraph3Run.setText(text);
        paragraph3Run.setColor("000000");
        paragraph3Run.setFontSize(12);
        paragraph3Run.setFontFamily("85");

        return paragraph3;
    }
}
