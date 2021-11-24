package com.eng_hussein_khalaf066336.pds.ui.Activities;

import androidx.annotation.Nullable;
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
import com.eng_hussein_khalaf066336.pds.model.appointments;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AppointmentsViewModel;
import com.eng_hussein_khalaf066336.pds.ui.viewModel.AvailableAppointmentsViewModel;
import com.google.android.material.textfield.TextInputEditText;

public class BookAppointmentActivity extends AppCompatActivity {
    TextInputEditText editText_reasonAppointment,editText_DateAppointment,
            editText_TimeAppointment,editText_descriptionAppointme;
    Button Button_ChooseAvailableAppointment,button_appointment_save;
    String AvailableAppointment_id;

    String  ID_Appointment,Appointment_date,Appointment_Time,
            reason_appointment,description_appointment,ID_Current_user,ID_Current_doctor ;
    public static int DAT_REQ_CODE=1;
    public static String ID_NAME_DOCTOR_CODE="ID_DOCTOR_NAME";
    private AppointmentsViewModel appointmentsViewModel;
    private AvailableAppointmentsViewModel availableAppointmentsViewModel;
    public static   String channelID="X_channelID";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        doInitialization();
    }
    private void doInitialization()
    {
        appointmentsViewModel =new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AppointmentsViewModel.class);
        availableAppointmentsViewModel =new ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory
                .getInstance(this.getApplication())).get(AvailableAppointmentsViewModel.class);

        editText_reasonAppointment = findViewById(R.id.BookAppointment_EitText_reasonAppointment);
        editText_DateAppointment = findViewById(R.id.BookAppointment_EitText_DateAppointment);
        editText_TimeAppointment = findViewById(R.id.BookAppointment_EitText_TimeAppointment);
        editText_descriptionAppointme = findViewById(R.id.BookAppointment_EitText_descriptionAppointment);
        Button_ChooseAvailableAppointment = findViewById(R.id.BookAppointment_btn_ChooseAvailableAppointment);
        button_appointment_save = findViewById(R.id.button_appointment_save);

        ID_Current_user= CurrentUser.getCurrentUserId();
        ID_Current_doctor=getIntent().getStringExtra(ChooseDoctorActivity.NAME_ID_choose_doctor_CODE);

        Button_ChooseAvailableAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ChooseAvailableAppointmentsActivity.class);
                intent.putExtra(ID_NAME_DOCTOR_CODE,ID_Current_doctor);
                startActivityForResult(intent,DAT_REQ_CODE);
            }
        });
        button_appointment_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reason_appointment=editText_reasonAppointment.getText().toString();
                Appointment_date=editText_DateAppointment.getText().toString();
                Appointment_Time=editText_TimeAppointment.getText().toString();
                description_appointment=editText_descriptionAppointme.getText().toString();

                if(reason_appointment.isEmpty()||reason_appointment.equals(" "))
                {
                    editText_reasonAppointment.setError("Fill here please !");
                    return;
                }
                if(Appointment_date.isEmpty()||Appointment_date.equals(" "))
                {
                    editText_DateAppointment.setError("Fill here please !");
                    return;
                }
                if(Appointment_Time.isEmpty()||Appointment_Time.equals(" "))
                {
                    editText_TimeAppointment.setError("Fill here please !");
                    return;
                }
                if(description_appointment.isEmpty()||description_appointment.equals(" "))
                {
                    editText_descriptionAppointme.setError("Fill here please !");
                    return;
                }
                appointments appointment = new appointments(reason_appointment,Appointment_date,
                        Appointment_Time,description_appointment,ID_Current_user,ID_Current_doctor);
                appointment.setAppointment_Id(ID_Appointment);
                addAppointment(appointment);
                deleteAvailableAppointments(AvailableAppointment_id);
                displayNotification ();
                GoToShowAppointment();

            }
        });
    }
    private void addAppointment(appointments appointment){
        appointmentsViewModel.addAppointment(appointment);
        Toast.makeText(getBaseContext(), "availableAppointmentId created successfully", Toast.LENGTH_SHORT).show();
    }
    private void deleteAvailableAppointments(String AvailableAppointment_id)
    {
        availableAppointmentsViewModel.deleteAvailableAppointments(AvailableAppointment_id);
        Toast.makeText(getBaseContext(), "removeValue successfully", Toast.LENGTH_SHORT).show();

    }
        @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DAT_REQ_CODE || resultCode == ChooseAvailableAppointmentsActivity.DAT_RESULT_CODE )
        {
            if (requestCode==0||data==null ){
                AvailableAppointment_id="";
                Appointment_date= "";
                Appointment_Time="";
                editText_DateAppointment.setText(Appointment_date);
                editText_TimeAppointment.setText(Appointment_Time);
                return;
            }

          AvailableAppointment_id= data.getStringExtra(ChooseAvailableAppointmentsActivity.NAME_ID_AVAILABLE_APPOINTMENT_CODE);
          Appointment_date= data.getStringExtra(ChooseAvailableAppointmentsActivity.NAME_DATE_CODE);
          Appointment_Time= data.getStringExtra(ChooseAvailableAppointmentsActivity.NAME_TIME_CODE);
          Toast.makeText(getBaseContext(), " :" +   AvailableAppointment_id, Toast.LENGTH_LONG).show();
          editText_DateAppointment.setText(Appointment_date);
          editText_TimeAppointment.setText(Appointment_Time);
        }

    }
    public void GoToShowAppointment()
    {
        reason_appointment.isEmpty();
        Appointment_date.isEmpty();
        Appointment_Time.isEmpty();
        description_appointment.isEmpty();
        editText_reasonAppointment.setText(" ");
        editText_DateAppointment.setText(" ");
        editText_TimeAppointment.setText(" ");
        editText_descriptionAppointme.setText(" ");
        Intent intent_show_appointment = new Intent(getBaseContext(), ShowAppointmentsActivity.class);
        startActivity(intent_show_appointment);
        finish();
    }
    private void displayNotification ()
    {
        Intent intent = new Intent(getBaseContext(), ShowAppointmentsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),0,intent,0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(channelID,"channel_display_Appointment",
                    NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Description");
            NotificationManager NM =getSystemService(NotificationManager.class);
            NM.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder =new NotificationCompat.Builder(getBaseContext(),channelID);
        builder.setSmallIcon(R.drawable.ic_icon_calendar)
                .setContentTitle("تطبيق خدمات المرضى والأطباء").setContentText("تم خجز موعد لك عند الطبيب أنقر ليتم عرضه ").setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("PDS"))
                .setContentIntent(pendingIntent).addAction(R.drawable.ic_icon_calendar,"فتح المواعيد",pendingIntent);
        NotificationManagerCompat NMC =  NotificationManagerCompat.from(getBaseContext());
        NMC.notify(10,builder.build());

    }
}
