package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.model.MedicalReport;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.MedicalReportViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class MedicalReportActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText editText_ReportTitle,
           eitText_ReportDetails,eitText_DoctorsInstructions, editText_SuggestedMedications;
    private Button button_Report_save;
    private String ReportTitle,ReportDetails,DoctorsInstructions,
            SuggestedMedication,ID_Current_Patient,ID_Current_doctor ;
    private MedicalReportViewModel medicalReportViewModel;
    public static String channelID = "X_channelIDReport";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_report);
        doInitialization();

    }
    private void doInitialization()
    {
        editText_ReportTitle = findViewById(R.id.MedicalReportActivity_EitText_ReportTitle);
        eitText_ReportDetails = findViewById(R.id.MedicalReport_EditText_ReportDetails);
        eitText_DoctorsInstructions = findViewById(R.id.MedicalReport_EditText_DoctorsInstructions);
        editText_SuggestedMedications = findViewById(R.id.MedicalReport_EditText_SuggestedMedication);
        button_Report_save = findViewById(R.id.MedicalReportActivity_Button_save);

        medicalReportViewModel =new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(MedicalReportViewModel.class);

        ID_Current_Patient=getIntent().getStringExtra(ChoosePatientActivity.NAME_ID_choose_patient_CODE);
        ID_Current_doctor= CurrentUser.getCurrentUserId();
        button_Report_save.setOnClickListener(this);

    }
    public void addReport(MedicalReport medicalReport)
    {
        medicalReportViewModel.addReport(medicalReport);
        Toast.makeText(getBaseContext(), "MedicalReports created successfully", Toast.LENGTH_SHORT).show();
        GoToShowMedicalReport();
        displayNotification();
    }

    @Override
    public void onClick(View v) {
        ReportTitle=editText_ReportTitle.getText().toString();
        ReportDetails=eitText_ReportDetails.getText().toString();
        DoctorsInstructions=eitText_DoctorsInstructions.getText().toString();
        SuggestedMedication=editText_SuggestedMedications.getText().toString();

        if(ReportTitle.isEmpty()||ReportTitle.equals(" "))
        {
            editText_ReportTitle.setError("Fill here please !");
            return;
        }
        if(ReportDetails.isEmpty()||ReportDetails.equals(" "))
        {
            eitText_ReportDetails.setError("Fill here please !");
            return;
        }
        if(DoctorsInstructions.isEmpty()||DoctorsInstructions.equals(" "))
        {
            eitText_DoctorsInstructions.setError("Fill here please !");
            return;
        }
        if(SuggestedMedication.isEmpty()||SuggestedMedication.equals(" "))
        {
            editText_SuggestedMedications.setError("Fill here please !");
            return;
        }
        MedicalReport medicalReport = new MedicalReport(ReportTitle,ReportDetails,
                DoctorsInstructions,SuggestedMedication,ID_Current_Patient,ID_Current_doctor);
        addReport(medicalReport);
    }
    public void GoToShowMedicalReport()
    {
        ReportTitle.isEmpty();
        ReportDetails.isEmpty();
        DoctorsInstructions.isEmpty();
        SuggestedMedication.isEmpty();
        editText_ReportTitle.setText(" ");
        eitText_ReportDetails.setText(" ");
        eitText_DoctorsInstructions.setText(" ");
        editText_SuggestedMedications.setText(" ");
        Intent intent_show_appointment = new Intent(getBaseContext(), ShowMedicalReportActivity.class);
        startActivity(intent_show_appointment);
        ShowMedicalReportActivity.userType="doctor";
        finish();
    }
    private void displayNotification() {
        Intent intent = new Intent(getBaseContext(), ShowAppointmentsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, intent, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelID, "channel_display_Appointment",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Description");
            NotificationManager NM = getSystemService(NotificationManager.class);
            NM.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext(), channelID);
        builder.setSmallIcon(R.drawable.ic_report2red)
                .setContentTitle(getString(R.string.TitleNotification)).setContentText(getString(R.string.TextNotificationReport)).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.app_name)))
                .setContentIntent(pendingIntent).addAction(R.drawable.ic_report2red, getString(R.string.pendingIntentNotificationReport), pendingIntent);
        NotificationManagerCompat NMC = NotificationManagerCompat.from(getBaseContext());
        NMC.notify(10, builder.build());

    }

}

