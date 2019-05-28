package com.zhangwei.exceltool;

import com.zhangwei.entity.Student;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static void main(String[] args) {
        testStyle();

    }

    private static void testStyle(){
        // 创建工作簿
        Workbook wb = new XSSFWorkbook();

        // 创建一个工作表sheet
        Sheet sheet = wb.createSheet("测试样式");

        //首行
        Row row = sheet.createRow(1);
        row.setHeight((short) 606);
        Cell cell = row.createCell(0);
        cell.setCellValue("学院（系）    届毕业班学生审查情况报告表");
        cell.setCellStyle(createCellStyle(wb));
        XSSFFont font = createFont(wb);
        font.setFontHeightInPoints((short)14);
        font.setBold(true);
        cell.getCellStyle().setFont(font);
        for(int i=1;i<10;i++){
            cell = row.createCell(i);
            cell.setCellValue("");
            cell.setCellStyle(createCellStyle(wb));
        }
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,9));

        row = sheet.createRow(2);
        row.setHeight((short) 606);

        String[] columns = {"序号","名称","总人数","合格人数","待确定人数","淘汰人数","继续人数","认证人数","未认证人数","备注"};
        double[] widths = {3.57, 21.57, 12.43, 15.57, 11.14, 11.14, 11.14, 22.71, 22.71, 8.71};
        for(int i=0;i<columns.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(columns[i]);

            cell.setCellStyle(createCellStyle(wb));
            font = createFont(wb);
            font.setFontHeightInPoints((short)9);
            font.setBold(true);
            cell.getCellStyle().setFont(font);
            sheet.setColumnWidth(i,(int)(widths[i]*256));
        }
        row = sheet.createRow(3);
        row.setHeight((short) 606);
        for(int i=0;i<columns.length;i++) {
            cell = row.createCell(i);
            cell.setCellValue("xxxx");
            if(i==0){
                cell.setCellValue(i+1);
            }
            cell.setCellStyle(createCellStyle(wb));
            font = createFont(wb);
            font.setFontHeightInPoints((short)9);
            cell.getCellStyle().setFont(font);
        }

        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            wb.write(byteArrayOutputStream);
            byte[] bytesArray = byteArrayOutputStream.toByteArray();
            byte[] buffer = new byte[1024];
            ByteArrayInputStream inputStream = new ByteArrayInputStream(bytesArray);
            FileOutputStream outputStream = new FileOutputStream("e:/tem_file/testStyle.xlsx");
            int len = inputStream.read(buffer);
            while (len != -1) {
                outputStream.write(buffer, 0, len);
                len = inputStream.read(buffer);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        System.out.println("写出Excel结束");
    }

    private static XSSFCellStyle createCellStyle(Workbook wb){
        XSSFCellStyle cellStyle = ((XSSFWorkbook) wb).createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        cellStyle.setWrapText(true);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }

    private static XSSFFont createFont(Workbook wb){
        XSSFFont font = ((XSSFWorkbook) wb).createFont();
        font.setFontName("宋体");
        return font;
    }

    private static void testWrite(){
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
                cell.setCellValue(value == null ? "" : value.toString());
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
        outputStream.flush();
        outputStream.close();

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
