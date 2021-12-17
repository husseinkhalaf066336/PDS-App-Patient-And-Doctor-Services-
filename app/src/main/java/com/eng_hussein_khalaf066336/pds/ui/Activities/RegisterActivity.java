package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.model.Users;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AuthViwModel;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.PatientsViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {
    private TextInputEditText editText_userName,editText_userEmail,
            editText_userPassword,editText_confirmPassword,editText_userDateOfBirth,editText_userAddress;
    private Button btn_Choose_Age;
    private CircleImageView circleImageView_userImage;
    private String UserId, userName,userEmail,
           userPassword,confirmPassword,userDateOfBirth,userAddress,imageUserUri,userType="user";
    private AuthViwModel authViwModel;
    private PatientsViewModel patientsViewModel;
    private ProgressBar progressBar;
    private int PICK_IMAGE_REQ_CODE = 1;
    private Uri imageUri;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    public static String  optionType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        doInitialization(); //Initialize
        circleImageView_userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUserImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentUserImage, PICK_IMAGE_REQ_CODE);

            }
        });
        btn_Choose_Age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDate();

            }
        });

    }
    private void doInitialization()
    {
        editText_userName = findViewById(R.id.activityRegister_EitText_userName1);
        editText_userEmail = findViewById(R.id.activityRegister_EitText_email1);
        editText_userPassword = findViewById(R.id.activityRegister_EitText_password1);
        editText_confirmPassword = findViewById(R.id.activityRegister_EitText_ConfirmPassword1);
        editText_userDateOfBirth = findViewById(R.id.activityRegister_EitText_Age1);
        editText_userAddress = findViewById(R.id.activityRegister_EitText_Address1);
        btn_Choose_Age = findViewById(R.id.activityRegister_btn_Choose_Age1);
        circleImageView_userImage = findViewById(R.id.UserProfile_imageView_user);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        authViwModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AuthViwModel.class);
        patientsViewModel =new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(PatientsViewModel.class);
        if (optionType=="EditUserProfile")
        {
            getPatient(CurrentUser.getCurrentUserId());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQ_CODE && resultCode == RESULT_OK)
        {
            if (data != null)
            {
                imageUri=data.getData();
                circleImageView_userImage.setImageURI(imageUri);
            }

        }
    }
    public void register(View view) {
        userName = editText_userName.getText().toString();
        userEmail = editText_userEmail.getText().toString();
        userPassword = editText_userPassword.getText().toString();
        confirmPassword = editText_confirmPassword.getText().toString();
        userDateOfBirth = editText_userDateOfBirth.getText().toString();
        userAddress = editText_userAddress.getText().toString();

        if(userName.isEmpty()||userName.equals(" "))
        {
            editText_userName.setError("Fill here please !");
            return;
        }
        if(userEmail.isEmpty()||userEmail.equals(" "))
        {
            editText_userEmail.setError("Fill here please !");
            return;
        }
        if(userPassword.isEmpty()||userPassword.equals(" "))
        {
            editText_userPassword.setError("Fill here please !");
            return;
        }
        if(confirmPassword.isEmpty()||confirmPassword.equals(" "))
        {
            editText_confirmPassword.setError("Fill here please !");
            return;
        }
        if(userPassword.length()<8)
        {
            editText_userPassword.setError("Length must be greater than 8");
            return;
        }
        if(!confirmPassword.equals(userPassword))
        {
            editText_confirmPassword.setError("Password not match !");
            return;
        }
        //Check the type of operation
        //Register an account or edit
        if (optionType=="Register")
        {
            authViwModel.signUp(userEmail,userPassword);
            authViwModel.getFirebaseUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
                @Override
                public void onChanged(FirebaseUser firebaseUser) {
                    if (firebaseUser!=null)
                    {
                        Toast.makeText(getBaseContext(),"success",Toast.LENGTH_LONG).show();
                        UserId =firebaseUser.getUid();
                        if (imageUri!=null) {
                            uploadToFirebase(imageUri);}
                        else {//register without image
                            Users user = new Users(UserId,userName,userEmail,userPassword,
                                    userDateOfBirth,userAddress,userType,null);
                            patientsViewModel.addPatient(user);
                            Toast.makeText(getBaseContext(), "User created successfully but non image!", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getBaseContext(),HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(getBaseContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else
        {//Edit
            UserId =CurrentUser.getCurrentUserId();
            if (imageUri!=null) {
                uploadToFirebase(imageUri);}
            else {//Edit but the same image
                Users user = new Users(UserId,userName,userEmail,userPassword,
                        userDateOfBirth,userAddress,userType,imageUserUri);
                patientsViewModel.updatePatient(user);
                Toast.makeText(getBaseContext(), "User update successfully but non image!", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getBaseContext(),HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

    }
    private void uploadToFirebase(Uri uri) {

        final StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Users user = new Users(UserId,userName,userEmail,userPassword,
                                userDateOfBirth,userAddress,userType,uri.toString());
                        if (optionType=="Register")
                        {
                            patientsViewModel.addPatient(user);
                            Toast.makeText(getBaseContext(), "User created successfully!", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            patientsViewModel.updatePatient(user);
                            Toast.makeText(getBaseContext(), "User updated successfully!", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(RegisterActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getBaseContext(),HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(RegisterActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String getFileExtension(Uri uri){

        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));

    }

    private void handleDate() {
        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.YEAR, year);
                calendar1.set(Calendar.MONTH, month);
                calendar1.set(Calendar.DATE, date);
                String dateText = DateFormat.format("EEEE, MMM d, yyyy", calendar1).toString();

                editText_userDateOfBirth.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }
    public void getPatient(String patientId) {
        patientsViewModel.getPatient(patientId);
        patientsViewModel.getPatientMutableLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshotPatient :dataSnapshot.getChildren())
                {
                    Users user=  snapshotPatient.getValue(Users.class);
                    editText_userName.setText(user.getUserFullName());
                    editText_userEmail.setText(user.getUserEmail());
                    editText_userPassword.setText(user.getUserPassword());
                    editText_confirmPassword.setText(user.getUserPassword());
                    editText_userDateOfBirth.setText(user.getUserDateOfBirth());
                    editText_userAddress.setText(user.getUserAddress());
                    Picasso.get().load(user.getUserImage()).into(circleImageView_userImage);
                    imageUserUri=user.getUserImage();

                }}
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        onClear();
    }
    private void onClear()
    {
        editText_userName.setText(" ");
        editText_userEmail.setText(" ");
        editText_userPassword.setText(" ");
        editText_confirmPassword.setText(" ");
        editText_userDateOfBirth.setText(" ");
        editText_userAddress.setText(" ");
        circleImageView_userImage.setImageDrawable(getDrawable(R.drawable.img9));
    }
}
