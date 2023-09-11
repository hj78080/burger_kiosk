package org.components;

/*
# 2023/09/11 hyeongjun Lim
# 주문 받은 내용이 표시될 ordered list panel
# flow layout (left) , scroll pane 를 사용하여 주문받은 내용이 왼쪽부터 쌓이며, 창을 넘어갈 경우 스크롤이 생김
 */

import javax.swing.*;
import java.awt.*;

public class OrderedList extends JPanel {
    private static JPanel listPanel;

    public OrderedList(JFrame jFrame){
        listPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JScrollPane scrollPane = new JScrollPane(listPanel);

        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

        jFrame.add(this);
    }

    //주문 받은 내용이 표시될 버튼, 음식을 다 만들었을 경우를 고려해 버튼을 누르면 사라짐
    public static void createNewOrder(String message){
        JButton orderButton = new JButton(message);
        orderButton.setVerticalAlignment(SwingConstants.NORTH);
        orderButton.setBackground(Color.white);
        orderButton.setPreferredSize(new Dimension(180,500));

        orderButton.addActionListener(e -> {
            listPanel.remove(orderButton);
            listPanel.revalidate();
            listPanel.repaint();
        });

        listPanel.add(orderButton);
        listPanel.revalidate();
        listPanel.repaint();
    }
}
