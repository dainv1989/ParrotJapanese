package com.dainv.parrotjapanese.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.util.Log;

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
    private final static String TAG = "AppData";
    private static AppData data;

    public final static String[] hiragana = {
            "あ", "い", "う", "え", "お",
            "か", "き", "く", "け", "こ",
            "さ", "し", "す", "せ", "そ",
            "た", "ち", "つ", "て", "と",
            "な", "に", "ぬ", "ね", "の",
            "は", "ひ", "ふ", "へ", "ほ",
            "ま", "み", "む", "め", "も",
            "や", "", "ゆ", "", "よ",
            "ら", "り", "る", "れ", "ろ",
            "わ", "", "", "", "を"
    };

    public final static String[] katakana = {
            "ア", "イ", "ウ", "エ", "オ",
            "カ", "キ", "ク", "ケ", "コ",
            "サ", "シ", "ス", "セ", "ソ",
            "タ", "チ", "ツ", "テ", "ト",
            "ナ", "ニ", "ヌ", "ネ", "ノ",
            "ハ", "ヒ", "フ", "ヘ", "ホ",
            "マ", "ミ", "ム", "メ", "モ",
            "ヤ", "", "ユ", "", "ヨ",
            "ラ", "リ", "ル", "レ", "ロ",
            "ワ", "", "", "", "ヲ"
    };

    public final static String[] pronun = {
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
    };

    /**
     * buttons: Variable for buttons on Main screen
     * videos: Videos list on Grammar screen
     * lstCount: List counter items of Count number screen
     * lstVocab: List vocabulary items of Vocabulary screen
     */
    public static ArrayList<ListItem> buttons;
    public static ArrayList<VideoEntry> videos;
    public static ArrayList<ListItem> lstCount;
    public static ArrayList<ListItem> lstVocab;

    /* Questions - answers screen variable */
    public static List<ListLearnItem> lstHira;
    public static List<ListLearnItem> lstKata;
    public static List<ListLearnItem> lstVocabFull;

    public static QASummary hiraQASummary;
    public static QASummary kataQASummary;
    public static QASummary pronunQASummary;
    public static QASummary meaningQASummary;

    /* application configuration varibale */
    public static Configuration config;
    public static String language = Constant.LANG_ENGLISH;
    public static int questions = 10;

    private AppData(){
        /**
         * initialize component here
         */
        buttons = new ArrayList<ListItem>();
        videos = new ArrayList<VideoEntry>();
        lstCount = new ArrayList<ListItem>();
        lstVocab = new ArrayList<ListItem>();

        lstHira = createHiraganaList();
        lstKata = createKatakanaList();
        lstVocabFull = new ArrayList<ListLearnItem>();

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

    private static List<ListLearnItem> createHiraganaList() {
        List<ListLearnItem> lstHiragana = new ArrayList<>();

        for (int i = 0; i < AppData.hiragana.length; i++) {
                /* skip empty words */
            if (AppData.hiragana[i].isEmpty())
                continue;

            ListLearnItem item = new ListLearnItem();
            item.kanji = AppData.hiragana[i];
            item.romaji = AppData.pronun[i];
            //Log.v(TAG, "add: " + item.kanji + " - " + item.romaji);

            lstHiragana.add(item);
        }
        return lstHiragana;
    }

    private static List<ListLearnItem> createKatakanaList() {
        List<ListLearnItem> lstKatakana = new ArrayList<>();

        for (int i = 0; i < AppData.katakana.length; i++) {
                /* skip empty words */
            if (AppData.hiragana[i].isEmpty())
                continue;

            ListLearnItem item = new ListLearnItem();
            item.kanji = AppData.katakana[i];
            item.romaji = AppData.pronun[i];

            lstKatakana.add(item);
        }
        return lstKatakana;
    }

    public static void clearData() {
        int i = 0;
        ListItem item = null;
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
        public ListLearnItem[] answers;
        public ListLearnItem correctAnswer;
        public QA() {
            answers = new ListLearnItem[Constant.NUMBER_OF_ANSWERS];
            correctAnswer = new ListLearnItem();
        }
    }

    /**
     * Randomize a list and return randomized list with specific number of elements
     * In case required numbers of elements is greater than source list size
     * Return multiple randomized source list
     * @param lst       : list to be randomized
     * @param number    : number of elements in return list
     * @return
     */
    public static List pickRandom(List lst, int number) {
        List copy = new LinkedList(lst);
        List ret = new LinkedList();

        while (ret.size() < number) {
            Collections.shuffle(copy);
            ret.addAll(copy);
        }

        return ret.subList(0, number);
    }

    /**
     * Build a set consist of specific numbers of question from a data source
     * @param source Data source
     * @param number Number of questions to be built
     * @return List of QA struction
     */
    public static List buildQuestions(List<ListLearnItem> source, int number) {
        List<QA> lstQA = new ArrayList<QA>(number);
        Random random = new Random();

        /* step 1: select list of answers */
        List<ListLearnItem> answers = pickRandom(source, number);

        for (int count = 0; count < number; count++) {
            QA newQA = new QA();
            newQA.correctAnswer = answers.get(count);

            /* step 2: create confusion for each answer by select 4 other selections */
            List<ListLearnItem> qa = pickRandom(source, Constant.NUMBER_OF_ANSWERS);

            /* step 3: check whether 4 other selections list contains answer or not */
            boolean isContain = false;
            ListLearnItem item1;
            ListLearnItem item2;
            for (int i = 0; i < Constant.NUMBER_OF_ANSWERS; i++) {
                item1 = qa.get(i);
                item2 = answers.get(count);
                if ((item1).isEquals(item2)) {
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
                int position = random.nextInt(Constant.NUMBER_OF_ANSWERS);
                qa.remove(position);
                qa.add(position, answers.get(count));
            }
            for (int i = 0; i < Constant.NUMBER_OF_ANSWERS; i++) {
                newQA.answers[i] = (ListLearnItem)qa.get(i);
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
            strNumQAs = prefs.getString(Constant.KEY_PREF_QA_NUMBERS, "10");
            if (!strNumQAs.isEmpty()) {
                ret = Integer.parseInt(strNumQAs);
                // Log.v(TAG, "update number of questions: " + ret);
            }
            return ret;
        }

        public static boolean isLearnBySubject() {
            boolean ret = true;
            ret = prefs.getBoolean(Constant.KEY_PREF_EXER_BY_SUBJECT, true);
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
}
