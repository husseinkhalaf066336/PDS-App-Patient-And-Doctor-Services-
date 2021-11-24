package com.eng_hussein_khalaf066336.pds.ui.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eng_hussein_khalaf066336.pds.ui.Activities.ShowMedicalReportActivity;
import com.eng_hussein_khalaf066336.pds.R;
public class UserMedicalReportFragment extends Fragment {
    Button GoToActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_usere_medical_report, container, false);

        GoToActivity = v.findViewById(R.id.UserGOTO);
        GoToActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowMedicalReportActivity.class);
                startActivity(intent);
                ShowMedicalReportActivity.userType="user";
            }
        });

        return v;
    }
}
