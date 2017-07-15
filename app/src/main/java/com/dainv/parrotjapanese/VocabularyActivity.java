package com.dainv.parrotjapanese;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dainv.parrotjapanese.adapter.CustomListAdapter;
import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.util.TextLoader;

public class VocabularyActivity extends AppCompatActivity {

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        Resources res = getResources();
        tvTitle = (TextView)findViewById(R.id.txtVocabTitle);
        tvTitle.setText(res.getString(R.string.title_vocabulary));

        /** load vocabulary categories from file */
        if (AppData.lstVocab.isEmpty()) {
            TextLoader loader = new TextLoader(getApplicationContext());
            loader.loadMenuFile(R.raw.menu_vocabulary, "~", AppData.lstVocab);
        }

        ListView lvVocab = (ListView)findViewById(R.id.listVocabulary);
        CustomListAdapter listVocabAdapter = new CustomListAdapter(this, lvVocab.getId(), AppData.lstVocab);
        lvVocab.setAdapter(listVocabAdapter);

        lvVocab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itVocabList = new Intent(getApplicationContext(), VocabItemActivity.class);

                /* check if data resouce file is available */
                int dataResId = getResources().getIdentifier(
                        AppData.lstVocab.get(position).dataRes, "raw", getPackageName());

                if (dataResId > 0) {
                    itVocabList.putExtra("selected_index", position);
                    startActivity(itVocabList);
                } else {
                    Toast.makeText(getApplicationContext(), "Cannot find data file",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
