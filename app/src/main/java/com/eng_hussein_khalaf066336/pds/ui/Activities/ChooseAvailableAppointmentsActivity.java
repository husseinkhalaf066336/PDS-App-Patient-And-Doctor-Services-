package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.eng_hussein_khalaf066336.pds.ui.Adapter.ChooseAvailableAppointment;
import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickChooseListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.availableAppointment;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AvailableAppointmentsViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ChooseAvailableAppointmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ChooseAvailableAppointment AdapterChooseAvailableAppointment;
    private ArrayList<com.eng_hussein_khalaf066336.pds.model.availableAppointment> availableAppointmentS;
    private AvailableAppointmentsViewModel availableAppointmentsViewModel;
    public static int DAT_RESULT_CODE = 11;
    private String ID_Current_doctor;
    public static String NAME_ID_AVAILABLE_APPOINTMENT_CODE = "name_id_available_appointment";
    public static String NAME_DATE_CODE = "name_date_code";
    public static String NAME_TIME_CODE = "name_time_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choos_availbale_appointments);
        doInitialization();
    }

    private void doInitialization() {

        recyclerView = findViewById(R.id.ChoosAvailbaleAppointmentsActivity_recyclerView);
        availableAppointmentsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AvailableAppointmentsViewModel.class);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getBaseContext(), 2);
        recyclerView.setLayoutManager(lm);
        if (availableAppointmentS==null){
            availableAppointmentS = new ArrayList<>();
        }
        AdapterChooseAvailableAppointment = new ChooseAvailableAppointment(availableAppointmentS, new OnRecyclerItemClickChooseListener() {
            @Override
            public void onItemClick(String id, String date, String Time) {
                Intent intent = new Intent();
                intent.putExtra(NAME_ID_AVAILABLE_APPOINTMENT_CODE, id);
                intent.putExtra(NAME_DATE_CODE, date);
                intent.putExtra(NAME_TIME_CODE, Time);
                setResult(DAT_RESULT_CODE, intent);
                finish();

            }

        });
        recyclerView.setAdapter(AdapterChooseAvailableAppointment);
        ID_Current_doctor = getIntent().getStringExtra(BookAppointmentActivity.ID_NAME_DOCTOR_CODE);
        getAvailableAppointments(ID_Current_doctor);
    }

    private void getAvailableAppointments(String ID_doctor) {
        availableAppointmentS.clear();
        availableAppointmentsViewModel.getAvailableAppointments(ID_doctor);
        availableAppointmentsViewModel.getMutableLiveDataAvailableAppointments().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    availableAppointment availableAppointment = postSnapshot.getValue(availableAppointment.class);
                    availableAppointmentS.add(availableAppointment);
                }

                AdapterChooseAvailableAppointment.notifyDataSetChanged();
            }
        });
    }
}
