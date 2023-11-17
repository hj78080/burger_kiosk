package org.components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class MenuPanel extends JPanel {
    private final ArrayList<MenuItem> itemList;
    private final int totalPage;
    private int currPage;
    private final JPanel itemPanel;
    private JButton[] buttons;

    public MenuPanel(ArrayList<MenuItem> itemList) {
        setLayout(new BorderLayout());
        this.itemList = itemList;

        itemPanel = new JPanel(new GridLayout(4, 3));
        buttonInit();

        totalPage = Math.max(itemList.size() - 1, 0) / 12;
        currPage = 0;

        createItemGridPanel();
        add(itemPanel, BorderLayout.CENTER);

        createNextButtonPanel();
    }

    private void createItemGridPanel() {
        itemPanel.removeAll();

        int startIdx = currPage * 12;
        int endIdx = startIdx + 12;

        for(int i=startIdx; i<endIdx; i++){
            itemPanel.add(buttons[i]);
        }

        validate();
        repaint();
    }

    private void buttonInit(){
        buttons = new JButton[(itemList.size() + 11) / 12 * 12];

        for(int i=0; i<buttons.length; i++){
            buttons[i] = new JButton();

            if(i < itemList.size()) {
                MenuItem item = itemList.get(i);
                String path = item.image();
                System.out.println(path);
                ImageIcon imageIcon = (path == null) ?
                        new ImageIcon() : new ImageIcon(Objects.requireNonNull(getClass().getResource(item.image())));

                buttons[i].setText(
                        "<html><center>" + item.menu() + "<br>" + item.getFormattedCost()+"</center></html>");
                buttons[i].setIcon(imageIcon);
                Font font = buttons[i].getFont();
                buttons[i].setFont(new Font(font.getName(), font.getStyle(), 12));
                buttons[i].setVerticalTextPosition(SwingConstants.BOTTOM);
                buttons[i].setHorizontalTextPosition(SwingConstants.CENTER);
                buttons[i].setBackground(Color.WHITE);

                buttons[i].addActionListener(e -> {
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
            }
            else{
                buttons[i].setBackground(Color.LIGHT_GRAY);
            }
        }

    }

    private void createNextButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel space = new JLabel();
        JButton backButton = new JButton("<");
        JButton nextButton = new JButton(">");
        space.setPreferredSize(new Dimension(100, 0));

        backButton.addActionListener(e -> {
            if (currPage > 0) {
                currPage--;
                itemPanel.removeAll();
                createItemGridPanel();
            }
        });

        nextButton.addActionListener(e -> {
            if (currPage < totalPage) {
                currPage++;
                itemPanel.removeAll();
                createItemGridPanel();
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(space);
        buttonPanel.add(nextButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}