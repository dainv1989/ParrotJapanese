package com.dainv.parrotjapanese.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by dainv on 12/4/2015.
 *
 * Description: Global data class implemented in Singleton style
 *              This class provides universal data for all activities
 *
 */
public class AppData {
    private static AppData data;

    /* Youtube API developer key */
    public static final String DEVELOPER_KEY = "AlzaSyc5lanwybSDZnEtlvM0sZdNDR8jk8hCqcM";

    /* language */
    /* public static final String LANG_VIETNAMESE = "vi"; */
    private static final String LANG_ENGLISH = "en";

    /* preference keys */
    public static final String PREFKEY_LANGUAGE = "prefkey_language";
    public static final String PREFKEY_QUESTION_COUNT = "prefkey_question_count";
    public static final String PREFKEY_SUBJECT_CHOICE = "prefkey_subject_choice";

    public static final String EXTRA_EXER_KEY = "exercise";
    public static final String EXTRA_EXER_HIRAGANA = "hiragana";
    public static final String EXTRA_EXER_KATAKANA = "katakana";
    public static final String EXTRA_EXER_PRONUN = "pronunciation";
    public static final String EXTRA_EXER_MEANING = "meaning";

    public static final String CHART_TYPE = "CHART_TYPE";
    public static final int CHART_HIRAGANA = 1;
    public static final int CHART_KATAKANA = 2;

    public final static int NUMBER_OF_ANSWERS = 4;

    private final static String[] hiragana = {
            "あ", "い", "う", "え", "お",
            "か", "き", "く", "け", "こ",
            "さ", "し", "す", "せ", "そ",
            "た", "ち", "つ", "て", "と",
            "な", "に", "ぬ", "ね", "の",
            "は", "ひ", "ふ", "へ", "ほ",
            "ま", "み", "む", "め", "も",
            "や", "", "ゆ", "", "よ",
            "ら", "り", "る", "れ", "ろ",
            "わ", "", "", "", "を",
            "ん"
    };

    private final static String[] katakana = {
            "ア", "イ", "ウ", "エ", "オ",
            "カ", "キ", "ク", "ケ", "コ",
            "サ", "シ", "ス", "セ", "ソ",
            "タ", "チ", "ツ", "テ", "ト",
            "ナ", "ニ", "ヌ", "ネ", "ノ",
            "ハ", "ヒ", "フ", "ヘ", "ホ",
            "マ", "ミ", "ム", "メ", "モ",
            "ヤ", "", "ユ", "", "ヨ",
            "ラ", "リ", "ル", "レ", "ロ",
            "ワ", "", "", "", "ヲ",
            "ン"
    };

    private final static String[] furigana = {
            "a", "i", "u", "e", "o",
            "ka", "ki", "ku", "ke", "ko",
            "sa", "shi", "su", "se", "so",
            "ta", "chi", "tsu", "te", "to",
            "na", "ni", "nu", "ne", "no",
            "ha", "hi", "fu", "he", "ho",
            "ma", "mi", "mu", "me", "mo",
            "ya", "", "yu", "", "yo",
            "ra", "ri", "ru", "re", "ro",
            "wa", "", "", "", "wo",
            "n"
    };

    /**
     * buttons: Variable for buttons on Main screen
     * videos: Videos list on Grammar screen
     * lstCount: List counter items of Count number screen
     * lstVocab: List vocabulary items of Vocabulary screen
     */
    public static ArrayList<ButtonItem> buttons;
    public static ArrayList<VideoEntry> videos;
    public static ArrayList<ButtonItem> lstCount;
    public static ArrayList<ButtonItem> lstVocab;

    /* Questions - answers screen variable */
    public static List<LearnItem> lstHira;
    public static List<LearnItem> lstKata;
    public static List<LearnItem> lstVocabFull;

    public static QASummary hiraQASummary;
    public static QASummary kataQASummary;
    public static QASummary pronunQASummary;
    public static QASummary meaningQASummary;

    /* application configuration varibale */
    public static Configuration config;
    public static String language = LANG_ENGLISH;
    public static int questions = 10;

