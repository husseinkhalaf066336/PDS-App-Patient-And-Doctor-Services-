package com.eng_hussein_khalaf066336.pds.repository;

import androidx.annotation.NonNull;

import com.eng_hussein_khalaf066336.pds.model.availableAppointment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AvailableAppointmentsRepository {
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference DatabaseAvailableAppointment =firebaseDatabase.getReference().child("availableAppointment");
    private OnFirebaseDatabaseAvailableAppointmentsTaskComplete onFirebaseDatabaseAvailableAppointmentsTaskComplete;
    private OnAvailableAppointmentAdded onAvailableAppointmentAdded;
    private OnAvailableAppointmentDeleted onAvailableAppointmentDeleted;

    public AvailableAppointmentsRepository(OnFirebaseDatabaseAvailableAppointmentsTaskComplete onFirebaseDatabaseAvailableAppointmentsTaskComplete
    ,OnAvailableAppointmentAdded onAvailableAppointmentAdded,OnAvailableAppointmentDeleted onAvailableAppointmentDeleted) {
        this.onFirebaseDatabaseAvailableAppointmentsTaskComplete = onFirebaseDatabaseAvailableAppointmentsTaskComplete;
        this.onAvailableAppointmentAdded=onAvailableAppointmentAdded;
        this.onAvailableAppointmentDeleted=onAvailableAppointmentDeleted;
    }

    public void getAvailableAppointments(String DoctorId)
    {
            DatabaseAvailableAppointment.orderByChild("availableAppointmentDoctorId")
                    .equalTo(DoctorId)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshotAppointments) {
                            onFirebaseDatabaseAvailableAppointmentsTaskComplete.AvailableAppointmentDataLoaded(snapshotAppointments);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            onFirebaseDatabaseAvailableAppointmentsTaskComplete.onError(error.toException());
                        }
                    });
    }
    public void addAvailableAppointment(availableAppointment availableAppointment){
        String availableAppointmentId =DatabaseAvailableAppointment.push().getKey();
        availableAppointment.setAvailableAppointmentId(availableAppointmentId);
        DatabaseAvailableAppointment.child(availableAppointmentId).setValue(availableAppointment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    onAvailableAppointmentAdded.onSubmit();
                }else{
                    onAvailableAppointmentAdded.onErrorAdded(task.getException());
                }
            }
        });
    }
    public void deleteAvailableAppointments(String ID)
    {
        DatabaseAvailableAppointment.child(ID).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    onAvailableAppointmentDeleted.onSubmit();
                }else{
                    onAvailableAppointmentDeleted.onErrorDeleted(task.getException());
                }
            }
        });
    }

    public interface OnFirebaseDatabaseAvailableAppointmentsTaskComplete {
        void AvailableAppointmentDataLoaded(DataSnapshot snapshotAvailableAppointments);
        void onError(Exception error);
    }
    public interface OnAvailableAppointmentAdded{
        boolean onSubmit();
        void onErrorAdded(Exception e);
    }
    public interface OnAvailableAppointmentDeleted{
        boolean onSubmit();
        void onErrorDeleted(Exception e);
    }
}
