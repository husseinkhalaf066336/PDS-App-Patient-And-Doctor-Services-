package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.eng_hussein_khalaf066336.pds.ui.Adapter.ShowUserAppointmentsAdapter;
import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.model.appointments;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AppointmentsViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ShowAppointmentsActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    private RecyclerView recyclerView;
    private ShowUserAppointmentsAdapter ShowUserAppointmentsAdapter;
    private ArrayList<appointments> AppointmentS;
    private AppointmentsViewModel appointmentsViewModel;
    private String ID_Current_user;
    public static String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user_appointments);
        doInitialization();
    }
    private void doInitialization()
    {
        appointmentsViewModel =new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AppointmentsViewModel.class);
        recyclerView = findViewById(R.id.show_user_appointmentActivity_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(lm);
        AppointmentS = new ArrayList<>();
        ShowUserAppointmentsAdapter = new ShowUserAppointmentsAdapter(AppointmentS,this);
        recyclerView.setAdapter(ShowUserAppointmentsAdapter);
        ID_Current_user= CurrentUser.getCurrentUserId();
        if (userType=="user")
        {
            getPatientAppointment(ID_Current_user);

        }
        else {
            getDoctorAppointment(ID_Current_user);
        }
    }
    private void getPatientAppointment(String PatientId)
    {
        if (AppointmentS!=null)
        {
            AppointmentS.clear();
        }
        appointmentsViewModel.getPatientAppointment(PatientId);
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
    public void getDoctorAppointment(String DoctorId)
    {
        if (AppointmentS!=null)
        {
            AppointmentS.clear();
        }
        appointmentsViewModel.getDoctorAppointment(DoctorId);
        appointmentsViewModel.getMutableLiveDataDoctorAppointment().observe(this, new Observer<DataSnapshot>() {
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
    @Override
    public void onItemClick(String id) {
        Toast.makeText(getBaseContext(), ""+id, Toast.LENGTH_SHORT).show();
    }
}