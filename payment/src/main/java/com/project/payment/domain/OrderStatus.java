package com.project.payment.domain;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PLACED("주문신청"),
    FINISHED("주문완료"),
    CANCEL("주문취소"),
    FAILED("주문실패");

    private String message;

    OrderStatus(String message) {
        this.message = message;
    }
}
