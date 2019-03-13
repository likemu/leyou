package com.leyou.order.enums;

import com.sun.org.apache.regexp.internal.RE;

/**
 * @Author：lidatu
 * @Date： 2019/02/11 星期一 23:27
 * @Description：
 */

public enum PayState {
    NOT_PAY(0), SUCCESS(1), FAIL(2);

    PayState(int value) {
        this.value = value;
    }

    int value;

    public int getValue() {
        return value;
    }

}

















