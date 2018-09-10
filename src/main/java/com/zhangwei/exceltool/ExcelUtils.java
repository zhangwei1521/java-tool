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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static void main(String[] args) {
        /*try{
            Student s1 = new Student("12345","tom",21,"art","2018","national-music");
            Student s2 = new Student("12346","jerry",22,"science","2017","phisics");
            List<Student> students = new ArrayList<>();
            students.add(s1);
            students.add(s2);
            writeToExcel(students);
        } catch (Exception e){
            e.printStackTrace();
        }*/

        try{
            InputStream inputStream = new FileInputStream("e:/zhangweei/files/test.xls");
            List<Student> list = readFromExcel(inputStream,Student.class);
            for(Student student : list){
                System.out.println(student.toString());
            }
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

    public static <T> List<T> readFromExcel(InputStream inputStream, Class<T> clazz) throws Exception {
        List<T> resultList = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        Map<String,Field> fieldMap = new HashMap<>();
        for(Field field : fields){
            fieldMap.put(field.getName(),field);
        }
        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        Row title = sheet.getRow(0);
        int columnsCount = title.getPhysicalNumberOfCells();
        String[] titles = new String[columnsCount];
        for(int i = 0; i < columnsCount; i++){
            titles[i] = title.getCell(i).getStringCellValue();
        }
        int rowIndex = 0;
        Row row = null;
        Cell cell = null;
        T obj = null;
        for(Iterator<Row> iterator = sheet.rowIterator(); iterator.hasNext();){
            row = iterator.next();
            if(rowIndex++ == 0){
                continue;
            }
            if(row == null){
                break;
            }
            obj = (T)clazz.newInstance();
            for(int i=0; i < columnsCount; i++){
                cell = row.getCell(i);
                Object cellValue = null;
                /*if(cell != null){
                    cellValue = resolve(cell);
                }*/
                String fieldName = titles[i];
                Field field = fieldMap.get(fieldName);
                field.setAccessible(true);
                if(field.getType().equals(String.class)){
                    cellValue = cell.getStringCellValue();
                    field.set(obj,(String)cellValue);
                }
                else if(field.getType().equals(Boolean.class)){
                    cellValue = cell.getBooleanCellValue();
                    field.set(obj,(Boolean)cellValue);
                }
                else if(field.getType().equals(Integer.class)){
                    cellValue = cell.getStringCellValue();
                    field.set(obj,Integer.parseInt(cellValue.toString()));
                }
                else {
                    field.set(obj,null);
                }
            }
            resultList.add(obj);
        }
        return resultList;
    }
}
