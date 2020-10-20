package com.ssso_knrdist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.ssso_knrdist.Utils.ApiCallingFlow;
import com.ssso_knrdist.Utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private String title;
    RecyclerView recyclerView;
    DistrictDataAdapter districtDataAdapter;
    DistrictDataModel districtDataModel;
    List<DistrictDataModel> districtDataModelList;
    View view;
    TextView tvTitle;
    ApiCallingFlow apiCallingFlow;
    protected CollapsingToolbarLayout mCollapsedTitle;
    private ViewPager mPager;
    private int currentPage;
    private List<BannerModel> stringList;
    PrefManager prefManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.fragment_home, container, false);
prefManager=new PrefManager(getActivity());
        title = getArguments().getString("title");
        tvTitle = view.findViewById(R.id.tv_title);
        recyclerView=view.findViewById(R.id.recyclerview);
        tvTitle.setText(title);
        getSliderImages();
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
api();
        return view;
    }
    private void api() {
        RelativeLayout parentLayout = (RelativeLayout) view.findViewById(R.id.relative_layout);

        apiCallingFlow = new ApiCallingFlow(getActivity(), parentLayout, true) {
            @Override
            public void callCurrentApiHere() {
                api();
            }
        };
        if (apiCallingFlow.getNetworkState()) {
            getDistrictData();
        }
    }
    private void getDistrictData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.district_data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                         districtDataModelList= new ArrayList<>();
                        apiCallingFlow.onSuccessResponse();
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("All_District");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String category_name = jsonObject1.getString("category_name");
                                    String category_icon = jsonObject1.getString("category_icon");
                                    String service_ID = jsonObject1.getString("id");


                                    Log.i("servic", "servic" + category_name + "" + category_icon+" "+" "+service_ID);
                                    districtDataModel = new DistrictDataModel();
                                    districtDataModel.setCategory_name(category_name);
                                    districtDataModel.setCategroy_icon(category_icon);
                                    districtDataModel.setService_id(service_ID);


                                    prefManager.storeValue(PrefManager.SERVICE_ID,service_ID);
                                    prefManager.setServiceId(service_ID);
                                    districtDataModelList.add(districtDataModel);

                                    Log.i("serviceId", "serviceId" + prefManager.getServiceId());
                                }
                            }
                            districtDataAdapter = new DistrictDataAdapter(districtDataModelList);
                            recyclerView.setAdapter(districtDataAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.i("HOME", "_--------------Distirct Response----------------" + response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        apiCallingFlow.onErrorResponse();
                        Log.i("HOME", "_--------------error Response----------------" );

                        // Toast.makeText(HomePageActivity.this, "Something Went wrong.. try again", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity()  );
        requestQueue.add(stringRequest);
    }

    private void getSliderImages() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.banners,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        stringList = new ArrayList<>();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            if (status.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("banners");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String image = object.getString("banner_image");
                                    String imageName = object.getString("image_name");
                                    BannerModel bannerModel = new BannerModel();
                                    bannerModel.setBannerImage(image);
                                    if (imageName != null) {
                                        bannerModel.setImageName(imageName);
                                    } else if (imageName.matches(".")) {
                                        bannerModel.setImageName("Banners");
                                    }
                                    stringList.add(bannerModel);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (stringList.size() > 0) {
//                            for(int j=0;i<stringList.size();j++)
//                                XMENArray.add(Integer.valueOf(stringList.get(j).getBannerImage()));
                            mPager = (ViewPager) view.findViewById(R.id.pager);
                            mPager.setAdapter(new BannerAdapter(getContext(), stringList));
                            CircleIndicator indicator = (CircleIndicator)view.findViewById(R.id.indicator);
                            indicator.setViewPager(mPager);

                            // Auto start of viewpager
                            final Handler handler = new Handler();
                            final Runnable Update = new Runnable() {
                                public void run() {
                                    if (currentPage == stringList.size()) {
                                        currentPage = 0;
                                    }
                                    mPager.setCurrentItem(currentPage++, true);
                                }
                            };
                            Timer swipeTimer = new Timer();
                            swipeTimer.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    handler.post(Update);
                                }
                            }, 5000, 5000);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);



    }
}