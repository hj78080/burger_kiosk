package org.components;

/*
# 2023/09/10 hyeongjun Lim
# 주문한 목록이 출력되는 OrderList. JPanel 객체인 OrderItem 을 ArrayList 형태로 저장함.
# 라벨 창, 리스트, 결제 버튼 창으로 이루어짐.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderList extends JPanel{

    public static ArrayList<OrderItem> orderedList;
    private static JPanel listPanel;
    private JPanel purchasePanel;
    private JPanel labelPanel;
    private JScrollPane scrollPane;
    public static JLabel totalCostLabel;
    private int orderNum;

    public OrderList(JFrame frame) {
        setLayout(new BorderLayout());
        orderedList = new ArrayList<>();
        orderNum = 0;

        createLabelPanel();
        createPurchasePanel();
        createScrollPane();
        add(labelPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(purchasePanel, BorderLayout.SOUTH);

        frame.getContentPane().add(this, BorderLayout.SOUTH);
    }

    //라벨 텝 생성
    private void createLabelPanel(){
        labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(1,4));
        labelPanel.setPreferredSize(new Dimension(450, 30));
        labelPanel.setBackground(Color.DARK_GRAY);

        JLabel menuLabel = new JLabel("주문 메뉴");
        JLabel space = new JLabel();    // grid layout 내에서 위치를 조정하기 위해 만든 빈 label
        JLabel cntLabel = new JLabel("수량");
        JLabel costLabel = new JLabel("금액");

        menuLabel.setHorizontalAlignment(JLabel.CENTER);
        cntLabel.setHorizontalAlignment(JLabel.CENTER);
        costLabel.setHorizontalAlignment(JLabel.CENTER);
        menuLabel.setForeground(Color.WHITE);
        cntLabel.setForeground(Color.WHITE);
        costLabel.setForeground(Color.WHITE);

        labelPanel.add(menuLabel);
        labelPanel.add(space);
        labelPanel.add(cntLabel);
        labelPanel.add(costLabel);
    }

    //리스트 panel 생성 및 스크롤 추가
    private void createScrollPane(){
        listPanel = new JPanel();
        listPanel.setLayout(new FlowLayout());
        listPanel.setPreferredSize(new Dimension(420, 0));

        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(450, 150));
        scrollPane.setViewportView(listPanel);
    }

    //결제 탭 생성, 총 결제 금액 label, 전체 삭제 버튼, 결제 버튼으로 구성
    private void createPurchasePanel(){
        purchasePanel = new JPanel();
        purchasePanel.setLayout(new GridLayout(1, 4));
        purchasePanel.setPreferredSize(new Dimension(450, 35));
        purchasePanel.setBackground(Color.DARK_GRAY);
        
        totalCostLabel = new JLabel("0 원");
        JLabel space = new JLabel();
        JButton removeAllButton = new JButton("전체 삭제");
        JButton purchaseButton = new JButton("결제");

        totalCostLabel.setHorizontalAlignment(JLabel.CENTER);
        totalCostLabel.setForeground(Color.WHITE);
        removeAllButton.setBackground(Color.DARK_GRAY);
        removeAllButton.setForeground(Color.WHITE);
        removeAllButton.setBorder(new EmptyBorder(0,0,0,0));
        purchaseButton.setBackground(Color.DARK_GRAY);
        purchaseButton.setForeground(Color.WHITE);
        purchaseButton.setBorder(new EmptyBorder(0,0,0,0));

        // yes 를 누를 시 remove all clicked 호출하여 전체 리스트 삭제
        removeAllButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                    null, "모든 주문 목록을 삭제하시겠습니까?","삭제 확인",JOptionPane.YES_NO_OPTION);
            if(response == JOptionPane.YES_OPTION){
                removeAllClicked();
            }
        });

        // yes 버튼 누를 시 purchased clicked 호출하여, excel write 와 send 수행 후 remove all 을 통해 전체 삭제
        purchaseButton.addActionListener(e -> {
            if(orderedList.size() == 0) {
                JOptionPane.showMessageDialog(null, "최소 1개의 메뉴를 주문해 주셔야 합니다.");
                return;
            }

            int response = JOptionPane.showConfirmDialog(
                    null, totalCostLabel.getText() + " 결제 하시겠습니까?","결제 확인",JOptionPane.YES_NO_OPTION);
            if(response == JOptionPane.YES_OPTION){
                purchaseClicked();
                removeAllClicked();
            }
        });

        purchasePanel.add(totalCostLabel);
        purchasePanel.add(space);
        purchasePanel.add(removeAllButton);
        purchasePanel.add(purchaseButton);
    }
    
    // 메뉴 pane 에서 item 버튼 클릭을 통해 주문 시 addList 호출하여 리스트에 추가
    public static void addList(String menu, int cost) {
        OrderItem orderItem = isAlreadyIn(menu);
        
        //list 에 추가하며 list panel 크기를 재 조정함
        if(orderItem == null) {
            orderItem = new OrderItem(menu, cost, listPanel);
            orderItem.setPreferredSize(new Dimension(410, 30));
            orderedList.add(orderItem);
            listPanel.add(orderItem);
        }
        //이미 있는 항목일 경우 새로 추가하지 않고 수량을 늘림
        else{
            orderItem.renumber(orderItem.getCnt()+1);
        }
        setTotalCost();
        // panel의 높이를 업데이트하여 스크롤 유발
        listPanel.setPreferredSize(new Dimension(420, orderedList.size() * 40));
        listPanel.revalidate(); // 패널을 다시 그리도록 호출
        listPanel.repaint();    // 패널을 다시 그리도록 호출
    }

    // 추가될 item 이 이미 리스트에 있는 항목인지 확인하는 함수
    private static OrderItem isAlreadyIn(String menu){
        for(OrderItem item : orderedList){
            if(item.getMenu().equals(menu)) {
                //만약 이미 있더라도 이전 작업에 의해 cnt가 0이라면 리스트에서 삭제한 뒤 null 반환.
                if(item.getCnt() == 0) {
                    orderedList.remove(item);
                    break;
                }
                else return item;
            }
        }

        return null;
    }
    
    // order list 의 모든 item 의 수량*비용 계산하여 표시
    public static void setTotalCost(){
        int cost = 0;

        for(OrderItem item : orderedList){
            cost += item.getCnt()*item.getCost();
        }
        DecimalFormat decimalFormat = new DecimalFormat("#,### 원");
        totalCostLabel.setText(decimalFormat.format(cost));
    }
    
    //리스트 초기화 및 list panel 크기 재조정
    private void removeAllClicked(){
        orderedList.clear();
        listPanel.setPreferredSize(new Dimension(420, 0));
        setTotalCost();
        listPanel.removeAll();
        listPanel.revalidate();
        listPanel.repaint();
    }
    
    //결제 시 필요한 함수. 결제 및 주문 번호 부여
    private void purchaseClicked() {
        DecimalFormat orderNumFormat = new DecimalFormat("000");

        //부여받는 주문 번호가 1~999로 나오게 함
        if (orderNum == 1000) orderNum = 0;
        SendOrder.send(orderNumFormat.format(++orderNum));
        PurchasedListIO.readWrite();

        JOptionPane.showMessageDialog(null, "<html>결제가 완료되었습니다.<br>주문 번호 " + orderNumFormat.format(orderNum) + "</html>");
        new Receipt();
    }
}
