package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.eng_hussein_khalaf066336.pds.ui.Adapter.ShowUserReportsAdapter;
import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickChooseListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.model.MedicalReport;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.MedicalReportViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ShowMedicalReportActivity extends AppCompatActivity implements OnRecyclerItemClickChooseListener {

    private RecyclerView recyclerView;
    private ShowUserReportsAdapter showUserReportsAdapter;
    private ArrayList<MedicalReport> reportArrayList;
    private MedicalReportViewModel medicalReportViewModel;
    private String ID_Current_user;
    public static String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medical_report);
        doInitialization();
    }

    private void doInitialization() {
        medicalReportViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(MedicalReportViewModel.class);
        recyclerView = findViewById(R.id.ShowMedicalReportActivity_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(lm);
        if (reportArrayList==null)
        {
            reportArrayList = new ArrayList<>();
        }
        showUserReportsAdapter = new ShowUserReportsAdapter(reportArrayList, this);
        recyclerView.setAdapter(showUserReportsAdapter);
        ID_Current_user = CurrentUser.getCurrentUserId();
        if (userType == "user") {
            getMedicalReportForPatient(ID_Current_user);
        } else {
            getMedicalReportForDoctor(ID_Current_user);
        }
    }

    private void getMedicalReportForPatient(String UserId) {
        reportArrayList.clear();
        medicalReportViewModel.getMedicalReportForPatient(UserId);
        medicalReportViewModel.getMutableLiveDataPatientReport().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    MedicalReport medicalReport = postSnapshot.getValue(MedicalReport.class);
                    reportArrayList.add(medicalReport);
                }
                showUserReportsAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getMedicalReportForDoctor(String DoctorId) {
        reportArrayList.clear();
        medicalReportViewModel.getMedicalReportForDoctor(DoctorId);
        medicalReportViewModel.getMutableLiveDataDoctorReport().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    MedicalReport medicalReport = postSnapshot.getValue(MedicalReport.class);
                    reportArrayList.add(medicalReport);
                }
                showUserReportsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClick(String id, String date, String Time) {
        // Toast.makeText(ShowMedicalReportActivity.this, " "+id+" "+
        //date+" "+Time, Toast.LENGTH_SHORT).show();
    }
}
