package com.zhangwei.pdftool;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PDFOpTools {



    public static void main(String[] args) {
        writeTablePDF();
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
        pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        pdfPCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
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
