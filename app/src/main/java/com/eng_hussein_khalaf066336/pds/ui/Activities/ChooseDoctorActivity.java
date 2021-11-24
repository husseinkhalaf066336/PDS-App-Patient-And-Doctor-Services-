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
    private ArrayList<Doctors> doctors;
    private DoctorsViewModel doctorsViewModel;
    public static String NAME_ID_choose_doctor_CODE= "name_id_choose_doctor_code";
    private DialogWaitFragment dialogWaitFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_doctor);
        doInitialization();
    }
    private void doInitialization()
    {
        doctorsViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(DoctorsViewModel.class);
        dialogWaitFragment= DialogWaitFragment.newInstance("waiting","please wait when loading the list doctors");
        dialogWaitFragment.show(getSupportFragmentManager(),null);
        recyclerView = findViewById(R.id.chooseDoctor_rv);
        toolbar = findViewById(R.id.chooseDoctor_toolbar);
        setSupportActionBar(toolbar);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(getBaseContext());
        recyclerView.setLayoutManager(lm);
        doctors = new ArrayList<>();
        adapter = new ChooseDoctorAdapter(doctors,this);
        recyclerView.setAdapter(adapter);
        getAllDoctors();

    }
    public void getAllDoctors()
    {
        doctors.clear();
        doctorsViewModel.getDoctor("");//all doctors
        doctorsViewModel.getDoctorMutableLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Doctors doctor=  postSnapshot.getValue(Doctors.class);
                    doctors.add(doctor);
                }
                adapter.notifyDataSetChanged();
                dialogWaitFragment.dismiss();
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

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        return  true;
    }

    @Override
    public void onItemClick(String id) {
        Intent intent = new Intent(getBaseContext(), BookAppointmentActivity.class);
        intent.putExtra(NAME_ID_choose_doctor_CODE,id);
        startActivity(intent);
        finish();
    }
}
