package com.eng_hussein_khalaf066336.pds.ui.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class DialogFragment extends androidx.fragment.app.DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_Title = "Title";
    private static final String ARG_message = "message";
    private static final String ARG_icon = "icon";

    // TODO: Rename and change types of parameters
    private String Title;
    private String Message;
    private int Icon;


    private onPositiveClickListener positiveClickListener;
    private onNegativeClickListener negativeClickListener;

    public DialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onPositiveClickListener)
        {
            positiveClickListener= (onPositiveClickListener) context;
        }
        else
        {
            throw new RuntimeException("Please implement listener : positive");
        }

        if (context instanceof onNegativeClickListener)
        {
            negativeClickListener = (onNegativeClickListener) context;
        }
        else
        {
            throw new RuntimeException("Please implement listener : negative");
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        positiveClickListener=null;
        negativeClickListener=null;

    }

    // TODO: Rename and change types and number of parameters
    public static DialogFragment newInstance(String title, String message,int icon) {
        DialogFragment fragment = new DialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_Title, title);
        bundle.putString(ARG_message, message);
        bundle.putInt(ARG_icon, icon);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Title = getArguments().getString(ARG_Title);
            Message = getArguments().getString(ARG_message);
            Icon = getArguments().getInt(ARG_icon);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setIcon(Icon);

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            positiveClickListener.onPositiveButtonClicked();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            negativeClickListener.onNegativeButtonClicked();
            }
        });

        return builder.create();

    }
    public  interface onPositiveClickListener{
        void onPositiveButtonClicked();
    }
    public  interface onNegativeClickListener{
        void onNegativeButtonClicked();
    }

}
