package com.dainv.parrotjapanese.data;

import android.text.TextUtils;

/**
 * Created by dainv on 10/29/2015.
 */
public class LearnItem {
    public String number;
    public String kanji;
    public String romaji;
    public String meaning;

    public LearnItem(String number,
                     String kanji,
                     String romaji,
                     String meaning) {
        this.number = number;
        this.kanji = kanji;
        this.romaji = romaji;
        this.meaning = meaning;
    }

    public LearnItem() {
        this.number = "";
        this.kanji = "";
        this.romaji = "";
        this.meaning = "";
    }

    /**
     * sound file name of sentence "ikura desuka" is: ikura_desuka
     * @return sound file name of a word or a sentence
     */
    public String getSoundFileName() {
        String soundFileName = "";
        /**
         * remove trailing spaces, ending dot characters from Romaji component
         */
        soundFileName = this.romaji.trim();
        soundFileName = soundFileName.replace("-", "");
        soundFileName = soundFileName.replace(".", "");
        soundFileName = soundFileName.replace(",", "");
        soundFileName = soundFileName.replaceAll("\\(.*\\)", "");
        soundFileName = soundFileName.trim();

        String[] words = TextUtils.split(soundFileName, " ");
        if (words.length > 1) {
            soundFileName = TextUtils.join("_", words);
        }

        return soundFileName;
    }
}
