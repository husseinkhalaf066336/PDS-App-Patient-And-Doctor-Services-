package com.eng_hussein_khalaf066336.pds.repository;

import androidx.annotation.NonNull;

import com.eng_hussein_khalaf066336.pds.model.Doctors;
import com.eng_hussein_khalaf066336.pds.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DoctorsRepository {
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference DatabaseDoctor=firebaseDatabase.getReference().child("doctors");
    private OnFirebaseDatabaseDoctorsTaskComplete onFirebaseDatabaseDoctorsTaskComplete;
    private OnDoctorAdded onDoctorAdded;

    public DoctorsRepository(OnFirebaseDatabaseDoctorsTaskComplete onFirebaseDatabaseDoctorsTaskComplete
    ,OnDoctorAdded onDoctorAdded) {
        this.onFirebaseDatabaseDoctorsTaskComplete = onFirebaseDatabaseDoctorsTaskComplete;
        this.onDoctorAdded=onDoctorAdded;
    }
    public void getDoctor(String doctorId)
    {
        if (doctorId!="")
        {
            DatabaseDoctor.orderByChild("doctorId")
                    .equalTo(doctorId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            onFirebaseDatabaseDoctorsTaskComplete.doctorDataLoaded(snapshot);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            onFirebaseDatabaseDoctorsTaskComplete.onError(error.toException());
                        }
                    });
        }
        else {
            DatabaseDoctor.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    onFirebaseDatabaseDoctorsTaskComplete.doctorDataLoaded(snapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    onFirebaseDatabaseDoctorsTaskComplete.onError(error.toException());
                }
            });
        }

    }
    public void addDoctor(Doctors doctor) {
        DatabaseDoctor.child(doctor.getDoctorId()).setValue(doctor).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    onDoctorAdded.onSubmit();
                } else {
                    onDoctorAdded.onErrorAdded(task.getException());
                }
            }
        });
    }
    public interface OnFirebaseDatabaseDoctorsTaskComplete {
        void doctorDataLoaded( DataSnapshot snapshotDoctor);
        void onError(Exception error);
    }
    public interface OnDoctorAdded {
        boolean onSubmit();
        void onErrorAdded(Exception e);
    }
}
