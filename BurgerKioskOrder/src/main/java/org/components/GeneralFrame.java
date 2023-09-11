package org.components;

/*
# 2023/09/11 hyeongjun Lim
# 전체적인 내용이 취합될 레이아웃, 기본 font 와 look and feel 을 설정하여 전체적인 분위기 변경
 */

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Enumeration;

public class GeneralFrame extends JFrame {

    public GeneralFrame(){
        setTitle("Burger Kiosk");
        setSize(900,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ServerOnOff.setServerOnOff(false);
            }
        });

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e){
            e.printStackTrace();
        }
        setDefaultFont();

        new OrderedList(this);
        setVisible(true);

        ServerOnOff server = new ServerOnOff();
        server.serverOn();
    }

    private void setDefaultFont() {
        Font defaultFont = new Font("배달의민족 한나체 Pro", Font.PLAIN, 15);

        // 모든 Swing 컴포넌트의 폰트 변경
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                UIManager.put(key, defaultFont);
            }
        }
    }

}
