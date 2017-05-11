package com.dainv.parrotjapanese;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.dainv.parrotjapanese.adapter.CountItemAdapter;
import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.data.ListItem;
import com.dainv.parrotjapanese.data.ListLearnItem;
import com.dainv.parrotjapanese.util.TextLoader;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountItemFragment extends Fragment {
    private static final String TAG = "CountItemFragment";
    private int index = 0;
    private ListView lvCount;
    AppCompatActivity activity;

    public CountItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(
            R.layout.activity_vocabulary, container, false);

        ListItem selectedItem = AppData.lstCount.get(index);
        activity = (AppCompatActivity)getActivity();
        Context context = activity.getApplicationContext();

        lvCount = (ListView) rootView.findViewById(R.id.listVocabulary);
        int dataResId = activity.getResources().getIdentifier(
                selectedItem.dataRes, "raw", activity.getPackageName());
        // Log.v(TAG, dataRes);
        if (dataResId > 0) {
            if (selectedItem.learnItems.isEmpty()) {
                TextLoader loader = new TextLoader(context);
                loader.loadFile(dataResId, "~", selectedItem.learnItems);
            }

            final CountItemAdapter adapter = new CountItemAdapter(
                    context,
                    lvCount.getId(),
                    selectedItem.learnItems);
            lvCount.setAdapter(adapter);

            lvCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent,
                                        View view, int position, long id) {
                    // play sound for each item of count numbers list
                    adapter.playSound(position);
                }
            });
        }
        return rootView;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
