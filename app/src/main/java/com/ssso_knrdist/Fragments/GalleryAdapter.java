package com.ssso_knrdist.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ssso_knrdist.R;
import com.ssso_knrdist.Samithies.SamithiesAdapter;
import com.ssso_knrdist.Samithies.SamithiesModel;
import com.ssso_knrdist.Wings.WingsActivity;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {

    private Context context;

    private List<GalleryModel> galleryModelList;

    public GalleryAdapter(List<GalleryModel> galleryModelList) {
        this.galleryModelList = galleryModelList;
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



        holder.date.setText(galleryModelList.get(position).getDate_of_post());
       holder.urlTv.setText(galleryModelList.get(position).getLink());


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = galleryModelList.get(position).getLink();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                context.startActivity(intent);

            }
        });
    }


    @Override
    public int getItemCount() {
        return galleryModelList.size();
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
