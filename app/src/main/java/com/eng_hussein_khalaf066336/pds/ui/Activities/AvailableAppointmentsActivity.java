package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.CurrentUser;
import com.eng_hussein_khalaf066336.pds.model.availableAppointment;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AvailableAppointmentsViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;

public class AvailableAppointmentsActivity extends AppCompatActivity {
    private AvailableAppointmentsViewModel availableAppointmentsViewModel;
    private TextInputEditText editText_DateAppointment,editText_TimeAppointment;
    private Button ButtonDateAppointment,ButtonTimeAppointment,ButtonSaveAppointment;
    private String DateAppointment,TimeAppointment,
            availableAppointmentDoctorId;
    public static String channelID = "X_channelIDAvailableAppointments";
    private ArrayList<com.eng_hussein_khalaf066336.pds.model.availableAppointment> availableAppointments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_appointments);
        doInitialization();
    }
    private void doInitialization()
    {
        availableAppointmentsViewModel=new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AvailableAppointmentsViewModel.class);

        editText_DateAppointment = findViewById(R.id.AvailableAppointment_EitText_DateAppointment);
        editText_TimeAppointment = findViewById(R.id.AvailableAppointment_EitText_TimeAppointment);
        ButtonDateAppointment = findViewById(R.id.AvailableAppointmentsActivity_Button_AddDate);
        ButtonTimeAppointment = findViewById(R.id.AvailableAppointmentsActivity_Button_AddTime);
        ButtonSaveAppointment = findViewById(R.id.AvailableAppointmentsActivity_Button_save);
        availableAppointments=new ArrayList<>();
        availableAppointmentDoctorId= CurrentUser.getCurrentUserId();
        ButtonDateAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleDate();
            }
        });
        ButtonTimeAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTime();
            }
        });
        ButtonSaveAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateAppointment=editText_DateAppointment.getText().toString();
                TimeAppointment=editText_TimeAppointment.getText().toString();
                if(DateAppointment.isEmpty()||DateAppointment.equals(" "))
                {
                    editText_DateAppointment.setError("Fill here please !");
                    return;
                }
                if(TimeAppointment.isEmpty()||TimeAppointment.equals(" "))
                {
                    editText_TimeAppointment.setError("Fill here please !");
                    return;
                }
                availableAppointment availableAppointment = new availableAppointment(DateAppointment,
                        TimeAppointment,availableAppointmentDoctorId);
                addAvailableAppointment(availableAppointment);

            }
        });
    }
    private void addAvailableAppointment(availableAppointment availableAppointment)
    {
        availableAppointmentsViewModel.addAvailableAppointment(availableAppointment);
        Toast.makeText(getBaseContext(), "availableAppointmentId created successfully", Toast.LENGTH_SHORT).show();
        GoToShowAvailableAppointment();
        displayNotification();
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
                editText_DateAppointment.setText(dateText);
            }
        }, YEAR, MONTH, DATE);

        datePickerDialog.show();
    }

    private void handleTime() {
        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);
        boolean is24HourFormat = DateFormat.is24HourFormat(this);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Calendar calendar1 = Calendar.getInstance();
                calendar1.set(Calendar.HOUR, hour);
                calendar1.set(Calendar.MINUTE, minute);
                String dateText = DateFormat.format("h:mm a", calendar1).toString();
                editText_TimeAppointment.setText(dateText);
            }
        }, HOUR, MINUTE, is24HourFormat);

        timePickerDialog.show();    }

   public void GoToShowAvailableAppointment()
   {
       DateAppointment.isEmpty();
       TimeAppointment.isEmpty();
       editText_DateAppointment.setText(" ");
       editText_TimeAppointment.setText(" ");
       Intent intent=new Intent(getBaseContext(),ShowAvailableAppointmentsActivity.class);
       startActivity(intent);
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
        builder.setSmallIcon(R.drawable.ic_clock1)
                .setContentTitle(getString(R.string.TitleNotification)).setContentText(getString(R.string.TextNotificationAvailableAppointments)).setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.app_name)))
                .setContentIntent(pendingIntent).addAction(R.drawable.ic_clock1, getString(R.string.pendingIntentNotificationAvailableAppointments), pendingIntent);
        NotificationManagerCompat NMC = NotificationManagerCompat.from(getBaseContext());
        NMC.notify(10, builder.build());

    }
}
