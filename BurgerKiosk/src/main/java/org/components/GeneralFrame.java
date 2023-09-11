package org.components;

/*
# 2023/09/10 hyeongjun Lim
# 프로그램의 가장 큰 단위 레이아웃
# 프로그램은 크게 Banner, MenuPane, OrderList 세 부분으로 나뉘며, GeneralFrame 에서 호출함
# 각 레이아웃들은 호출 시 생성자에서 기본적으로 생성, GeneralFrame 에 합쳐지는 방식으로 통일함
# 또한, 프로그램의 기본 look and feel 을 설정하고 기본 font 를 적용함
 */

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

public class GeneralFrame extends JFrame {
    public GeneralFrame() {
        setTitle("Burger Kiosk");
        setSize(450,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setDefaultFont();
        
        //기본 look and feel 설정, 전체적인 ui의 분위기를 바꿈
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e){
            e.printStackTrace();
        }

        new Banner(this);
        new MenuPane(this);
        new OrderList(this);

        setVisible(true);
    }

    private void setDefaultFont() {
        Font defaultFont = new Font("배달의민족 한나체 Pro", Font.PLAIN, 15);

        //SwingUtilities.invokeLater 를 사용하여 폰트 변경 코드를 이벤트 디스패치 스레드에서 실행.
        // 이렇게 하면 UI 업데이트가 완료된 후에 폰트 변경이 이루어짐.
        SwingUtilities.invokeLater(() -> {
            Enumeration<Object> keys = UIManager.getDefaults().keys();
            while (keys.hasMoreElements()) {
                Object key = keys.nextElement();
                Object value = UIManager.get(key);
                if (value instanceof FontUIResource) {
                    UIManager.put(key, defaultFont);
                }
            }
        });
    }
}