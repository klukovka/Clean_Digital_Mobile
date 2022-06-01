package com.yuliia_koba.clean_digital_mobile.activities;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.view_models.RegisterViewModel;
import com.yuliia_koba.clean_digital_mobile.view_models.RestorePasswordViewModel;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel viewModel;

    private EditText email, password, name, phone;
    private Button register;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        register = findViewById(R.id.register_button);
        progressBar = findViewById(R.id.register_progressBar);
        progressBar.setVisibility(View.GONE);

        viewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);

        viewModel.getStatusMutableLiveData().observe(this, status -> {
            progressBar.setVisibility(Status.LOADING == status ? View.VISIBLE : View.GONE);
            register.setVisibility(Status.LOADING == status ? View.GONE : View.VISIBLE);
            if (status == Status.SUCCESS){
                Toast.makeText(this, R.string.successfully_registered, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewModel.getErrorMessage().observe(this, s ->
                makeText(this, s, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onStart() {
        super.onStart();
        email = findViewById(R.id.email_register);
        password = findViewById(R.id.password_register);
        name = findViewById(R.id.name_register);
        phone = findViewById(R.id.phone_register);

        register.setOnClickListener(view -> viewModel.register(
                email.getText().toString(),
                password.getText().toString(),
                name.getText().toString(),
                phone.getText().toString(),
                view.getContext()));
    }
}