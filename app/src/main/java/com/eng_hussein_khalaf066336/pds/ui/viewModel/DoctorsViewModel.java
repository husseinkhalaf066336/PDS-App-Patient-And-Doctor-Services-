package com.eng_hussein_khalaf066336.pds.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.eng_hussein_khalaf066336.pds.model.Doctors;
import com.eng_hussein_khalaf066336.pds.repository.DoctorsRepository;
import com.google.firebase.database.DataSnapshot;

public class DoctorsViewModel extends ViewModel implements DoctorsRepository.OnFirebaseDatabaseDoctorsTaskComplete, DoctorsRepository.OnDoctorAdded, DoctorsRepository.onDoctorUpdated {
    private DoctorsRepository doctorsRepository=new DoctorsRepository(this,this,this);
    private MutableLiveData<DataSnapshot> doctorMutableLiveData=new MutableLiveData<>();
    public MutableLiveData<DataSnapshot> getDoctorMutableLiveData() {
        return doctorMutableLiveData;
    }

    public void getDoctor(String doctorId) {
        doctorsRepository.getDoctor(doctorId);}
    public void addDoctor(Doctors doctor) {
        doctorsRepository.addDoctor(doctor);
    }
    public void  updateDoctor(Doctors doctor)
    {
        doctorsRepository.updateDoctor(doctor);
    }
    @Override
    public void doctorDataLoaded(DataSnapshot snapshotDoctor) {
        doctorMutableLiveData.postValue(snapshotDoctor);
    }

    @Override
    public void onError(Exception error) {
        Log.e("error", "loadDoctors:onCancelled",error);

    }

    @Override
    public boolean onSubmit() {
        return false;
    }

    @Override
    public void onErrorUpdated(Exception e) {
        Log.d("error", "UpdatedDoctorOnCancelled: " + e.getMessage());
    }

    @Override
    public void onErrorAdded(Exception e) {
        Log.d("error", "AddedDoctorOnCancelled: " + e.getMessage());

    }
}
