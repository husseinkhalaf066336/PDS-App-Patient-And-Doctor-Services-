package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
    TextInputEditText editText_DateAppointment,editText_TimeAppointment;
    Button ButtonDateAppointment,ButtonTimeAppointment,ButtonSaveAppointment;
    String DateAppointment,TimeAppointment,
            availableAppointmentDoctorId;
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
        emptyFields();
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

   public void emptyFields()
   {
       DateAppointment.isEmpty();
       TimeAppointment.isEmpty();
       editText_DateAppointment.setText(" ");
       editText_TimeAppointment.setText(" ");
   }
}
