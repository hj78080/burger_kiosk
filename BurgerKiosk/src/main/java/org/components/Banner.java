package org.components;

/*
# 2023/09/10 hyeongjun Lim
# Panel 형식의 Banner, 생성 시 GeneralFrame 에 추가됨.
 */

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Banner extends JPanel{
    public Banner(JFrame frame){
        setLayout(new GridLayout(1,3));
        setPreferredSize(new Dimension(450, 80));
        setBackground(Color.DARK_GRAY);

        JLabel space = new JLabel();
        JLabel bannerText = new JLabel("<html>&nbsp;BURGER</html>");
        bannerText.setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("/logo.png"))));
        bannerText.setFont(new Font("배달의민족 한나체 Pro", Font.BOLD, 32));
        bannerText.setForeground(Color.WHITE);
        bannerText.setHorizontalAlignment(SwingConstants.LEFT);
        bannerText.setVerticalAlignment(SwingConstants.BOTTOM);

        this.add(space);
        this.add(bannerText);

        frame.getContentPane().add(this, BorderLayout.NORTH);
    }
}