    private AppData(){
        /**
         * initialize component here
         */
        buttons = new ArrayList<>();
        videos = new ArrayList<>();
        lstCount = new ArrayList<>();
        lstVocab = new ArrayList<>();

        lstHira = createHiraganaList();
        lstKata = createKatakanaList();
        lstVocabFull = new ArrayList<>();

        hiraQASummary = new QASummary();
        kataQASummary = new QASummary();
        pronunQASummary = new QASummary();
        meaningQASummary = new QASummary();

        config = new Configuration();
    }

    public static AppData getInstance() {
        if (data == null) {
            data = new AppData();
        }
        return data;
    }

    public static String[] getHiragana() {
        return hiragana;
    }

    public static String[] getKatakana() {
        return katakana;
    }

    public static String[] getFurigana() {
        return furigana;
    }

    private static List<LearnItem> createHiraganaList() {
        List<LearnItem> lstHiragana = new ArrayList<>();

        for (int i = 0; i < AppData.hiragana.length; i++) {
                /* skip empty words */
            if (AppData.hiragana[i].isEmpty())
                continue;

            LearnItem item = new LearnItem();
            item.kanji = AppData.hiragana[i];
            item.romaji = AppData.furigana[i];
            //Log.v(TAG, "add: " + item.kanji + " - " + item.romaji);

            lstHiragana.add(item);
        }
        return lstHiragana;
    }

    private static List<LearnItem> createKatakanaList() {
        List<LearnItem> lstKatakana = new ArrayList<>();

        for (int i = 0; i < AppData.katakana.length; i++) {
                /* skip empty words */
            if (AppData.hiragana[i].isEmpty())
                continue;

            LearnItem item = new LearnItem();
            item.kanji = AppData.katakana[i];
            item.romaji = AppData.furigana[i];

            lstKatakana.add(item);
        }
        return lstKatakana;
    }

    public static void clearData() {
        int i;
        ButtonItem item;
        for (i = 0; i < lstVocab.size(); i++) {
            item = lstVocab.get(i);
            item.learnItems.clear();
        }
        lstVocab.clear();

        for (i = 0; i < lstCount.size(); i++) {
            item = lstCount.get(i);
            item.learnItems.clear();
        }
        lstCount.clear();

        for (i = 0; i < buttons.size(); i++) {
            item = buttons.get(i);
            item.learnItems.clear();
        }
        buttons.clear();

        hiraQASummary.clear();
        kataQASummary.clear();
        pronunQASummary.clear();
        meaningQASummary.clear();
        lstVocabFull.clear();
    }

    /**
     * Question - Answer structure
     */
    public static class QA {
        public LearnItem[] answers;
        public LearnItem correctAnswer;
        public QA() {
            answers = new LearnItem[AppData.NUMBER_OF_ANSWERS];
            correctAnswer = new LearnItem();
        }
    }

    /**
     * Randomize a list and return randomized list with specific number of elements
     * In case required numbers of elements is greater than source list size
     * Return multiple randomized source list
     * @param list      : list to be randomized
     * @param number    : number of elements in return list
     * @return
     */
    public static List pickRandom(List list, int number) {
        List copy = new LinkedList(list);
        List ret = new LinkedList();

        while (ret.size() < number) {
            Collections.shuffle(copy);
            ret.addAll(copy);
        }

        return ret.subList(0, number);
    }

    public static List generateQuestion(List<LearnItem> sources, int count) {
        List<Question> lstQuestions = new ArrayList<>(count);
        List<LearnItem> lstConfusions;
        LearnItem correctAnswer;
        int correct_index;

        /* select list of answers first to prevent 2 questions has the same correct answer */
        List<LearnItem> lstAnswers = pickRandom(sources, count);

        for(int i = 0; i < count; i++) {
            Question question = new Question();
            correctAnswer = lstAnswers.get(i);

            lstConfusions = pickRandom(sources, NUMBER_OF_ANSWERS);

            /* replace 1st item by answer if confusion list does not contains answer */
            if (!lstConfusions.contains(correctAnswer))
                lstConfusions.set(0, correctAnswer);

            Collections.shuffle(lstConfusions);
            question.setAnswers(lstConfusions);

            correct_index = lstConfusions.indexOf(correctAnswer);
            question.setCorrectAnswerIndex(correct_index);

            lstQuestions.add(question);
        }

        return lstQuestions;
    }

