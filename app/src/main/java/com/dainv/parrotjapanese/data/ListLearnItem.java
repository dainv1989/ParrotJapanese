package com.dainv.parrotjapanese.data;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by dainv on 10/29/2015.
 */
public class ListLearnItem {
    public String number;
    public String kanji;
    public String romaji;
    public String meaning;

    public ListLearnItem (String number,
                          String kanji,
                          String romaji,
                          String meaning) {
        this.number = number;
        this.kanji = kanji;
        this.romaji = romaji;
        this.meaning = meaning;
    }

    public ListLearnItem() {
        this.number = "";
        this.kanji = "";
        this.romaji = "";
        this.meaning = "";
    }

    /* copy constructor */
    public ListLearnItem(ListLearnItem item) {
        this.number = item.number;
        this.kanji = item.kanji;
        this.romaji = item.romaji;
        this.meaning = item.meaning;
    }

    public boolean isEquals(ListLearnItem item) {
        boolean ret = true;
        if (!this.kanji.contentEquals(item.kanji))
            ret = false;
        if (!this.romaji.contentEquals(item.romaji))
            ret = false;
        if (!this.meaning.contentEquals(item.meaning))
            ret = false;
        return ret;
    }

    /**
     * sound file name of sentence "ikura desuka" is: ikura_desuka
     * @return sound file name of a word or a sentence
     */
    public String getSoundFileName() {
        // Log.v(getClass().getSimpleName(), this.romaji + "|");
        String soundFileName = "";
        /**
         * remove trailing spaces, ending dot characters from Romaji component
         */
        soundFileName = this.romaji.trim();
        soundFileName = soundFileName.replace("-", "");
        soundFileName = soundFileName.replace(".", "");
        soundFileName = soundFileName.replaceAll("\\(.*\\)", "");
        soundFileName = soundFileName.trim();

        String[] words = TextUtils.split(soundFileName, " ");
        if (words.length > 1) {
            soundFileName = TextUtils.join("_", words);
        }

        // Log.v(getClass().getSimpleName(), soundFileName + "|");
        return soundFileName;
    }
}
