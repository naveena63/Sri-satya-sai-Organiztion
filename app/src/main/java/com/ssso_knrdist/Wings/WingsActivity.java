package com.ssso_knrdist.Wings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.ssso_knrdist.R;
import com.ssso_knrdist.Samithies.DistrictNotifFragment;
import com.ssso_knrdist.Samithies.SamithiesActivity;
import com.ssso_knrdist.Samithies.SamithiesAdapter;
import com.ssso_knrdist.Samithies.SamithiesFragment;
import com.ssso_knrdist.Samithies.SamithiesModel;
import com.ssso_knrdist.Utils.ApiCallingFlow;
import com.ssso_knrdist.Utils.PrefManager;
import com.ssso_knrdist.Utils.Urls;

import java.util.ArrayList;
import java.util.List;

public class WingsActivity extends AppCompatActivity {
    PrefManager prefManager;
    String samithie_id,service_id;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wings);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        prefManager = new PrefManager(this);

        samithie_id = getIntent().getStringExtra("samithie_id");
        prefManager.storeValue(Urls.samithie_id, samithie_id);
        prefManager.setSamithiId(samithie_id);
        Log.i("samithieid",""+prefManager.getSamithiId());

//        service_id = getIntent().getStringExtra("service_id");
//        prefManager.storeValue(Urls.samit_serv_id,service_id);
//        prefManager.setSAMITHI_service_ID(service_id);
//Log.i("seamithiserviceid",""+prefManager.getSAMITHI_service_ID());

        createViewPager(viewPager);
        createTabIcons();
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Samithie Notifications");
        //tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_notifications_active_24, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText(" Wings");
        //  tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_baseline_notifications_active_24, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);


    }

    private void createViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new SamithiesNotifFragment(), "Tab 1");
        adapter.addFrag(new WingsDataFragment(), "Tab 2");

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