    /**
     * Build a set consist of specific numbers of question from a data source
     * @param source Data source
     * @param number Number of questions to be built
     * @return List of QA struction
     */
    public static List buildQuestions(List<LearnItem> source, int number) {
        List<QA> lstQA = new ArrayList<>(number);
        Random random = new Random();

        /* step 1: select list of answers */
        List<LearnItem> answers = pickRandom(source, number);

        for (int count = 0; count < number; count++) {
            QA newQA = new QA();
            newQA.correctAnswer = answers.get(count);

            /* step 2: create confusion for each answer by select 4 other selections */
            List<LearnItem> qa = pickRandom(source, AppData.NUMBER_OF_ANSWERS);

            /* step 3: check whether 4 other selections list contains answer or not */
            boolean isContain = false;
            LearnItem item1;
            LearnItem item2;
            for (int i = 0; i < AppData.NUMBER_OF_ANSWERS; i++) {
                item1 = qa.get(i);
                item2 = answers.get(count);
                if ((item1).equals(item2)) {
                    isContain = true;
                    break;
                }
            }

            /**
             * step 4:
             * if 4 other selections list contains answer
             * -> set these selections to 4 answers directly
             */
            if (isContain) {
                /* do nothing here */
                // Log.v(TAG, "item " + count + " is contain");
            }
            /**
             * if not
             * pick a random position in 4 selections
             * replace it with the correct answer
             */
            else {
                int position = random.nextInt(AppData.NUMBER_OF_ANSWERS);
                qa.remove(position);
                qa.add(position, answers.get(count));
            }
            for (int i = 0; i < AppData.NUMBER_OF_ANSWERS; i++) {
                newQA.answers[i] = (LearnItem)qa.get(i);
            }
            lstQA.add(newQA);
        }
        return lstQA;
    }

    /**
     * Handle application preferences class
     */
    public static class Settings {
        private final static String TAG = "AppSettings";
        private Context context;
        private static SharedPreferences prefs;
        //private static int numberOfQuestions = 0;
        //private static boolean isLearnBySubject = false;

        public Settings(Context context) {
            this.context = context;
            prefs = PreferenceManager.getDefaultSharedPreferences(context);
        }

        public static int getNumberOfQuestions() {
            int ret = 0;
            String strNumQAs = "";
            strNumQAs = prefs.getString(AppData.PREFKEY_QUESTION_COUNT, "10");
            if (!strNumQAs.isEmpty()) {
                ret = Integer.parseInt(strNumQAs);
                // Log.v(TAG, "update number of questions: " + ret);
            }
            return ret;
        }

        public static boolean isLearnBySubject() {
            boolean ret = true;
            ret = prefs.getBoolean(AppData.PREFKEY_SUBJECT_CHOICE, true);
            return ret;
        }
    }

    public static class QASummary {
        public int numQuestion = 0;
        public int numCorrectAns = 0;
        public int currentIndex = 0;
        public List<QA> lstQA = null;

        public QASummary() {
            clear();
            lstQA = new ArrayList<QA>();
        }

        public void clear() {
            numQuestion = 0;
            numCorrectAns = 0;
            currentIndex = 0;
            if (lstQA != null)
                lstQA.clear();
        }

        public boolean isEmpty() {
            return lstQA.isEmpty();
        }
    }

    public static void playSound(String fileName, Context context) {
        Resources resources = context.getResources();
        MediaPlayer player;

        /** get audio resource ID by name */
        int audioResId = resources.getIdentifier(
                "raw/" + fileName,
                "raw" ,
                context.getPackageName());

        if (audioResId <= 0)
            return;

        try {
            player = MediaPlayer.create(context, audioResId);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            player.start();

            /* release media player after finishing */
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        } catch (IllegalStateException exp) {
            exp.printStackTrace();
        }
    }
}
