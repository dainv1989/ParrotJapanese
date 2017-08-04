package com.dainv.parrotjapanese;

import android.app.Application;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.dainv.parrotjapanese.data.AppData;

import java.util.Locale;

/**
 * Created by dainv on 12/15/2015.
 */
public class ParrotJapaneseApp extends Application {
    private Locale locale = null;
    private Configuration config = null;

    @Override
    public void onCreate(){
        super.onCreate();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        config = new Configuration();
        String lang = settings.getString(AppData.PREFKEY_LANGUAGE, "en_US");
        if (!lang.isEmpty()) {
            locale = new Locale(lang);
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());
        }
        /**
         * initialize app data
         */
        AppData.getInstance();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if ((locale != null)) {
            newConfig.locale = locale;
            Locale.setDefault(locale);
            getBaseContext().getResources().updateConfiguration(newConfig,
                    getBaseContext().getResources().getDisplayMetrics());
        }
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}
