package com.yuliia_koba.clean_digital_mobile.activities;

import static android.widget.Toast.makeText;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;
import com.yuliia_koba.clean_digital_mobile.utils.Utils;
import com.yuliia_koba.clean_digital_mobile.view_models.RestorePasswordViewModel;

import java.util.Objects;

public class RestorePasswordActivity extends AppCompatActivity {
    private RestorePasswordViewModel viewModel;

    private EditText email;
    private Button restore;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLocale(this, PreferencesService.getLocale());
        setContentView(R.layout.activity_restore_password);


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        restore = findViewById(R.id.restore_password_button);
        progressBar = findViewById(R.id.restore_progressBar);
        progressBar.setVisibility(View.GONE);

        viewModel = ViewModelProviders.of(this).get(RestorePasswordViewModel.class);

        viewModel.getStatusMutableLiveData().observe(this, status -> {
            progressBar.setVisibility(Status.LOADING == status ? View.VISIBLE : View.GONE);
            restore.setVisibility(Status.LOADING == status ? View.GONE : View.VISIBLE);
            if (status == Status.SUCCESS){
                Toast.makeText(this, R.string.check_your_email, Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        viewModel.getErrorMessage().observe(this, s ->
                makeText(this, s, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onStart() {
        super.onStart();
        email = findViewById(R.id.email_restore);

        restore.setOnClickListener(view -> viewModel.restore(email.getText().toString(), view.getContext()));
    }
}