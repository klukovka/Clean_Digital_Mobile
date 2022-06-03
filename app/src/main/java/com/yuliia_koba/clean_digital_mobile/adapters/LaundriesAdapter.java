package com.yuliia_koba.clean_digital_mobile.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yuliia_koba.clean_digital_mobile.R;
import com.yuliia_koba.clean_digital_mobile.models.Laundry;
import com.yuliia_koba.clean_digital_mobile.models.LaundryActions;

import java.util.List;

public class LaundriesAdapter extends RecyclerView.Adapter<LaundriesAdapter.ViewHolder>{
    private final LayoutInflater inflater;
    private final LaundryActions laundryActions;
    private Laundry[]laundries;

    public LaundriesAdapter(Context context, Laundry[] laundries, LaundryActions laundryActions) {
        this.inflater = LayoutInflater.from(context);
        this.laundries = laundries;
        this.laundryActions = laundryActions;
    }

    @NonNull
    @Override
    public LaundriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.laundry_tile, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LaundriesAdapter.ViewHolder holder, int position) {
        Laundry laundry = laundries[position];

        holder.id.setText(laundry.getLaundryId());
        holder.name.setText(laundry.getName());
        holder.address.setText(laundry.getAddress());
        holder.phone.setText(laundry.getPhone());
        holder.email.setText(laundry.getEmail());
    }

    @Override
    public int getItemCount() {
        return laundries.length;
    }

    public void refresh(Laundry[] laundries){
        this.laundries = laundries;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView id, name, address, phone, email;

        ViewHolder(View view){
            super(view);

            id = view.findViewById(R.id.laundry_id);
            name = view.findViewById(R.id.laundry_name);
            address = view.findViewById(R.id.laundry_address);
            phone = view.findViewById(R.id.laundry_phone);
            email = view.findViewById(R.id.laundry_email);

            view.setOnClickListener(view1 -> laundryActions
                    .getWashMachines(laundries[getAdapterPosition()]
                            .getLaundryId()));
        }
    }
}
