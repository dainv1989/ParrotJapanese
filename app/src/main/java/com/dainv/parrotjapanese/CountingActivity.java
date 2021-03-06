package com.dainv.parrotjapanese;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dainv.parrotjapanese.adapter.ButtonAdapter;
import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.util.TextLoader;
import com.google.android.gms.ads.AdView;

/**
 * Created by dainv on 10/28/2015.
 */
public class CountingActivity extends AppCompatActivity {

    private final String TAG = "CountingActivity";

    private TextView tvTitle;

    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        /* set screen title */
        Resources res = getResources();
        tvTitle = (TextView)findViewById(R.id.txtVocabTitle);
        tvTitle.setText(res.getString(R.string.title_count));

        adView = (AdView)findViewById(R.id.adsVocabBanner);
        adView.setVisibility(View.GONE);

        /* load counter categories from menu resource file */
        if (AppData.lstCount.isEmpty()) {
            TextLoader.loadMenuFile(this, R.raw.menu_counter, "~", AppData.lstCount);
        }

        ListView lvCount = (ListView)findViewById(R.id.listVocabulary);
        ButtonAdapter listCountAdapter = new ButtonAdapter(this,
                lvCount.getId(), AppData.lstCount);
        lvCount.setAdapter(listCountAdapter);

        lvCount.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itCountItemAct = new Intent(getApplicationContext(),
                        CountItemActivity.class);

                /* check if data resouce file is available */
                String srcFileName = AppData.lstCount.get(position).dataRes;
                int dataResId = getResources().getIdentifier(
                        srcFileName, "raw", getPackageName());

                /**
                 * put current selected index as extra for new Intent
                 * this index is used to access information from AppData.lstCount array
                 */
                if (dataResId > 0) {
                    itCountItemAct.putExtra("selected_index", position);
                    Log.v(TAG, "selected_index = " + position);

                    startActivity(itCountItemAct);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot find data file",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
