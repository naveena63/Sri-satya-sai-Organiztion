package com.ssso_knrdist.Wings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ssso_knrdist.R;


import java.util.List;

public class WingsAdapter extends RecyclerView.Adapter<WingsAdapter.ViewHolder> {

    private Context context;

    private List<WingsModel> wingsModelList;

    public WingsAdapter(List<WingsModel> wingsModelList) {
        this.wingsModelList = wingsModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.district_custom_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        //  String imgPath = samithiesModelList.get(position).get();

        //   Log.e("imgPath", "" + imgPath);

        //   Picasso.with(context).load(imgPath).into(holder.serviceImg);

        holder.wingsNameTv.setText(wingsModelList.get(position).getSub_package_name());
        final String wingsId = wingsModelList.get(position).getWingsId();


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NotificationActivity.class);
                context.startActivity(intent);

            }
        });
    }






    @Override
    public int getItemCount() {
        return wingsModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView wingsImg;
        TextView wingsNameTv;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);

            wingsImg = itemView.findViewById(R.id.imageView);
            wingsNameTv = itemView.findViewById(R.id.textView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}