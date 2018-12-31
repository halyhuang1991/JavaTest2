package com.test.Excel;

import java.io.File;
import java.util.Date;
import jxl.*;
import jxl.write.*;
import jxl.write.Number;

public class TestExcel {
    public static void test() {
        try {
            WritableWorkbook workbook = Workbook.createWorkbook(new File("E:\\output.xls"));
            WritableSheet sheet = workbook.createSheet("First Sheet", 0);
            Label label = new Label(0, 2, "A label record");
            sheet.addCell(label);
            Number number = new Number(3, 4, 3.1459);
            sheet.addCell(number);
            WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 10);
            WritableCellFormat arial10format = new WritableCellFormat(arial10font);
            Label label2 = new Label(1, 0, "Arial 10 point label", arial10format);
            sheet.addCell(label2);
            // Cell a1 = sheet.getCell(0, 0);
            // Cell b2 = sheet.getCell(1, 1);
            // Cell c2 = sheet.getCell(2, 1);
            // String stringa1 = a1.getContents();
            // String stringb2 = b2.getContents();
            // String stringc2 = c2.getContents();
            // stringa1 = null;
            // double numberb2 = 0;
            // Date datec2 = null;
            // Cell a11 = sheet.getCell(0, 0);
            // Cell b11 = sheet.getCell(1, 1);
            // Cell c21 = sheet.getCell(2, 1);
            // if (a1.getType() == CellType.LABEL) {
            // LabelCell lc = (LabelCell) a1;
            // stringa1 = lc.getString();
            // }
            // if (b2.getType() == CellType.NUMBER) {
            // NumberCell nc = (NumberCell) b2;
            // numberb2 = nc.getValue();
            // }
            // if (c2.getType() == CellType.DATE) {
            // DateCell dc = (DateCell) c2;
            // datec2 = dc.getDate();
            // }

            workbook.write();
            workbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}