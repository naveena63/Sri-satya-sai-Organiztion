package com.ssso_knrdist.Districts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ssso_knrdist.Utils.PrefManager;
import com.ssso_knrdist.R;
import com.ssso_knrdist.Utils.ApiCallingFlow;
import com.ssso_knrdist.Utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DistrictFragment extends Fragment {

    RecyclerView recyclerView;
    DistrictDataAdapter districtDataAdapter;
    DistrictDataModel districtDataModel;
    List<DistrictDataModel> districtDataModelList;
    ApiCallingFlow apiCallingFlow;
    PrefManager prefManager;
    TextView no_packages_available;
  View view;
    String service_ID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     view= inflater.inflate(R.layout.fragment_district, container, false);
     recyclerView=view.findViewById(R.id.recycleview);
        no_packages_available=view.findViewById(R.id.no_packages_available);

     prefManager=new PrefManager(getContext());
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
                                     service_ID = jsonObject1.getString("id");
//                                    prefManager.storeValue(Urls.service_id,service_ID);
//                                    prefManager.setServiceId(service_ID);

                                    Log.i("servic", "servic" + category_name + "" + category_icon+" "+" "+service_ID);
                                    districtDataModel = new DistrictDataModel();
                                    districtDataModel.setCategory_name(category_name);
                                    districtDataModel.setCategroy_icon(category_icon);
                                    districtDataModel.setService_id(service_ID);



                                    districtDataModelList.add(districtDataModel);

                                    Log.i("serviceId", "serviceId" + prefManager.getServiceId());
                                }
                            }

                            if (districtDataModelList.size() > 0) {
                                districtDataAdapter = new DistrictDataAdapter(districtDataModelList);
                                recyclerView.setAdapter(districtDataAdapter);
                                no_packages_available.setVisibility(View.GONE);
                            }
                         else {
                            no_packages_available.setText(jsonObject.getString("msg"));
                            no_packages_available.setVisibility(View.VISIBLE);
                        }
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}