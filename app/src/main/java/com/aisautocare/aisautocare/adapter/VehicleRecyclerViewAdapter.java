package com.aisautocare.aisautocare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aisautocare.aisautocare.model.Vehicle;
import com.aisautocare.aisautocare.R;

import java.util.ArrayList;

/**
 * Created by Michael on 1/5/2017.
 */

public class VehicleRecyclerViewAdapter extends RecyclerView.Adapter<VehicleRecyclerViewAdapter.VehicleViewHolder> {

    private Context context;
    ArrayList<Vehicle> vehicles = new ArrayList<Vehicle>();

    // constructor
    public VehicleRecyclerViewAdapter(Context context, ArrayList<Vehicle> vehicles) {
        this.context = context;
        this.vehicles = vehicles;
    }

    @Override
    public VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_vehicle, parent, false);

        VehicleViewHolder view = new VehicleViewHolder(viewItem);

        return view;
    }

    @Override
    public void onBindViewHolder(VehicleViewHolder holder, int position) {
        holder.vehicleYear.setText(vehicles.get(position).getYear());
        holder.vehicleManufacturer.setText(vehicles.get(position).getManufacturer());
        holder.vehicleModel.setText(vehicles.get(position).getModel());
        holder.vehicleTransmission.setText(vehicles.get(position).getTransmission());
    }

    @Override
    public int getItemCount() {
        return vehicles.size();
    }

    public class VehicleViewHolder extends RecyclerView.ViewHolder {

        TextView vehicleYear, vehicleManufacturer, vehicleModel, vehicleTransmission;

        public VehicleViewHolder(View itemView) {
            super(itemView);

            vehicleYear = (TextView) itemView.findViewById(R.id.vehicle_year_text_view);
            vehicleManufacturer = (TextView) itemView.findViewById(R.id.vehicle_manufacturer_text_view);
            vehicleModel = (TextView) itemView.findViewById(R.id.vehicle_model_text_view);
            vehicleTransmission = (TextView) itemView.findViewById(R.id.vehicle_transmission_text_view);
        }
    }
}
