package com.eng_hussein_khalaf066336.pds.ui.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.eng_hussein_khalaf066336.pds.repository.AuthRepository;
import com.google.firebase.auth.FirebaseUser;

public class AuthViwModel extends AndroidViewModel {
    private AuthRepository authRepository;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private FirebaseUser currentUser;
    public AuthViwModel(@NonNull Application application) {
        super(application);
        authRepository=new AuthRepository(application);
        currentUser=authRepository.getCurrentUser();
        firebaseUserMutableLiveData=authRepository.getFirebaseUserMutableLiveData();
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public FirebaseUser getCurrentUser() {
        return currentUser;
    }

    public void signUp(String email, String Pass) {
        authRepository.signUp(email, Pass);
    }
    public void signIn(String email, String Pass) {
        authRepository.signIn(email, Pass);
    }
    public void signOut(){authRepository.signOut();}
}
