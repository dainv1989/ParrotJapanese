package com.dainv.parrotjapanese;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.data.Constant;
import com.dainv.parrotjapanese.data.ListLearnItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dainv on 12/22/2015.
 */
public class QuestionActivity extends AppCompatActivity {
    private final static String TAG = "QuestionActivity";
    private final static int KANJI_TO_ROMAJI = 1;
    private final static int KANJI_TO_MEANING = 2;
    private final static int ROMAJI_TO_HIRAGANA = 3;
    private final static int HIRAGANA_TO_ROMAJI = 4;

    private AppData.Settings appSettings;
    private static int numQAs = 0;
    private static boolean isLearnBySubject = true;

    private TextView tvResult;
    private TextView tvQuestion;
    private TextView tvExplanation;
    private ListView lvAns;

    private TextView tvTitle;

    private String exercise = "";
    private static boolean isWaitingThread = false;
    private static boolean isAnswered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        /* set screen title */
        Resources res = getResources();
        tvTitle = (TextView)findViewById(R.id.txtQATitle);
        tvTitle.setText(res.getString(R.string.title_question));

        Context context = getApplicationContext();
        appSettings = new AppData.Settings(context);
        boolean isChangedSetting = false;
        if ((numQAs != appSettings.getNumberOfQuestions()) ||
             isLearnBySubject != appSettings.isLearnBySubject()){
            isChangedSetting = true;
            numQAs = appSettings.getNumberOfQuestions();
            isLearnBySubject = appSettings.isLearnBySubject();
        }

        tvResult = (TextView)findViewById(R.id.qa_result);
        tvQuestion = (TextView)findViewById(R.id.qa_question);
        tvExplanation = (TextView)findViewById(R.id.qa_explain);
        lvAns = (ListView)findViewById(R.id.qa_answer);

        exercise = this.getIntent().getStringExtra(Constant.EXTRA_EXER_KEY);
        switch (exercise) {
            case Constant.EXTRA_EXER_HIRAGANA:
                if (AppData.hiraQASummary.isEmpty() || isChangedSetting) {
                    AppData.hiraQASummary.clear();
                    AppData.hiraQASummary.numQuestion = numQAs;
                    AppData.hiraQASummary.lstQA = AppData.buildQuestions(AppData.lstHira, numQAs);
                }
                setQAOnScreen(AppData.hiraQASummary, HIRAGANA_TO_ROMAJI);
                break;
            case Constant.EXTRA_EXER_KATAKANA:
                if (AppData.kataQASummary.isEmpty() || isChangedSetting) {
                    AppData.kataQASummary.clear();
                    AppData.kataQASummary.numQuestion = numQAs;
                    AppData.kataQASummary.lstQA = AppData.buildQuestions(AppData.lstKata, numQAs);
                }
                setQAOnScreen(AppData.kataQASummary, HIRAGANA_TO_ROMAJI);
                break;
            case Constant.EXTRA_EXER_PRONUN:
                boolean isLearnBySubject = AppData.Settings.isLearnBySubject();
                if (AppData.pronunQASummary.isEmpty() || isChangedSetting) {
                    AppData.pronunQASummary.clear();
                    AppData.pronunQASummary.numQuestion = numQAs;

                    /* in case learn vocabulary subject-by-subject */
                    if (isLearnBySubject) {
                        /* select random subject from vocabulary subjects list */
                        Random random = new Random();
                        int index = random.nextInt(AppData.lstVocab.size());

                        /* then build questions from selected subject */
                        AppData.pronunQASummary.lstQA = AppData.buildQuestions(
                                AppData.lstVocab.get(index).learnItems, numQAs);
                    /* otherwise, mixed all vocabulary and build questions */
                    } else {
                        AppData.pronunQASummary.lstQA = AppData.buildQuestions(
                                AppData.lstVocabFull, numQAs);
                    }
                }
                setQAOnScreen(AppData.pronunQASummary, KANJI_TO_ROMAJI);
                break;
            case Constant.EXTRA_EXER_MEANING:
                isLearnBySubject = AppData.Settings.isLearnBySubject();
                if (AppData.meaningQASummary.isEmpty() || isChangedSetting) {
                    AppData.meaningQASummary.clear();
                    AppData.meaningQASummary.numQuestion = numQAs;

                    /* in case learn vocabulary subject-by-subject */
                    if (isLearnBySubject) {
                        /* select random subject from vocabulary subjects list */
                        Random random = new Random();
                        int index = random.nextInt(AppData.lstVocab.size());

                        /* then build questions from selected subject */
                        AppData.meaningQASummary.lstQA = AppData.buildQuestions(
                                AppData.lstVocab.get(index).learnItems, numQAs);
                    /* otherwise, mixed all vocabulary and build questions */
                    } else {
                        AppData.meaningQASummary.lstQA = AppData.buildQuestions(
                                AppData.lstVocabFull, numQAs);
                    }
                }
                setQAOnScreen(AppData.meaningQASummary, KANJI_TO_MEANING);
                break;
            default:
            /* Do nothing */
                break;
        }



