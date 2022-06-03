package com.yuliia_koba.clean_digital_mobile.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.dto.WashMachine;

public class WashMachinesAdapter extends RecyclerView.Adapter<WashMachinesAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private WashMachine[]washMachines;

    public WashMachinesAdapter(Context context, WashMachine[] washMachines) {
        this.inflater = LayoutInflater.from(context);
        this.washMachines = washMachines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.wash_machine_tile, parent, false);

        return new WashMachinesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WashMachine washMachine = washMachines[position];

        holder.id.setText(washMachine.getWashMachineId());
        holder.model.setText(washMachine.getModel());
        holder.manufacturer.setText(washMachine.getManufacturer());
        holder.capacity.setText(String.valueOf(washMachine.getCapacity()));
        holder.powerUsage.setText(String.valueOf(washMachine.getPowerUsage()));
        holder.spinningSpeed.setText(String.valueOf(washMachine.getSpinningSpeed()));

        holder.isWashing.setText(washMachine.isWashing()?R.string.yes:R.string.no);
        holder.isWorking.setText(washMachine.isWorking()?R.string.yes:R.string.no);

        if (washMachine.isWashing()){
            holder.image.setBackgroundColor(Color.YELLOW);
        } else if (!washMachine.isWorking()) {
            holder.image.setBackgroundColor(Color.RED);
        } else {
            holder.image.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public int getItemCount() {
        return washMachines.length;
    }

    public void refresh(WashMachine[] washMachines){
        this.washMachines = washMachines;
        notifyDataSetChanged();
    }

    public class ViewHolder  extends RecyclerView.ViewHolder {
        TextView id, model, manufacturer, capacity, powerUsage, spinningSpeed, isWorking, isWashing;
        ImageView image;

        ViewHolder(View view){
            super(view);

            id = view.findViewById(R.id.wash_machine_id);
            model = view.findViewById(R.id.model);
            manufacturer = view.findViewById(R.id.manufacturer);
            capacity = view.findViewById(R.id.capacity);
            powerUsage = view.findViewById(R.id.power_usage);
            spinningSpeed = view.findViewById(R.id.spinning_speed);
            isWorking = view.findViewById(R.id.is_working);
            isWashing = view.findViewById(R.id.is_washing);
            image = view.findViewById(R.id.wash_machine_image);
        }
    }
}
