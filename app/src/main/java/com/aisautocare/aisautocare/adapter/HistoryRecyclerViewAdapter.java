package com.aisautocare.aisautocare.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aisautocare.aisautocare.model.History;
import com.aisautocare.aisautocare.R;

import java.util.ArrayList;

/**
 * Created by Michael on 1/5/2017.
 */

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.TransactionViewHolder> {

    private Context context;
    ArrayList<History> histories = new ArrayList<History>();

    public HistoryRecyclerViewAdapter(Context context, ArrayList<History> histories) {
        this.context = context;
        this.histories = histories;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_history, parent, false);

        TransactionViewHolder view = new TransactionViewHolder(viewItem);

        return view;
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
//        holder.thumbnail.setImageResource(histories.get(position).getImageResourceId());
        holder.name.setText(histories.get(position).getName());
        holder.info.setText(histories.get(position).getNotes());
        holder.time.setText(histories.get(position).getTime());
        holder.date.setText(histories.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return histories.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {

//        ImageView thumbnail;
        TextView name, info, time, date;

        public TransactionViewHolder(View itemView) {
            super(itemView);

//            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail_image_view);
            name = (TextView) itemView.findViewById(R.id.name_text_view);
            info = (TextView) itemView.findViewById(R.id.info_text_view);
            time = (TextView) itemView.findViewById(R.id.time_text_view);
            date = (TextView) itemView.findViewById(R.id.date_text_view);
        }
    }
}
