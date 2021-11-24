package com.eng_hussein_khalaf066336.pds.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.Doctors;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChooseDoctorAdapter extends  RecyclerView.Adapter <ChooseDoctorAdapter.chooseDoctorHolder> {
    private ArrayList<Doctors> doctors ;
    private OnRecyclerItemClickListener listener;
    private boolean VisibleItem;
    public  ChooseDoctorAdapter(ArrayList<Doctors> doctors, OnRecyclerItemClickListener listener)
    {
        this.doctors = doctors;
        this.listener=listener;
    }

    @NonNull
    @Override
    public chooseDoctorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_choos_doctor,null,false);
        ChooseDoctorAdapter.chooseDoctorHolder chooseDoctorHolder = new  ChooseDoctorAdapter.chooseDoctorHolder(v);
        return chooseDoctorHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull chooseDoctorHolder holder, int position) {
        Doctors doctor = doctors.get(position);
        Picasso.get().load(doctor.getDoctorImage()).into( holder.doctorImage);
        holder.textView_doctorName.setText(doctor.getDoctorFullName());
        holder.textView_doctorSpecialization.setText(doctor.getDoctorSpecialization());
        holder.chooseDoctor_Functional_section.setText(doctor.getDoctorFunctionalSection());
        holder.chooseDoctor_doctorEmail.setText(doctor.getDoctorEmail());
        holder.doctorImage.setTag(doctor.getDoctorId());

    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }

    class chooseDoctorHolder extends RecyclerView.ViewHolder
    {
        TextView textView_doctorName, textView_doctorSpecialization,
        textView_chooseDoctor_Functional_section,chooseDoctor_Functional_section,
                textView_chooseDoctor_doctorEmail,chooseDoctor_doctorEmail;
        ImageView doctorImage;
        Button button_show_info;
        public chooseDoctorHolder(@NonNull View itemView) {
            super(itemView);
            textView_doctorName = itemView.findViewById(R.id.doctorName);
            textView_doctorSpecialization = itemView.findViewById(R.id.doctorSpecialization);
            doctorImage = itemView.findViewById(R.id.doctorImage);

            button_show_info = itemView.findViewById(R.id.cardViewDoctor_button);

            textView_chooseDoctor_Functional_section = itemView.findViewById(R.id.textView_chooseDoctor_Functional_section);
            chooseDoctor_Functional_section = itemView.findViewById(R.id.chooseDoctor_Functional_section);
            textView_chooseDoctor_doctorEmail = itemView.findViewById(R.id.textView_chooseDoctor_doctorEmail);
            chooseDoctor_doctorEmail = itemView.findViewById(R.id.chooseDoctor_doctorEmail);
            inVisibleItem();
            VisibleItem=false;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = (String) doctorImage.getTag();
                    listener.onItemClick(id);
                }
            });
            button_show_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!VisibleItem)
                    {
                        VisibleItem();
                        VisibleItem=true;
                        button_show_info.setText(R.string.chooseDoctor_btn_doctorViewInfo2);
                    }
                    else {
                        inVisibleItem();
                        VisibleItem=false;
                        button_show_info.setText(R.string.chooseDoctor_btn_doctorViewInfo);
                    }
                }
            });
        }
        void VisibleItem()
        {
            textView_chooseDoctor_Functional_section.setVisibility(View.VISIBLE);
            chooseDoctor_Functional_section.setVisibility(View.VISIBLE);
            textView_chooseDoctor_doctorEmail.setVisibility(View.VISIBLE);
            chooseDoctor_doctorEmail.setVisibility(View.VISIBLE);
        }

        void inVisibleItem()
        {
            textView_chooseDoctor_Functional_section.setVisibility(View.GONE);
            chooseDoctor_Functional_section.setVisibility(View.GONE);
            textView_chooseDoctor_doctorEmail.setVisibility(View.GONE);
            chooseDoctor_doctorEmail.setVisibility(View.GONE);
        }

    }
}
