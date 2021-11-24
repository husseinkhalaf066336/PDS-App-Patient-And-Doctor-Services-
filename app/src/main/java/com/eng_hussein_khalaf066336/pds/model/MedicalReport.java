package com.eng_hussein_khalaf066336.pds.model;

public class MedicalReport {

    String  ID_Report,ReportTitle,ReportDetails,DoctorsInstructions,
            SuggestedMedication,ID_Current_Patient,ID_Current_doctor ;

    public MedicalReport() {
    }

    public MedicalReport(String reportTitle, String reportDetails, String doctorsInstructions, String suggestedMedication, String ID_Current_Patient, String ID_Current_doctor) {
        ReportTitle = reportTitle;
        ReportDetails = reportDetails;
        DoctorsInstructions = doctorsInstructions;
        SuggestedMedication = suggestedMedication;
        this.ID_Current_Patient = ID_Current_Patient;
        this.ID_Current_doctor = ID_Current_doctor;
    }

    public String getID_Report() {
        return ID_Report;
    }

    public void setID_Report(String ID_Report) {
        this.ID_Report = ID_Report;
    }

    public String getReportTitle() {
        return ReportTitle;
    }

    public void setReportTitle(String reportTitle) {
        ReportTitle = reportTitle;
    }

    public String getReportDetails() {
        return ReportDetails;
    }

    public void setReportDetails(String reportDetails) {
        ReportDetails = reportDetails;
    }

    public String getDoctorsInstructions() {
        return DoctorsInstructions;
    }

    public void setDoctorsInstructions(String doctorsInstructions) {
        DoctorsInstructions = doctorsInstructions;
    }

    public String getSuggestedMedication() {
        return SuggestedMedication;
    }

    public void setSuggestedMedication(String suggestedMedication) {
        SuggestedMedication = suggestedMedication;
    }

    public String getID_Current_Patient() {
        return ID_Current_Patient;
    }

    public void setID_Current_Patient(String ID_Current_Patient) {
        this.ID_Current_Patient = ID_Current_Patient;
    }

    public String getID_Current_doctor() {
        return ID_Current_doctor;
    }

    public void setID_Current_doctor(String ID_Current_doctor) {
        this.ID_Current_doctor = ID_Current_doctor;
    }
}