        lvAns.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int index = 0;
                lvAns.setBackgroundColor(Color.TRANSPARENT);
                if (isAnswered)
                    return;

                isAnswered = true;
                switch (exercise) {
                    case Constant.EXTRA_EXER_HIRAGANA:
                        index = AppData.hiraQASummary.currentIndex;
                        if (index < AppData.hiraQASummary.lstQA.size()) {
                            AppData.QA qa = AppData.hiraQASummary.lstQA.get(index);
                            if (qa.answers[position].kanji.contentEquals(qa.correctAnswer.kanji)) {
                                /**
                                 * set blue on correct answer
                                 * set red on wrong answer
                                 * then stop for a while before goto next question
                                 */
                                view.setBackgroundResource(R.drawable.round_solid_bkg);
                                AppData.hiraQASummary.numCorrectAns++;
                            } else {
                                view.setBackgroundResource(R.drawable.round_solid_red_bkg);
                            }

                            showCorrectAnswer(qa);
                            /* show next question */
                            if (!isWaitingThread) {
                                isWaitingThread = true;
                                waitingThread(1500);
                            }
                        }
                        break;
                    case Constant.EXTRA_EXER_KATAKANA:
                        index = AppData.kataQASummary.currentIndex;
                        if (index < AppData.kataQASummary.lstQA.size()) {
                            AppData.QA qa = AppData.kataQASummary.lstQA.get(index);
                            if (qa.answers[position].kanji.contentEquals(qa.correctAnswer.kanji)) {
                                /**
                                 * set blue on correct answer
                                 * set red on wrong answer
                                 * then stop for a while before goto next question
                                 */
                                view.setBackgroundResource(R.drawable.round_solid_bkg);
                                AppData.kataQASummary.numCorrectAns++;
                            } else {
                                view.setBackgroundResource(R.drawable.round_solid_red_bkg);
                            }
                            showCorrectAnswer(qa);
                            /* show next question */
                            if (!isWaitingThread) {
                                isWaitingThread = true;
                                waitingThread(1500);
                            }
                        }
                        break;
                    case Constant.EXTRA_EXER_PRONUN:
                        index = AppData.pronunQASummary.currentIndex;
                        if (index < AppData.pronunQASummary.lstQA.size()) {
                            AppData.QA qa = AppData.pronunQASummary.lstQA.get(index);
                            String selectedAns = qa.answers[position].kanji;
                            String correctAns = qa.correctAnswer.kanji;
                            if (selectedAns.contentEquals(correctAns)) {
                                /**
                                 * set blue on correct answer
                                 * set red on wrong answer
                                 * then stop for a while before goto next question
                                 */
                                view.setBackgroundResource(R.drawable.round_solid_bkg);
                                AppData.pronunQASummary.numCorrectAns++;
                            } else {
                                view.setBackgroundResource(R.drawable.round_solid_red_bkg);
                            }
                            showCorrectAnswer(qa);
                            /* show next question */
                            if (!isWaitingThread) {
                                isWaitingThread = true;
                                waitingThread(1500);
                            }
                        }
                        break;
                    case Constant.EXTRA_EXER_MEANING:
                        index = AppData.meaningQASummary.currentIndex;
                        if (index < AppData.meaningQASummary.lstQA.size()) {
                            AppData.QA qa = AppData.meaningQASummary.lstQA.get(index);
                            String selectedAns = qa.answers[position].kanji;
                            String correctAns = qa.correctAnswer.kanji;
                            if (selectedAns.contentEquals(correctAns)) {
                                /**
                                 * set blue on correct answer
                                 * set red on wrong answer
                                 * then stop for a while before goto next question
                                 */
                                view.setBackgroundResource(R.drawable.round_solid_bkg);
                                AppData.meaningQASummary.numCorrectAns++;
                            } else {
                                view.setBackgroundResource(R.drawable.round_solid_red_bkg);
                            }
                            showCorrectAnswer(qa);
                            /* show next question */
                            if (!isWaitingThread) {
                                isWaitingThread = true;
                                waitingThread(1500);
                            }
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void waitingThread(int millisecond) {
        final int waitingTime = millisecond;
        Thread thread = new Thread() {
            @Override
            public void run() {
                // sleep 1 second
                try {
                    Thread.sleep(waitingTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int index = 0;
                        switch (exercise) {
                            case Constant.EXTRA_EXER_HIRAGANA:
                                /* show next question */
                                AppData.hiraQASummary.currentIndex++;
                                setQAOnScreen(AppData.hiraQASummary, HIRAGANA_TO_ROMAJI);

                                index = AppData.hiraQASummary.currentIndex;
                                if (index == AppData.hiraQASummary.lstQA.size()) {
                                    /* start score activity */
                                    Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                                    intent.putExtra(Constant.EXTRA_EXER_KEY, Constant.EXTRA_EXER_HIRAGANA);
                                    /* clear history and start a new task */
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                    Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                }
                                break;
                            case Constant.EXTRA_EXER_KATAKANA:
                                AppData.kataQASummary.currentIndex++;
                                setQAOnScreen(AppData.kataQASummary, HIRAGANA_TO_ROMAJI);

                                index = AppData.kataQASummary.currentIndex;
                                if (index == AppData.kataQASummary.lstQA.size()) {
                                    /* start score activity */
                                    Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                                    intent.putExtra(Constant.EXTRA_EXER_KEY, Constant.EXTRA_EXER_KATAKANA);
                                    /* clear history and start a new task */
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                    Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                }
                                break;
                            case Constant.EXTRA_EXER_MEANING:
                                AppData.meaningQASummary.currentIndex++;
                                setQAOnScreen(AppData.meaningQASummary, KANJI_TO_MEANING);

                                index = AppData.meaningQASummary.currentIndex;
                                if (index == AppData.meaningQASummary.lstQA.size()) {
                                    /* start score activity */
                                    Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                                    intent.putExtra(Constant.EXTRA_EXER_KEY, Constant.EXTRA_EXER_MEANING);
                                    /* clear history and start a new task */
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                    Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                }
                                break;
                            case Constant.EXTRA_EXER_PRONUN:
                                AppData.pronunQASummary.currentIndex++;
                                setQAOnScreen(AppData.pronunQASummary, KANJI_TO_ROMAJI);

                                index = AppData.pronunQASummary.currentIndex;
                                if (index == AppData.pronunQASummary.lstQA.size()) {
                                    /* start score activity */
                                    Intent intent = new Intent(getApplicationContext(), ScoreActivity.class);
                                    intent.putExtra(Constant.EXTRA_EXER_KEY, Constant.EXTRA_EXER_PRONUN);
                                    /* clear history and start a new task */
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                                    Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                                    Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                }
                                break;
                            default:
                                break;
                        }
                    }
                });
                isWaitingThread = false;
                isAnswered = false;
            }
        };
        thread.start();
    }

    private void setQAOnScreen(AppData.QASummary qaSummary, int exerciseType) {
        qaSummary.numQuestion = numQAs;
        ListLearnItem item;
        int index = qaSummary.currentIndex;
        if (index < qaSummary.lstQA.size()) {
            final AppData.QA qa = qaSummary.lstQA.get(index);

            /* set text for question, answer */
            List<String> answers = new ArrayList<String>();
            switch(exerciseType) {
                case KANJI_TO_MEANING:
                    tvQuestion.setText(qa.correctAnswer.kanji);
                    tvQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                    tvExplanation.setVisibility(View.VISIBLE);
                    tvExplanation.setText(qa.correctAnswer.romaji);

                    /* play sound if any on first display question */
                    playSound(qa.correctAnswer);
                    /* play sound on click */
                    tvQuestion.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            playSound(qa.correctAnswer);
                        }
                    });

                    /* set answers for listview */
                    for (int i = 0; i < Constant.NUMBER_OF_ANSWERS; i++) {
                        item = qa.answers[i];
                        answers.add(item.meaning);
                    }
                    break;
                case ROMAJI_TO_HIRAGANA:
                    tvQuestion.setText(qa.correctAnswer.kanji);
                    tvQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
                    tvExplanation.setVisibility(View.GONE);

                    /* set answers for listview */
                    for (int i = 0; i < Constant.NUMBER_OF_ANSWERS; i++) {
                        item = qa.answers[i];
                        answers.add(item.kanji);
                    }
                    break;
                case KANJI_TO_ROMAJI:
                    tvQuestion.setText(qa.correctAnswer.kanji);
                    tvQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                    tvExplanation.setVisibility(View.VISIBLE);
                    tvExplanation.setText(qa.correctAnswer.meaning);

                    /* set answers for listview */
                    for (int i = 0; i < Constant.NUMBER_OF_ANSWERS; i++) {
                        item = qa.answers[i];
                        answers.add(item.romaji);
                    }
                    break;
                case HIRAGANA_TO_ROMAJI:
                    tvQuestion.setText(qa.correctAnswer.kanji);
                    tvQuestion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);
                    tvExplanation.setVisibility(View.GONE);

