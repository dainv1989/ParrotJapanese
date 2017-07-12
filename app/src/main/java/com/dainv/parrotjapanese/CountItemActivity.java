package com.dainv.parrotjapanese;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dainv.parrotjapanese.adapter.CountItemAdapter;
import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.data.ListItem;
import com.dainv.parrotjapanese.data.ListLearnItem;
import com.dainv.parrotjapanese.util.OnSwipeTouchListener;
import com.dainv.parrotjapanese.util.TextLoader;

import java.util.ArrayList;

/**
 * Created by dainv on 10/29/2015.
 */
public class CountItemActivity extends AppCompatActivity {
    private final static String TAG = "CountItemActivity";
    private static int current_index = 0;
    private Resources res;
    private ArrayList<ListLearnItem> lstNumber = null;
    private Activity activity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_vocabulary);

        /* set toolbar title */
        res = getResources();
        current_index = (int)getIntent().getExtras().get("selected_index");
        ListItem selectedItem = AppData.lstCount.get(current_index);

        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setTitle(selectedItem.title);

        // Log.v(TAG, dataRes);
        int dataResId = res.getIdentifier(selectedItem.dataRes, "raw", getPackageName());
        lstNumber = new ArrayList<ListLearnItem>();
        TextLoader loader = new TextLoader(getApplicationContext());
        loader.loadFile(dataResId, "~", lstNumber);

        ListView lvCount = (ListView)findViewById(R.id.listVocabulary);
        final CountItemAdapter adapter = new CountItemAdapter(this, lvCount.getId(),
                lstNumber);
        lvCount.setAdapter(adapter);

        lvCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // play sound for each item of count numbers list
                adapter.playSound(position);
            }
        });

        /**
         * Handle swipe left/right event
         * swipe left: go to previous count number type item in list
         * swipe right: go to next count number type item in list
         */
        final Activity activity = this;
        lvCount.setOnTouchListener(new OnSwipeTouchListener(activity) {
            @Override
            public void onSwipeRight() {
                //Log.v(TAG, "onSwipeRight, index = " + current_index);
                if (current_index > 0) {
                    current_index--;
                    ((CountItemActivity)activity).loadData(current_index);
                    activity.findViewById(android.R.id.content).invalidate();
                }
            }

            @Override
            public void onSwipeLeft() {
                //Log.v(TAG, "onSwipeLeft, index = " + current_index);
                if (current_index < AppData.lstCount.size() - 1) {
                    current_index++;
                    ((CountItemActivity)activity).loadData(current_index);
                    activity.findViewById(android.R.id.content).invalidate();
                }
            }
        });
    }

    /**
     * Load data from file based on index of AppData.lstCount list
     * @param index
     */
    private void loadData(int index) {
        if (index >= AppData.lstCount.size()
                || index < 0) {
            Log.v(TAG, "invalid index");
            return;
        }

        ListItem selectedItem = AppData.lstCount.get(index);
        /* set Toolbar title with new data */
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setTitle(selectedItem.title);

        // Log.v(TAG, dataRes);
        int dataResId = res.getIdentifier(selectedItem.dataRes, "raw", getPackageName());
        if (dataResId > 0) {
                /* clear current count number list and refill with new data */
            lstNumber.clear();
            TextLoader loader = new TextLoader(getApplicationContext());
            loader.loadFile(dataResId, "~", lstNumber);

            ListView lvCount = (ListView) findViewById(R.id.listVocabulary);
            final CountItemAdapter adapter = new CountItemAdapter(this, lvCount.getId(),
                    lstNumber);
            lvCount.setAdapter(adapter);
        } else {
            Toast.makeText(getApplicationContext(), "Cannot found data file",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
