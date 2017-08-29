package com.dainv.parrotjapanese;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dainv.parrotjapanese.adapter.ListLearnAdapter;
import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.data.ButtonItem;
import com.dainv.parrotjapanese.data.LearnItem;
import com.dainv.parrotjapanese.util.TextLoader;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

/**
 * Created by dainv on 10/29/2015.
 */
public class CountItemActivity extends AppCompatActivity {

    private final static String TAG = "CountItemActivity";
    private static int current_index = 0;
    private Resources res;
    private ArrayList<LearnItem> lstNumber = null;

    private TextView tvTitle;

    private AdView adView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        res = getResources();
        current_index = (int)getIntent().getExtras().get("selected_index");
        ButtonItem selectedItem = AppData.lstCount.get(current_index);

        /* set screen title */
        tvTitle = (TextView)findViewById(R.id.txtVocabTitle);
        tvTitle.setText(selectedItem.title);

        int dataResId = res.getIdentifier(selectedItem.dataRes, "raw", getPackageName());
        lstNumber = new ArrayList<>();
        TextLoader.loadFile(this, dataResId, "~", lstNumber);

        ListView lvCount = (ListView)findViewById(R.id.listVocabulary);
        final ListLearnAdapter adapter = new ListLearnAdapter(this, lvCount.getId(), lstNumber);
        lvCount.setAdapter(adapter);

        lvCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // play sound for each item of count numbers list
                adapter.playSound(position);
            }
        });

        adView = (AdView)findViewById(R.id.adsVocabBanner);
        adView.setVisibility(View.GONE);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("1F17B575D2A0B81A953E526D33694A52")
                .build();
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                adView.setVisibility(View.GONE);
            }
        });
        adView.loadAd(adRequest);
    }

    @Override
    public void onPause() {
        if (adView != null)
            adView.pause();
        super.onPause();
    }

    @Override
    public void onResume() {
        if (adView != null)
            adView.resume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (adView != null)
            adView.destroy();
        super.onDestroy();
    }
}
