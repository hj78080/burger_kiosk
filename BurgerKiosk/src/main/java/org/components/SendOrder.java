package org.components;

/*
# 2023/09/10 hyeongjun Lim
# 결재 시 호출되는 send() 함수가 구현된 class
# 소켓 통신 이용, 결재 시 서버에 연결하여 결재 정보를 전송함
# 서버는 받은 정보를 통해 조리를 시작할 수 있도록 주문 번호, 주문 메뉴 리스트를 주문창에 띄움
 */

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SendOrder {
    public static void send(String orderNum) {
        String serverAddress = "localhost";
        int serverPort = 37808;

        try {
            Socket socket = new Socket(serverAddress, serverPort);

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            writer.println(OrderList.orderedList.size());   //먼저 orderedList 의 크기를 전송한 다음, orderedList 의 내용을 보냄
            writer.println(orderNum);

            for (OrderItem item : OrderList.orderedList) {
                String order = item.getMenu() + " x" + item.getCnt();
                writer.println(order);
            }
            socket.close();
        } catch (ConnectException c){
            System.out.println("서버에 연결되지 않았습니다.");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
