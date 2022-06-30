package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.eng_hussein_khalaf066336.pds.model.Doctors;
import com.eng_hussein_khalaf066336.pds.model.appointments;
import com.eng_hussein_khalaf066336.pds.ui.Adapter.ChooseAvailableAppointment;
import com.eng_hussein_khalaf066336.pds.ui.Adapter.ShowUserAppointmentsAdapter;
import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickChooseListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.model.availableAppointment;
import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickListener;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AppointmentsViewModel;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AvailableAppointmentsViewModel;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.DoctorsViewModel;
import com.google.firebase.database.DataSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorProfileActivity extends AppCompatActivity implements OnRecyclerItemClickChooseListener, OnRecyclerItemClickListener {
    private Button btn_ShowAppointment, btn_ShowAvailableAppointment, btn_EditProfile ;
    private RecyclerView rv_ShowAppointment, rv_ShowAvailableAppointment;
    private TextView textView_doctorName, textViewEmail;
    private CircleImageView circleImageViewUser;
    boolean  rv_ShowAppointment_visible = false ,rv_ShowAvailableAppointment_visible = false;

    private ShowUserAppointmentsAdapter ShowUserAppointmentsAdapter;
    private ChooseAvailableAppointment AdapterChooseAvailableAppointment;
    private ArrayList<availableAppointment> availableAppointmentList;
    private ArrayList<appointments> AppointmentsList;
    private AvailableAppointmentsViewModel availableAppointmentsViewModel;
    private DoctorsViewModel doctorsViewModel;
    private AppointmentsViewModel appointmentsViewModel;
    private String ID_Current_doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        doInitializationForEditDoctorProfile();
        doInitializationForAvailableAppointments();
        doInitializationForAppointments();

    }

    private void doInitializationForEditDoctorProfile() {
        //EditProfile
        doctorsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(DoctorsViewModel.class);
        textView_doctorName = findViewById(R.id.DoctorProfile_txt_userName);
        textViewEmail = findViewById(R.id.UserProfile_txt_Email);
        circleImageViewUser = findViewById(R.id.DoctorProfile_imageView_user);
        btn_EditProfile = findViewById(R.id.DoctorProfile_btn_EditProfile);
        ID_Current_doctor = CurrentUser.getCurrentUserId();
        getDoctor(ID_Current_doctor);
        btn_EditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), NewDoctorActivity.class);
                startActivity(intent);
                NewDoctorActivity.optionType = "EditDoctorProfile";
            }
        });
    }

    private void doInitializationForAppointments() {
        // appointments
        appointmentsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AppointmentsViewModel.class);
        btn_ShowAppointment = findViewById(R.id.DoctorProfile_btn_ShowAppointment);
        rv_ShowAppointment = findViewById(R.id.DoctorProfile_rv_ShowAppointment);
        rv_ShowAppointment.setVisibility(View.GONE);
        btn_ShowAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv_ShowAppointment_visible==false) {
                    rv_ShowAppointment.setVisibility(View.VISIBLE);
                    rv_ShowAppointment_visible = true;
                } else {
                    rv_ShowAppointment.setVisibility(View.GONE);
                    rv_ShowAppointment_visible = false;
                }
            }
        });
        rv_ShowAppointment.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        rv_ShowAppointment.setLayoutManager(lm);
        if (AppointmentsList == null) {
            AppointmentsList = new ArrayList<>();}
        ShowUserAppointmentsAdapter = new ShowUserAppointmentsAdapter(AppointmentsList, this);
        rv_ShowAppointment.setAdapter(ShowUserAppointmentsAdapter);
        getDoctorAppointment(ID_Current_doctor);
    }

    private void doInitializationForAvailableAppointments() {
        // AvailableAppointments
        availableAppointmentsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AvailableAppointmentsViewModel.class);
        btn_ShowAvailableAppointment = findViewById(R.id.DoctorProfile_btn_ShowAvailableAppointment);
        rv_ShowAvailableAppointment = findViewById(R.id.DoctorProfile_rv_ShowAvailableAppointment);
        rv_ShowAvailableAppointment.setVisibility(View.GONE);
        btn_ShowAvailableAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv_ShowAvailableAppointment_visible==false) {
                    rv_ShowAvailableAppointment.setVisibility(View.VISIBLE);
                    rv_ShowAvailableAppointment_visible = true;
                } else {
                    rv_ShowAvailableAppointment.setVisibility(View.GONE);
                    rv_ShowAvailableAppointment_visible = false;
                }
            }
        });
        rv_ShowAvailableAppointment.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getBaseContext(), 2);
        rv_ShowAvailableAppointment.setLayoutManager(lm);
        if (availableAppointmentList == null) {
            availableAppointmentList = new ArrayList<>();

        }
        AdapterChooseAvailableAppointment = new ChooseAvailableAppointment(availableAppointmentList, this);
        rv_ShowAvailableAppointment.setAdapter(AdapterChooseAvailableAppointment);
        getAvailableAppointments(ID_Current_doctor);
    }

    public void getDoctorAppointment(String DoctorId) {
        AppointmentsList.clear();
        appointmentsViewModel.getDoctorAppointment(DoctorId);
        appointmentsViewModel.getMutableLiveDataDoctorAppointment().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    appointments appointment = postSnapshot.getValue(appointments.class);
                    AppointmentsList.add(appointment);
                }
                ShowUserAppointmentsAdapter.notifyDataSetChanged();
            }
        });

    }

    private void getAvailableAppointments(String ID_doctor) {
        availableAppointmentList.clear();
        availableAppointmentsViewModel.getAvailableAppointments(ID_doctor);
        availableAppointmentsViewModel.getMutableLiveDataAvailableAppointments().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    availableAppointment availableAppointment = postSnapshot.getValue(availableAppointment.class);
                    availableAppointmentList.add(availableAppointment);
                }

                AdapterChooseAvailableAppointment.notifyDataSetChanged();
            }
        });
    }

    public void getDoctor(String doctorId) {
        doctorsViewModel.getDoctor(doctorId);
        doctorsViewModel.getDoctorMutableLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshotDoctor : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Doctors currentDoctor = snapshotDoctor.getValue(Doctors.class);
                    textView_doctorName.setText(currentDoctor.getDoctorFullName());
                    textViewEmail.setText(currentDoctor.getDoctorEmail());
                    Picasso.get().load(currentDoctor.getDoctorImage()).into(circleImageViewUser);
                }
            }
        });
    }


    @Override
    public void onItemClick(String id, String date, String Time) {

    }

    @Override
    public void onItemClick(String id) {

    }
}
