package com.yuliia_koba.clean_digital_mobile.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.Laundry;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.view_models.LaundriesViewModel;


public class LaundriesFragment extends Fragment {
    private LaundriesViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(LaundriesViewModel.class);

        viewModel.getLaundries().observe(this, new Observer<Laundry[]>() {
            @Override
            public void onChanged(Laundry[] laundries) {

            }
        });

        viewModel.getPage().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                viewModel.loadLaundries(integer,getContext() );
            }
        });


        viewModel.getErrorMessage().observe(this, s -> {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        });

        viewModel.getStatus().observe(this, new Observer<Status>() {
            @Override
            public void onChanged(Status status) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_laundries, container, false);
    }
}