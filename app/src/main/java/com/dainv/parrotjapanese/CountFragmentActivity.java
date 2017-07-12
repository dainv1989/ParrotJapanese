package com.dainv.parrotjapanese;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.data.ListItem;

/**
 * Created by dainv on 12/18/2015.
 */
public class CountFragmentActivity extends AppCompatActivity {
    private final static String TAG = "CountFragmentActivity";
    private static int current_index = 0;

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    private AppCompatActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_screen_slide);

        current_index = (int)getIntent().getExtras().get("selected_index");
        ListItem selectedItem = AppData.lstCount.get(current_index);

        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setTitle(selectedItem.title);

        mPager = (ViewPager)findViewById(R.id.slider);
        mPagerAdapter = new SlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        mPager.setCurrentItem(current_index);
    }

    private class SlidePagerAdapter extends FragmentStatePagerAdapter {
        public SlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            CountItemFragment itemFragment = new CountItemFragment();
            itemFragment.setIndex(position);
            Log.v(TAG, "getItem = " + position);
            return itemFragment;
        }

        @Override
        public int getCount() {
            return AppData.lstCount.size();
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            int position = mPager.getCurrentItem();
            ActionBar ab = activity.getSupportActionBar();
            if (ab != null)
                ab.setTitle(AppData.lstCount.get(position).title);
            super.finishUpdate(container);
        }
    }
}
