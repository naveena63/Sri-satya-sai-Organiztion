package com.ssso_knrdist.Activties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ssso_knrdist.Districts.HomeFragment;
import com.ssso_knrdist.Fragments.GalleryFragment;
import com.ssso_knrdist.Fragments.ProfileFragment;
import com.ssso_knrdist.Fragments.SocialMediaLinksFragment;
import com.ssso_knrdist.Fragments.ThoughtOfDayFragment;
import com.ssso_knrdist.R;
import com.ssso_knrdist.databinding.ActivityBottomPageBinding;
import java.util.ArrayList;
import java.util.List;

public class BottomPageActivity extends AppCompatActivity {
    private static final String TAG = BottomPageActivity.class.getSimpleName();
    private ActivityBottomPageBinding bind;
    private VpAdapter adapter;
    private List<Fragment> fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bind = DataBindingUtil.setContentView(this, R.layout.activity_bottom_page);

        initData();
        initView();
        initEvent();
    }

    private void initData() {
        fragments = new ArrayList<>(4);

        // create music fragment and add it
        HomeFragment homefragment = new HomeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", "home");
        homefragment.setArguments(bundle);

        // create backup fragment and add it
        GalleryFragment notifactns = new GalleryFragment();
        bundle = new Bundle();
        bundle.putString("title", "notifications");
        notifactns.setArguments(bundle);

        // create friends fragment and add it
        ThoughtOfDayFragment thoughtOfDayFragment = new ThoughtOfDayFragment();
        bundle = new Bundle();
        bundle.putString("title", "messages");
        thoughtOfDayFragment.setArguments(bundle);

        // create friends fragment and add it
        ProfileFragment profile = new ProfileFragment();
        bundle = new Bundle();
        bundle.putString("title", "profile");
        profile.setArguments(bundle);


        // add to fragments for adapter
        fragments.add(homefragment);
        fragments.add(notifactns);
        fragments.add(thoughtOfDayFragment);
        fragments.add(profile);
    }


    private void initView() {
        bind.bnve.enableItemShiftingMode(false);
        bind.bnve.enableShiftingMode(false);
        bind.bnve.enableAnimation(false);

        // set adapter
        adapter = new VpAdapter(getSupportFragmentManager(), fragments);
        bind.vp.setAdapter(adapter);
    }

    private void initEvent() {
        // set listener to change the current item of view pager when click bottom nav item
        bind.bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            private int previousPosition = -1;

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int position = 0;
                switch (item.getItemId()) {
                    case R.id.i_music:
                        position = 0;
                        break;
                    case R.id.i_backup:
                        position = 1;
                        break;
                    case R.id.i_favor:
                        position = 2;
                        break;
                    case R.id.i_visibility:
                        position = 3;
                        break;
                    case R.id.i_empty: {
                        return false;
                    }
                }
                if (previousPosition != position) {
                    bind.vp.setCurrentItem(position, false);
                    previousPosition = position;
                    Log.i(TAG, "-----bnve-------- previous item:" + bind.bnve.getCurrentItem() + " current item:" + position + " ------------------");
                }

                return true;
            }
        });

        // set listener to change the current checked item of bottom nav when scroll view pager
        bind.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "-----ViewPager-------- previous item:" + bind.bnve.getCurrentItem() + " current item:" + position + " ------------------");
                if (position >= 2)// 2 is center
                    position++;// if page is 2, need set bottom item to 3, and the same to 3 -> 4
                bind.bnve.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // center item click listener
        bind.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Toast.makeText(BottomPageActivity.this, "Center", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private static class VpAdapter extends FragmentPagerAdapter {
        private List<Fragment> data;

        public VpAdapter(FragmentManager fm, List<Fragment> data) {
            super(fm);
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Fragment getItem(int position) {
            return data.get(position);
        }
    }
}