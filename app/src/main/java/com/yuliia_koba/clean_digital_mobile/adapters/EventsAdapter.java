package com.yuliia_koba.clean_digital_mobile.adapters;

import android.content.Context;
import android.os.Build;
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
import com.yuliia_koba.clean_digital_mobile.models.actions.LaundryActions;
import com.yuliia_koba.clean_digital_mobile.models.dto.Event;
import com.yuliia_koba.clean_digital_mobile.models.dto.Laundry;
import com.yuliia_koba.clean_digital_mobile.utils.Utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final EventActions  eventActions;
    private Event[] events;

    public EventsAdapter(Context context, Event[] events, EventActions eventActions) {
        this.inflater = LayoutInflater.from(context);
        this.events = events;
        this.eventActions = eventActions;
    }


    @NonNull
    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.event_tile, parent, false);

        return new EventsAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.ViewHolder holder, int position) {
        Event event = events[position];

        holder.id.setText(event.eventId);
        holder.washMachine.setText(event.washMachineId);
        holder.temperature.setText(String.valueOf(event.temperature));
        holder.spinning.setText(String.valueOf(event.spinning));

        int maxProgress = event.mode.time;

        holder.mode.setText(event.mode.name);

        if (event.additionalMode!=null){
            holder.additionalMode.setText(event.additionalMode.name);
            maxProgress += event.additionalMode.time;
        } else {
            holder.additionalMode.setVisibility(View.GONE);
            holder.additionalModeLabel.setVisibility(View.GONE);
        }

        holder.timeBegin.setText(event.timeBegin.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));

        if (event.timeEnd!=null){
            holder.timeEnd.setText(event.timeEnd.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
        } else {
            holder.timeEnd.setVisibility(View.GONE);
            holder.timeEndLabel.setVisibility(View.GONE);
            holder.takeButton.setVisibility(View.GONE);
        }

        holder.paidStatus.setText(event.paidStatus?R.string.paid:R.string.not_paid);

        holder.paidBonuses.setText(String.valueOf(event.paidBonuses));
        holder.paidMoney.setText(String.valueOf(event.paidMoney));

        if (event.rating!=null){
            holder.rating.setText(String.valueOf(event.rating));
        } else{
            holder.rating.setVisibility(View.GONE);
            holder.ratingLabel.setVisibility(View.GONE);
        }

        if (event.rating!=null || event.timeEnd==null){
            holder.takeButton.setVisibility(View.GONE);
        }

        holder.progressBar.setMax(maxProgress);
        int currentProgress = Utils.getMinutes(LocalDateTime.now()) - Utils.getMinutes(event.timeBegin);
        holder.progressBar.setProgress(currentProgress);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void refresh(Event[] events){
        this.events = events;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView id, washMachine, temperature, spinning,
        mode, additionalMode, additionalModeLabel, timeBegin,
        timeEnd, timeEndLabel,paidStatus, paidBonuses,
        paidMoney, rating, ratingLabel;

        ProgressBar progressBar;
        Button rateButton, takeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.event_id);
            washMachine = itemView.findViewById(R.id.event_washMachine);
            temperature = itemView.findViewById(R.id.event_temperature);
            spinning = itemView.findViewById(R.id.event_spinning);
            mode = itemView.findViewById(R.id.event_mode);
            additionalMode = itemView.findViewById(R.id.event_additionalMode);
            additionalModeLabel = itemView.findViewById(R.id.event_additionalMode_label);
            timeBegin = itemView.findViewById(R.id.event_timeBegin);
            timeEnd = itemView.findViewById(R.id.event_timeEnd);
            timeEndLabel = itemView.findViewById(R.id.event_timeEnd_label);
            paidStatus = itemView.findViewById(R.id.event_paidStatus);
            paidBonuses = itemView.findViewById(R.id.event_paidBonuses);
            paidMoney = itemView.findViewById(R.id.event_paidMoney);
            rating = itemView.findViewById(R.id.event_rating);
            ratingLabel = itemView.findViewById(R.id.event_rating_label);
            progressBar = itemView.findViewById(R.id.event_progress);
            rateButton = itemView.findViewById(R.id.rate_button);
            takeButton = itemView.findViewById(R.id.take_button);

            rateButton.setOnClickListener(view -> eventActions.rateEvent(events[getAdapterPosition()].eventId));

            takeButton.setOnClickListener(view -> eventActions.takeEvent(events[getAdapterPosition()].eventId));

        }
    }
}
