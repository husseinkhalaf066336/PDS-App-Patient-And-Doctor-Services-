package com.eng_hussein_khalaf066336.pds.repository;

import androidx.annotation.NonNull;

import com.eng_hussein_khalaf066336.pds.model.appointments;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AppointmentsRepository {
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference DatabaseAppointment=firebaseDatabase.getReference().child("Appointments");
    private OnFirebaseDatabasePatientAppointmentsTaskComplete onFirebaseDatabasePatientAppointmentsTaskComplete;
    private OnFirebaseDatabaseDoctorAppointmentsTaskComplete onFirebaseDatabaseDoctorAppointmentsTaskComplete;
    private onAppointmentAdded onAppointmentAdded;

    public AppointmentsRepository(OnFirebaseDatabasePatientAppointmentsTaskComplete onFirebaseDatabasePatientAppointmentsTaskComplete,
                                  OnFirebaseDatabaseDoctorAppointmentsTaskComplete onFirebaseDatabaseDoctorAppointmentsTaskComplete,
                                  onAppointmentAdded onAppointmentAdded) {
        this.onFirebaseDatabasePatientAppointmentsTaskComplete = onFirebaseDatabasePatientAppointmentsTaskComplete;
        this.onFirebaseDatabaseDoctorAppointmentsTaskComplete=onFirebaseDatabaseDoctorAppointmentsTaskComplete;
        this.onAppointmentAdded=onAppointmentAdded;
    }

    public void getPatientAppointment(String PatientId)
    {
        DatabaseAppointment.orderByChild("appointment_current_userId")
                .equalTo(PatientId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onFirebaseDatabasePatientAppointmentsTaskComplete.PatientAppointmentDataLoaded(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onFirebaseDatabasePatientAppointmentsTaskComplete.PatientAppointmentError(databaseError.toException());
            }
        });
    }
    public void getDoctorAppointment(String DoctorId)
    {
        DatabaseAppointment.orderByChild("appointment_current_doctorId")
                .equalTo(DoctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                onFirebaseDatabaseDoctorAppointmentsTaskComplete.DoctorAppointmentDataLoaded(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                onFirebaseDatabaseDoctorAppointmentsTaskComplete.DoctorAppointmentError(databaseError.toException());
            }
        });
    }
    public void addAppointment(appointments appointment){
        String AppointmentId =DatabaseAppointment.push().getKey();
        appointment.setAppointment_Id(AppointmentId);
        DatabaseAppointment.child(AppointmentId).setValue(appointment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    onAppointmentAdded.onSubmit();
                }else{
                    onAppointmentAdded.onErrorAdded(task.getException());
                }
            }
        });
    }


    public interface OnFirebaseDatabasePatientAppointmentsTaskComplete {
        void PatientAppointmentDataLoaded(DataSnapshot snapshotAppointments);
        void PatientAppointmentError(Exception error);
    }
    public interface OnFirebaseDatabaseDoctorAppointmentsTaskComplete {
        void DoctorAppointmentDataLoaded(DataSnapshot snapshotAppointments);
        void DoctorAppointmentError(Exception error);
    }
    public interface onAppointmentAdded{
        boolean onSubmit();
        void onErrorAdded(Exception e);
    }
}
