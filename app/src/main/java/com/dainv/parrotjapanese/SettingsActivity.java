package com.dainv.parrotjapanese;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        /* set screen title */
        Resources res = getResources();
        tvTitle = (TextView)findViewById(R.id.txtSettingTitle);
        tvTitle.setText(res.getString(R.string.title_setting));
    }
}
