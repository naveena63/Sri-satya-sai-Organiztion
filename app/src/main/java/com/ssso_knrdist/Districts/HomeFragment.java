package com.ssso_knrdist.Districts;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;
import com.ssso_knrdist.HomeBanners.BannerAdapter;
import com.ssso_knrdist.HomeBanners.BannerModel;
import com.ssso_knrdist.Districts.AllNotificationFragment;
import com.ssso_knrdist.Districts.DistrictFragment;
import com.ssso_knrdist.Samithies.SamithiesFragment;
import com.ssso_knrdist.Utils.PrefManager;
import com.ssso_knrdist.R;
import com.ssso_knrdist.Utils.Urls;
import com.ssso_knrdist.Wings.WingsDataFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class HomeFragment extends Fragment {
    private String title;
    View view;
    TextView tvTitle;
//    private ViewPager mPager;
//    private int currentPage;
    private TabLayout tabLayout;
    private ViewPager viewPager;

  //  private List<BannerModel> stringList;
    PrefManager prefManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        prefManager = new PrefManager(getActivity());
        title = getArguments().getString("title");
        tvTitle = view.findViewById(R.id.tv_title);
        tvTitle.setText(title);
       // getSliderImages();

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        createViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        createTabIcons();
        return view;
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setText("All  Notifications");
        //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_notifications_active_24, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("All Districts");
        //  tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_notifications_active_24, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabThree.setText("All Wings");
        //  tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_notifications_active_24, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);


    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFrag(new AllNotificationFragment(), "Tab 1");
        adapter.addFrag(new DistrictFragment(), "Tab 2");
        adapter.addFrag(new WingsDataFragment(), "Tab 3");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


//    private void getSliderImages() {
//        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        // Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.banners,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        stringList = new ArrayList<>();
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            String status = jsonObject.getString("status");
//                            if (status.equals("success")) {
//                                JSONArray jsonArray = jsonObject.getJSONArray("banners");
//                                for (int i = 0; i < jsonArray.length(); i++) {
//                                    JSONObject object = jsonArray.getJSONObject(i);
//                                    String image = object.getString("banner_image");
//                                    String imageName = object.getString("image_name");
//                                    BannerModel bannerModel = new BannerModel();
//                                    bannerModel.setBannerImage(image);
//                                    if (imageName != null) {
//                                        bannerModel.setImageName(imageName);
//                                    } else if (imageName.matches(".")) {
//                                        bannerModel.setImageName("Banners");
//                                    }
//                                    stringList.add(bannerModel);
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        if (stringList.size() > 0) {
////                            for(int j=0;i<stringList.size();j++)
////                                XMENArray.add(Integer.valueOf(stringList.get(j).getBannerImage()));
//                            mPager = (ViewPager) view.findViewById(R.id.pager);
//                            mPager.setAdapter(new BannerAdapter(getContext(), stringList));
//                            CircleIndicator indicator = (CircleIndicator) view.findViewById(R.id.indicator);
//                            indicator.setViewPager(mPager);
//
//                            // Auto start of viewpager
//                            final Handler handler = new Handler();
//                            final Runnable Update = new Runnable() {
//                                public void run() {
//                                    if (currentPage == stringList.size()) {
//                                        currentPage = 0;
//                                    }
//                                    mPager.setCurrentItem(currentPage++, true);
//                                }
//                            };
//                            Timer swipeTimer = new Timer();
//                            swipeTimer.schedule(new TimerTask() {
//                                @Override
//                                public void run() {
//                                    handler.post(Update);
//                                }
//                            }, 5000, 5000);
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(1000, 1, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        requestQueue.add(stringRequest);
//
//
//    }
}