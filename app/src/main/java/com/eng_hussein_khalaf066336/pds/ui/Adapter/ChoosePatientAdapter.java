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
import com.eng_hussein_khalaf066336.pds.model.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChoosePatientAdapter extends RecyclerView.Adapter<ChoosePatientAdapter.ChoosePatientHolder> {

    private ArrayList<Users> PatientsList;
    OnRecyclerItemClickListener listener;
    private boolean VisibleItem;

    public  ChoosePatientAdapter(ArrayList<Users> PatientsList, OnRecyclerItemClickListener listener)
    {
        this.PatientsList = PatientsList;
        this.listener=listener;
    }


    @NonNull
    @Override
    public ChoosePatientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_choose_patient,null,false);
        ChoosePatientAdapter.ChoosePatientHolder choosePatientHolder= new ChoosePatientHolder(v);
        return choosePatientHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChoosePatientHolder holder, int position) {
        Users user = PatientsList.get(position);
        Picasso.get().load(user.getUserImage()).into( holder.PatientImage);
        holder.textView_PatientName.setText(user.getUserFullName());
        holder.textView_patientAge.setText(user.getUserDateOfBirth());
        holder.text_Patient_email.setText(user.getUserEmail());
        holder.text_Patient_Address.setText(user.getUserAddress());
        holder.PatientImage.setTag(user.getUserId());
    }

    @Override
    public int getItemCount() {
        return PatientsList.size();
    }
    public void filterList(ArrayList<Users> patientsFilterList)
    {
        PatientsList=patientsFilterList;
        notifyDataSetChanged();
    }
    class ChoosePatientHolder extends RecyclerView.ViewHolder
    {
        TextView textView_PatientName, textView_patientAge,text_Patient_email,text_Patient_Address,
                textView_choosePatient_email,textView_choosePatient_Address;
        ImageView PatientImage;
        Button button_show_info_Patient ;
        public ChoosePatientHolder(View itemView)
        {
            super(itemView);
            textView_PatientName = itemView.findViewById(R.id.PatientName);
            textView_patientAge = itemView.findViewById(R.id.PatientPron);

            text_Patient_email = itemView.findViewById(R.id.text_choosePatient_email);
            textView_choosePatient_email = itemView.findViewById(R.id.textView_choosePatient_email);

            text_Patient_Address = itemView.findViewById(R.id.text_choosePatient_Address);
            textView_choosePatient_Address = itemView.findViewById(R.id.textView_choosePatient_Address);
            button_show_info_Patient = itemView.findViewById(R.id.cardViewPatient_button);
            PatientImage = itemView.findViewById(R.id.PatientImage);

            inVisibleItem();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = (String) PatientImage.getTag();
                    listener.onItemClick(id);
                }
            });
            button_show_info_Patient.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!VisibleItem)
                    {
                        VisibleItem();
                        VisibleItem=true;
                        button_show_info_Patient.setText(R.string.choosePatient_btn_PatientViewInfo2);
                    }
                    else {
                        inVisibleItem();
                        VisibleItem=false;
                        button_show_info_Patient.setText(R.string.choosePatient_btn_PatientViewInfo);
                    }
                }
            });
        }
        void VisibleItem()
        {
            text_Patient_Address.setVisibility(View.VISIBLE);
            text_Patient_email.setVisibility(View.VISIBLE);

            textView_choosePatient_email.setVisibility(View.VISIBLE);
            textView_choosePatient_Address.setVisibility(View.VISIBLE);
        }

        void inVisibleItem()
        {
            text_Patient_email.setVisibility(View.GONE);
            text_Patient_Address.setVisibility(View.GONE);

            textView_choosePatient_email.setVisibility(View.GONE);
            textView_choosePatient_Address.setVisibility(View.GONE);
        }

    }
}
