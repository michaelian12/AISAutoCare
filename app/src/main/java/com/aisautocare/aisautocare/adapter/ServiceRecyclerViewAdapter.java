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
 * Created by Michael on 1/5/2017.
 */

public class ServiceRecyclerViewAdapter extends RecyclerView.Adapter<ServiceRecyclerViewAdapter.ServiceViewHolder> {

    private Context context;
    ArrayList<Service> services = new ArrayList<Service>();

    // constructor
    public ServiceRecyclerViewAdapter(Context context, ArrayList<Service> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_service, parent, false);

        ServiceViewHolder view = new ServiceViewHolder(viewItem);

        return view;
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
//        holder.serviceThumbnail.setImageResource(services.get(position).getImageResourceId());
        holder.serviceName.setText(services.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {

//        ImageView serviceThumbnail;
        TextView serviceName;

        public ServiceViewHolder(View itemView) {
            super(itemView);

//            serviceThumbnail = (ImageView) itemView.findViewById(R.id.service_thumbnail_image_view);
            serviceName = (TextView) itemView.findViewById(R.id.service_name_text_view);

        }
    }
}
