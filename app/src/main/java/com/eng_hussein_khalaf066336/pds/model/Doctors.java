package com.eng_hussein_khalaf066336.pds.model;
public class Doctors {
    String doctorId;
    String doctorFullName;
    String doctorEmail;
    String doctorPassword;
    String doctorDateOfBirth;
    String doctorAddress;
    String doctorSpecialization;
    String doctorFunctionalSection;
    String doctorAbout_the_doctor;
    String doctorType;
    String doctorImage;

    public Doctors() {
    }

    public Doctors(String doctorId, String doctorFullName, String doctorEmail, String doctorPassword,
                   String doctorDateOfBirth, String doctorAddress, String doctorSpecialization,
                   String doctorFunctionalSection, String doctorAbout_the_doctor, String doctorType,
                   String doctorImage)
    {
        this.doctorId = doctorId;
        this.doctorFullName = doctorFullName;
        this.doctorEmail = doctorEmail;
        this.doctorPassword = doctorPassword;
        this.doctorDateOfBirth = doctorDateOfBirth;
        this.doctorAddress = doctorAddress;
        this.doctorSpecialization = doctorSpecialization;
        this.doctorFunctionalSection = doctorFunctionalSection;
        this.doctorAbout_the_doctor = doctorAbout_the_doctor;
        this.doctorType = doctorType;
        this.doctorImage = doctorImage;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
    }

    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    public String getDoctorPassword() {
        return doctorPassword;
    }

    public void setDoctorPassword(String doctorPassword) {
        this.doctorPassword = doctorPassword;
    }

    public String getDoctorDateOfBirth() {
        return doctorDateOfBirth;
    }

    public void setDoctorDateOfBirth(String doctorDateOfBirth) {
        this.doctorDateOfBirth = doctorDateOfBirth;
    }

    public String getDoctorAddress() {
        return doctorAddress;
    }

    public void setDoctorAddress(String doctorAddress) {
        this.doctorAddress = doctorAddress;
    }

    public String getDoctorSpecialization() {
        return doctorSpecialization;
    }

    public void setDoctorSpecialization(String doctorSpecialization) {
        this.doctorSpecialization = doctorSpecialization;
    }

    public String getDoctorFunctionalSection() {
        return doctorFunctionalSection;
    }

    public void setDoctorFunctionalSection(String doctorFunctionalSection) {
        this.doctorFunctionalSection = doctorFunctionalSection;
    }

    public String getDoctorAbout_the_doctor() {
        return doctorAbout_the_doctor;
    }

    public void setDoctorAbout_the_doctor(String doctorAbout_the_doctor) {
        this.doctorAbout_the_doctor = doctorAbout_the_doctor;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getDoctorImage() {
        return doctorImage;
    }

    public void setDoctorImage(String doctorImage) {
        this.doctorImage = doctorImage;
    }
}
