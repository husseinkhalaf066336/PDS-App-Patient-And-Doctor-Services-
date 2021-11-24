package com.eng_hussein_khalaf066336.pds.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eng_hussein_khalaf066336.pds.model.MedicalReport;
import com.eng_hussein_khalaf066336.pds.repository.MedicalReportRepository;
import com.google.firebase.database.DataSnapshot;

public class MedicalReportViewModel extends ViewModel implements MedicalReportRepository.onFirebaseDatabasePatientReportsTaskComplete, MedicalReportRepository.onReportAdded, MedicalReportRepository.onFirebaseDatabaseDoctorReportsTaskComplete {
    private MedicalReportRepository medicalReportRepository= new MedicalReportRepository(this,this,this);
    private MutableLiveData<DataSnapshot> mutableLiveDataPatientReport=new MutableLiveData<>();
    private MutableLiveData<DataSnapshot> mutableLiveDataDoctorReport=new MutableLiveData<>();

    public MutableLiveData<DataSnapshot> getMutableLiveDataPatientReport() {
        return mutableLiveDataPatientReport;
    }

    public MutableLiveData<DataSnapshot> getMutableLiveDataDoctorReport() {
        return mutableLiveDataDoctorReport;
    }

    public void getMedicalReportForPatient(String PatientId) {
        medicalReportRepository.getMedicalReportForPatient(PatientId);
    }
    public void getMedicalReportForDoctor(String DoctorId) {
        medicalReportRepository.getMedicalReportForDoctor(DoctorId);
    }
    public void addReport(MedicalReport medicalReport)
    {
        medicalReportRepository.addReport(medicalReport);
    }
        @Override
    public void PatientReportDataLoaded(DataSnapshot snapshotReport) {
            mutableLiveDataPatientReport.postValue(snapshotReport);
    }

    @Override
    public void onErrorPatientReportLoaded(Exception e) {
        Log.d("error", "loadPatientReport:onCancelled: " + e.getMessage());

    }
    @Override
    public void DoctorReportDataLoaded(DataSnapshot snapshotReport) {
        mutableLiveDataDoctorReport.postValue(snapshotReport);
    }

    @Override
    public void onErrorDoctorReportLoaded(Exception e) {
        Log.d("error", "loadDoctorReport:onCancelled: " + e.getMessage());

    }

    @Override
    public boolean onSubmit() {
        return true;
    }

    @Override
    public void onErrorAdded(Exception e) {
        Log.d("error", "AddedReport:onCancelled: " + e.getMessage());

    }


}
