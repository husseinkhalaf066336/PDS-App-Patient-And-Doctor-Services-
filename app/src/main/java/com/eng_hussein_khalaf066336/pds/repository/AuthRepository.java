package com.eng_hussein_khalaf066336.pds.repository;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.eng_hussein_khalaf066336.pds.ui.Activities.LoginActivity;
import com.eng_hussein_khalaf066336.pds.ui.Fragments.DialogWaitFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class AuthRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    public AuthRepository(Application application) {
        this.application = application;
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUserMutableLiveData=new MutableLiveData<>();
    }
    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }
    public void signUp(String email, String Pass) {
        firebaseAuth.createUserWithEmailAndPassword(email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                } else {
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void signIn(String email, String Pass) {
        firebaseAuth.signInWithEmailAndPassword(email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                } else {
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    LoginActivity.dialogWaitFragment.dismiss();
                }
            }
        });
    }
    public void signOut()
    {
        firebaseAuth.signOut();
    }
}
