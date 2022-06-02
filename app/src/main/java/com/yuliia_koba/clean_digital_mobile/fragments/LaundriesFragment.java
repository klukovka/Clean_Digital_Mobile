package com.yuliia_koba.clean_digital_mobile.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
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
import com.yuliia_koba.clean_digital_mobile.adapters.LaundriesAdapter;
import com.yuliia_koba.clean_digital_mobile.models.Laundry;
import com.yuliia_koba.clean_digital_mobile.models.User;
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_laundries, container, false);

        adapter = new LaundriesAdapter(view.getContext(), new Laundry[]{
                new Laundry("name", "address", "phone", "id", 4,
                        new User("email", "id"), "id"),
                new Laundry("name", "address", "phone", "id", 4,
                        new User("email", "id"), "id"),
                new Laundry("name", "address", "phone", "id", 4,
                        new User("email", "id"), "id"),
                new Laundry("name", "address", "phone", "id", 4,
                        new User("email", "id"), "id"),
                new Laundry("name", "address", "phone", "id", 4,
                        new User("email", "id"), "id"),
                new Laundry("name", "address", "phone", "id", 4,
                        new User("email", "id"), "id"),
        });

        recyclerView = view.findViewById(R.id.laundries_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

}