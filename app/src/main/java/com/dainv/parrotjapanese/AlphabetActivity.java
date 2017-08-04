package com.dainv.parrotjapanese;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.dainv.parrotjapanese.adapter.AlphabetAdapter;
import com.dainv.parrotjapanese.data.AlphabetItem;
import com.dainv.parrotjapanese.data.AppData;

import java.util.ArrayList;

/**
 * Created by dainv on 10/26/2015.
 */
public class AlphabetActivity extends AppCompatActivity {

    private GridView gridAlphabet;

    private TextView txtChartTitle;

    ArrayList<AlphabetItem> lstAlphabet = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        gridAlphabet    = (GridView)findViewById(R.id.gridAlphabet);
        txtChartTitle   = (TextView)findViewById(R.id.txtChartTitle);

        int chart_type  = getIntent().getIntExtra(
                AppData.CHART_TYPE,
                AppData.CHART_HIRAGANA);

        if (lstAlphabet == null) {
            lstAlphabet = new ArrayList<>();
        } else {
            lstAlphabet.clear();
        }

        int i;
        int size = AppData.getHiragana().length;

        if (chart_type == AppData.CHART_KATAKANA) {
            txtChartTitle.setText("katakana");

            for (i = 0; i < size; i++) {
                lstAlphabet.add(new AlphabetItem(
                        AppData.getKatakana()[i],
                        AppData.getFurigana()[i]));
            }
        } else {
            txtChartTitle.setText("hiragana");

            for (i = 0; i < size; i++) {
                lstAlphabet.add(new AlphabetItem(
                        AppData.getHiragana()[i],
                        AppData.getFurigana()[i]));
            }
        }

        AlphabetAdapter adapter = new AlphabetAdapter(this, gridAlphabet.getId(), lstAlphabet);
        gridAlphabet.setAdapter(adapter);

        final Context context = this;
        gridAlphabet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AppData.playSound(lstAlphabet.get(position).toString(), context);
            }
        });
    }
}
