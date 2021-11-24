package com.eng_hussein_khalaf066336.pds.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickChooseListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.availableAppointment;

import java.util.ArrayList;

public class ChooseAvailableAppointment extends  RecyclerView.Adapter <ChooseAvailableAppointment.ChooseAvailableAppointmentHolder> {
    private ArrayList<availableAppointment> availableAppointments ;
    private OnRecyclerItemClickChooseListener listener;
    public  ChooseAvailableAppointment(ArrayList<availableAppointment> availableAppointments, OnRecyclerItemClickChooseListener listener)
    {
        this.availableAppointments = availableAppointments;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ChooseAvailableAppointment.ChooseAvailableAppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_available_appointments,null,false);
        ChooseAvailableAppointment.ChooseAvailableAppointmentHolder chooseAvailableAppointmentHolder =
                new  ChooseAvailableAppointment.ChooseAvailableAppointmentHolder (v);
        return chooseAvailableAppointmentHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  ChooseAvailableAppointment.ChooseAvailableAppointmentHolder holder, int position) {
        availableAppointment availableAppointment = availableAppointments.get(position);

        holder.textView_dateAvailableAppointment.setText(availableAppointment.getAvailableAppointmentDate());
        holder.textView_TimeAvailableAppointment.setText(availableAppointment.getAvailableAppointmentDateTime());
        holder.textView_TimeAvailableAppointment.setTag(availableAppointment.getAvailableAppointmentId());

    }

    @Override
    public int getItemCount() {
        return availableAppointments.size();
    }

    class ChooseAvailableAppointmentHolder extends RecyclerView.ViewHolder
    {
        TextView textView_dateAvailableAppointment;
        TextView textView_TimeAvailableAppointment;

        public ChooseAvailableAppointmentHolder(@NonNull View itemView) {
            super(itemView);
            textView_dateAvailableAppointment = itemView.findViewById(R.id.DateAvailableAppointment);
            textView_TimeAvailableAppointment = itemView.findViewById(R.id.TimeAvailableAppointment);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = (String) textView_TimeAvailableAppointment.getTag();
                    String date = (String) textView_dateAvailableAppointment.getText();
                    String time = (String) textView_TimeAvailableAppointment.getText();
                    listener.onItemClick(id,date,time);
                }
            });
        }
    }
}
