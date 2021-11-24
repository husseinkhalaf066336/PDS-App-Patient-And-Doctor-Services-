package com.eng_hussein_khalaf066336.pds.ui.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eng_hussein_khalaf066336.pds.ui.interfaces.OnRecyclerItemClickChooseListener;
import com.eng_hussein_khalaf066336.pds.R;
import com.eng_hussein_khalaf066336.pds.model.MedicalReport;

import java.util.ArrayList;

public class ShowUserReportsAdapter  extends  RecyclerView.Adapter <ShowUserReportsAdapter.ShowUserReportsHolder> {
    private ArrayList<MedicalReport> MedicalReports ;
    private OnRecyclerItemClickChooseListener listener;
    public  ShowUserReportsAdapter(ArrayList<MedicalReport> MedicalReport , OnRecyclerItemClickChooseListener listener)
    {
        this.MedicalReports = MedicalReport;
        this.listener=listener;
    }

    @NonNull
    @Override
    public ShowUserReportsAdapter.ShowUserReportsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_show_report,null,false);
        ShowUserReportsAdapter.ShowUserReportsHolder showUserReportsHolder = new ShowUserReportsAdapter.ShowUserReportsHolder(v);
        return showUserReportsHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull   ShowUserReportsAdapter.ShowUserReportsHolder holder, int position) {
        MedicalReport report = MedicalReports.get(position);
        holder.textView_MedicalReport_ReportTitle.setText(report.getReportTitle());
        holder.textView_MedicalReport_ReportDetails.setText(report.getReportDetails());
        holder.textView_MedicalReport_DoctorsInstructions.setText(report.getDoctorsInstructions());
        holder.textView_MedicalReport_SuggestedMedication.setText(report.getSuggestedMedication());
        holder.textView_MedicalReport_ReportDetails.setText(report.getReportDetails());

        holder.textView_MedicalReport_ReportTitle.setTag(report.getID_Report());
        holder.textView_MedicalReport_ReportDetails.setTag(report.getID_Current_Patient());
        holder.textView_MedicalReport_DoctorsInstructions.setTag(report.getID_Current_Patient());


    }

    @Override
    public int getItemCount() {
        return MedicalReports.size();
    }

    class ShowUserReportsHolder extends RecyclerView.ViewHolder
    {
        TextView textView_MedicalReport_ReportTitle;
        TextView textView_MedicalReport_ReportDetails;
        TextView textView_MedicalReport_DoctorsInstructions;
        TextView textView_MedicalReport_SuggestedMedication;
        public ShowUserReportsHolder(@NonNull View itemView) {
            super(itemView);
            textView_MedicalReport_ReportTitle = itemView.findViewById(R.id.MedicalReport_ReportTitle);
            textView_MedicalReport_ReportDetails = itemView.findViewById(R.id.MedicalReport_ReportDetails);
            textView_MedicalReport_DoctorsInstructions = itemView.findViewById(R.id.MedicalReportDoctorsInstructions);
            textView_MedicalReport_SuggestedMedication = itemView.findViewById(R.id.MedicalReport_SuggestedMedication);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id_report = (String) textView_MedicalReport_ReportTitle.getTag();
                    String id_patient = (String) textView_MedicalReport_ReportDetails.getTag();
                    String id_doctor = (String) textView_MedicalReport_DoctorsInstructions.getTag();
                    listener.onItemClick(id_report,id_patient,id_doctor);
                }
            });
        }
    }
}

