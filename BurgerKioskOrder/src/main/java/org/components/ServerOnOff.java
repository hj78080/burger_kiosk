package org.components;

/*
# 2023/09/11 hyeongjun Lim
# 37808번 포트로 소켓 통신 서버를 열어 키오스크로 주문한 내용을 전달받을 수 있게 함
# 전달받은 내용을 OrderedList.createNewOrder() 을 호출하여 주문 표 버튼 생성
# 클라이언트의 내용을 정상적으로 전달받기 위해 프로토콜을 정의하였음
# header 에는 주문 번호, 입력받을 텍스트 수, body 에는 주문 목록이 들어감
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ServerOnOff {

    private static boolean serverOnOff;
    public static void setServerOnOff(boolean bool){
        serverOnOff = bool;
    }

    public void serverOn(){
        try {
            ServerSocket serverSocket = new ServerSocket(37808);
            setServerOnOff(true);

            while (serverOnOff) {
                Socket clientSocket = serverSocket.accept();

                BufferedReader messageReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
                int messageNum = Integer.parseInt(messageReader.readLine());
                String orderNum = messageReader.readLine();
                LocalDateTime orderTime = LocalDateTime.now();
                DateTimeFormatter orderTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd / hh:mm");
                StringBuilder sb = new StringBuilder(
                        "<html><h2>주문 번호  " + orderNum + "</h2>" + orderTime.format(orderTimeFormat) +"<br><br>");

                //클라이언트를 통해 총 메세지의 길이인 messageNum 을 전달 받아 반복함.
                for (int i = 0; i < messageNum; i++) {
                    String message = messageReader.readLine();
                    sb.append("<br>").append(message);
                }
                sb.append("</html>");
                OrderedList.createNewOrder(sb.toString());

                clientSocket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
