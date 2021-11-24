package com.eng_hussein_khalaf066336.pds.repository;

import androidx.annotation.NonNull;

import com.eng_hussein_khalaf066336.pds.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PatientsRepository {
    private onFirebaseDatabasePatientsTaskComplete onFirebaseDatabasePatientsTaskComplete;
    private PatientsRepository.onUserAdded onUserAdded;
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference DatabasePatient=firebaseDatabase.getReference().child("users");

    public PatientsRepository(onFirebaseDatabasePatientsTaskComplete onFirebaseDatabasePatientsTaskComplete, PatientsRepository.onUserAdded onUserAdded) {
        this.onFirebaseDatabasePatientsTaskComplete = onFirebaseDatabasePatientsTaskComplete;
        this.onUserAdded=onUserAdded;
    }
    public void getPatient(String patientId)
    {
        if (patientId!="")
        {
            DatabasePatient.orderByChild("userId")
                    .equalTo(patientId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            onFirebaseDatabasePatientsTaskComplete.patientDataLoaded(snapshot);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            onFirebaseDatabasePatientsTaskComplete.onErrorLoaded(error.toException());
                        }
                    });
        }
        else {
            DatabasePatient.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    onFirebaseDatabasePatientsTaskComplete.patientDataLoaded(snapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    onFirebaseDatabasePatientsTaskComplete.onErrorLoaded(error.toException());
                }
            });
        }
    }
    public void addPatient(Users user)
    {
        DatabasePatient.child(user.getUserId()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    onUserAdded.onSubmit();
                }else{
                    onUserAdded.onErrorAdded(task.getException());
                }
            }
        });
    }
    public interface onFirebaseDatabasePatientsTaskComplete {
        void patientDataLoaded(DataSnapshot snapshotPatient);
        void onErrorLoaded(Exception e);
    }
    public interface onUserAdded {
        boolean onSubmit();
        void onErrorAdded(Exception e);
    }
}