                    /* set answers for listview */
                    for (int i = 0; i < Constant.NUMBER_OF_ANSWERS; i++) {
                        item = qa.answers[i];
                        answers.add(item.romaji);
                    }
                    break;
                default:
                    break;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.custom_simple_list_item, answers);
            lvAns.setAdapter(adapter);
            tvResult.setText(qaSummary.numCorrectAns + "/" +
                    qaSummary.numQuestion);
        }
    }

    /**
     * In case user select a wrong one
     * Highlight correct answer in answers list after selecting event 500ms
     * @param qa
     */
    private void showCorrectAnswer(final AppData.QA qa) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                // sleep 1 second
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < Constant.NUMBER_OF_ANSWERS; i++) {
                            if (qa.answers[i].kanji.contentEquals(qa.correctAnswer.kanji)) {
                                lvAns.getChildAt(i).setBackgroundResource(R.drawable.round_solid_bkg);
                                break;
                            }
                        }
                        playSound(qa.correctAnswer);
                    }
                });
            }
        };
        thread.start();
    }

    private void playSound(ListLearnItem item) {
        String pkgName = getPackageName();
        Resources res = this.getResources();
        int soundId = res.getIdentifier(item.getSoundFileName(), "raw", pkgName);
        if (soundId > 0) {
            MediaPlayer player = MediaPlayer.create(getApplicationContext(), soundId);
            player.start();
            /**
             * release system resource for MediaPlay object
             * after playing completed
             */
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
    }
}
