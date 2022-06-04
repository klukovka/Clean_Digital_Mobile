package com.yuliia_koba.clean_digital_mobile.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.activities.LoginActivity;
import com.yuliia_koba.clean_digital_mobile.activities.RestorePasswordActivity;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;

public class SettingsFragment extends Fragment {


    private RadioGroup ratingRadioGroup;
    private Button delete, logout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        ratingRadioGroup = view.findViewById(R.id.languageRadioGroup);
        delete = view.findViewById(R.id.delete_account_button);
        logout = view.findViewById(R.id.exit_button);

        ratingRadioGroup.check(PreferencesService.getLocale()=="uk"? R.id.ukrainian: R.id.english);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ratingRadioGroup.setOnCheckedChangeListener((radioGroup, i) -> {

            int id = radioGroup.getCheckedRadioButtonId();

            if (R.id.ukrainian == id){
                PreferencesService.setLocale("uk");
            } else if (R.id.english == id){
                PreferencesService.setLocale("en");
            }
            getActivity().recreate();
        });

        logout.setOnClickListener(view -> {
            PreferencesService.clear();
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        
        delete.setOnClickListener(view -> {
            PreferencesService.clear();
            Intent intent = new Intent(view.getContext(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
    }
}