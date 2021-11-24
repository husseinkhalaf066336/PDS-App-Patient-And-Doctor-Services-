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

import com.eng_hussein_khalaf066336.pds.ui.Activities.ChooseDoctorActivity;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.ui.Activities.ShowAppointmentsActivity;
import com.eng_hussein_khalaf066336.pds.ui.Activities.UserProfileActivity;

public class DashboardUserFragment extends Fragment {

    CardView cardView_appointment,cardView_diagnoses,cardViewUsereMedicalReport,cardView4;
    Fragment fragment = null;


    public DashboardUserFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View v = inflater.inflate(R.layout.fragment_dashboard_user, container, false);
        cardView_appointment=v.findViewById(R.id.cardViewDashboardUser1);
        cardView_diagnoses=v.findViewById(R.id.cardViewDashboardUser2);
        cardViewUsereMedicalReport=v.findViewById(R.id.cardViewDashboardUser3);
        cardView4=v.findViewById(R.id.cardViewDashboardUser4);

        cardView_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChooseDoctorActivity.class);
                startActivity(intent);


            }
        });

        cardView_diagnoses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAppointmentsActivity.class);
                startActivity(intent);
                ShowAppointmentsActivity.userType="user";


            }
        });
        cardViewUsereMedicalReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new UserMedicalReportFragment();
                loadFragment(fragment);


            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserProfileActivity.class);
                startActivity(intent);

            }
        });
        return v;
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.containet1, fragment).commit();
        // drawerLayout.closeDrawer(GravityCompat.START);
         fragmentTransaction.addToBackStack(null);
    }

}
