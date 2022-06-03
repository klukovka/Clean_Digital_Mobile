package com.yuliia_koba.clean_digital_mobile.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.WashMachine;

public class WashMachinesAdapter extends RecyclerView.Adapter<WashMachinesAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final Context context;
    private WashMachine[]washMachines;

    public WashMachinesAdapter(Context context, WashMachine[] washMachines) {
        this.context = context;
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
        holder.capacity.setText(washMachine.getCapacity());
        holder.powerUsage.setText(washMachine.getPowerUsage());
        holder.spinningSpeed.setText(washMachine.getSpinningSpeed());

        holder.isWashing.setText(washMachine.isWashing()?R.string.yes:R.string.no);
        holder.isWorking.setText(washMachine.isWorking()?R.string.yes:R.string.no);

        if (washMachine.isWashing()){
            holder.image.setColorFilter(R.color.yellow);
        } else if (!washMachine.isWorking()) {
            holder.image.setColorFilter(R.color.red);
        } else {
            holder.image.setColorFilter(R.color.green);
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