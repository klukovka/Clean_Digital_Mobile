package com.yuliia_koba.clean_digital_mobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;
import com.yuliia_koba.clean_digital_mobile.utils.Utils;
import com.yuliia_koba.clean_digital_mobile.view_models.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;

    private EditText email, password;
    private Button login, forget, register;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLocale(this, PreferencesService.getLocale());
        setContentView(R.layout.activity_login);

        login = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.login_progressBar);
        progressBar.setVisibility(View.GONE);

        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        viewModel.getStatusMutableLiveData().observe(this, status -> {
            progressBar.setVisibility(Status.LOADING == status ? View.VISIBLE : View.GONE);
            login.setVisibility(Status.LOADING == status ? View.GONE : View.VISIBLE);
            if (status == Status.SUCCESS){
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        viewModel.getErrorMessage().observe(this, s ->
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onStart() {
        super.onStart();

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        forget = findViewById(R.id.forget_password_button);
        register = findViewById(R.id.register_button);

        login.setOnClickListener(view -> {
            viewModel.login(email.getText().toString(),
                    password.getText().toString(), this);
        });

        forget.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RestorePasswordActivity.class);
            startActivity(intent);
        });

        register.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), RegisterActivity.class);
            startActivity(intent);
        });
    }
}