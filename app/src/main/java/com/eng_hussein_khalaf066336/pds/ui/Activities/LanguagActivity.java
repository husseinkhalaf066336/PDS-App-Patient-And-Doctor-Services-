package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

import com.eng_hussein_khalaf066336.pds.R;

import java.util.Locale;

public class LanguagActivity extends AppCompatActivity {
    Button Language_btn_AR,Language_btn_EN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_languag);
        Language_btn_AR= findViewById(R.id.Language_btn_ar);
        Language_btn_EN= findViewById(R.id.Language_btn_en);

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
    }


    @SuppressWarnings("deprecation")
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        Configuration conf = getResources().getConfiguration();
        conf.locale = myLocale;
        getResources().updateConfiguration(conf, dm);

        Intent intent = new Intent(getBaseContext(),LoginActivity.class);
        startActivity(intent);
        finish();

        //  Intent intent = new Intent(getBaseContext(),LanguagActivity.class);
      //  startActivity(intent);

        //finish();
       // startActivity(getIntent());
    }

    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
