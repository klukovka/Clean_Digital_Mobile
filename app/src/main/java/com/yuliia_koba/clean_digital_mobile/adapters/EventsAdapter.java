package com.yuliia_koba.clean_digital_mobile.adapters;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.actions.EventActions;
import com.yuliia_koba.clean_digital_mobile.models.dto.Event;
import com.yuliia_koba.clean_digital_mobile.utils.Utils;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final EventActions eventActions;
    private Event[]events;

    public EventsAdapter(Context context, EventActions eventActions, Event[] events) {
        this.inflater = LayoutInflater.from(context);
        this.eventActions = eventActions;
        this.events = events;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.event_tile, parent, false);

        return new EventsAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = events[position];

        holder.id.setText(event.eventId);
        holder.washingMachine.setText(event.washMachineId);
        holder.temperature.setText(String.valueOf(event.temperature));
        holder.speed.setText(String.valueOf(event.spinning));
        holder.mode.setText(event.mode.name);

        int maxProgress = event.mode.time;

        if (event.additionalMode!=null){
            holder.additionalMode.setText(event.additionalMode.name);
            maxProgress += event.additionalMode.time;
        } else {
            holder.additionalMode.setVisibility(View.GONE);
            holder.additionalModeLabel.setVisibility(View.GONE);
        }


        SimpleDateFormat dateFor = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        holder.timeBegin.setText(dateFor.format(event.timeBegin));

        if (event.timeEnd!=null){
            holder.timeEnd.setText(dateFor.format(event.timeEnd));
        } else {
            holder.timeEnd.setVisibility(View.GONE);
            holder.timeEndLabel.setVisibility(View.GONE);
        }

        holder.paidStatus.setText(event.paidStatus?R.string.paid:R.string.not_paid);


        holder.paidBonuses.setText(String.valueOf(event.paidBonuses));
        holder.paidMoney.setText(String.valueOf(event.paidMoney));

        if (event.rating!=null){
            holder.rating.setText(String.valueOf(event.rating));
        } else {
            holder.rating.setVisibility(View.GONE);
            holder.ratingLabel.setVisibility(View.GONE);
        }

        if (event.taken || event.timeEnd==null){
            holder.takeButton.setVisibility(View.GONE);
        } else {
            holder.takeButton.setVisibility(View.VISIBLE);
        }

        if (event.rating!=null || !event.taken){
            holder.rateButton.setVisibility(View.GONE);
        } else {
            holder.rateButton.setVisibility(View.VISIBLE);
        }

        holder.progressBar.setMax(100);
        int current = Utils.getMinutesBetweenNow(event.timeBegin)*100/maxProgress;

        holder.progressBar.setProgress(current);
    }

    @Override
    public int getItemCount() {
        return events.length;
    }

    public void refresh(Event[] events){
        this.events = events;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, washingMachine, temperature, speed, mode,
        additionalMode, additionalModeLabel, timeBegin, timeEnd, timeEndLabel,
        paidStatus, paidBonuses, paidMoney, rating, ratingLabel;

        Button takeButton, rateButton;

        ProgressBar progressBar;

        ViewHolder(View view){
            super(view);

            id = view.findViewById(R.id.event_id);
            washingMachine = view.findViewById(R.id.event_washMachine);
            temperature = view.findViewById(R.id.event_temperature);
            speed = view.findViewById(R.id.event_spinning);
            mode = view.findViewById(R.id.event_mode);
            additionalMode = view.findViewById(R.id.event_additionalMode);
            additionalModeLabel = view.findViewById(R.id.event_additionalMode_label);
            timeBegin = view.findViewById(R.id.event_timeBegin);
            timeEnd = view.findViewById(R.id.event_timeEnd);
            timeEndLabel = view.findViewById(R.id.event_timeEnd_label);
            paidStatus = view.findViewById(R.id.event_paidStatus);
            paidBonuses = view.findViewById(R.id.event_paidBonuses);
            paidMoney = view.findViewById(R.id.event_paidMoney);
            rating = view.findViewById(R.id.event_rating);
            ratingLabel = view.findViewById(R.id.event_rating_label);
            takeButton = view.findViewById(R.id.take_button);
            rateButton = view.findViewById(R.id.rate_button);
            progressBar = view.findViewById(R.id.event_progress);

            takeButton.setOnClickListener(view1 ->
                    eventActions.takeEvent(events[getAdapterPosition()].eventId));

            rateButton.setOnClickListener(view1 ->
                    eventActions.rateEvent(events[getAdapterPosition()].eventId));
        }
    }
}
