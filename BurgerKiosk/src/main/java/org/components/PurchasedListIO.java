package org.components;

/*
# 2023/09/10 hyeongjun Lim
# 결재 시 호출되는 readWrite() 함수를 구현한 class
# 초기 file 이 없을 경우, createAtFirst 를 통해 생성하고 메뉴 탭을 만들며
# 결재한 메뉴들을 날짜, 메뉴, 수량, 비용 으로 purchased list 파일에 출력, 저장함.
 */


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PurchasedListIO {
    private static final String filePath = "src/main/output/purchased list.xlsx";
    
    //파일이 없을 경우 새로 생성하며, 0번째 행에 헤더 생성
    public static void createAtFirst(){
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            XSSFSheet sheet = workbook.createSheet("Data");

            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            headerStyle.setBorderBottom(BorderStyle.DOUBLE);
            headerStyle.setFillForegroundColor(new XSSFColor(new byte[] {(byte) 226,(byte) 239,(byte) 217}, null));
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);

            Row row = sheet.createRow(0);
            Cell cell;
            cell = row.createCell(0);
            cell.setCellValue("날짜");
            cell = row.createCell(1);
            cell.setCellValue("메뉴");
            cell = row.createCell(2);
            cell.setCellValue("수량");
            cell = row.createCell(3);
            cell.setCellValue("판매 금액");

            for(int i=0; i<4; i++){
                cell = row.getCell(i);
                cell.setCellStyle(headerStyle);
                sheet.setColumnWidth(i, 1024*4);
            }

            try (FileOutputStream fo = new FileOutputStream(filePath)) {
                workbook.write(fo);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void readWrite() {
        //파일이 없을 경우 create at first 호출
        if (!(new File(filePath).exists())) {
            createAtFirst();
        }

        //결제 완료 버튼을 누를 당시 orderedList 에 있는 항목들을 엑셀 파일로 출력함
        try (InputStream in = new BufferedInputStream(new FileInputStream(filePath))) {
            XSSFWorkbook workbook = new XSSFWorkbook(in);
            Sheet sheet = workbook.getSheet("Data");

            //파일의 마지막 행을 받아와 거기서부터 입력
            int lastRowIndex = sheet.getLastRowNum();
            Row row;
            Cell cell;
            for (OrderItem item : OrderList.orderedList) {
                row = sheet.createRow(++lastRowIndex);

                cell = row.createCell(0);
                LocalDate now = LocalDate.now();
                cell.setCellValue(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                cell = row.createCell(1);
                cell.setCellValue(item.getMenu());
                cell = row.createCell(2);
                cell.setCellValue(item.getCnt());
                cell = row.createCell(3);
                cell.setCellValue(item.getCost() * item.getCnt());
            }

            // 파일 저장
            try (FileOutputStream fo = new FileOutputStream(filePath)) {
                workbook.write(fo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
