package com.ssso_knrdist.HomeBanners;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.ssso_knrdist.R;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    private List<BannerModel> images;
    private LayoutInflater inflater;
    private Context context;


    public BannerAdapter(Context context, List<BannerModel> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        final BannerModel bannerModel = images.get(position);
        View myImageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);
        final ImageView myImage = (ImageView) myImageLayout.findViewById(R.id.image);
        final TextView textView = myImageLayout.findViewById(R.id.textLabel);
        textView.setText(bannerModel.getImageName());
        Log.e("image", "image==>  " + bannerModel.getBannerImage());

        //added Piccasso for memory cache...
        Picasso.with(context)
                .load( bannerModel.getBannerImage())
                .placeholder(R.drawable.saibaba_logo)
                .error(R.drawable.saibaba_logo)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(myImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        Log.e("image_slide", "image_slide " +bannerModel.getBannerImage());
                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(context)
                                .load( bannerModel.getBannerImage())
                                .error(R.drawable.saibaba_logo)
                                .into(myImage, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        Log.e("image_s", bannerModel.getBannerImage());
                                    }

                                    @Override
                                    public void onError() {
                                        Log.e("Picasso", "Could not fetch image");
                                    }
                                });
                    }
                });
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}