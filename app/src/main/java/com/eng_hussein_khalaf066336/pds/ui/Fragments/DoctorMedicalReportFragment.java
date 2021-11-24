package com.eng_hussein_khalaf066336.pds.ui.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eng_hussein_khalaf066336.pds.ui.Activities.ChoosePatientActivity;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.ui.Activities.ShowMedicalReportActivity;

public class DoctorMedicalReportFragment extends Fragment {

    Button AddReportButton,ShowReportButton;
    public DoctorMedicalReportFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doctor_medical_report, container, false);
        AddReportButton= v.findViewById(R.id.AddReportButton);
        ShowReportButton= v.findViewById(R.id.ShowReportButton);
        AddReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChoosePatientActivity.class);
                startActivity(intent);
            }
        });
        ShowReportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowMedicalReportActivity.class);
                startActivity(intent);
                ShowMedicalReportActivity.userType="doctor";
            }
        });


        return v;
    }
}
