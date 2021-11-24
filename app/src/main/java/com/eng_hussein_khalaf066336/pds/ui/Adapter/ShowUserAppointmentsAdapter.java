package com.eng_hussein_khalaf066336.pds.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.appointments;

import java.util.ArrayList;

public class ShowUserAppointmentsAdapter extends  RecyclerView.Adapter <ShowUserAppointmentsAdapter.ShowUserAppointmentsHolder> {
    private ArrayList<appointments> appointments ;
    private OnRecyclerItemClickListener listener;
    private boolean VisibleItem;
    public  ShowUserAppointmentsAdapter( ArrayList<appointments> appointments , OnRecyclerItemClickListener listener)
    {
        this.appointments = appointments;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ShowUserAppointmentsAdapter.ShowUserAppointmentsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_show_user_appointments,null,false);
        ShowUserAppointmentsAdapter.ShowUserAppointmentsHolder showUserAppointmentsHolder = new ShowUserAppointmentsAdapter.ShowUserAppointmentsHolder(v);
        return showUserAppointmentsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull   ShowUserAppointmentsAdapter.ShowUserAppointmentsHolder holder, int position) {
        appointments appointment = appointments.get(position);
        holder.textView_appointment_reason.setText(appointment.getAppointment_reason());
        holder.textView_appointment_dat.setText(appointment.getAppointment_dat());
        holder.textView_appointment_Time.setText(appointment.getAppointment_Time());
        holder.textView_appointment_description.setText(appointment.getAppointment_description());
        holder.textView_appointment_reason.setTag(appointment.getAppointment_Id());
        holder.textView_appointment_description.setTag(appointment.getAppointment_current_doctorId());

    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    class ShowUserAppointmentsHolder extends RecyclerView.ViewHolder
    {
        TextView textView_appointment_reason;
        TextView textView_appointment_dat;
        TextView textView_appointment_Time;
        TextView textView_appointment_description;
        Button button_show_info;

        public ShowUserAppointmentsHolder(@NonNull View itemView) {
            super(itemView);
            textView_appointment_reason = itemView.findViewById(R.id.appointment_reason);
            textView_appointment_dat = itemView.findViewById(R.id.appointment_dat);
            textView_appointment_Time = itemView.findViewById(R.id.appointment_Time);
            textView_appointment_description = itemView.findViewById(R.id.appointment_description);

            button_show_info = itemView.findViewById(R.id.cardViewAppointment_button);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = (String) textView_appointment_reason.getTag();
                    listener.onItemClick(id);
                }
            });
            button_show_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = (String) textView_appointment_reason.getTag();
                    listener.onItemClick(id);
                }
            });

        }
    }
}

