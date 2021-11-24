package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.model.Doctors;
import com.eng_hussein_khalaf066336.pds.ui.Fragments.DashboardDoctorFragment;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AuthViwModel;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.DoctorsViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewDoctorActivity extends AppCompatActivity {
    private TextInputEditText editText_doctorName,editText_doctorEmail,
            editText_doctorPassword,editText_confirmPassword,editText_doctorDateOfBirth,editText_doctorAddress,
            editText_doctorSpecialization,editText_doctorFunctionalSection,editText_doctorAbout_the_doctor;
    private Button btn_Choose_Age;
    private Switch admin_Switch;
    private CircleImageView circleImageView_doctorImage;
    private String doctorId,doctorFullName ,doctorEmail, doctorPassword,  doctorConfirmPassword,doctorDateOfBirth, doctorAddress,
            doctorSpecialization, doctorFunctionalSection, doctorAbout_the_doctor, doctorType;
    private DoctorsViewModel doctorsViewModel;
    private AuthViwModel authViwModel;
    private ProgressBar progressBar;
    private int PICK_DOCTOR_IMAGE_REQ_CODE = 1;
    private Uri imageUri;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    public static String  optionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_doctor);
        doInitialization();



    }
    private void doInitialization() {
        doctorsViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(DoctorsViewModel.class);
        authViwModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AuthViwModel.class);
        editText_doctorName = findViewById(R.id.activityNewDoctor_EitText_doctorName);
        editText_doctorEmail = findViewById(R.id.activityNewDoctor_EitText_email);
        editText_doctorPassword = findViewById(R.id.activityNewDoctor_EitText_password);
        editText_doctorName = findViewById(R.id.activityNewDoctor_EitText_doctorName);
        editText_confirmPassword = findViewById(R.id.activityNewDoctor_EitText_ConfirmPassword);
        editText_doctorDateOfBirth = findViewById(R.id.activityNewDoctor_EitText_Age);
        editText_doctorAddress = findViewById(R.id.activityNewDoctor_EitText_Address);
        editText_doctorSpecialization = findViewById(R.id.activityNewDoctor_EitText_Specialization);
        editText_doctorFunctionalSection = findViewById(R.id.activityNewDoctor_EitText_Functional_section);
        editText_doctorAbout_the_doctor = findViewById(R.id.activityNewDoctor_EitText_About_the_doctor);
        btn_Choose_Age = findViewById(R.id.activityNewDoctor_btn_Choose_Age);
        admin_Switch= findViewById(R.id.switch1);
        circleImageView_doctorImage = findViewById(R.id.activityNewDoctor_imageView_doctor);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        circleImageView_doctorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent1, PICK_DOCTOR_IMAGE_REQ_CODE);

            }
        });
        btn_Choose_Age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDate();

            }
        });
        if (optionType=="EditDoctorProfile")
        {
            getDoctor(CurrentUser.getCurrentUserId());
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_DOCTOR_IMAGE_REQ_CODE && resultCode == RESULT_OK)
        {
            if (data != null)
            {
                imageUri=data.getData();
                circleImageView_doctorImage.setImageURI(imageUri);
            }

        }
    }


    public void DoctorRegister(View view) {
        doctorFullName = editText_doctorName.getText().toString();
        doctorEmail = editText_doctorEmail.getText().toString();
        doctorPassword = editText_doctorPassword.getText().toString();
        doctorConfirmPassword = editText_confirmPassword.getText().toString();
        doctorDateOfBirth = editText_doctorDateOfBirth.getText().toString();
        doctorAddress = editText_doctorAddress.getText().toString();
        doctorSpecialization = editText_doctorSpecialization.getText().toString();
        doctorFunctionalSection = editText_doctorFunctionalSection.getText().toString();
        doctorAbout_the_doctor = editText_doctorAbout_the_doctor.getText().toString();

        if ( admin_Switch.isChecked()){doctorType="admin";}
        else {doctorType="doctor";}
        if(doctorFullName.isEmpty()||doctorFullName.equals(" "))
        {
            editText_doctorName.setError("Fill here please !");
            return;
        }
        if(doctorEmail.isEmpty()||doctorEmail.equals(" "))
        {
            editText_doctorEmail.setError("Fill here please !");
            return;
        }
        if(doctorPassword.isEmpty()||doctorPassword.equals(" "))
        {
            editText_doctorPassword.setError("Fill here please !");
            return;
        }
        if(doctorConfirmPassword.isEmpty()||doctorConfirmPassword.equals(" "))
        {
            editText_confirmPassword.setError("Fill here please !");
            return;
        }
        if(doctorPassword.length()<8)
        {
            editText_doctorEmail.setError("Length must be greater than 8");
            return;
        }
        if(!doctorConfirmPassword.equals(doctorPassword))
        {
            editText_confirmPassword.setError("Password not match !");
            return;
        }
        if (optionType=="Register")
        {
            authViwModel.signUp(doctorEmail,doctorPassword);
            authViwModel.getFirebaseUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
                @Override
                public void onChanged(FirebaseUser firebaseUser) {
                    if (firebaseUser!=null)
                    {
                        if (firebaseUser!=null) {
                            Toast.makeText(getBaseContext(), "success", Toast.LENGTH_LONG).show();
                            doctorId= firebaseUser.getUid();

                            if (imageUri != null) {
                                uploadToFirebase(imageUri);
                            }else {
                                Doctors doctor = new Doctors(doctorId,doctorFullName,doctorEmail,doctorPassword,
                                        doctorDateOfBirth,doctorAddress,doctorSpecialization,doctorFunctionalSection
                                        ,doctorAbout_the_doctor,doctorType,null);
                                addDoctor(doctor);
                                finish();
                            }
                        }else {
                            Toast.makeText(getBaseContext(), "Authentication failed.",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        }
        else {

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

                        Doctors doctor = new Doctors(doctorId,doctorFullName,doctorEmail,doctorPassword,
                                doctorDateOfBirth,doctorAddress,doctorSpecialization,doctorFunctionalSection
                                ,doctorAbout_the_doctor,doctorType,uri.toString());
                        addDoctor(doctor);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(NewDoctorActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(NewDoctorActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
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

                editText_doctorDateOfBirth.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }
    private void addDoctor(Doctors doctor) {
        doctorsViewModel.addDoctor(doctor);
        Toast.makeText(getBaseContext(), "doctor created successfully but non image! ", Toast.LENGTH_SHORT).show();
    }
    public void getDoctor(String doctorId) {
        doctorsViewModel.getDoctor(doctorId);
        doctorsViewModel.getDoctorMutableLiveData().observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshotDoctor: dataSnapshot.getChildren()) {
                    // TODO: handle the post
                    Doctors doctor=  snapshotDoctor.getValue(Doctors.class);
                    editText_doctorName.setText(doctor.getDoctorFullName());
                    editText_doctorEmail.setText(doctor.getDoctorEmail());
                    editText_doctorPassword.setText(doctor.getDoctorPassword());
                    editText_confirmPassword.setText(doctor.getDoctorPassword());
                    editText_doctorDateOfBirth.setText(doctor.getDoctorDateOfBirth());
                    editText_doctorAddress.setText(doctor.getDoctorAddress());
                    editText_doctorSpecialization.setText(doctor.getDoctorSpecialization());
                    editText_doctorFunctionalSection.setText(doctor.getDoctorFunctionalSection());
                    editText_doctorAbout_the_doctor.setText(doctor.getDoctorAbout_the_doctor());
                }
            }
        });
    }
}
