package org.components;

/*
# 2023/09/10 hyeongjun Lim
# MenuPane 의 버튼에 들어갈 항목들을 가지고 있는 Record
# 이름, 비용, 이미지 url 을 포함하고 있음
 */

import java.text.DecimalFormat;

public record MenuItem(String menu, int cost, String image) {

    public String getFormattedCost() {
        DecimalFormat decimalFormat = new DecimalFormat("#,### 원");
        return decimalFormat.format(cost);
    }
}
