package com.aisautocare.mobile.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.firebasenotifications.R;

import com.aisautocare.mobile.GlobalVar;
import com.aisautocare.mobile.activity.OrderActivity;
import com.aisautocare.mobile.model.Service;

import java.util.ArrayList;

/**
 * Created by Michael on 1/14/2017.
 */

public class ServiceRecyclerViewAdapter extends RecyclerView.Adapter<ServiceRecyclerViewAdapter.CareViewHolder> {

    private Context context;
    ArrayList<Service> services = new ArrayList<Service>();

    public ServiceRecyclerViewAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public CareViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_service, parent, false);
        CareViewHolder view = new CareViewHolder(viewItem);

        return view;
    }

    @Override
    public void onBindViewHolder(ServiceRecyclerViewAdapter.CareViewHolder holder, int position) {
        holder.serviceName.setText(services.get(position).getName());
        holder.servicePrice.setText(services.get(position).getPrice());
        holder.servicePrice.setVisibility(View.INVISIBLE);
        holder.serviceIcon.setImageResource(services.get(position).getImageResourceId());
        holder.serviceCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new GlobalVar().isVehicleSelected){
                    Intent intent = new Intent(view.getContext(), OrderActivity.class);
                    //intent.putExtra("Order", order)
                    view.getContext().startActivity(intent);
                } else {
                    new AlertDialog.Builder(context)
                            .setTitle("Data Kendaraan Belum Diisi")
                            .setMessage("Silahkan tambah kendaraan terlebih dahulu.")

                            .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // do nothing
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class CareViewHolder extends RecyclerView.ViewHolder {

        CardView serviceCard;
        TextView serviceName, servicePrice;
        ImageView serviceIcon;

        public CareViewHolder(View itemView) {
            super(itemView);
            serviceCard = (CardView) itemView.findViewById(R.id.card_service);
            serviceName = (TextView) itemView.findViewById(R.id.service_name_card_text_view);
            servicePrice = (TextView) itemView.findViewById(R.id.service_price_card_text_view);
            serviceIcon = (ImageView) itemView.findViewById(R.id.service_icon_card_image_view);
        }
    }
}
