package com.eng_hussein_khalaf066336.pds.model;

public class appointments {
    String appointment_Id,appointment_reason,appointment_dat,appointment_Time,appointment_description,
            appointment_current_userId,appointment_current_doctorId;

    public appointments() {
    }

    public appointments(String appointment_reason, String appointment_dat, String appointment_Time, String appointment_description,
                        String appointment_current_userId, String appointment_current_doctorId) {
        this.appointment_reason = appointment_reason;
        this.appointment_dat = appointment_dat;
        this.appointment_Time = appointment_Time;
        this.appointment_description = appointment_description;
        this.appointment_current_userId = appointment_current_userId;
        this.appointment_current_doctorId = appointment_current_doctorId;
    }

    public String getAppointment_Id() {
        return appointment_Id;
    }

    public void setAppointment_Id(String appointment_Id) {
        this.appointment_Id = appointment_Id;
    }

    public String getAppointment_reason() {
        return appointment_reason;
    }

    public void setAppointment_reason(String appointment_reason) {
        this.appointment_reason = appointment_reason;
    }

    public String getAppointment_dat() {
        return appointment_dat;
    }

    public void setAppointment_dat(String appointment_dat) {
        this.appointment_dat = appointment_dat;
    }

    public String getAppointment_Time() {
        return appointment_Time;
    }

    public void setAppointment_Time(String appointment_Time) {
        this.appointment_Time = appointment_Time;
    }

    public String getAppointment_description() {
        return appointment_description;
    }

    public void setAppointment_description(String appointment_description) {
        this.appointment_description = appointment_description;
    }

    public String getAppointment_current_userId() {
        return appointment_current_userId;
    }

    public void setAppointment_current_userId(String appointment_current_userId) {
        this.appointment_current_userId = appointment_current_userId;
    }

    public String getAppointment_current_doctorId() {
        return appointment_current_doctorId;
    }

    public void setAppointment_current_doctorId(String appointment_current_doctorId) {
        this.appointment_current_doctorId = appointment_current_doctorId;
    }
}
