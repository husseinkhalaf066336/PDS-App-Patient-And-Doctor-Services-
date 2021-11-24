package com.eng_hussein_khalaf066336.pds.ui.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.ui.Activities.AvailableAppointmentsActivity;
import com.eng_hussein_khalaf066336.pds.ui.Activities.ShowAvailableAppointmentsActivity;

public class AvailableAppointmentsDoctorFragment extends Fragment {
    CardView cardView_AvailableAppointmentsActivity,cardView_ShowAvailableAppointmentsActivity;

    public AvailableAppointmentsDoctorFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_available_appointments_doctor, container, false);
        cardView_AvailableAppointmentsActivity=view.findViewById(R.id.cardViewDashboardDoctorAvailableAppointment1);
        cardView_ShowAvailableAppointmentsActivity=view.findViewById(R.id.cardViewDashboardDoctorAvailableAppointment2);
        cardView_AvailableAppointmentsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AvailableAppointmentsActivity.class);
                startActivity(intent);
            }
        });
        cardView_ShowAvailableAppointmentsActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAvailableAppointmentsActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }



}
