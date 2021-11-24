package com.eng_hussein_khalaf066336.pds.ui.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eng_hussein_khalaf066336.pds.ui.Activities.AvailableAppointmentsActivity;
import com.eng_hussein_khalaf066336.pds.ui.Activities.DoctorProfileActivity;
import com.eng_hussein_khalaf066336.pds.ui.Activities.ShowAppointmentsActivity;
import com.eng_hussein_khalaf066336.pds.ui.Activities.ShowAvailableAppointmentsActivity;
import com.eng_hussein_khalaf066336.pds.R;


public class DashboardDoctorFragment extends Fragment {

    CardView cardView_DoctorShowAppointment, cardViewAvailableAppointments, cardViewDoctorMedicalReport,cardView4;
    Fragment fragment = null;


    public DashboardDoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_dashboard_doctor, container, false);
        cardView_DoctorShowAppointment =v.findViewById(R.id.CardViewDashboardDoctor1);
        cardViewAvailableAppointments =v.findViewById(R.id.CardViewDashboardDoctor2);
        cardViewDoctorMedicalReport=v.findViewById(R.id.CardViewDashboardDoctor3);
        cardView4=v.findViewById(R.id.CardViewDashboardDoctor4);

        cardView_DoctorShowAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAppointmentsActivity.class);
                startActivity(intent);
                ShowAppointmentsActivity.userType="doctor";
            }
        });
        cardViewAvailableAppointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new AvailableAppointmentsDoctorFragment();
                loadFragment(fragment);
            }
        });
        cardViewDoctorMedicalReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new DoctorMedicalReportFragment();
                loadFragment(fragment);

            }
        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DoctorProfileActivity.class);
                startActivity(intent);
            }
        });
        return  v;
    }


    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containet1, fragment).commit();
        // drawerLayout.closeDrawer(GravityCompat.START);
        fragmentTransaction.addToBackStack(null);
    }
}
