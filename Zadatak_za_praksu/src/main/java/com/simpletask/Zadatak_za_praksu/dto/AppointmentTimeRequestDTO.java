package com.simpletask.Zadatak_za_praksu.dto;

import java.util.Date;

public class AppointmentTimeRequestDTO {

    private Date date;
    private Integer duration;

    public AppointmentTimeRequestDTO() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
