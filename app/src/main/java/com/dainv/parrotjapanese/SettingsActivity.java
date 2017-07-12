package com.dainv.parrotjapanese;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {
    private final static String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        transaction.replace(android.R.id.content, settingsFragment);
        transaction.commit();
    }

    public void setActionbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
