package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.eng_hussein_khalaf066336.pds.model.Doctors;
import com.eng_hussein_khalaf066336.pds.ui.Adapter.ChoosePatientAdapter;
import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.Users;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.PatientsViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ChoosePatientActivity extends AppCompatActivity implements OnRecyclerItemClickListener {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ChoosePatientAdapter adapter;
    private ArrayList<Users> patientsList;
    private PatientsViewModel patientsViewModel;

    public static String NAME_ID_choose_patient_CODE= "name_id_choose_patient_code";
//    String name,DateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_patient);
        doInitialization();
    }
    public void doInitialization()
    {
        patientsViewModel =new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(PatientsViewModel.class);
        recyclerView = findViewById(R.id.ChoosePatientActivity_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(lm);
        if (patientsList ==null)
        {
            patientsList = new ArrayList<>();
        }
        adapter= new ChoosePatientAdapter(patientsList,this);
        recyclerView.setAdapter(adapter);
        toolbar = findViewById(R.id.ChoosePatientActivity_toolbar);
        setSupportActionBar(toolbar);
        getPatients("");//all patients

    }
    public void getPatients(String patientId) {
        patientsList.clear();
        patientsViewModel.getPatient(patientId);
        patientsViewModel.getPatientMutableLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshotPatient :dataSnapshot.getChildren())
                {
                    Users user=  snapshotPatient.getValue(Users.class);
                    patientsList.add(user);
//                    name= user.getUserFullName();
//                    DateOfBirth= user.getUserDateOfBirth();
                }
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu,menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_Search).getActionView();

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getPatients("");//all patients;
                return false;
            }
        });
        return  true;
    }
    private void filter(String text) {
        ArrayList<Users> patientsListFilter = new ArrayList<>();
        for (Users patient : patientsList) {
            if (patient.getUserFullName().toLowerCase().contains(text.toLowerCase()) ||
                    patient.getUserEmail().toLowerCase().contains(text.toLowerCase())||
                    patient.getUserAddress().toLowerCase().contains(text.toLowerCase())) {
                patientsListFilter.add(patient);
            }
            adapter.filterList(patientsListFilter);
        }
    }


    @Override
    public void onItemClick(String id) {
        Intent intent = new Intent(getBaseContext(),MedicalReportActivity.class);
        intent.putExtra(NAME_ID_choose_patient_CODE,id);
        startActivity(intent);
        finish();
    }
}
