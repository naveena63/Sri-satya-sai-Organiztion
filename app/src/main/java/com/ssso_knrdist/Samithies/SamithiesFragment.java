package com.ssso_knrdist.Samithies;

import android.content.Intent;
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
import com.ssso_knrdist.Districts.DistrictDataAdapter;
import com.ssso_knrdist.Districts.DistrictDataModel;
import com.ssso_knrdist.R;
import com.ssso_knrdist.Utils.ApiCallingFlow;
import com.ssso_knrdist.Utils.PrefManager;
import com.ssso_knrdist.Utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SamithiesFragment extends Fragment {
    SamithiesAdapter samithiesAdapter;
    SamithiesModel samithiesModel;
    List<SamithiesModel> samithiesModelList;
    ApiCallingFlow apiCallingFlow;
    PrefManager prefManager;
    View view;
    RecyclerView recycleview;
    TextView no_packages_available;
    SamithiesActivity samithiesActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_samithies, container, false);


        recycleview = view.findViewById(R.id.recycleview);
        no_packages_available=view.findViewById(R.id.no_packages_available);
        prefManager = new PrefManager(getContext());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        recycleview.setLayoutManager(manager);

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
            getSamithiesData();
        }
    }

    private void getSamithiesData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.samithies_data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        samithiesModelList = new ArrayList<>();
                        apiCallingFlow.onSuccessResponse();
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("Samithis");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String package_name = jsonObject1.getString("package_name");
                                    String service_id = jsonObject1.getString("service_id");
                                    String samithieId = jsonObject1.getString("id");


                                    prefManager.storeValue(Urls.samit_serv_id,service_id);
                                    prefManager.setServiceId(service_id);

                                    samithiesModel = new SamithiesModel();
                                    samithiesModel.setPackage_name(package_name);
                                    samithiesModel.setSamithieId(samithieId);
                                    samithiesModel.setService_id(service_id);

                                    samithiesModelList.add(samithiesModel);

                                    Log.i("samithieId", "samithieId" + prefManager.getSamithiId());
                                }
                            }
                            if (samithiesModelList.size() > 0) {
                                samithiesAdapter = new SamithiesAdapter(samithiesModelList);
                                recycleview.setAdapter(samithiesAdapter);
                                no_packages_available.setVisibility(View.GONE);
                            }
                            else {
                                no_packages_available.setText(jsonObject.getString("msg"));
                                no_packages_available.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.i("", "_--------------samithies Response----------------" + response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        apiCallingFlow.onErrorResponse();


                        // Toast.makeText(HomePageActivity.this, "Something Went wrong.. try again", Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                        map.put("service_id", prefManager.getServiceId() );

                        Log.i("serviceid samithies",""+   prefManager.getServiceId());
                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
