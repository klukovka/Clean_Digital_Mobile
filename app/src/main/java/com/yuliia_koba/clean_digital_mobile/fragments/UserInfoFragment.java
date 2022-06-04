package com.yuliia_koba.clean_digital_mobile.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.models.dto.Client;
import com.yuliia_koba.clean_digital_mobile.view_models.EventsViewModel;
import com.yuliia_koba.clean_digital_mobile.view_models.UserInfoViewModel;

public class UserInfoFragment extends Fragment {

    private UserInfoViewModel viewModel;

    TextView name, email, phone, bonuses, sale;
    ProgressBar progressBar;
    Button editUser, editPassword;
    LinearLayout layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        name = view.findViewById(R.id.client_name);
        email = view.findViewById(R.id.client_email);
        phone = view.findViewById(R.id.client_phone);
        bonuses = view.findViewById(R.id.client_bonuses);
        sale = view.findViewById(R.id.client_sale);
        progressBar = view.findViewById(R.id.info_progressBar);
        editUser = view.findViewById(R.id.edit_info);
        editPassword = view.findViewById(R.id.edit_password);
        layout = view.findViewById(R.id.user_info_layout);


        viewModel.getStatus().observe(getActivity(), status -> {
            progressBar.setVisibility(Status.LOADING == status ? View.VISIBLE : View.GONE);
            layout.setVisibility(Status.LOADING == status ? View.GONE : View.VISIBLE);
        });

        viewModel.getErrorMessage().observe(getActivity(), s ->
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show());

        viewModel.getClient().observe(getActivity(), client -> {
            name.setText(client.name);
            email.setText(client.user.getEmail());
            phone.setText(client.phone);
            sale.setText(String.valueOf(client.sale));
            bonuses.setText(String.valueOf(client.bonuses));
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}