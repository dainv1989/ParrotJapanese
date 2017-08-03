package com.dainv.parrotjapanese;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.data.Constant;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by dainv on 12/24/2015.
 */
public class ScoreActivity extends AppCompatActivity {
    private static int numberOfQAs = 0;
    private static int correctAns = 0;
    private static String extra;

    private ImageView btnContinue;
    private ImageView btnHome;

    private TextView tvTitle;

    private InterstitialAd adsFullScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Resources res = getResources();
        tvTitle = (TextView)findViewById(R.id.txtScoreTitle);
        tvTitle.setText(res.getString(R.string.title_score));

        TextView tvScore = (TextView)findViewById(R.id.sc_score);
        btnContinue = (ImageView)findViewById(R.id.sc_continue);
        btnHome = (ImageView)findViewById(R.id.sc_exit);

        final Intent qaIntent = new Intent(getApplicationContext(), QuestionActivity.class);
        extra = (String)getIntent().getStringExtra(Constant.EXTRA_EXER_KEY);
        switch (extra) {
            case Constant.EXTRA_EXER_HIRAGANA:
                numberOfQAs = AppData.hiraQASummary.numQuestion;
                correctAns = AppData.hiraQASummary.numCorrectAns;

                break;
            case Constant.EXTRA_EXER_KATAKANA:
                numberOfQAs = AppData.kataQASummary.numQuestion;
                correctAns = AppData.kataQASummary.numCorrectAns;

                break;
            case Constant.EXTRA_EXER_PRONUN:
                numberOfQAs = AppData.pronunQASummary.numQuestion;
                correctAns = AppData.pronunQASummary.numCorrectAns;

                break;
            case Constant.EXTRA_EXER_MEANING:
                numberOfQAs = AppData.meaningQASummary.numQuestion;
                correctAns = AppData.meaningQASummary.numCorrectAns;

                break;
            default:
                /* do nothing here */
                break;
        }
        tvScore.setText(correctAns + "/" + numberOfQAs);

        /**
         * Continue question activitiy
         * If Continue button is clicked
         */
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numQAs = 0;
                numberOfQAs = AppData.Settings.getNumberOfQuestions();
                switch (extra) {
                    case Constant.EXTRA_EXER_HIRAGANA:
                        AppData.hiraQASummary.clear();
                        AppData.hiraQASummary.numQuestion = numQAs;

                        qaIntent.putExtra(Constant.EXTRA_EXER_KEY, Constant.EXTRA_EXER_HIRAGANA);
                        break;
                    case Constant.EXTRA_EXER_KATAKANA:
                        AppData.kataQASummary.clear();
                        AppData.kataQASummary.numQuestion = numQAs;

                        qaIntent.putExtra(Constant.EXTRA_EXER_KEY, Constant.EXTRA_EXER_KATAKANA);
                        break;
                    case Constant.EXTRA_EXER_PRONUN:
                        AppData.pronunQASummary.clear();
                        AppData.pronunQASummary.numQuestion = numQAs;

                        qaIntent.putExtra(Constant.EXTRA_EXER_KEY, Constant.EXTRA_EXER_PRONUN);
                        break;
                    case Constant.EXTRA_EXER_MEANING:
                        AppData.meaningQASummary.clear();
                        AppData.meaningQASummary.numQuestion = numQAs;

                        qaIntent.putExtra(Constant.EXTRA_EXER_KEY, Constant.EXTRA_EXER_MEANING);
                        break;
                    default:
                        break;
                }
                /* clear history and start a new task */
                qaIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                  Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                  Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(qaIntent);
            }
        });

        /**
         * Goto main activity on Exit button is clicked
         */
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (extra) {
                    case Constant.EXTRA_EXER_HIRAGANA:
                        AppData.hiraQASummary.clear();
                        break;
                    case Constant.EXTRA_EXER_KATAKANA:
                        AppData.kataQASummary.clear();
                        break;
                    case Constant.EXTRA_EXER_PRONUN:
                        AppData.pronunQASummary.clear();
                        break;
                    case Constant.EXTRA_EXER_MEANING:
                        AppData.meaningQASummary.clear();
                        break;
                    default:
                        break;
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                /* clear history stack and start a new activity */
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        adsFullScreen = new InterstitialAd(this);
        adsFullScreen.setAdUnitId(getResources().getString(R.string.fullscreen_static_ads_id));
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice("1F17B575D2A0B81A953E526D33694A52")
                .build();
        adsFullScreen.loadAd(adRequest);
        adsFullScreen.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                adsFullScreen.show();
            }
        });
    }

    /**
     * sometimes, at Score screen user presses Back button instead of "Continue" or "Exit" buttons
     * in that case, if QAsummary is not cleared when user come back will cause abnormal display QA
     * so we have to reset QAsummary here
     */
    @Override
    public void onBackPressed(){
        switch (extra) {
            case Constant.EXTRA_EXER_HIRAGANA:
                AppData.hiraQASummary.clear();
                break;
            case Constant.EXTRA_EXER_KATAKANA:
                AppData.kataQASummary.clear();
                break;
            case Constant.EXTRA_EXER_PRONUN:
                AppData.pronunQASummary.clear();
                break;
            case Constant.EXTRA_EXER_MEANING:
                AppData.meaningQASummary.clear();
                break;
            default:
                break;
        }
        super.onBackPressed();
    }
}
