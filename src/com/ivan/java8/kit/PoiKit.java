package com.ivan.java8.kit;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by feiFan.gou on 2017/11/28 16:52.
 */
class PoiKit {


    @Test
    void test() throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("test");
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("哈哈哈哈");
        HSSFCellStyle style = workbook.createCellStyle();
        style.setFont(setHeaderFont(workbook));
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