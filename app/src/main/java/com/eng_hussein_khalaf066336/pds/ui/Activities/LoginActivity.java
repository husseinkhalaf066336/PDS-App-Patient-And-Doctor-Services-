package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.ui.Fragments.DialogWaitFragment;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AuthViwModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText editText_email, editText_password;
    private Button button_login, button_register;
    private String email, password;
    private AuthViwModel authViwModel;
    private FirebaseUser currentUser;
    public static DialogWaitFragment dialogWaitFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        doInitialization(); //Initialize
    }
    private void doInitialization()
    {
        editText_email = findViewById(R.id.activityLogin_EitText_email);
        editText_password = findViewById(R.id.activityLogin_EitText_password);
        button_login = findViewById(R.id.activityLogin_Button_login);
        button_register = findViewById(R.id.activityLogin_Button_registe);
        authViwModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AuthViwModel.class);
        currentUser = authViwModel.getCurrentUser();
        if (currentUser != null) {
            CurrentUser.currentUserId=currentUser.getUid();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_register = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent_register);
                RegisterActivity.optionType="Register";
            }
        });
    }
    public void login(View view) {
        dialogWaitFragment = DialogWaitFragment.newInstance("Waiting","Please Wait");
        dialogWaitFragment.show(getSupportFragmentManager(),null);
        email = editText_email.getText().toString();
        password = editText_password.getText().toString();
        if(email.isEmpty()||email.equals(" "))
        {
            editText_email.setError("Fill here please !");
            return;
        }
        if(password.isEmpty()||password.equals(" "))
        {
            editText_password.setError("Fill here please !");
            return;
        }
        authViwModel.signIn(email,password);
        authViwModel.getFirebaseUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser!=null)
                {
                    Toast.makeText(getBaseContext(),"success",Toast.LENGTH_LONG).show();
                    dialogWaitFragment.dismiss();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    CurrentUser.currentUserId=firebaseUser.getUid();
                    finish();

                } else {
                    dialogWaitFragment.dismiss();
                    Toast.makeText(getBaseContext(),"failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
