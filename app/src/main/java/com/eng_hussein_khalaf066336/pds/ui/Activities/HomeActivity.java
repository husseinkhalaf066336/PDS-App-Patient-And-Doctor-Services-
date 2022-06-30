package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.ui.Fragments.AvailableAppointmentsDoctorFragment;
import com.eng_hussein_khalaf066336.pds.ui.Fragments.DialogFragment;
import com.eng_hussein_khalaf066336.pds.ui.Fragments.DashboardDoctorFragment;
import com.eng_hussein_khalaf066336.pds.ui.Fragments.DashboardUserFragment;
import com.eng_hussein_khalaf066336.pds.ui.Fragments.DoctorMedicalReportFragment;
import com.eng_hussein_khalaf066336.pds.ui.Fragments.PatientsAppointmentsFragment;
import com.eng_hussein_khalaf066336.pds.ui.Fragments.UserMedicalReportFragment;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.Doctors;
import com.eng_hussein_khalaf066336.pds.model.Users;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AuthViwModel;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.DoctorsViewModel;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.PatientsViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;

public class HomeActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,
        DialogFragment.onPositiveClickListener,DialogFragment.onNegativeClickListener {
    public static String userId, UserType;
    private Toolbar toolbar;
    private ProgressBar progressBarLoad;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Fragment fragment = null;
    private AuthViwModel authViwModel;
    private PatientsViewModel patientsViewModel;
    private DoctorsViewModel doctorsViewModel;
    View H_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        doInitialization(); //Initialize
    }
    public void doInitialization()
    {
        toolbar = findViewById(R.id.nav_toolbar);
        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navigation);
        progressBarLoad = findViewById(R.id.progressBarLoad);


        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
//control icon color
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);
//change icon menu
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_mune3);

        authViwModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AuthViwModel.class);

        patientsViewModel =new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
        .getInstance(this.getApplication())).get(PatientsViewModel.class);

        doctorsViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(DoctorsViewModel.class);

        userId = authViwModel.getCurrentUser().getUid();
        CurrentUser.currentUserId=userId;
        getPatient(userId);
        getDoctor(userId);
    }
    public void getPatient(String patientId) {
        patientsViewModel.getPatient(patientId);
        patientsViewModel.getPatientMutableLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshotPatient :dataSnapshot.getChildren())
                {
                    Users user=  snapshotPatient.getValue(Users.class);
                    UserType=user.getUserType();
                    if (UserType.equals("user")) {
                        fragment = new DashboardUserFragment();
                        loadFragment(fragment);
                        progressBarLoad.setVisibility(View.INVISIBLE);}}}
        });
    }
    public void getDoctor(String doctorId) {
        doctorsViewModel.getDoctor(doctorId);
        doctorsViewModel.getDoctorMutableLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshotDoctor: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Doctors doctor=  snapshotDoctor.getValue(Doctors.class);
                    UserType=doctor.getDoctorType();
                    if (UserType.equals("doctor")||UserType.equals("admin")){
                        fragment = new DashboardDoctorFragment();
                        loadFragment(fragment);
                        progressBarLoad.setVisibility(View.INVISIBLE);}
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.Home:
                if (UserType.equals("user"))
                {
                    fragment = new DashboardUserFragment();
                    loadFragment(fragment);
                }
                if (UserType.equals("doctor")||UserType.equals("admin"))

                {
                    fragment = new DashboardDoctorFragment();
                    loadFragment(fragment);
                }
                return true;
            case R.id.Appointments:
                if (UserType.equals("user"))
                {
                    fragment=new PatientsAppointmentsFragment();
                    loadFragment(fragment);
                }
                if (UserType.equals("doctor")||UserType.equals("admin"))
                {
                    fragment=new AvailableAppointmentsDoctorFragment();
                    loadFragment(fragment);
                }
                return true;

            case R.id.MedicalReport:
                if (UserType.equals("user"))
                {
                    fragment = new UserMedicalReportFragment();
                    loadFragment(fragment);
                }
                if (UserType.equals("doctor")||UserType.equals("admin"))

                {
                    fragment = new DoctorMedicalReportFragment();
                    loadFragment(fragment);
                }
                return true;

            case R.id.MyProfile:
                if (UserType.equals("user"))
                {
                    Intent intent = new Intent(getBaseContext(), UserProfileActivity.class);
                    startActivity(intent);

                }
                if (UserType.equals("doctor")||UserType.equals("admin"))

                {
                    Intent intent = new Intent(getBaseContext(), DoctorProfileActivity.class);
                    startActivity(intent);
                }
                return true;
            case R.id._management:
                if (UserType.equals("user"))
                {
                    Toast.makeText(this, getString(R.string.Not_permission), Toast.LENGTH_SHORT).show();

                }
                if (UserType.equals("doctor"))

                {
                    Toast.makeText(this, getString(R.string.Not_permission), Toast.LENGTH_SHORT).show();

                }
                if (UserType.equals("admin"))

                {
                    Intent intent = new Intent(getBaseContext(), NewDoctorActivity.class);
                    startActivity(intent);
                    NewDoctorActivity.optionType="Register";
                }
                return true;
            case  R.id.App_language:
                Intent intent_language = new Intent(getBaseContext(), LanguagActivity.class);
                startActivity(intent_language);
                finish();
                return true;
            case R.id.Logout:
                DialogFragment dialogFragmentLogout = DialogFragment.newInstance("Logout","are you sure?"
                ,R.drawable.ic_arro);
                dialogFragmentLogout.show(getSupportFragmentManager(),null);
                return true;


        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containet1, fragment).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
      //  fragmentTransaction.addToBackStack(null);
    }

    private void signOutUser() {
        authViwModel.signOut();
        Intent intent = new Intent(getBaseContext(),LoginActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    public void onPositiveButtonClicked() {
        signOutUser();
    }

    @Override
    public void onNegativeButtonClicked() {
    }


}
