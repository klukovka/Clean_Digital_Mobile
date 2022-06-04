package com.yuliia_koba.clean_digital_mobile.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.adapters.WashMachinesAdapter;
import com.yuliia_koba.clean_digital_mobile.models.dto.Laundry;
import com.yuliia_koba.clean_digital_mobile.models.dto.WashMachine;
import com.yuliia_koba.clean_digital_mobile.services.PreferencesService;
import com.yuliia_koba.clean_digital_mobile.utils.Utils;
import com.yuliia_koba.clean_digital_mobile.view_models.WashMachinesViewModel;

public class WashMachinesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    WashMachinesAdapter adapter;
    ProgressBar progressBar;
    FloatingActionButton reloadButton;

    WashMachinesViewModel viewModel;
    LinearLayoutManager layoutManager = new LinearLayoutManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setLocale(this, PreferencesService.getLocale());
        setContentView(R.layout.activity_wash_machines);
        viewModel = new ViewModelProvider(this).get(WashMachinesViewModel.class);

        initIntent();

        progressBar = findViewById(R.id.wash_machines_progressBar);
        reloadButton = findViewById(R.id.wash_machines_floatingActionButton);
        recyclerView = findViewById(R.id.wash_machines_RecyclerView);


        viewModel.getWashMachines().observe(this, washMachines -> adapter.refresh(washMachines));

        viewModel.getPage().observe(this, integer -> viewModel.loadWashMachines(integer, this));

        viewModel.getErrorMessage().observe(this, s ->
                Toast.makeText(this, s, Toast.LENGTH_SHORT).show());

        viewModel.getStatus().observe(this, status -> {
            switch (status){
                case ERROR:
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.GONE);
                    reloadButton.setVisibility(View.VISIBLE);
                    break;
                case LOADING:
                    progressBar.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    reloadButton.setVisibility(View.GONE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    reloadButton.setVisibility(View.GONE);
                    break;
            }
        });

    }

    void initIntent(){
        Bundle extras = getIntent().getExtras();

        if (extras!=null){
            viewModel.setLaundryId(extras.getString(Laundry.LAUNDRY_ID, ""));
        }
    }

    protected void onStart() {
        super.onStart();
        adapter = new WashMachinesAdapter(WashMachinesActivity.this, new WashMachine[]{});
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        reloadButton.setOnClickListener(view -> viewModel.restart());

    }

}