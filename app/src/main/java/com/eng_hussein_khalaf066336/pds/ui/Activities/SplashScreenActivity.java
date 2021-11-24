package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AuthViwModel;

public class SplashScreenActivity extends AppCompatActivity {
    ImageView imageView_logo;
    LottieAnimationView lottieAnimationView;
    private AuthViwModel authViwModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init(); //Initialize


    }
    private void init()
    {
        imageView_logo = findViewById(R.id.SplashScreen_imageView_logo);
        lottieAnimationView = findViewById(R.id.animationView);
        authViwModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AuthViwModel.class);
        imageView_logo.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        lottieAnimationView.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (authViwModel.getCurrentUser() != null) {
                    Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                    startActivity(intent);
                    CurrentUser.currentUserId=authViwModel.getCurrentUser().getUid();
                    finish();
                }
                else {
                    Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 4500);
    }
}
