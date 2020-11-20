package com.ssso_knrdist.Samithies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ssso_knrdist.Districts.AllNotificationFragment;
import com.ssso_knrdist.Districts.DistrictFragment;
import com.ssso_knrdist.R;
import com.ssso_knrdist.Utils.PrefManager;
import com.ssso_knrdist.Utils.Urls;
import com.ssso_knrdist.Wings.WingsDataFragment;

import java.util.ArrayList;
import java.util.List;

public class SamithiesActivity extends AppCompatActivity {
    private ViewPager mPager;
    private int currentPage;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String service_id;
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_samithies);
        prefManager = new PrefManager(this);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        createViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        service_id = getIntent().getStringExtra("serviceid");
        prefManager.storeValue(Urls.service_id, service_id);
        prefManager.setServiceId(service_id);


        Log.i("serviceid", "" + service_id);
        createTabIcons();
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("District Notifications");
        //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_notifications_active_24, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("All Samities");
        //  tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_notifications_active_24, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabthree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabthree.setText("All Wings");
        //  tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_notifications_active_24, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabthree);


    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new DistrictNotifFragment(), "Tab 1");
        adapter.addFrag(new SamithiesFragment(), "Tab 2");
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


}