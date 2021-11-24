package com.eng_hussein_khalaf066336.pds.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eng_hussein_khalaf066336.pds.model.availableAppointment;
import com.eng_hussein_khalaf066336.pds.repository.AvailableAppointmentsRepository;
import com.google.firebase.database.DataSnapshot;

public class AvailableAppointmentsViewModel extends ViewModel implements AvailableAppointmentsRepository.OnAvailableAppointmentAdded, AvailableAppointmentsRepository.OnAvailableAppointmentDeleted, AvailableAppointmentsRepository.OnFirebaseDatabaseAvailableAppointmentsTaskComplete {
    private AvailableAppointmentsRepository availableAppointmentsRepository =new AvailableAppointmentsRepository(
            this,this,this);
    private MutableLiveData<DataSnapshot> mutableLiveDataAvailableAppointments=new MutableLiveData<>();

    public MutableLiveData<DataSnapshot> getMutableLiveDataAvailableAppointments() {
        return mutableLiveDataAvailableAppointments;
    }
    public void getAvailableAppointments(String DoctorId) {
        availableAppointmentsRepository.getAvailableAppointments(DoctorId);
    }
    public void addAvailableAppointment(availableAppointment availableAppointment) {
        availableAppointmentsRepository.addAvailableAppointment(availableAppointment);
    }
    public void deleteAvailableAppointments(String ID){
        availableAppointmentsRepository.deleteAvailableAppointments(ID);
    }
    @Override
    public void AvailableAppointmentDataLoaded(DataSnapshot snapshotAvailableAppointments) {
        mutableLiveDataAvailableAppointments.postValue(snapshotAvailableAppointments);
    }

    @Override
    public void onError(Exception error) {
        Log.e("error", "loadAvailableAppointment:onCancelled", error);

    }

    @Override
    public boolean onSubmit() {
        return true;
    }

    @Override
    public void onErrorDeleted(Exception e) {
        Log.e("error", "DeletedAvailableAppointment:onCancelled", e);

    }

    @Override
    public void onErrorAdded(Exception e) {
        Log.e("error", "AddedAvailableAppointment:onCancelled", e);
    }
}
