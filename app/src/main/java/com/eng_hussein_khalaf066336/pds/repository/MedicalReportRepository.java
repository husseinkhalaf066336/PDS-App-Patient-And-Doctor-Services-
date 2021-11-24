package com.eng_hussein_khalaf066336.pds.repository;

import androidx.annotation.NonNull;

import com.eng_hussein_khalaf066336.pds.model.MedicalReport;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MedicalReportRepository {
    private FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    private DatabaseReference DatabaseReport=firebaseDatabase.getReference().child("MedicalReports");
    private onFirebaseDatabasePatientReportsTaskComplete onFirebaseDatabasePatientReportsTaskComplete;
    private onFirebaseDatabaseDoctorReportsTaskComplete onFirebaseDatabaseDoctorReportsTaskComplete;
    private onReportAdded onReportAdded;

    public MedicalReportRepository(onFirebaseDatabasePatientReportsTaskComplete onFirebaseDatabasePatientReportsTaskComplete
    ,onFirebaseDatabaseDoctorReportsTaskComplete onFirebaseDatabaseDoctorReportsTaskComplete, onReportAdded onReportAdded) {
        this.onFirebaseDatabasePatientReportsTaskComplete = onFirebaseDatabasePatientReportsTaskComplete;
        this.onFirebaseDatabaseDoctorReportsTaskComplete=onFirebaseDatabaseDoctorReportsTaskComplete;
        this.onReportAdded=onReportAdded;
    }

    public void getMedicalReportForPatient(String PatientId)
    {
            DatabaseReport.orderByChild("id_Current_Patient")
                    .equalTo(PatientId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    onFirebaseDatabasePatientReportsTaskComplete.PatientReportDataLoaded(snapshot);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    onFirebaseDatabasePatientReportsTaskComplete.onErrorPatientReportLoaded(error.toException());
                }
            });
    }
    public void getMedicalReportForDoctor(String DoctorId)
    {
        DatabaseReport.orderByChild("id_Current_doctor")
                .equalTo(DoctorId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                onFirebaseDatabaseDoctorReportsTaskComplete.DoctorReportDataLoaded(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                onFirebaseDatabaseDoctorReportsTaskComplete.onErrorDoctorReportLoaded(error.toException());
            }
        });
    }
    public void addReport(MedicalReport medicalReport)
    {
        String ID_Report = DatabaseReport.push().getKey();
        medicalReport.setID_Report(ID_Report);
        DatabaseReport.child(ID_Report).setValue(medicalReport).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    onReportAdded.onSubmit();
                }else{
                    onReportAdded.onErrorAdded(task.getException());
                }
            }
        });

    }
    public interface onFirebaseDatabasePatientReportsTaskComplete {
        void PatientReportDataLoaded(DataSnapshot snapshotReport);
        void onErrorPatientReportLoaded(Exception e);
    }
    public interface onFirebaseDatabaseDoctorReportsTaskComplete {
        void DoctorReportDataLoaded(DataSnapshot snapshotReport);
        void onErrorDoctorReportLoaded(Exception e);
    }
    public interface onReportAdded {
        boolean onSubmit();
        void onErrorAdded(Exception e);
    }
}
