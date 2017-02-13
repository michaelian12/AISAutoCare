package com.aisautocare.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aisautocare.mobile.model.Garage;
import com.aisautocare.mobile.model.History;

import java.util.ArrayList;

import info.androidhive.firebasenotifications.R;

/**
 * Created by Michael on 2/13/2017.
 */

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.HistoryViewHolder>{

    private Context context;
    ArrayList<History> histories = new ArrayList<History>();

    public HistoryRecyclerViewAdapter(Context context, ArrayList<History> histories) {
        this.context = context;
        this.histories = histories;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history, parent, false);
        HistoryRecyclerViewAdapter.HistoryViewHolder view = new HistoryRecyclerViewAdapter.HistoryViewHolder(viewItem);

        return view;
    }

    @Override
    public void onBindViewHolder(HistoryRecyclerViewAdapter.HistoryViewHolder holder, int position) {
        holder.vehicleImage.setImageResource(histories.get(position).getImageResourceId());
        holder.serviceDate.setText(histories.get(position).getDate());
        holder.servicePrice.setText(histories.get(position).getPrice());
        holder.vehicleName.setText(histories.get(position).getVehicleName());
        holder.serviceName.setText(histories.get(position).getServiceName());
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        ImageView vehicleImage;
        TextView serviceDate, servicePrice, vehicleName, serviceName;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            vehicleImage = (ImageView) itemView.findViewById(R.id.history_vehicle_image);
            serviceDate = (TextView) itemView.findViewById(R.id.history_service_date);
            servicePrice = (TextView) itemView.findViewById(R.id.history_service_price);
            vehicleName = (TextView) itemView.findViewById(R.id.history_vehicle_name);
            serviceName = (TextView) itemView.findViewById(R.id.history_service_name);
        }
    }
}
