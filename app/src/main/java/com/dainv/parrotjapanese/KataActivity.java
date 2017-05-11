package com.dainv.parrotjapanese;

import android.app.Activity;
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
public class KataActivity  extends AppCompatActivity {
    private final static String TAG = "KataActivity";
    private GridView gridViewKata;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alphabet);

        gridViewKata = (GridView)findViewById(R.id.gridAlphabet);
        final ArrayList<AlphabetItem> lstKata = new ArrayList<AlphabetItem>();
        for (int i = 0; i < AppData.katakana.length; i++) {
            lstKata.add(new AlphabetItem(AppData.katakana[i],
                                         AppData.pronun[i]));
        }

        AlphabetAdapter adapter = new AlphabetAdapter(this, gridViewKata.getId(), lstKata);
        gridViewKata.setAdapter(adapter);

        final Context context = getApplicationContext();
        gridViewKata.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /** get audio resource ID by name */
                int audioResId = context.getResources().getIdentifier("raw/" + lstKata.get(position).toString(),
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
