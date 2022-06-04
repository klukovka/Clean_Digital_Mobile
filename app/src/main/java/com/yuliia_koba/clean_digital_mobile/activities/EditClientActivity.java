package com.yuliia_koba.clean_digital_mobile.activities;

import static android.widget.Toast.makeText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.models.dto.Client;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;
import com.yuliia_koba.clean_digital_mobile.utils.Utils;
import com.yuliia_koba.clean_digital_mobile.view_models.EditClientViewModel;
import com.yuliia_koba.clean_digital_mobile.view_models.RegisterViewModel;

public class EditClientActivity extends AppCompatActivity {
    private EditClientViewModel viewModel;

    private EditText email, name, phone;
    private Button save;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_client);
        Utils.setLocale(this, PreferencesService.getLocale());
        viewModel = ViewModelProviders.of(this).get(EditClientViewModel.class);

        email = findViewById(R.id.email_update);
        name = findViewById(R.id.name_update);
        phone = findViewById(R.id.phone_update);
        save = findViewById(R.id.update_button);
        progressBar = findViewById(R.id.update_progressBar);
        progressBar.setVisibility(View.GONE);

        viewModel.getStatus().observe(this, status -> {
            progressBar.setVisibility(Status.LOADING == status ? View.VISIBLE : View.GONE);
            save.setVisibility(Status.LOADING == status ? View.GONE : View.VISIBLE);
            if (status == Status.SAVED){
                finish();
            }
        });

        viewModel.getErrorMessage().observe(this, s ->
                makeText(this, s, Toast.LENGTH_SHORT).show());

        viewModel.getClient().observe(this, client -> {
            email.setText(client.user.getEmail());
            name.setText(client.name);
            phone.setText(client.phone);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        save.setOnClickListener(view -> viewModel.save(
                email.getText().toString(),
                name.getText().toString(),
                phone.getText().toString()));
    }
}