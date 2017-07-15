package com.dainv.parrotjapanese;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StrategyActivity extends AppCompatActivity {
    private final static String TAG = "StrategyActivity";

    private String[] learnSteps;
    private String[] learnTargets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy);

        Resources res = getResources();
        int resId;
        TextView title;
        TextView desc;
        TextView number;

        learnSteps = res.getStringArray(R.array.st_steps);
        learnTargets = res.getStringArray(R.array.st_targets);

        LinearLayout steps[] = new LinearLayout[learnSteps.length];
        for (int i = 0; i < learnSteps.length; i++) {
            resId = res.getIdentifier("step" + (i+1), "id", getPackageName());
            if (resId > 0) {
                Log.v(TAG, "resID: " + resId);
                steps[i] = (LinearLayout) findViewById(resId);
                number = (TextView)steps[i].findViewById(R.id.step_number);
                desc = (TextView)steps[i].findViewById(R.id.step_desc);
                title = (TextView)steps[i].findViewById(R.id.step_title);

                number.setText((i+1) + "");
                title.setText(learnSteps[i]);
                desc.setText(learnTargets[i]);
            }
        }
    }
}
