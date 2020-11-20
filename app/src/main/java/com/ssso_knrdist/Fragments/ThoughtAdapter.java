package com.ssso_knrdist.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssso_knrdist.R;

import java.util.List;

public class ThoughtAdapter extends RecyclerView.Adapter<ThoughtAdapter.ViewHolder> {

    private Context context;

    private List<ThoughtModel> thoughtModelList;

    public ThoughtAdapter(List<ThoughtModel> thoughtModelList) {
        this.thoughtModelList = thoughtModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.gallery_custom_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {



        holder.date.setText(thoughtModelList.get(position).getDate_of_post());
        holder.urlTv.setText(thoughtModelList.get(position).getMessage());


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }


    @Override
    public int getItemCount() {
        return thoughtModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        TextView date,urlTv;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);

            urlTv = itemView.findViewById(R.id.url);
            date = itemView.findViewById(R.id.date);
            relativeLayout = itemView.findViewById(R.id.relative_layout);
        }
    }
}
