package com.example.fitclub;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ScheduleData {

    private String day;
    private int count;

    public ScheduleData(String day, int count) {
        this.day = day;
        this.count = count;
    }
}
