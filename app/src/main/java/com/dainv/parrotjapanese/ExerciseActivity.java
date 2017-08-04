package com.dainv.parrotjapanese;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.data.ButtonItem;
import com.dainv.parrotjapanese.util.TextLoader;

public class ExerciseActivity extends AppCompatActivity {

    private TextView btnHira;
    private TextView btnKata;
    private TextView btnPronun;
    private TextView btnMean;

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        /* set screen title */
        Resources res = getResources();
        tvTitle = (TextView)findViewById(R.id.txtExerciseTitle);
        tvTitle.setText(res.getString(R.string.title_exercise));

        btnHira = (TextView)findViewById(R.id.ex_hiragana);
        btnKata = (TextView)findViewById(R.id.ex_katakana);
        btnPronun = (TextView)findViewById(R.id.ex_pronun);
        btnMean = (TextView)findViewById(R.id.ex_meaning);

        final Context context = getApplicationContext();
        btnHira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra(AppData.EXTRA_EXER_KEY,
                                AppData.EXTRA_EXER_HIRAGANA);
                startActivity(intent);
            }
        });

        btnKata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra(AppData.EXTRA_EXER_KEY,
                        AppData.EXTRA_EXER_KATAKANA);
                startActivity(intent);
            }
        });

        btnPronun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra(AppData.EXTRA_EXER_KEY,
                        AppData.EXTRA_EXER_PRONUN);
                startActivity(intent);
            }
        });

        btnMean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, QuestionActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra(AppData.EXTRA_EXER_KEY,
                        AppData.EXTRA_EXER_MEANING);
                startActivity(intent);
            }
        });

        TextLoader loader = new TextLoader(getApplicationContext());
        String pkgName = getPackageName();
        int dataResId;
        /**
         * Load vocabulary subject list if it's not existed
         * This situation occurs in case user goes to Exercise before Vocabulary screen
         * after running app
         */
        if (AppData.lstVocab.isEmpty()) {
            loader.loadMenuFile(R.raw.menu_vocabulary, "~", AppData.lstVocab);
        }

        /* Loading full vocabulary data from raw files */
        ButtonItem item = new ButtonItem();
        int totalVocab = 0;
        for (int i = 0; i < AppData.lstVocab.size(); i++) {
            item = AppData.lstVocab.get(i);
            dataResId = res.getIdentifier(item.dataRes, "raw", pkgName);
            if (item.learnItems.isEmpty() && (dataResId > 0)) {
                loader.loadFile(dataResId, "~", item.learnItems);
                totalVocab += item.learnItems.size();
            }
        }

        /**
         * add all vocabulary to a full list,
         * this list is used to build questions
         */
        if (totalVocab != AppData.lstVocabFull.size()) {
            AppData.lstVocabFull.clear();
            for (int i = 0; i < AppData.lstVocab.size(); i++) {
                item = AppData.lstVocab.get(i);
                AppData.lstVocabFull.addAll(item.learnItems);
            }
        }
    }
}
