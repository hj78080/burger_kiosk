package org.components;

import javax.swing.*;

public class Receipt extends JFrame {
    public Receipt(){
        setTitle("Receipt");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 520);

        StringBuilder text = new StringBuilder("""
                <html>
                    <body>
                        <h2>영수증</h2>
                        <table style="width: 100%;">
                            <tr>
                                <th style="width: 25%;">수량</th>
                                <th style="text-align: left;">품목</th>
                                <th style="text-align: right;">금액</th>
                            </tr>
                            <tr>
                                <td style="border-top: 1px solid black;" colspan="3"></td>
                            </tr>
                """);
        for (OrderItem item : OrderList.orderedList) {
            String s = String.format("""
                        <tr>
                            <td style="text-align: center;">%d</td>
                            <td>%s</td>
                            <td style="text-align: right;">%d</td>
                        </tr>
                        """, item.getCnt(), item.getMenu(), item.getCost()*item.getCnt());
            text.append(s);
        }
        text.append(String.format("""
                            <tr>
                                <td style="border-bottom: 1px solid black;" colspan="3"></td>
                            </tr>
                            <tr>
                                <td style="text-align: left;">합계</td>
                                <td></td>
                                <td style="text-align: right;">%s</td>
                            </tr>
                            <tr>
                                <td style="text-align: left;">받을 금액</td>
                                <td></td>
                                <td style="text-align: right;">%s</td>
                            </tr>
                            <tr>
                                <td style="text-align: left;">받은 금액</td>
                                <td></td>
                                <td style="text-align: right;">%s</td>
                            </tr>
                        </table>
                        </body>
                    </html>
                    """, OrderList.totalCostLabel.getText(),OrderList.totalCostLabel.getText(), OrderList.totalCostLabel.getText()));

        JTextPane textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setText(text.toString());
        textPane.setEditable(false);
        getContentPane().add(textPane);

        setVisible(true);
    }
}
