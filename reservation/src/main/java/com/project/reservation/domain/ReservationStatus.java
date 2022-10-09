package com.project.reservation.domain;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    PLACED("예약신청"),
    FINISHED("예약완료"),
    CANCEL("예약취소"),
    FAILED("예약실패");

    private String value;

    ReservationStatus(String value) {
        this.value = value;
    }
}
