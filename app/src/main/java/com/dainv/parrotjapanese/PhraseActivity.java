package com.dainv.parrotjapanese;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dainv.parrotjapanese.adapter.ListLearnAdapter;
import com.dainv.parrotjapanese.data.ListLearnItem;
import com.dainv.parrotjapanese.util.TextLoader;

import java.util.ArrayList;

/**
 * Created by dainv on 10/29/2015.
 */
public class PhraseActivity extends AppCompatActivity {

    private ArrayList<ListLearnItem> lstPhrase = null;

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        /* set screen title */
        Resources res = getResources();
        tvTitle = (TextView)findViewById(R.id.txtVocabTitle);
        tvTitle.setText(res.getString(R.string.title_phrase));

        if (lstPhrase == null) {
            lstPhrase = new ArrayList<>();
            TextLoader loader = new TextLoader(getApplicationContext());
            loader.loadFile(R.raw.simple_phrases, "~", lstPhrase);
        }

        final ListView lvPhrase = (ListView)findViewById(R.id.listVocabulary);
        final ListLearnAdapter adapter = new ListLearnAdapter(this,
                lvPhrase.getId(), lstPhrase);
        lvPhrase.setAdapter(adapter);

        // play sound for each item click
        lvPhrase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.playSound(position);
            }
        });
    }
}
