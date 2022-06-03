package com.yuliia_koba.clean_digital_mobile.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.actions.EventActions;
import com.yuliia_koba.clean_digital_mobile.models.actions.RateAction;

import java.util.concurrent.Callable;

public class RatingDialogFragment extends DialogFragment {

    private RateAction action;
    private RadioGroup ratingRadioGroup;
    private Button cancel, send;

    private int mark = 5;

    public RatingDialogFragment(RateAction action) {
        this.action = action;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rating_dialog, container, false);

        ratingRadioGroup = view.findViewById(R.id.ratingRadioGroup);
        cancel = view.findViewById(R.id.cancel_rating);
        send = view.findViewById(R.id.send_rating);

        ratingRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                int id = radioGroup.getCheckedRadioButtonId();

                if (R.id.oneMark == id){
                    mark = 1;
                } else if (R.id.twoMark == id){
                    mark = 2;
                } else if (R.id.threeMark == id){
                    mark = 3;
                } else if (R.id.fourMark == id){
                    mark = 4;
                } else if (R.id.fiveMark == id){
                    mark = 5;
                }
            }
        });

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        cancel.setOnClickListener(view -> dismiss());

        send.setOnClickListener(view -> {
            action.rate(mark);
            dismiss();
        });

    }
}
