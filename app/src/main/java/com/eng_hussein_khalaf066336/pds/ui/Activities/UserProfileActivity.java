package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eng_hussein_khalaf066336.pds.model.Users;
import com.eng_hussein_khalaf066336.pds.ui.Adapter.ShowUserAppointmentsAdapter;
import com.eng_hussein_khalaf066336.pds.ui.Adapter.ShowUserReportsAdapter;
import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickChooseListener;
import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.model.MedicalReport;
import com.eng_hussein_khalaf066336.pds.model.appointments;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AppointmentsViewModel;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.MedicalReportViewModel;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.PatientsViewModel;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserProfileActivity extends AppCompatActivity {
    private Button btn_ShowAppointment ,btn_ShowMedicalReport,btn_EditProfile;
    private RecyclerView rv_ShowAppointment , rv_ShowMedicalReport;
    private TextView textView_userName,textViewEmail;
    private CircleImageView circleImageViewUser;
    private boolean rv_ShowAppointment_visible=false;
    private boolean rv_ShowMedicalReport_visible=false;
    private ShowUserAppointmentsAdapter ShowUserAppointmentsAdapter;
    private ArrayList<appointments> AppointmentS;
    private AppointmentsViewModel appointmentsViewModel;
    private MedicalReportViewModel medicalReportViewModel;
    private ShowUserReportsAdapter showUserReportsAdapter;
    private PatientsViewModel patientsViewModel;
    private ArrayList<MedicalReport> reportArrayList;
    private String ID_Current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        doInitializationForEditUserProfile();
        doInitializationForAppointments();
        doInitializationForReports();

    }
    private void doInitializationForEditUserProfile() {

        //EditProfile
        patientsViewModel =new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(PatientsViewModel.class);
        textView_userName =findViewById(R.id.UserProfile_txt_userName);
        textViewEmail =findViewById(R.id.UserProfile_txt_Email);
        circleImageViewUser =findViewById(R.id.UserProfile_imageView_user);
        btn_EditProfile =findViewById(R.id.UserProfile_btn_EditProfile);
        ID_Current_user= CurrentUser.getCurrentUserId();
        getPatient(ID_Current_user);
        btn_EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(),RegisterActivity.class);
                startActivity(intent);
                RegisterActivity.optionType="EditUserProfile";
            }
        });
    }

    private void doInitializationForAppointments()
    {
        // appointments
        appointmentsViewModel =new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AppointmentsViewModel.class);
        btn_ShowAppointment =findViewById(R.id.UserProfile_btn_ShowAppointment);
        rv_ShowAppointment =findViewById(R.id.UserProfile_rv_ShowAppointment);
        rv_ShowAppointment.setVisibility(View.GONE);

        btn_ShowAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv_ShowAppointment_visible==false)
                {
                    rv_ShowAppointment.setVisibility(View.VISIBLE);
                    rv_ShowAppointment_visible=true;
                }
                else
                {
                    rv_ShowAppointment.setVisibility(View.GONE);
                    rv_ShowAppointment_visible=false;
                }
            }
        });
        rv_ShowAppointment.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        rv_ShowAppointment.setLayoutManager(lm);
        AppointmentS = new ArrayList<>();
        ShowUserAppointmentsAdapter  = new ShowUserAppointmentsAdapter(AppointmentS, new OnRecyclerItemClickListener() {
            @Override
            public void onItemClick(String id) {
                Toast.makeText(getBaseContext(), ""+id, Toast.LENGTH_SHORT).show();

            }

        });
        rv_ShowAppointment.setAdapter(ShowUserAppointmentsAdapter);
        getAppointment(ID_Current_user);
    }

    private void doInitializationForReports() {
        //MedicalReport
        medicalReportViewModel =new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(MedicalReportViewModel.class);
        btn_ShowMedicalReport =findViewById(R.id.UserProfile_btn_ShowMedicalReport);
        rv_ShowMedicalReport =findViewById(R.id.UserProfile_rv_ShowMedicalReport);
        rv_ShowMedicalReport.setVisibility(View.GONE);
        btn_ShowMedicalReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv_ShowMedicalReport_visible==false)
                {
                    rv_ShowMedicalReport.setVisibility(View.VISIBLE);
                    rv_ShowMedicalReport_visible=true;
                }
                else
                {
                    rv_ShowMedicalReport.setVisibility(View.GONE);
                    rv_ShowMedicalReport_visible=false;
                }
            }
        });
        rv_ShowMedicalReport.setHasFixedSize(true);
        RecyclerView.LayoutManager lm_report = new LinearLayoutManager(getBaseContext());
        rv_ShowMedicalReport.setLayoutManager(lm_report);

        reportArrayList = new ArrayList<>();
        showUserReportsAdapter = new ShowUserReportsAdapter(reportArrayList, new OnRecyclerItemClickChooseListener() {
            @Override
            public void onItemClick(String id, String date, String Time) {
            }
        });
        rv_ShowMedicalReport.setAdapter(showUserReportsAdapter);
        getMedicalReport(ID_Current_user);

    }
    public void getPatient(String patientId) {
        patientsViewModel.getPatient(patientId);
        patientsViewModel.getPatientMutableLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshotPatient :dataSnapshot.getChildren())
                {
                    Users user=  snapshotPatient.getValue(Users.class);
                    textView_userName.setText(user.getUserFullName());
                    textViewEmail.setText(user.getUserType());
                    Picasso.get().load(user.getUserImage()).into(circleImageViewUser);

                }}
        });
    }
    private void getAppointment(String UserId)
    {
        if (AppointmentS!=null)
        {
            AppointmentS.clear();
        }
        appointmentsViewModel.getPatientAppointment(UserId);
        appointmentsViewModel.getMutableLiveDataPatientAppointment().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    appointments appointment=  postSnapshot.getValue(appointments.class);
                    AppointmentS.add(appointment);
                }

                ShowUserAppointmentsAdapter.notifyDataSetChanged();
            }
        });

    }
    private void getMedicalReport(String UserId)
    {
        if (reportArrayList!=null)
        {
            reportArrayList.clear();
        }
        medicalReportViewModel.getMedicalReportForPatient(UserId);
        medicalReportViewModel.getMutableLiveDataPatientReport().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    MedicalReport medicalReport=  postSnapshot.getValue(MedicalReport.class);
                    reportArrayList.add(medicalReport);
                }
                showUserReportsAdapter.notifyDataSetChanged();
            }
        });
    }
}
