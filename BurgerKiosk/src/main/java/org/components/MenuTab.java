package org.components;

/*
# 2023/09/10 hyeongjun Lim
# 클릭하여 주문할 수 있는 Menu item Button 으로 구성된 MenuPane
# food, drink, side 세가지 텝이 있으며 각각 JPanel 로 구현하여 jTabbedPane 에 추가함.
# MenuList 생성, Menu Button 추가 메서드가 있음
 */

import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MenuTab {
    private final ArrayList<MenuItem> foodList = new ArrayList<>();
    private final ArrayList<MenuItem> drinkList = new ArrayList<>();
    private final ArrayList<MenuItem> sideList = new ArrayList<>();

    public MenuTab(JFrame frame){
        JTabbedPane tabbedPane = new JTabbedPane();
        menuListInit();

        JPanel foodTab = new MenuPanel(foodList);
        JPanel drinkTab = new MenuPanel(drinkList);
        JPanel sideTab = new MenuPanel(sideList);

        tabbedPane.addTab("Food", foodTab);
        tabbedPane.addTab("Drink", drinkTab);
        tabbedPane.addTab("Side", sideTab);

        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    //Menu item 생성
    private void menuListInit() {
        Map<String, ArrayList<MenuItem>> listMap = new HashMap<>();

        //동적으로 변환 필요
        listMap.put("Food", foodList);
        listMap.put("Drink", drinkList);
        listMap.put("Side", sideList);

        try (FileInputStream fileInputStream = new FileInputStream("src/main/input/item list.xlsx");
             Workbook workbook = WorkbookFactory.create(fileInputStream)) {

            for(String sheetName : listMap.keySet()){
                Sheet sheet = workbook.getSheet(sheetName);
                Iterator<Row> rowIterator = sheet.iterator();
                rowIterator.next(); // Skip header row if present


                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    String menu = row.getCell(0).getStringCellValue();
                    int cost = (int) row.getCell(1).getNumericCellValue();
                    String image = row.getCell(2) == null ? null : row.getCell(2).getStringCellValue();

                    listMap.get(sheetName).add(new MenuItem(menu, cost, image));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
