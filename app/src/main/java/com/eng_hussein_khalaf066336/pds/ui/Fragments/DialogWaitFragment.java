package com.eng_hussein_khalaf066336.pds.ui.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eng_hussein_khalaf066336.pds.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DialogWaitFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DialogWaitFragment extends  androidx.fragment.app.DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Title = "Title";
    private static final String ARG_Message = "Message";

    // TODO: Rename and change types of parameters
    private String Title;
    private String Message;

    public DialogWaitFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DialogWaitFragment newInstance(String title, String message) {
        DialogWaitFragment fragment = new DialogWaitFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_Title, title);
        bundle.putString(ARG_Message, message);
        fragment.setArguments( bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Title = getArguments().getString(ARG_Title);
            Message = getArguments().getString(ARG_Message);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View v =  inflater.inflate(R.layout.fragment_dialog_wait, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView tvTitle = view.findViewById(R.id.DialogWait_textView_Title_wait);
        TextView tvMessage = view.findViewById(R.id.dialogWait_textView_Message_wait);
        ProgressBar pbWait = view.findViewById(R.id.DialogWait_progressBar_wait);
        tvTitle.setText(Title);
        tvMessage.setText(Message);
    }
}
