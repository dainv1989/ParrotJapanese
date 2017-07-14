package com.dainv.parrotjapanese;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.dainv.parrotjapanese.adapter.ImageAdapter;
import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.data.Constant;
import com.dainv.parrotjapanese.util.TextLoader;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences settings;
    private static String language;

    private GridView gridView;
    private ImageView btnSetting;
    private ImageView btnVote;
    private ImageView btnShare;
    private ImageView btnInfo;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        language = settings.getString(Constant.KEY_PREF_LANG, "");

        setContentView(R.layout.activity_main);

        /** load button list from menu config file */
        if (AppData.buttons.size() == 0) {
            TextLoader loader = new TextLoader(getApplicationContext());
            loader.loadMenuFile(R.raw.menu_main, "~", AppData.buttons);
        }

        gridView    = (GridView)findViewById(R.id.gridView);
        btnSetting  = (ImageView)findViewById(R.id.imgSetting);
        btnVote     = (ImageView)findViewById(R.id.imgVote);
        btnShare    = (ImageView)findViewById(R.id.imgShare);
        btnInfo     = (ImageView)findViewById(R.id.imgInfo);

        ImageAdapter adapter = new ImageAdapter(this, gridView.getId(), AppData.buttons);
        gridView.setAdapter(adapter);

        final Activity main = this;
        final Context context = this;
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                // Learning strategy
                case 0:
                    Intent itStrategy = new Intent(context,
                            StrategyActivity.class);
                    startActivity(itStrategy);
                    break;
                // Hiragana
                case 1:
                    Intent itHiragana = new Intent(context, HiraActivity.class);
                    startActivity(itHiragana);
                    break;
                // Katakana
                case 2:
                    Intent itKatakana = new Intent(context, KataActivity.class);
                    startActivity(itKatakana);
                    break;
                // Essential vocabulary
                case 3:
                    Intent itVocabulary = new Intent(context, VocabularyActivity.class);
                    startActivity(itVocabulary);
                    break;
                // Basic grammar
                case 4:
                    /** check YouTube API service */
                    YouTubeInitializationResult result = YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(context);
                    if (result == YouTubeInitializationResult.SUCCESS) {
                        Intent itGrammar = new Intent(context, GrammarActivity.class);
                        startActivity(itGrammar);
                    } else {
                        result.getErrorDialog(main, 0).show();
                    }
                    break;
                // Counters
                case 5:
                    Intent itCounting = new Intent(context, CountingActivity.class);
                    startActivity(itCounting);
                    break;
                // Simple phrases
                case 6:
                    Intent itPhrase = new Intent(context, PhraseActivity.class);
                    startActivity(itPhrase);
                    break;
                // Exercise
                case 7:
                    Intent itExercise = new Intent(context, ExerciseActivity.class);
                    startActivity(itExercise);
                    break;
                // Resources
                case 8:
                    Intent itResource = new Intent(context, ResourceActivity.class);
                    startActivity(itResource);
                    break;
                default:
                    // do nothing
                    break;
            }
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        btnVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * open Google Play market to rate app
                 * or start web browser if Google Play app is not available
                 */
                Uri uri = Uri.parse("market://details?id=" +
                        getApplicationContext().getPackageName());
                Intent itGotoMarket = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(itGotoMarket);
                }
                catch (ActivityNotFoundException e) {
                    Uri webUri = Uri.parse("http://play.google.com/store/apps/details?id=" +
                            getApplicationContext().getPackageName());
                    Intent itWebMarket = new Intent(Intent.ACTION_VIEW, webUri);
                    startActivity(itWebMarket);
                }
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String shareText = context.getResources().getString(R.string.share_content);
                String appUrl = " http://play.google.com/store/apps/details?id=" +
                        context.getPackageName();

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/html");
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareText + appUrl);

                startActivity(Intent.createChooser(shareIntent, "Share via"));
            }
        });

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AboutActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String newLanguage = prefs.getString(Constant.KEY_PREF_LANG, "");

        if (!newLanguage.isEmpty()) {
            if (!newLanguage.contentEquals(language)) {
                language = newLanguage;
                /**
                 * reload data resource file with new language setting
                 */
                AppData.buttons.clear();
                TextLoader loader = new TextLoader(getApplicationContext());
                loader.loadMenuFile(R.raw.menu_main, "~", AppData.buttons);
                /**
                 * force to update screen
                 */
                gridView = (GridView)findViewById(R.id.gridView);
                ImageAdapter adapter = new ImageAdapter(this, gridView.getId(), AppData.buttons);
                gridView.setAdapter(adapter);

                this.findViewById(android.R.id.content).invalidate();
            }
        }
    }
}
