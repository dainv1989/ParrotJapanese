package com.dainv.parrotjapanese;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.dainv.parrotjapanese.adapter.AlphabetAdapter;
import com.dainv.parrotjapanese.data.AlphabetItem;
import com.dainv.parrotjapanese.data.AppData;

import java.util.ArrayList;

/**
 * Created by dainv on 10/26/2015.
 */
public class HiraActivity extends AppCompatActivity {
    private static final String TAG="HiraActivity";
    private GridView gridViewHira;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        gridViewHira = (GridView)findViewById(R.id.gridAlphabet);
        final ArrayList<AlphabetItem> lstHira = new ArrayList<AlphabetItem>();
        for (int i = 0; i < AppData.hiragana.length; i++) {
            lstHira.add(new AlphabetItem(AppData.hiragana[i], AppData.pronun[i]));
        }

        AlphabetAdapter adapter = new AlphabetAdapter(this, gridViewHira.getId(), lstHira);
        gridViewHira.setAdapter(adapter);

        final Context context = getApplicationContext();
        gridViewHira.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** get audio resource ID by name */
                int audioResId = context.getResources().getIdentifier("raw/" + lstHira.get(position).toString(),
                        "raw", getPackageName());

                /** play audio file if audio resource is available */
                if (audioResId > 0) {
                    try {
                        MediaPlayer player = MediaPlayer.create(context, audioResId);
                        player.start();
                        /**
                         * release system resource for MediaPlay object
                         * after playing completed
                         */
                        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mp.release();
                                mp = null;
                            }
                        });
                    }
                    catch (IllegalStateException exp) {
                        Log.v(TAG, exp.toString());
                    }
                }
            }
        });
    }
}
