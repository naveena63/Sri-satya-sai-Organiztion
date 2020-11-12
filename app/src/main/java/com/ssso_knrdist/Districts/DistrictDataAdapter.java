package com.ssso_knrdist.Districts;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.ssso_knrdist.R;

import java.util.List;

public class DistrictDataAdapter extends RecyclerView.Adapter<DistrictDataAdapter.ViewHolder> {

    private Context context;

    private List<DistrictDataModel> districtDataModelList;

    public DistrictDataAdapter(List<DistrictDataModel> dataModelList) {
        this.districtDataModelList = dataModelList;
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

        String imgPath = districtDataModelList.get(position).getCategroy_icon();

        Log.e("imgPath", "" + imgPath);

        Picasso.with(context).load(imgPath).into(holder.serviceImg);

        holder.serviceNameTv.setText(districtDataModelList.get(position).getCategory_name());
        final String serviceId = districtDataModelList.get(position).getService_id();
        final String serviceName = districtDataModelList.get(position).getCategory_name();
        final String sub_category_status = districtDataModelList.get(position).getSub_category_status();
        Log.e("server",serviceId+serviceName+sub_category_status);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }






    @Override
    public int getItemCount() {
        return districtDataModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView serviceImg;
        TextView serviceNameTv;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);

            serviceImg = itemView.findViewById(R.id.imageView);
            serviceNameTv = itemView.findViewById(R.id.textView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}
