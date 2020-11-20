package com.ssso_knrdist.Samithies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import com.ssso_knrdist.Districts.DistrictDataAdapter;
import com.ssso_knrdist.Districts.DistrictDataModel;
import com.ssso_knrdist.R;
import com.ssso_knrdist.Wings.WingsActivity;

import java.util.List;

public class SamithiesAdapter extends RecyclerView.Adapter<SamithiesAdapter.ViewHolder> {

    private Context context;

    private List<SamithiesModel> samithiesModelList;

    public SamithiesAdapter(List<SamithiesModel> samithiesModelList) {
        this.samithiesModelList = samithiesModelList;
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

        holder.samithieNameTv.setText(samithiesModelList.get(position).getPackage_name());
        final String samithie_id = samithiesModelList.get(position).getSamithieId();
        final String service_id = samithiesModelList.get(position).getService_id();




        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, WingsActivity.class);
                intent.putExtra("samithie_id", samithie_id);
                intent.putExtra("service_id", service_id);
                context.startActivity(intent);

            }
        });
    }






    @Override
    public int getItemCount() {
        return samithiesModelList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView samithieImg;
        TextView samithieNameTv;
        RelativeLayout relativeLayout;

        ViewHolder(View itemView) {
            super(itemView);

            samithieImg = itemView.findViewById(R.id.imageView);
            samithieNameTv = itemView.findViewById(R.id.textView);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);
        }
    }
}