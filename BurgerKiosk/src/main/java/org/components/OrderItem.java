package org.components;

/*
# 2023/09/10 hyeongjun Lim
# OrderList 에 추가될 item 을 구현함
# [ 메뉴 이름 - 주문수량 + 비용 ] 을 버튼, Panel 로 구현
# 수량이 0이 되면 list 에서 사라지며, 수량이 10이 넘었을 경우 더이상 추가할 수 없음
# 수량 관련은 renumber 함수를 통해 다른 클래스에서도 호출하여 사용할 수 있도록 구현함
 */

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;

public class OrderItem extends JPanel{
    private final JPanel listPanel;
    private final JLabel costLabel;
    private JLabel cntLabel;
    private JPanel buttonPanel;

    private final DecimalFormat decimalFormat = new DecimalFormat("#,### 원");
    private final String menu;
    private final int cost;
    private int cnt;

    public OrderItem(String menu, int cost, JPanel listPanel){
        this.menu = menu;
        this.cnt = 1;
        this.cost = cost;
        this.listPanel = listPanel;

        JLabel menuLabel = new JLabel(menu);
        JLabel space = new JLabel();
        costLabel = new JLabel(decimalFormat.format(cost));

        menuLabel.setHorizontalAlignment(JLabel.CENTER);
        costLabel.setHorizontalAlignment(JLabel.CENTER);
        createButtonPanel();

        Font font = menuLabel.getFont();
        menuLabel.setFont(new Font("한컴 고딕", font.getStyle(), 13));
        cntLabel.setFont(new Font("한컴 고딕", font.getStyle(), 13));
        costLabel.setFont(new Font("한컴 고딕", font.getStyle(), 13));

        setLayout(new GridLayout(1, 3));
        add(menuLabel);
        add(space);
        add(buttonPanel);
        add(costLabel);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.WHITE);
    }

    public String getMenu(){ return menu; }
    public int getCnt() { return cnt; }
    public int getCost() { return cost; }

    // 주문 수량을 +, - 할 수 있는 버튼 생성
    private void createButtonPanel(){
        JButton buttonUp = new JButton("+");
        JButton buttonDown = new JButton("-");
        cntLabel = new JLabel("1");
        cntLabel.setHorizontalAlignment(JLabel.CENTER);

        buttonUp.addActionListener(e -> renumber(cnt+1));
        buttonDown.addActionListener(e -> renumber(cnt-1));

        buttonPanel = new JPanel(new GridLayout(1,3));
        buttonPanel.add(buttonDown);
        buttonPanel.add(cntLabel);
        buttonPanel.add(buttonUp);
    }

    // 모든 주문 수량을 변경하는 메커니즘 마다 호출되는 함수.
    // 감소 시 0이 되면 자기 자신을 삭제함, 증가 시 10이 넘지 않게 함.
    public void renumber(int num){
        if(num == 0) {
            OrderList.orderedList.remove(this);
            OrderList.setTotalCost();

            listPanel.remove(this);
            listPanel.setPreferredSize(new Dimension(420, OrderList.orderedList.size() * 40));
            listPanel.revalidate();
            listPanel.repaint();
        }
        if(num > 10) {
            JOptionPane.showMessageDialog(null, "한 번에 주문할 수 있는 수량은 최대 10개 까지 입니다.");
        }
        else {
            cntLabel.setText(String.valueOf(num));
            costLabel.setText(decimalFormat.format((long) cost * num));
            cnt = num;
            OrderList.setTotalCost();
        }
    }
}
