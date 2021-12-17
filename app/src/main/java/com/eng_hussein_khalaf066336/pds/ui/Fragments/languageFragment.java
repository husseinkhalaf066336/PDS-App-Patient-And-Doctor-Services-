package com.eng_hussein_khalaf066336.pds.ui.Fragments;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.ui.Activities.LoginActivity;

import java.util.Locale;


public class languageFragment extends Fragment {
    private Button Language_btn_AR,Language_btn_EN;

    public languageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_language, container, false);
        Language_btn_AR= view.findViewById(R.id.Language_btn_ar);
        Language_btn_EN= view.findViewById(R.id.Language_btn_en);

        Language_btn_AR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tr1 =new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setLocale("ar");

                    }
                });
                tr1.start();
            }
        });
        Language_btn_EN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread tr2 =new Thread(new Runnable() {
                    @Override
                    public void run() {
                        setLocale("en");

                    }
                });
                tr2.start();}
        });
        return view;

    }
    @SuppressWarnings("deprecation")
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration conf = getResources().getConfiguration();
        conf.locale = myLocale;
        getResources().updateConfiguration(conf, dm);

        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().onBackPressed();
    }

}
