package com.aisautocare.mobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.firebasenotifications.R;
import com.aisautocare.mobile.model.Garage;
import com.aisautocare.mobile.activity.GarageActivity;
import com.aisautocare.mobile.model.Garage;

import java.util.ArrayList;

/**
 * Created by Michael on 1/16/2017.
 */

public class GarageRecyclerViewAdapter extends RecyclerView.Adapter<GarageRecyclerViewAdapter.GarageViewHolder> {

    private Context context;
    ArrayList<Garage> garages = new ArrayList<Garage>();

    public GarageRecyclerViewAdapter(Context context, ArrayList<Garage> garages) {
        this.context = context;
        this.garages = garages;
    }



    @Override
    public GarageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_garage, parent, false);
        GarageViewHolder view = new GarageViewHolder(viewItem);

        return view;
    }

    @Override
    public void onBindViewHolder(GarageViewHolder holder, int position) {
        holder.garageIcon.setImageResource(garages.get(position).getImageResourceId());
        holder.garageYear.setText(garages.get(position).getYear());
        holder.garageManufacturer.setText(garages.get(position).getManufacturer());
        holder.garageModel.setText(garages.get(position).getModel());
        holder.garageTransmission.setText(garages.get(position).getTransmission());
    }

    @Override
    public int getItemCount() {
        return garages.size();
    }

    public class GarageViewHolder extends RecyclerView.ViewHolder {

        ImageView garageIcon;
        TextView garageYear, garageManufacturer, garageModel, garageTransmission;

        public GarageViewHolder(View itemView) {
            super(itemView);
            garageIcon = (ImageView) itemView.findViewById(R.id.garage_icon_card_image_view);
            garageYear = (TextView) itemView.findViewById(R.id.garage_year_card_text_view);
            garageManufacturer = (TextView) itemView.findViewById(R.id.garage_manufacturer_card_text_view);
            garageModel = (TextView) itemView.findViewById(R.id.garage_model_card_text_view);
            garageTransmission = (TextView) itemView.findViewById(R.id.garage_transmission_card_text_view);
        }
    }
}
