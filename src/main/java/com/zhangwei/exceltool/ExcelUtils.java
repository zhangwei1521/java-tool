package com.zhangwei.exceltool;

import com.zhangwei.entity.Student;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ExcelUtils {

    public static void main(String[] args) {
        try{
            Student s1 = new Student("12345","tom",21,"art","2018","national-music");
            Student s2 = new Student("12346","jerry",22,"science","2017","phisics");
            List<Student> students = new ArrayList<>();
            students.add(s1);
            students.add(s2);
            writeToExcel(students);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static <T> void writeToExcel(List<T> list) throws Exception {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        Row row = sheet.createRow(0);
        Cell cell = null;
        CreationHelper creationHelper = workbook.getCreationHelper();
        if(list == null || list.size() < 1){
            return;
        }
        Field[] fields = list.get(0).getClass().getDeclaredFields();
        HSSFCellStyle titleStyle = ((HSSFWorkbook) workbook).createCellStyle();
        titleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setFillForegroundColor(HSSFColor.GREEN.index);
        titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
        Font titleFont = workbook.createFont();
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        titleFont.setColor(HSSFColor.BLACK.index);
        titleStyle.setFont(titleFont);

        row.setHeight((short)(2*256));
        Field.setAccessible(fields,true);
        int i = 0;
        for(Field field : fields){
            sheet.setColumnWidth(i,20*256);
            String title = field.getName();
            cell = row.createCell(i++);
            cell.setCellStyle(titleStyle);
            cell.setCellValue(title);
        }

        CellStyle contentStyle = workbook.createCellStyle();
        contentStyle.setAlignment(CellStyle.ALIGN_CENTER);
        contentStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        contentStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);

        i = 1;
        for(T t : list){
            row = sheet.createRow(i++);
            int j = 0;
            Object value;
            Field.setAccessible(fields,true);
            for (Field field : fields){
                cell = row.createCell(j++);
                cell.setCellStyle(contentStyle);
                value = field.get(t);
                cell.setCellValue(value.toString());
            }
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        byte[] bytesArray = byteArrayOutputStream.toByteArray();
        byte[] buffer = new byte[1024];
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytesArray);
        FileOutputStream outputStream = new FileOutputStream("e:/zhangweei/files/test.xls");
        int len = inputStream.read(buffer);
        while (len != -1) {
            outputStream.write(buffer, 0, len);
            len = inputStream.read(buffer);
        }
        System.out.println("写出Excel结束");
    }

}
