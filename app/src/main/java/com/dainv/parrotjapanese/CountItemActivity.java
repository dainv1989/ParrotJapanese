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
import com.dainv.parrotjapanese.data.ListItem;
import com.dainv.parrotjapanese.data.ListLearnItem;
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

    private TextView tvTitle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        res = getResources();
        current_index = (int)getIntent().getExtras().get("selected_index");
        ListItem selectedItem = AppData.lstCount.get(current_index);

        /* set toolbar title */
        tvTitle = (TextView)findViewById(R.id.txtVocabTitle);
        tvTitle.setText(selectedItem.title);

        int dataResId = res.getIdentifier(selectedItem.dataRes, "raw", getPackageName());
        lstNumber = new ArrayList<>();
        TextLoader loader = new TextLoader(this);
        loader.loadFile(dataResId, "~", lstNumber);

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
    }
}
