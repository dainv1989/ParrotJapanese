package com.dainv.parrotjapanese;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.data.Constant;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {
    private final static String TAG = "SettingFragment";

    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Load the preferences from XML resource
         */
        addPreferencesFromResource(R.xml.preferences);

        /**
         * set title for parrent activity
         * this trick is useful in case change language,
         * the actionbar title language will change correspondingly
         */
        SettingsActivity parent = (SettingsActivity)getActivity();
        String title = getResources().getString(R.string.title_activity_settings);
        parent.setActionbarTitle(title);

        /**
         * set summary for preferences
         */
        PreferenceScreen prefs = getPreferenceScreen();
        SharedPreferences settings = prefs.getSharedPreferences();
        String numberOfQa = settings.getString(Constant.KEY_PREF_QA_NUMBERS, "10");

        Preference qaNumbers = findPreference(Constant.KEY_PREF_QA_NUMBERS);
        qaNumbers.setSummary(numberOfQa + " " +
                getResources().getString(R.string.pref_qa_summary));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(Constant.KEY_PREF_LANG)) {
            String language = sharedPreferences.getString(key, "en");
            Log.v(TAG, language);

            if (language != null) {
                AppData.language = language;
                AppData.clearData();
                Locale locale = new Locale(language);
                Locale.setDefault(locale);

                ParrotJapaneseApp globalState = (ParrotJapaneseApp)
                        getActivity().getApplicationContext();
                globalState.setLocale(locale);
                globalState.onConfigurationChanged(AppData.config);

                // update current setting screen with new language
                this.onCreate(null);
            }
        }
        else if (key.equals(Constant.KEY_PREF_QA_NUMBERS)) {
            String qa = sharedPreferences.getString(key, "10");
            if (qa != null) {
                AppData.questions = Integer.parseInt(qa.trim());
                Preference qaNumbers = findPreference(Constant.KEY_PREF_QA_NUMBERS);
                qaNumbers.setSummary(AppData.questions + " " +
                        getResources().getString(R.string.pref_qa_summary));
                Log.v(TAG, "new number of questions: " + AppData.questions);
            }
        }
    }

    /**
     * Register and unregister listener for SharedPreference
     * of proper lifecycle management in the activity. Follow by Google's recommend at:
     * http://developer.android.com/guide/topics/ui/settings.html
     */
    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public  void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
