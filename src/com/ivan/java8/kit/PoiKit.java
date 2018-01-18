package com.ivan.java8.kit;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by feiFan.gou on 2017/11/28 16:52.
 */
public class PoiKit {


    @Test
    public void test() throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("test");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("哈哈哈哈");
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(setHeaderFont(workbook));
        cell.setCellStyle(style);
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.HSSFColorPredefined.BRIGHT_GREEN.getIndex());
        font.setBold(true);
        style.setFont(font);
        cell.setCellStyle(style);
        workbook.write(new File("d://java_excel.xls"));
        workbook.close();
    }


    private HSSFFont setHeaderFont(HSSFWorkbook workbook) {

        HSSFFont font = workbook.createFont();
        font.setBold(true);
        return font;
    }
}
