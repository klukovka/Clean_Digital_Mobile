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
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;
import com.yuliia_koba.clean_digital_mobile.utils.Utils;
import com.yuliia_koba.clean_digital_mobile.view_models.RegisterViewModel;
import com.yuliia_koba.clean_digital_mobile.view_models.UpdatePasswordViewModel;

public class UpdatePasswordActivity extends AppCompatActivity {
    private UpdatePasswordViewModel viewModel;

    private EditText password, newPassword;
    private Button save;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLocale(this, PreferencesService.getLocale());
        setContentView(R.layout.activity_update_password);


        save = findViewById(R.id.update_password_button);
        progressBar = findViewById(R.id.update_password_progressBar);
        progressBar.setVisibility(View.GONE);

        viewModel = ViewModelProviders.of(this).get(UpdatePasswordViewModel.class);

        viewModel.getStatus().observe(this, status -> {
            progressBar.setVisibility(Status.LOADING == status ? View.VISIBLE : View.GONE);
            save.setVisibility(Status.LOADING == status ? View.GONE : View.VISIBLE);
            if (status == Status.SUCCESS){
                finish();
            }
        });

        viewModel.getErrorMessage().observe(this, s ->
                makeText(this, s, Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onStart() {
        super.onStart();

        password = findViewById(R.id.password_update);
        newPassword = findViewById(R.id.new_password_update);

        save.setOnClickListener(view -> viewModel.save(password.getText().toString(), newPassword.getText().toString()));
    }
}