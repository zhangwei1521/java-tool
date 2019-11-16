package com.zhangwei.pdftool;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.zhangwei.entity.Student;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PDFOpTools {



    public static void main(String[] args) {
        //writeNewPDF();
        writeTablePDF();
        //writeNewTable(new Student());
    }


    private static void writeSimplePDF(){
        Document document = new Document();
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document,new FileOutputStream("/tem_file/simplepdf.pdf"));
            document.open();
            document.add(new Paragraph("hello world"));
            document.close();
            pdfWriter.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeNewPDF(){
        Font cnFont = getChineseFont(14);

        Rectangle pageSize = new RectangleReadOnly(500,300);
        Document document = new Document(pageSize,18,18,30,18);
        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document,new FileOutputStream("/tem_file/请假确认函.pdf"));
            document.open();
            Paragraph parag1 = new Paragraph("请假确认函",getChineseFont(16));
            parag1.setAlignment(Element.ALIGN_CENTER);
            document.add(parag1);

            document.add(new Paragraph("\n"));

            Paragraph parag2 = new Paragraph("xx部xx员工（工号：xx）：",cnFont);
            parag2.setAlignment(Element.ALIGN_LEFT);
            document.add(parag2);

            document.add(new Paragraph("\n"));

            Paragraph parag3 = new Paragraph("经研究，同意你因事请假xxx天（自xx年xx月xx日至xx年xx月xx日止）。",cnFont);
            parag3.setFirstLineIndent(28);
            parag3.setAlignment(Element.ALIGN_LEFT);
            document.add(parag3);

            document.add(new Paragraph("\n"));

            Paragraph parag4 = new Paragraph("xx部",cnFont);
            parag4.setAlignment(Element.ALIGN_RIGHT);
            document.add(parag4);

            Paragraph parag5 = new Paragraph("xx年xx月xx日",cnFont);
            parag5.setAlignment(Element.ALIGN_RIGHT);
            document.add(parag5);

            document.close();
            pdfWriter.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void writeNewTable(Student student){
        try {
            Document document = new Document(PageSize.A4);
            PdfWriter pdfWriter = PdfWriter.getInstance(document,new FileOutputStream("/tem_file/ApplyTable.pdf"));
            document.setMargins(30, 30, 20, 20);
            document.open();

            final PdfPTable table = new PdfPTable(4);
            table.setHeaderRows(3);
            //table.setFooterRows(1);
            table.setWidthPercentage(100);
            table.setTotalWidth(120);
            table.setWidths(new float[]{30,30,32,30});

            int fontSize1 = 16;
            int fontSize2 = 12;
            float borderSize = 0.5f;
            int titleHeight = 30;
            //int lineHeight = 20;

            PdfPCell cell = createPdfPCell("xxxxxxxxxxxxxxxx申请表",fontSize1,4,3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(0);
            cell.setFixedHeight(titleHeight);
            table.addCell(cell);


            cell = createPdfPCell("申请xxxxxxx",fontSize2,1,1);
            cell.setFixedHeight(0.0f);
            cell.setBorderWidthTop(borderSize);

            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthTop(borderSize);
            table.addCell(cell);
            cell = createPdfPCell("xxxxxxx归属单位",fontSize2,1,1);
            cell.setBorderWidthTop(borderSize);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthTop(borderSize);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("工号",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell("姓名",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("原职位",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell("原单位",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("工龄",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell("大区",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("联系电话\n",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell("联系邮箱\n",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("原职称\n",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell("申请岗位职称\n",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("原岗位工作情况",fontSize2,4,1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("工作成果",fontSize2,2,1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
            cell = createPdfPCell("绩效考核",fontSize2,2,1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("同事A",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(null==null?"":null,fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell("同事B",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("经理A",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell("经理B",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            //---
            cell = createPdfPCell("主任A",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell("主任B",fontSize2,1,1);
            cell.setFixedHeight(0.0f);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("客户A",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell("客户B",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("单位综合",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell("客户综合",fontSize2,1,1);
            cell.setFixedHeight(0.0f);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,1,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("其他情况",fontSize2,4,1);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            //===
            cell = createPdfPCell("其他情况A",fontSize2,1,3);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = createPdfPCell("请假",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,2,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("加班",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,2,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("奖励",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,2,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            //===
            cell = createPdfPCell("其他情况B",fontSize2,1,2);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = createPdfPCell("迟到",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,2,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("早退",fontSize2,1,1);
            table.addCell(cell);
            cell = createPdfPCell(student.getStuNum(),fontSize2,2,1);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("上级经理意见：",fontSize2,4,1);
            cell.setFixedHeight(60);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);
            cell = createPdfPCell("部门经理意见：",fontSize2,4,1);
            cell.setFixedHeight(60);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);
            cell = createPdfPCell("部门负责人意见：",fontSize2,4,1);
            cell.setFixedHeight(60);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);
            cell = createPdfPCell("公司总经理意见：",fontSize2,4,1);
            cell.setFixedHeight(60);
            cell.setVerticalAlignment(Element.ALIGN_TOP);
            cell.setBorderWidthRight(borderSize);
            table.addCell(cell);

            cell = createPdfPCell("说明：1）附本人工作考勤一份；",fontSize2,4,1);
            cell.setBorder(0);
            table.addCell(cell);
            cell = createPdfPCell("2）xxxxxxx不能和xxxxxxx相同。如果xxxxxxxx，“xxxxxxxxxxx”这部分可以不显示。如果xxxxx，就xxxxx",fontSize2,4,1);
            cell.setBorder(0);
            cell.setFixedHeight(0.0f);
            cell.setLeading(0.0f,1.5f);
            cell.setIndent(36);
            table.addCell(cell);

            cell = createPdfPCell("年     月     日填",fontSize2,4,1);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            cell.setBorder(0);
            table.addCell(cell);

            cell = createPdfPCell("\n",fontSize2,4,3);
            cell.setBorder(0);
            table.addCell(cell);

            document.add(table);
            document.close();
            pdfWriter.close();
            System.out.println("导出PDF成功");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void writeTablePDF(){
        String grade = "二零一八";
        String companyName = "xxx公司";
        String departmentName = "yyy部门";
        int year = 2019;
        int month = 5;
        int total = 51,newSum=20,continueSum=20,leaveSum=11,originJobSum=40,newJobSum=11;
        String[] tablesHead = {"编号","姓名","性别","出生年月","职位类型","职位","工号","身份证号","签名","备注"};
        List<Map<String,String>> dataList = new ArrayList<>();

        for(int i=1;i<=50;i++){
            Map<String,String> dataItem = new LinkedHashMap<>();
            dataItem.put("编号","编号"+i);
            dataItem.put("姓名","姓名"+i);
            dataItem.put("性别","性别"+i);
            dataItem.put("出生年月","出生年月"+i);
            dataItem.put("职位类型","职位类型"+i);
            dataItem.put("职位","职位"+i);
            dataItem.put("工号","工号"+i);
            dataItem.put("身份证号","身份证号"+i);
            dataItem.put("签名","签名"+i);
            dataItem.put("备注","备注"+i);
            dataList.add(dataItem);
        }
        try {
            Document document = new Document(PageSize.A4.rotate());
            PdfWriter pdfWriter = PdfWriter.getInstance(document,new FileOutputStream("/tem_file/tablepdf.pdf"));
            PdfPageEventHelper pageEventHelper = new PdfPageHelper();
            pdfWriter.setPageEvent(pageEventHelper);
            document.setMargins(30, 45, 20, 30);
            document.open();

            document.add(new Paragraph("xxxxxxxxxxxxxxxx"));
            document.newPage();

            final PdfPTable table = new PdfPTable(10);
            table.setHeaderRows(4);
            table.setFooterRows(1);
            table.setWidthPercentage(100);
            table.setTotalWidth(120);
            table.setWidths(new float[]{10,12,8,12,10,8,20,20,15,15});

            PdfPCell cell = createPdfPCell(grade+"年职工信息登记表",15,10,1);
            cell.setBorder(0);
            cell.setFixedHeight(20);
            table.addCell(cell);
            cell = createPdfPCell(companyName,13,3,1);
            cell.setBorder(0);
            table.addCell(cell);
            cell = createPdfPCell(departmentName,13,4,1);
            cell.setBorder(0);
            table.addCell(cell);
            cell = createPdfPCell(year+"年"+month+"月",13,3,1);
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);

            for(String head : tablesHead){
                cell = createPdfPCell(head,13,1,1);
                cell.setBorderWidthLeft(0);
                cell.setBorderWidthRight(0);
                table.addCell(cell);
            }

            cell = createPdfPCell("本部门人数合计："+total+"人 新签："+newSum+"人 续签："+continueSum+"人 离职："+leaveSum+"人 原职："+originJobSum+"人 新职："+newJobSum+"人",13,10,1);
            cell.setBorder(0);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            for(Map<String,String> dataItem : dataList){
                for(String value : dataItem.values()){
                    cell = createPdfPCell(value,11,1,1);
                    table.addCell(cell);
                }
            }
            document.add(table);
            document.close();
            pdfWriter.close();
            System.out.println("导出PDF成功");
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    private static PdfPCell createPdfPCell(String text, int fontSize, int colSpan, int rowSpan){
        PdfPCell pdfPCell = new PdfPCell();
        pdfPCell.setColspan(colSpan);
        pdfPCell.setRowspan(rowSpan);
        pdfPCell.setPhrase(createParagraph(text,fontSize));
        pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        pdfPCell.setBorderWidthTop(0);
        pdfPCell.setBorderWidthRight(0);
        pdfPCell.setFixedHeight(20);
        return pdfPCell;
    }

    private static Paragraph createParagraph(String text,int size){
        Paragraph paragraph;
        paragraph = new Paragraph(text, getChineseFont(size));
        paragraph.setAlignment(Phrase.ALIGN_CENTER);
        return paragraph;
    }

    private static Font getChineseFont(int size){
        BaseFont baseFontChinese = null;
        try {
            baseFontChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font fontChinese = new Font(baseFontChinese, size);
        return fontChinese;
    }

}
