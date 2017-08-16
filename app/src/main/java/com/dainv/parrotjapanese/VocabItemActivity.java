package com.dainv.parrotjapanese;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dainv.parrotjapanese.adapter.ListLearnAdapter;
import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.data.ButtonItem;
import com.dainv.parrotjapanese.util.TextLoader;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by dainv on 10/29/2015.
 */
public class VocabItemActivity extends AppCompatActivity {

    private final static String TAG = "VocabItemActivity";
    private static int current_index = 0;

    private TextView tvTitle;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        current_index = getIntent().getIntExtra("selected_index", 0);
        /* validate index */
        if (current_index >= AppData.lstVocab.size())
            current_index = AppData.lstVocab.size() - 1;
        if (current_index < 0)
            current_index = 0;

        ButtonItem selectedItem = AppData.lstVocab.get(current_index);

        Resources res = getResources();
        int dataResId = res.getIdentifier(selectedItem.dataRes, "raw", getPackageName());

        if (dataResId <= 0) {
            Toast.makeText(getApplicationContext(), "Cannot find data file",
                    Toast.LENGTH_SHORT).show();
            onBackPressed();
            return;
        }

        /* set toolbar title */
        tvTitle = (TextView)findViewById(R.id.txtVocabTitle);
        tvTitle.setText(selectedItem.title);

        /* load vocabulary from raw data file */
        if (selectedItem.learnItems.isEmpty()) {
            TextLoader.loadFile(this, dataResId, "~", selectedItem.learnItems);
        }

        ListView lvVocab = (ListView)findViewById(R.id.listVocabulary);
        final ListLearnAdapter adapter = new ListLearnAdapter(this,
                lvVocab.getId(), selectedItem.learnItems);
        lvVocab.setAdapter(adapter);

        lvVocab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* play sound of clicked item */
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
