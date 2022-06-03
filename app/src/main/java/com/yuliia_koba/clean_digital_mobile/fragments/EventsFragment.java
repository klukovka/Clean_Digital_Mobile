package com.yuliia_koba.clean_digital_mobile.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.adapters.EventsAdapter;
import com.yuliia_koba.clean_digital_mobile.models.actions.EventActions;
import com.yuliia_koba.clean_digital_mobile.models.actions.RateAction;
import com.yuliia_koba.clean_digital_mobile.models.dto.Event;
import com.yuliia_koba.clean_digital_mobile.view_models.EventsViewModel;

public class EventsFragment extends Fragment {
    private EventsViewModel viewModel;

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton reloadButton;

    private EventsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(EventsViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        adapter = new EventsAdapter(view.getContext(), new EventsFragment.MainEventActions(), new Event[]{});

        recyclerView = view.findViewById(R.id.events_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        progressBar = view.findViewById(R.id.events_progressBar);
        reloadButton = view.findViewById(R.id.events_floatingActionButton);

        viewModel.getEvents().observe(getActivity(), events -> adapter.refresh(events));
        viewModel.getPage().observe(getActivity(), integer -> viewModel.loadEvents(integer,  getActivity()));

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

    class MainEventActions implements EventActions{

        @Override
        public void takeEvent(String eventId) {
            viewModel.takeEvent(eventId, getActivity());
        }

        @Override
        public void rateEvent(String eventId) {
            new RatingDialogFragment(new RateAction() {
                @Override
                public void rate(int mark) {
                    viewModel.rateEvent(eventId, mark, getActivity());
                }
            }).show(getParentFragmentManager(), "RATING");
        }
    }
}