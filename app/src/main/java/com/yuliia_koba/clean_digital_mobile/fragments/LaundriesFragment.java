package com.yuliia_koba.clean_digital_mobile.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.activities.WashMachinesActivity;
import com.yuliia_koba.clean_digital_mobile.adapters.LaundriesAdapter;
import com.yuliia_koba.clean_digital_mobile.models.dto.Laundry;
import com.yuliia_koba.clean_digital_mobile.models.actions.LaundryActions;
import com.yuliia_koba.clean_digital_mobile.view_models.LaundriesViewModel;


public class LaundriesFragment extends Fragment {
    private LaundriesViewModel viewModel;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton reloadButton;

    private LaundriesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LaundriesViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_laundries, container, false);

        adapter = new LaundriesAdapter(view.getContext(), new Laundry[]{}, new MainLaundryActions());

        recyclerView = view.findViewById(R.id.laundries_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.laundries_progressBar);
        reloadButton = view.findViewById(R.id.laundries_floatingActionButton);


        viewModel.getLaundries().observe(getActivity(), laundries -> adapter.refresh(laundries));

        viewModel.getPage().observe(getActivity(), integer -> viewModel.loadLaundries(integer,  getActivity()));

        viewModel.getErrorMessage().observe(getActivity(), s ->
                Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show());

        viewModel.getStatus().observe(getActivity(), status -> {
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

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        reloadButton.setOnClickListener(view -> viewModel.restart());
    }

    private class MainLaundryActions implements LaundryActions{
        @Override
        public void getWashMachines(String laundryId) {
            Intent intent = new Intent(getActivity(), WashMachinesActivity.class);
            intent.putExtra(Laundry.LAUNDRY_ID, laundryId);
            startActivity(intent);
        }
    }
}