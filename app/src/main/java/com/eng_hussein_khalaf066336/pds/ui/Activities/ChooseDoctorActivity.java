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

import com.eng_hussein_khalaf066336.pds.ui.Adapter.ChooseDoctorAdapter;
import com.eng_hussein_khalaf066336.pds.ui.Fragments.DialogWaitFragment;
import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.Doctors;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.DoctorsViewModel;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class ChooseDoctorActivity extends AppCompatActivity implements OnRecyclerItemClickListener {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private ChooseDoctorAdapter adapter;
    private ArrayList<Doctors> doctorsList;
    private DoctorsViewModel doctorsViewModel;
    public static String NAME_ID_choose_doctor_CODE = "name_id_choose_doctor_code";
    private DialogWaitFragment dialogWaitFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor);
        doInitialization();
    }

    private void doInitialization() {
        doctorsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(DoctorsViewModel.class);
        dialogWaitFragment = DialogWaitFragment.newInstance("waiting", "please wait when loading the list doctors");
        dialogWaitFragment.show(getSupportFragmentManager(), null);
        recyclerView = findViewById(R.id.chooseDoctor_rv);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(lm);
        if (doctorsList == null) {
            doctorsList = new ArrayList<>();
        }
        adapter = new ChooseDoctorAdapter(doctorsList,this);
        recyclerView.setAdapter(adapter);
        toolbar = findViewById(R.id.chooseDoctor_toolbar);
        setSupportActionBar(toolbar);
        getAllDoctors();

    }

    public void getAllDoctors() {
        doctorsList.clear();
        doctorsViewModel.getDoctor("");//all doctors
        doctorsViewModel.getDoctorMutableLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Doctors doctor = postSnapshot.getValue(Doctors.class);
                    doctorsList.add(doctor);
                }
                adapter.notifyDataSetChanged();
                dialogWaitFragment.dismiss();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
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
                getAllDoctors();
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        ArrayList<Doctors> doctorsListFilter = new ArrayList<>();
        for (Doctors doctor : doctorsList) {
            if (doctor.getDoctorFullName().toLowerCase().contains(text.toLowerCase()) ||
                    doctor.getDoctorEmail().toLowerCase().contains(text.toLowerCase())||
                    doctor.getDoctorSpecialization().toLowerCase().contains(text.toLowerCase())||
                    doctor.getDoctorFunctionalSection().toLowerCase().contains(text.toLowerCase())||
                    doctor.getDoctorAddress().toLowerCase().contains(text.toLowerCase())) {
                doctorsListFilter.add(doctor);
            }
            adapter.filterList(doctorsListFilter);
        }
    }

    @Override
    public void onItemClick(String id) {
        Intent intent = new Intent(getBaseContext(), BookAppointmentActivity.class);
        intent.putExtra(NAME_ID_choose_doctor_CODE, id);
        startActivity(intent);
        finish();
    }
}
