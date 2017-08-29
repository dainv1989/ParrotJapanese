package com.dainv.parrotjapanese.util;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;

import com.dainv.parrotjapanese.data.ButtonItem;
import com.dainv.parrotjapanese.data.LearnItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by dainv on 11/24/2015.
 */
public final class TextLoader {

    private final static int LEARN_NUMBER_OF_FIELDS = 3;
    private final static int COUNT_NUMBER_OF_FIELDS = 4;
    private final static int MENU_NUMBER_OF_FIELDS = 4;

    private TextLoader() {}

    /**
     * Load raw text resource file, which have multiple fields split by a character such as dot, comma ...
     * @param resId     : Text resource file ID
     * @param split     : split character
     * @param result    : Store result in a list of item
     */
    public static void loadFile(Context context, int resId, String split, List<LearnItem> result) {
        Resources resources = context.getResources();
        InputStream input = resources.openRawResource(resId);
        /**
         * open input file with UTF-8 character encoding
         * japanese cannot be saved and displayed correctly with ANSI encoding
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(input,
                Charset.forName("UTF-8")));
        try {
            String line;
            int count = 1;
            do {
                line = reader.readLine();
                if (line == null)
                    break;

                /* character # is used to start a comment line. */
                if (line.startsWith("#"))
                    continue;

                String[] strings = TextUtils.split(line, split);
                if (strings.length == LEARN_NUMBER_OF_FIELDS) {
                    result.add(new LearnItem(count + "",
                            strings[0].trim(),      // strings[0]: kanji
                            strings[1].trim(),      // strings[1]: romaji
                            strings[2].trim()));    // strings[2]: meaning
                    count++;
                } else if (strings.length == COUNT_NUMBER_OF_FIELDS) {
                    result.add(new LearnItem(
                            count + "",             // strings[0] is ignored
                            strings[1].trim(),      // kanji
                            strings[2].trim(),      // romaji
                            strings[3].trim()));    // meaning + number expression
                    count++;
                } else {
                    /* invalid format */
                }
            } while (line != null);
        }
        catch (IOException exp) {
            exp.printStackTrace();
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException exp) {
                exp.printStackTrace();
            }
        }
    }

    public static void loadMenuFile(Context context, int resId, String split, List<ButtonItem> result) {
        Resources resources = context.getResources();
        InputStream input = resources.openRawResource(resId);
        /**
         * open input file with UTF-8 character encoding
         * japanese cannot be saved and displayed correctly with ANSI encoding
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(input,
                Charset.forName("UTF-8")));
        if (reader == null)
            return;

        try {
            String line;
            do {
                line = reader.readLine();
                if (line == null)
                    break;

                /* character # is used to start a comment line. */
                if (line.startsWith("#"))
                    continue;

                String[] strings = TextUtils.split(line, split);
                if (strings.length == MENU_NUMBER_OF_FIELDS) {
                    result.add(new ButtonItem(
                            strings[0].trim(),      // title
                            strings[1].trim(),      // description
                            strings[2].trim(),      // data resource file name
                            strings[3].trim()       // photo resource file name
                    ));
                } else {
                    /* invalid format */
                }
            } while (line != null);
        }
        catch (IOException exp) {
            exp.printStackTrace();
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException exp) {
                exp.printStackTrace();
            }
        }
    }
}
