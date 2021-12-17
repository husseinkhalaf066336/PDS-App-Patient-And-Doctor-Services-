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

import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.ui.Activities.ChooseDoctorActivity;
import com.eng_hussein_khalaf066336.pds.ui.Activities.ShowAppointmentsActivity;
import com.eng_hussein_khalaf066336.pds.ui.Activities.UserProfileActivity;

public class PatientsAppointmentsFragment extends Fragment {
    private CardView cardView_appointment, cardView_ShowAppointment;
    public PatientsAppointmentsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_patient_appointment, container, false);
        cardView_appointment=v.findViewById(R.id.cardViewDashboardUserAppointment1);
        cardView_ShowAppointment =v.findViewById(R.id.cardViewDashboardUserAppointment2);

        cardView_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChooseDoctorActivity.class);
                startActivity(intent);
            }
        });
        cardView_ShowAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAppointmentsActivity.class);
                startActivity(intent);
                ShowAppointmentsActivity.userType="user";


            }
        });
        return v;
    }
}
