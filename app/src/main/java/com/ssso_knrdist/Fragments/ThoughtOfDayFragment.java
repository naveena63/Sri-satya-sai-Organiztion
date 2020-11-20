package com.ssso_knrdist.Fragments;

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
import com.ssso_knrdist.Utils.ApiCallingFlow;
import com.ssso_knrdist.Utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThoughtOfDayFragment extends Fragment {
    RecyclerView recyclerView;
    TextView no_packages_available;
    ApiCallingFlow apiCallingFlow;

    ThoughtAdapter thoughtAdapter;
    ThoughtModel thoughtModel;
    List<ThoughtModel> thoughtModelList;
View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_thought_of_day, container, false);
        recyclerView=view.findViewById(R.id.recycleview);
        no_packages_available=view.findViewById(R.id.no_packages_available);

        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.VERTICAL, false);
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
            getGalleryData();
        }
    }

    private void getGalleryData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.thought_day,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        thoughtModelList = new ArrayList<>();
                        apiCallingFlow.onSuccessResponse();
                        try {

                            JSONObject jsonObject = new JSONObject(response);

                            String status = jsonObject.getString("status");

                            if (status.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("message");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                    String message = jsonObject1.getString("message");
                                    String date_of_post = jsonObject1.getString("date_of_post");

                                    thoughtModel = new ThoughtModel();
                                    thoughtModel.setDate_of_post(date_of_post);
                                    thoughtModel.setMessage(message);


                                    thoughtModelList.add(thoughtModel);

                                    Log.i("galley", "galley" +response);
                                }
                            }
                            if (thoughtModelList.size() > 0) {
                                thoughtAdapter = new ThoughtAdapter(thoughtModelList);
                                recyclerView.setAdapter(thoughtAdapter);
                                no_packages_available.setVisibility(View.GONE);
                            }
                            else {
                                no_packages_available.setText(jsonObject.getString("msg"));
                                no_packages_available.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Log.i("", "_--------------thoughtoftheday Response----------------" + response);


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

                return map;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


}


