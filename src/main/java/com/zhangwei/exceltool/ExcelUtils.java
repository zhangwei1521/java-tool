package com.zhangwei.exceltool;

import org.apache.commons.lang3.StringUtils;
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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
        //列名数组
        String[] columns = new String[10];
        for(int i=0;i<10;i++){
            columns[i] = "列"+(i+1);
        }

        // 创建工作簿
        Workbook wb = new XSSFWorkbook();

        // 创建一个工作表sheet
        Sheet sheet = wb.createSheet("测试样式");

        //标题行
        Row row = sheet.createRow(1);
        //行高 1000--66像素
        row.setHeight((short) 1000);

        //标题单元格
        Cell cell = row.createCell(0);
        cell.setCellValue("这是标题");

        //标题单元格样式
        XSSFCellStyle cellStyle = ((XSSFWorkbook) wb).createCellStyle();
        cell.setCellStyle(cellStyle);
        setBorderForCell(cell);
        setAlignmentForCell(cell,"center","center");
        setWrapText(cell,true);

        //标题单元格字体
        cellStyle.setFont(((XSSFWorkbook) wb).createFont());
        setFontForCell(cell,"宋体",16,true,Color.RED);

        //因为标题单元格需要合并单元格，并且设置了边框，所以需要先创建这些被合并的单元格
        for(int i=1;i<columns.length;i++){
            cell = row.createCell(i);
            CellStyle style = wb.createCellStyle();
            cell.setCellStyle(style);
            setBorderForCell(cell);
        }
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,columns.length-1));

        //列名行
        row = sheet.createRow(2);
        row.setHeight((short) 600);

        //列宽
        double[] widths = {3, 3.5, 4, 5, 7.5, 10.5, 14, 18, 22.5, 27.5};
        String[] fonts = {"宋体","仿宋","新宋体","黑体","楷体","微软雅黑","微软雅黑 Light","Cambria","Candara","Courier New"};
        for(int i=0;i<columns.length;i++){
            cell = row.createCell(i);
            cell.setCellValue(columns[i]);

            XSSFCellStyle style = ((XSSFWorkbook)wb).createCellStyle();
            cell.setCellStyle(style);
            setBorderForCell(cell);
            setAlignmentForCell(cell,"center","center");

            style.setFont(((XSSFWorkbook) wb).createFont());
            //这里设置颜色为黑色，但是实际写出的文档中字体却显示白色，设置WHITE实际却是黑色
            setFontForCell(cell,fonts[i],6+i,true,Color.WHITE);

            sheet.setColumnWidth(i,(int)(widths[i]*256));
        }

        //数据行
        row = sheet.createRow(3);
        row.setHeight((short) 800);
        for(int i=0;i<columns.length;i++) {
            cell = row.createCell(i);
            cell.setCellValue("xxxx");
            if(i==0){
                cell.setCellValue(i+1);
            }
            XSSFCellStyle style = ((XSSFWorkbook)wb).createCellStyle();
            cell.setCellStyle(style);
            setBorderForCell(cell);
            setAlignmentForCell(cell,"center","center");
            setWrapText(cell,true);

            style.setFont(((XSSFWorkbook) wb).createFont());
            setFontForCell(cell,fonts[i],15-i,false, Color.CYAN);
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

    private static void setBorderForCell(Cell cell){
        checkCellAndCellStyle(cell);
        XSSFCellStyle cellStyle = (XSSFCellStyle)cell.getCellStyle();
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cell.setCellStyle(cellStyle);
    }

    private static void setAlignmentForCell(Cell cell, String horizontal, String vertical){
        checkCellAndCellStyle(cell);
        XSSFCellStyle cellStyle = (XSSFCellStyle)cell.getCellStyle();
        if(StringUtils.equals(horizontal,"left")){
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_LEFT);
        }
        if(StringUtils.equals(horizontal,"center")){
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        }
        if(StringUtils.equals(horizontal,"right")){
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        }
        if(StringUtils.equals(vertical,"top")){
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_TOP);
        }
        if(StringUtils.equals(vertical,"center")){
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        }
        if(StringUtils.equals(vertical,"bottom")){
            cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_BOTTOM);
        }
    }

    private static void setWrapText(Cell cell, boolean wrapSign){
        checkCellAndCellStyle(cell);
        XSSFCellStyle cellStyle = (XSSFCellStyle)cell.getCellStyle();
        cellStyle.setWrapText(true);
    }

    private static void setFontForCell(Cell cell,String fontName,int fontSize,boolean boldSign,Color color){
        checkCellAndCellStyle(cell);
        XSSFCellStyle cellStyle = (XSSFCellStyle)cell.getCellStyle();
        XSSFFont font = cellStyle.getFont();
        if(font != null && StringUtils.isNoneEmpty(fontName)){
            font.setFontName(fontName);
        }
        font.setFontHeightInPoints((short)fontSize);
        font.setBold(boldSign);
        byte [] colors = {(byte)color.getRed(),(byte)color.getGreen(),(byte)color.getBlue()};
        font.setColor(new XSSFColor(colors));
    }

    private static void checkCellAndCellStyle(Cell cell){
        if(null == cell || null == cell.getCellStyle()){
            throw new RuntimeException("参数不能为空");
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
        FileOutputStream outputStream = new FileOutputStream("e:/tem_file/test.xls");
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
