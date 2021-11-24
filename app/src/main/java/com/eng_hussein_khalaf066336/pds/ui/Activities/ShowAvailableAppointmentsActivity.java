package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.eng_hussein_khalaf066336.pds.ui.Adapter.ChooseAvailableAppointment;
import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickChooseListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.model.availableAppointment;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AvailableAppointmentsViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ShowAvailableAppointmentsActivity extends AppCompatActivity implements OnRecyclerItemClickChooseListener {

    private RecyclerView recyclerView;
    private ChooseAvailableAppointment AdapterChooseAvailableAppointment;
    private ArrayList<availableAppointment> availableAppointmentS;
    private AvailableAppointmentsViewModel availableAppointmentsViewModel;
    private String ID_Current_doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_availble_appointnent);
        doInitialization();
    }
    private void doInitialization()
    {

        availableAppointmentsViewModel =new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AvailableAppointmentsViewModel.class);
        recyclerView = findViewById(R.id.ShowAvailbaleAppointmentsActivity_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new GridLayoutManager(getBaseContext(),2);
        recyclerView.setLayoutManager(lm);
        availableAppointmentS = new ArrayList<>();
        AdapterChooseAvailableAppointment = new ChooseAvailableAppointment (availableAppointmentS,this);
        recyclerView.setAdapter(AdapterChooseAvailableAppointment);
        ID_Current_doctor= CurrentUser.getCurrentUserId();
        getAvailableAppointments(ID_Current_doctor);
    }

        private void getAvailableAppointments(String ID_doctor) {
        if (availableAppointmentS != null) {
            availableAppointmentS.clear();
        }
        availableAppointmentsViewModel.getAvailableAppointments(ID_doctor);
        availableAppointmentsViewModel.getMutableLiveDataAvailableAppointments().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    availableAppointment availableAppointment=  postSnapshot.getValue(availableAppointment.class);
                    availableAppointmentS.add(availableAppointment);
                }

                AdapterChooseAvailableAppointment.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onItemClick(String id, String date, String Time) {

    }
}
