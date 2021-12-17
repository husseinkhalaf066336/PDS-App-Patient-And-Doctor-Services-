package com.eng_hussein_khalaf066336.pds.ui.viewModel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.eng_hussein_khalaf066336.pds.model.Users;
import com.eng_hussein_khalaf066336.pds.repository.PatientsRepository;
import com.google.firebase.database.DataSnapshot;

public class PatientsViewModel extends ViewModel implements PatientsRepository.onFirebaseDatabasePatientsTaskComplete, PatientsRepository.onUserAdded, PatientsRepository.onUserUpdated {
    private PatientsRepository patientsRepository =new PatientsRepository(this,this,this);
    private MutableLiveData<DataSnapshot> patientMutableLiveData=new MutableLiveData<>();

    public MutableLiveData<DataSnapshot> getPatientMutableLiveData() {
        return patientMutableLiveData;
    }

    public void getPatient(String patientId) {
        patientsRepository.getPatient(patientId);}
    public void addPatient(Users user){
        patientsRepository.addPatient(user);
    }
    public void  updatePatient(Users user)
    {
        patientsRepository.updatePatient(user);
    }
    @Override
    public void patientDataLoaded(DataSnapshot snapshotPatient) {
        patientMutableLiveData.postValue(snapshotPatient);

    }

    @Override
    public void onErrorLoaded(Exception e) {
        Log.d("error", "loadPatient:onCancelled: " + e.getMessage());
    }

    @Override
    public boolean onSubmit() {
        return true;
    }

    @Override
    public void onErrorUpdated(Exception e) {
        Log.d("error", "UpdatedPatient:onCancelled: " + e.getMessage());

    }

    @Override
    public void onErrorAdded(Exception e) {
        Log.d("error", "AddedPatient:onCancelled: " + e.getMessage());
    }
}
