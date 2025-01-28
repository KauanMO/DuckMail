package com.duckmail.dtos.dashboard;

import lombok.Getter;

@Getter
public enum DailyHourSegregationEnum {
    A(0, 0, 3, 59),
    B(4, 0, 7, 59),
    C(8, 0, 11, 59),
    D(12, 0, 15, 59),
    E(16, 0, 19, 59),
    F(20, 0, 23, 59);

    final Integer initialHour;
    final Integer initialMinute;
    final Integer finalHour;
    final Integer finalMinute;

    DailyHourSegregationEnum(Integer initialHour, Integer initialMinute, Integer finalHour, Integer finalMinute) {
        this.initialHour = initialHour;
        this.initialMinute = initialMinute;
        this.finalHour = finalHour;
        this.finalMinute = finalMinute;
    }

    public String getHourSegregationString() {
        return String.format("%02d:%02d - %02d:%02d", initialHour, initialMinute, finalHour, finalMinute);
    }
}