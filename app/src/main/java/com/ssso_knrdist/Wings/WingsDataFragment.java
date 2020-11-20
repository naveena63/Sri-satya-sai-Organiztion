package com.ssso_knrdist.Wings;

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
import com.ssso_knrdist.R;
import com.ssso_knrdist.Samithies.SamithiesAdapter;
import com.ssso_knrdist.Samithies.SamithiesModel;
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


public class WingsDataFragment extends Fragment {
    WingsAdapter wingsAdapter;
    WingsModel wingsModel;
    List<WingsModel> wingsModelLists;
    ApiCallingFlow apiCallingFlow;
    PrefManager prefManager;
    View view;
    RecyclerView recycleview;
    TextView no_packages_available;

    String service_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wings_data, container, false);

        recycleview = view.findViewById(R.id.recycleview);
        no_packages_available = view.findViewById(R.id.no_packages_available);
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
            getWingssData();
        }
    }

    private void getWingssData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Urls.wings_data,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        wingsModelLists = new ArrayList<>();
                        apiCallingFlow.onSuccessResponse();
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("subpackages");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String wing_name = jsonObject1.getString("sub_package_name");

                                    String wing_Id = jsonObject1.getString("sub_package_id");
                                    service_id = jsonObject1.getString("service_id");

                                    wingsModel = new WingsModel();
                                    wingsModel.setSub_package_name(wing_name);
                                    wingsModel.setWingsId(wing_Id);

                                    wingsModelLists.add(wingsModel);

                                }
                            }
                            if (wingsModelLists.size() > 0) {
                                wingsAdapter = new WingsAdapter(wingsModelLists);
                                recycleview.setAdapter(wingsAdapter);
                                no_packages_available.setVisibility(View.GONE);
                            } else {
                                no_packages_available.setText(jsonObject.getString("msg"));
                                no_packages_available.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.i("", "_--------------wings Response----------------" + response);


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
                map.put("service_id", "425381");
                map.put("package_id", "1");
                Log.i("servid", "" + prefManager.getServiceId());
                Log.i("samithiid", "" + prefManager.getSamithiId());


                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}