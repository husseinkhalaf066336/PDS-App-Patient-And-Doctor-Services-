package com.eng_hussein_khalaf066336.pds.model;

public class availableAppointment {
    String availableAppointmentId;
    String availableAppointmentDate;
    String availableAppointmentDateTime;
    String availableAppointmentDoctorId;

    public availableAppointment() {
    }

    public availableAppointment(String availableAppointmentDate, String availableAppointmentDateTime, String availableAppointmentDoctorId) {
        this.availableAppointmentDate = availableAppointmentDate;
        this.availableAppointmentDateTime = availableAppointmentDateTime;
        this.availableAppointmentDoctorId = availableAppointmentDoctorId;
    }

    public String getAvailableAppointmentId() {
        return availableAppointmentId;
    }

    public void setAvailableAppointmentId(String availableAppointmentId) {
        this.availableAppointmentId = availableAppointmentId;
    }

    public String getAvailableAppointmentDate() {
        return availableAppointmentDate;
    }

    public void setAvailableAppointmentDate(String availableAppointmentDate) {
        this.availableAppointmentDate = availableAppointmentDate;
    }

    public String getAvailableAppointmentDateTime() {
        return availableAppointmentDateTime;
    }

    public void setAvailableAppointmentDateTime(String availableAppointmentDateTime) {
        this.availableAppointmentDateTime = availableAppointmentDateTime;
    }

    public String getAvailableAppointmentDoctorId() {
        return availableAppointmentDoctorId;
    }

    public void setAvailableAppointmentDoctorId(String availableAppointmentDoctorId) {
        this.availableAppointmentDoctorId = availableAppointmentDoctorId;
    }
}

