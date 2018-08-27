package com.project.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ProgressDto {

    private String step;
    private String notificationData;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private Date time;

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getNotificationData() {
        return notificationData;
    }

    public void setNotificationData(String notificationData) {
        this.notificationData = notificationData;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
