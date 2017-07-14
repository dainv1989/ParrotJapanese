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
import com.dainv.parrotjapanese.util.TextLoader;

/**
 * Created by dainv on 10/29/2015.
 */
public class VocabItemActivity extends AppCompatActivity {

    private final static String TAG = "VocabItemActivity";
    private static int current_index = 0;

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        Resources res = getResources();
        current_index = (int)getIntent().getExtras().get("selected_index");
        ListItem selectedItem = AppData.lstVocab.get(current_index);
        int dataResId = res.getIdentifier(selectedItem.dataRes, "raw", getPackageName());

        /* set toolbar title */
        tvTitle = (TextView)findViewById(R.id.txtVocabTitle);
        tvTitle.setText(selectedItem.title);

        /** load vocabulary from raw data file */
        if (selectedItem.learnItems.isEmpty()) {
            TextLoader loader = new TextLoader(getApplicationContext());
            loader.loadFile(dataResId, "~", selectedItem.learnItems);
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
    }
}
