package com.example.yosemite.service;

import com.example.yosemite.data.Schedule;

import java.util.ArrayList;
import java.util.List;

public class Schedule_Handler {
    private static Schedule_Handler handler;
    private List<Schedule> scheduleList;

    private Schedule_Handler() {
        scheduleList = new ArrayList<>();
    }

    public static synchronized Schedule_Handler getInstance() {
        if (handler == null) {
            handler = new Schedule_Handler();
        }
        return handler;
    }

    public void addSchedule(Schedule schedule) {
        scheduleList.add(schedule);
    }

    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

}
