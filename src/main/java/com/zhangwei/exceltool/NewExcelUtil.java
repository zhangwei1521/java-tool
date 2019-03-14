package com.zhangwei.exceltool;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewExcelUtil {
    public static final Map<Integer,Character> COLNUM_LETTER_MAP = new HashMap<>();
    static {
        for(int i=0;i<26;i++){
            COLNUM_LETTER_MAP.put(i,Character.toChars('A'+i)[0]);
        }
    }
    public static void exportExcelTemplate(HttpServletRequest request,HttpServletResponse response, List<Map<String,String>> dataList, List<String> exportColumns, String[] specialReasons) throws IOException{
        response.setContentType("application/force-download");
        response.setCharacterEncoding("UTF-8");
        String fileName = "导出列表";
        if(request.getHeader("User-Agent") != null &&
                request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > -1) {
            fileName = "=?" + StandardCharsets.UTF_8.name() + "?B?" +
                    new String(Base64.encodeBase64(fileName.getBytes(StandardCharsets.UTF_8.name())), StandardCharsets.UTF_8) + "?=";
        }
        else {
            fileName = new String(URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()).getBytes(StandardCharsets.UTF_8.name()), "GB2312");
        }
        response.setHeader("Content-Disposition", "attachment;fileName=\""+ fileName + ".xlsx\"");

        writeWorkBook(response , dataList, exportColumns,specialReasons);
    }

    private static void writeWorkBook(HttpServletResponse response, List<Map<String,String>> dataList, List<String> exportColumns,String[] specialReasons) throws IOException {

        if (dataList == null || dataList.size() == 0){
            return;
        }
        // 创建工作簿
        Workbook wb = new XSSFWorkbook();
        // 创建一个工作表sheet
        String tableName = "工作表";
        Sheet sheet = wb.createSheet(tableName);
        //首行
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,exportColumns.size()-1));
        Row firstRow = sheet.createRow(0);
        firstRow.setHeight((short) 500);
        Cell firstCell = firstRow.createCell(0);
        firstCell.setCellValue(tableName);
        XSSFCellStyle cellStyle = ((XSSFWorkbook) wb).createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        firstCell.setCellStyle(cellStyle);
        Row headRow = sheet.createRow(1);
        headRow.setHeight((short) 400);
        Row subHeadRow = sheet.createRow(2);
        subHeadRow.setHeight((short)0);
        //设置标题
        int columnIndex = 0;
        for (String field : exportColumns) {
            sheet.setColumnWidth(columnIndex,5120);
            String title = field.split(";;")[1];
            Cell cell = headRow.createCell(columnIndex);
            cell.setCellValue(title);

            Cell ncell = subHeadRow.createCell(columnIndex);
            ncell.setCellValue(field.split(";;")[0]);
            columnIndex++;
        }

        //分项序号-权重map
        Map<Integer,String> achieveItemWeightMap = new HashMap<>();
        for (String field : exportColumns) {
            if(field.startsWith("subItem-")){
                String weight = field.substring(field.lastIndexOf('(')+1,field.lastIndexOf(')'));
                achieveItemWeightMap.put(exportColumns.indexOf(field),weight);
            }
        }

        Row row = sheet.createRow(3);
        row.setHeight((short)(500));
        Cell cell = null;
        int rowIndex = 3;

        int n = dataList.size();
        for (String column : exportColumns) {
            if (StringUtils.equals(column.split(";;")[0], "specialReason")) {
                //特殊原因下拉
                int specialResonColIndex = exportColumns.indexOf(column);
                XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(specialReasons);
                CellRangeAddressList addressList = new CellRangeAddressList(rowIndex, n + 2, specialResonColIndex, specialResonColIndex);
                XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
                sheet.addValidationData(validation);
            }
        }
        for (int i = 0 ; i < n ; i++) {
            row = sheet.createRow(rowIndex);
            columnIndex = 0;
            Object o = null;

            Map data = dataList.get(i);
            for (String field : exportColumns) {
                cell = row.createCell(columnIndex);
                //最终成绩公式
                if(StringUtils.equals(field.split(";;")[0],"finalAchievement")){
                    StringBuilder funcBuilder = new StringBuilder();
                    funcBuilder.append("SUM(");
                    for(Map.Entry<Integer,String> item : achieveItemWeightMap.entrySet()){
                        funcBuilder.append(String.valueOf(COLNUM_LETTER_MAP.get(item.getKey()))+(rowIndex+1));
                        funcBuilder.append("*");
                        funcBuilder.append(item.getValue());
                        funcBuilder.append(",");
                    }
                    funcBuilder.setCharAt(funcBuilder.length()-1,')');
                    cell.setCellFormula(funcBuilder.toString());
                }

                // 数据
                try{
                    o = data.get(field.split(";;")[0]);
                }catch (Exception e) {
                    o = null;
                }

                // 如果数据为空
                if (o == null){
                    cell.setCellValue("");
                    columnIndex++;
                    continue;
                }
                if(field.split(";;")[0].startsWith("subItem-") ||
                        StringUtils.equals(field.split(";;")[0],"totalAchievement") ||
                        StringUtils.equals(field.split(";;")[0],"finalAchievement")){
                    cell.setCellValue(Float.parseFloat(o.toString()));
                }
                else {
                    cell.setCellValue(data.get(field.split(";;")[0]).toString());
                }
                columnIndex++;
            }
            rowIndex++;
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        wb.write(os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int i = is.read(buffer);
        while (i != -1) {
            out.write(buffer, 0, i);
            i = is.read(buffer);
        }
        os.close();
    }
}