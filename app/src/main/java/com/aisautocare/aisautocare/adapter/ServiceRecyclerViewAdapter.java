package com.aisautocare.aisautocare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aisautocare.aisautocare.R;
import com.aisautocare.aisautocare.model.Service;

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
        holder.serviceIcon.setImageResource(services.get(position).getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class CareViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName;
        ImageView serviceIcon;

        public CareViewHolder(View itemView) {
            super(itemView);
            serviceName = (TextView) itemView.findViewById(R.id.service_name_card_text_view);
            serviceIcon = (ImageView) itemView.findViewById(R.id.service_icon_card_image_view);
        }
    }
}
