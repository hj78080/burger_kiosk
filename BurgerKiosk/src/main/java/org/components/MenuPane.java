package org.components;

/*
# 2023/09/10 hyeongjun Lim
# 클릭하여 주문할 수 있는 Menu item Button 으로 구성된 MenuPane
# food, drink, side 세가지 텝이 있으며 각각 JPanel 로 구현하여 jTabbedPane 에 추가함.
# MenuList 생성, Menu Button 추가 메서드가 있음
 */

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class MenuPane {
    private ArrayList<MenuItem> foodList;
    private ArrayList<MenuItem> drinkList;
    private ArrayList<MenuItem> sideList;

    public MenuPane(JFrame frame){
        JTabbedPane tabbedPane = new JTabbedPane();

        menuListInit();

        JPanel foodTab = createItemGridPanel(foodList);
        JPanel drinkTab = createItemGridPanel(drinkList);
        JPanel sideTab = createItemGridPanel(sideList);

        tabbedPane.addTab("Food", foodTab);
        tabbedPane.addTab("Drink", drinkTab);
        tabbedPane.addTab("Side", sideTab);

        frame.getContentPane().setBackground(Color.DARK_GRAY);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    //4*3 Grid Layout 에 button 생성. Menu item 이 있을 경우 Menu item 버튼을 생성하며, 없을 경우 빈 버튼을 생성해 그리드를 채움
    private JPanel createItemGridPanel(ArrayList<MenuItem> list){
        JPanel panel = new JPanel(new GridLayout(4, 3));

        for(MenuItem item : list){
            ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource(item.image())).getPath());
            JButton button = new JButton(
                    "<html><center>" + item.menu() + "<br>" + item.getFormattedCost()+"</center></html>", imageIcon);
            Font font = button.getFont();
            button.setFont(new Font(font.getName(), font.getStyle(), 11));
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            button.setBackground(Color.WHITE);

            button.addActionListener(e -> {
                if(OrderList.orderedList.size() >= 10){
                    JOptionPane.showMessageDialog(null, "한 번에 주문할 수 있는 품목은 최대 10개 까지 입니다.");
                }
                else {
                    int response = JOptionPane.showConfirmDialog(
                            null, "<html>" + item.menu() + " (은)는  " + item.getFormattedCost() + " 입니다.<br>추가 하시겠습니까?</html>","주문 확인",JOptionPane.YES_NO_OPTION);
                    if(response == JOptionPane.YES_OPTION){
                        OrderList.addList(item.menu(), item.cost());
                    }
                }
            });
            panel.add(button);
        }

        //빈 버튼 생성하여 그리드를 채움
        for(int i=list.size(); i<12; i++){
            JButton button = new JButton();
            button.setBackground(Color.LIGHT_GRAY);
            panel.add(button);
        }

        return panel;
    }

    //Menu item 생성
    private void menuListInit(){
        foodList = new ArrayList<>();
        foodList.add(new MenuItem("치즈 버거", 7500, "/cheese.png"));
        foodList.add(new MenuItem("치킨 버거", 3700, "/ck.png"));
        foodList.add(new MenuItem("갈릭 불고기 버거", 7300, "/garlic.png"));
        foodList.add(new MenuItem("통새우 버거", 7700, "/shrimp.png"));
        foodList.add(new MenuItem("바베큐 치킨 버거", 3700, "/babaeck.png"));
        foodList.add(new MenuItem("롱 치킨 버거", 4600, "/longck.png"));
        foodList.add(new MenuItem("기네스 버거", 9300, "/guinness.png"));
        foodList.add(new MenuItem("불고기 버거", 5400, "/bulgogi.png"));
        foodList.add(new MenuItem("몬스터 버거", 9300, "/monster.png"));
        foodList.add(new MenuItem("콰트로 치즈 버거", 7700, "/qcheese.png"));

        drinkList = new ArrayList<>();
        drinkList.add(new MenuItem("코카콜라", 1900, "/coke.png"));
        drinkList.add(new MenuItem("코카콜라 제로", 1900, "/cokez.png"));
        drinkList.add(new MenuItem("스프라이트", 1900, "/sprite.png"));
        drinkList.add(new MenuItem("스프라이트 제로", 1900, "/spritez.png"));
        drinkList.add(new MenuItem("미닛메이드 오렌지", 2700, "/orange.png"));


        sideList = new ArrayList<>();
        sideList.add(new MenuItem("해쉬브라운", 2100, "/hash.png"));
        sideList.add(new MenuItem("너겟", 2800, "/nugget.png"));
        sideList.add(new MenuItem("치즈스틱", 2000, "/cheesestick.png"));
        sideList.add(new MenuItem("어니언링", 2300, "/ling.png"));
        sideList.add(new MenuItem("감자튀김", 2000, "/frfri.png"));
        sideList.add(new MenuItem("코울슬로", 2000, "/coul.png"));
        sideList.add(new MenuItem("콘샐러드", 2000, "/consal.png"));
        sideList.add(new MenuItem("치즈볼", 2500, "/cheeseball.png"));
    }

}
