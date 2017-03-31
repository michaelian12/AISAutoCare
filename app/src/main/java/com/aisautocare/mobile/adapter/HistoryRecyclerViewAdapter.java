package com.aisautocare.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aisautocare.mobile.activity.HistoryDetailActivity;
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
    public void onBindViewHolder(HistoryRecyclerViewAdapter.HistoryViewHolder holder, final int position) {
        holder.serviceImage.setImageResource(histories.get(position).getImageResourceId());
        holder.serviceDate.setText(histories.get(position).getDate());
        holder.servicePrice.setText(histories.get(position).getPrice());
        holder.serviceName.setText(histories.get(position).getServiceName());
        holder.vehicleName.setText(histories.get(position).getVehicleName());
        holder.address.setText(histories.get(position).getAddress());

        holder.historyCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HistoryDetailActivity.class);
                intent.putExtra("idOrder", histories.get(position).getOrderId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        CardView historyCard;
        ImageView serviceImage;
        TextView serviceDate, servicePrice, serviceName, vehicleName, address;

        public HistoryViewHolder(View itemView) {
            super(itemView);
            historyCard = (CardView) itemView.findViewById(R.id.card_history);
            serviceImage = (ImageView) itemView.findViewById(R.id.history_service_image_view);
            serviceDate = (TextView) itemView.findViewById(R.id.history_date_text_view);
            servicePrice = (TextView) itemView.findViewById(R.id.history_price_text_view);
            serviceName = (TextView) itemView.findViewById(R.id.history_service_name_text_view);
            vehicleName = (TextView) itemView.findViewById(R.id.history_vehicle_name_text_view);
            address = (TextView) itemView.findViewById(R.id.history_address_text_view);
        }
    }
}
