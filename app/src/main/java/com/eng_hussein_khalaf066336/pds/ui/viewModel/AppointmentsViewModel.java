package com.eng_hussein_khalaf066336.pds.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eng_hussein_khalaf066336.pds.model.appointments;
import com.eng_hussein_khalaf066336.pds.repository.AppointmentsRepository;
import com.google.firebase.database.DataSnapshot;

public class AppointmentsViewModel extends ViewModel implements AppointmentsRepository.OnFirebaseDatabasePatientAppointmentsTaskComplete, AppointmentsRepository.onAppointmentAdded, AppointmentsRepository.OnFirebaseDatabaseDoctorAppointmentsTaskComplete {
    private AppointmentsRepository appointmentsRepository=new AppointmentsRepository(this,this,this);
    private MutableLiveData<DataSnapshot> mutableLiveDataPatientAppointment=new MutableLiveData<>();
    private MutableLiveData<DataSnapshot> mutableLiveDataDoctorAppointment=new MutableLiveData<>();

    public MutableLiveData<DataSnapshot> getMutableLiveDataPatientAppointment() {
        return mutableLiveDataPatientAppointment;
    }

    public MutableLiveData<DataSnapshot> getMutableLiveDataDoctorAppointment() {
        return mutableLiveDataDoctorAppointment;
    }

    public void getPatientAppointment(String PatientId) {
        appointmentsRepository.getPatientAppointment(PatientId);
    }
    public void getDoctorAppointment(String DoctorId) {
        appointmentsRepository.getDoctorAppointment(DoctorId);
    }
    public void addAppointment(appointments appointment){
        appointmentsRepository.addAppointment(appointment);
    }

    @Override
    public void PatientAppointmentDataLoaded(DataSnapshot snapshotAppointments) {
        mutableLiveDataPatientAppointment.postValue(snapshotAppointments);
    }

    @Override
    public void PatientAppointmentError(Exception error) {
        Log.e("error", "loadPatientAppointment:onCancelled", error);

    }

    @Override
    public void DoctorAppointmentDataLoaded(DataSnapshot snapshotAppointments) {
        mutableLiveDataDoctorAppointment.postValue(snapshotAppointments);
    }

    @Override
    public void DoctorAppointmentError(Exception error) {
        Log.e("error", "loadDoctorAppointment:onCancelled", error);

    }

    @Override
    public boolean onSubmit() {
        return true;
    }

    @Override
    public void onErrorAdded(Exception e) {
        Log.e("error", "AddedAppointment:onCancelled", e);

    }

}
