package com.simpletask.Zadatak_za_praksu.dto;

import java.util.Date;

public class MakeAppointmentDTO {

    private String selectedDateTime;
    private Integer duration;
    private String username;
    private String phoneNumber;

    public MakeAppointmentDTO() {
    }

    public String getSelectedDateTime() {
        return selectedDateTime;
    }

    public void setSelectedDateTime(String selectedDateTime) {
        this.selectedDateTime = selectedDateTime;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
