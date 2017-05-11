package com.dainv.parrotjapanese.util;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import com.dainv.parrotjapanese.data.ListItem;
import com.dainv.parrotjapanese.data.ListLearnItem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by dainv on 11/24/2015.
 */
public class TextLoader {
    private final static String TAG = "TextLoader";
    private final static int LEARN_NUMBER_OF_FIELDS = 3;
    private final static int COUNT_NUMBER_OF_FIELDS = 4;
    private final static int MENU_NUMBER_OF_FIELDS = 4;
    private Context mContext;

    public TextLoader(Context context) {
        this.mContext = context;
    }

    /**
     * Load raw text resource file, which have multiple fields split by a character such as dot, comma ...
     * @param resId     : Text resource file ID
     * @param split     : split character
     * @param result    : Store result in a list of item
     */
    public void loadFile(int resId, String split, List<ListLearnItem> result) {
        Resources resources = mContext.getResources();
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
            while ((line = reader.readLine()) != null) {
                /**
                 * Character # is used to start comment line.
                 */
                if (line.startsWith("#"))
                    continue;

                // Log.v(TAG, line);
                String[] strings = TextUtils.split(line, split);
                if (strings.length == LEARN_NUMBER_OF_FIELDS) {
                    result.add(new ListLearnItem(count + "",
                            strings[0].trim(),      // strings[0]: kanji
                            strings[1].trim(),      // strings[1]: romaji
                            strings[2].trim()));    // strings[2]: meaning
                    count++;
                } else if (strings.length == COUNT_NUMBER_OF_FIELDS) {
                    result.add(new ListLearnItem(
                            strings[0].trim(),      // specific count number
                            strings[1].trim(),      // kanji
                            strings[2].trim(),      // romaji
                            strings[3].trim()));    // meaning
                    count++;
                } else {
                    Log.v(TAG, "INVALID LINE: " + line);
                }
            }
        }
        catch (IOException exp) {
            // TODO: display pop-up error for user
            Log.v(TAG, exp.toString());
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException exp) {
                // TODO: display pop-up error for user
                Log.v(TAG, exp.toString());
            }
        }
    }

    public void loadMenuFile(int resId, String split, List<ListItem> result) {
        Resources resources = mContext.getResources();
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
            while ((line = reader.readLine()) != null) {
                /**
                 * Character # is used to start comment line.
                 */
                if (line.startsWith("#"))
                    continue;

                // Log.v(TAG, line);
                String[] strings = TextUtils.split(line, split);
                if (strings.length == MENU_NUMBER_OF_FIELDS) {
                    result.add(new ListItem(
                            strings[0].trim(),      // title
                            strings[1].trim(),      // description
                            strings[2].trim(),      // data resource file name
                            strings[3].trim()       // photo resource file name
                    ));
                    count++;
                } else {
                    Log.v(TAG, "INVALID LINE: " + line);
                }
            }
        }
        catch (IOException exp) {
            // TODO: display pop-up error for user
            Log.v(TAG, exp.toString());
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException exp) {
                // TODO: display pop-up error for user
                Log.v(TAG, exp.toString());
            }
        }
    }
}
