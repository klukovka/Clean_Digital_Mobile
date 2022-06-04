package com.yuliia_koba.clean_digital_mobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.Status;
import com.yuliia_koba.clean_digital_mobile.models.dto.Event;
import com.yuliia_koba.clean_digital_mobile.models.dto.Laundry;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;
import com.yuliia_koba.clean_digital_mobile.utils.Utils;
import com.yuliia_koba.clean_digital_mobile.view_models.CreateEventViewModel;
import com.yuliia_koba.clean_digital_mobile.view_models.WashMachinesViewModel;

public class CreateEventActivity extends AppCompatActivity {
    CreateEventViewModel viewModel;

    TextView id, costs, time, leftBonuses;
    Button payButton;
    ProgressBar progressBar;
    EditText bonuses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLocale(this, PreferencesService.getLocale());
        setContentView(R.layout.activity_create_event);
        viewModel = new ViewModelProvider(this).get(CreateEventViewModel.class);

        initIntent();

        costs = findViewById(R.id.new_event_sum);
        time = findViewById(R.id.new_event_time);
        leftBonuses = findViewById(R.id.left_bonuses);
        payButton = findViewById(R.id.new_event_pay);
        progressBar =findViewById(R.id.create_event_progressBar);


        viewModel.getCosts().observe(this, integer -> costs.setText(String.valueOf(integer)));

        viewModel.getTime().observe(this, integer -> time.setText(String.valueOf(integer)));

        viewModel.getLeftBonuses().observe(this, integer -> {
            leftBonuses.setText(String.valueOf(integer));
            payButton.setVisibility(integer < 0 ? View.GONE : View.VISIBLE);
        });


        viewModel.getErrorMessage().observe(this, s ->
                Toast.makeText(CreateEventActivity.this, s, Toast.LENGTH_SHORT).show());

        viewModel.getStatus().observe(this, status -> {
            progressBar.setVisibility(Status.LOADING == status ? View.VISIBLE : View.GONE);
            payButton.setVisibility(Status.LOADING == status ? View.GONE : View.VISIBLE);

            if (status == Status.SAVED){
                finish();
            }
        });

    }

    void initIntent(){
        Bundle extras = getIntent().getExtras();

        if (extras!=null){
            viewModel.init(extras.getString(Event.EVENT_ID, ""), this);
            id = findViewById(R.id.new_event_id);
            id.setText(extras.getString(Event.EVENT_ID, ""));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bonuses = findViewById(R.id.new_event_bonuses);

        bonuses.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (bonuses.getText().toString().isEmpty()){
                    viewModel.setBonuses(0);
                } else {
                    int bonus = Integer.parseInt(bonuses.getText().toString());
                    viewModel.setBonuses(bonus);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        payButton.setOnClickListener(view -> viewModel.save(CreateEventActivity.this));
    }

}