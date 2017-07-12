package com.dainv.parrotjapanese;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.dainv.parrotjapanese.adapter.ListVideoAdapter;
import com.dainv.parrotjapanese.data.Constant;
import com.dainv.parrotjapanese.data.ListLearnItem;
import com.dainv.parrotjapanese.data.VideoEntry;
import com.dainv.parrotjapanese.util.TextLoader;
import com.google.android.youtube.player.YouTubeApiServiceUtil;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dainv on 10/29/2015.
 */
public class GrammarActivity extends AppCompatActivity {
    /** The request code when calling startActivityForResult to recover from an API service error. */
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private ListVideoAdapter adapter;

    private List<VideoEntry> lstVideo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        /* set toolbar title */
        Resources res = getResources();
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setTitle(res.getString(R.string.str_btnGrammar));

        /** load YouTube video list from a resource file */
        if (lstVideo == null) {
            lstVideo = new ArrayList<VideoEntry>();
            final List<ListLearnItem> lstItem = new ArrayList<ListLearnItem>();
            TextLoader loader = new TextLoader(getApplicationContext());
            loader.loadFile(R.raw.grammar_videos, "~", lstItem);
            for (int i = 0; i < lstItem.size(); i++) {
                VideoEntry entry = new VideoEntry();
                entry.videoId = lstItem.get(i).kanji;
                entry.title = lstItem.get(i).romaji;
                entry.desc = lstItem.get(i).meaning;
                lstVideo.add(entry);
            }
        }

        adapter = new ListVideoAdapter(this, lstVideo);
        ListView lsVideoView = (ListView)findViewById(R.id.listVocabulary);
        lsVideoView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lsVideoView.setAdapter(adapter);
        checkYoutubeApi();

        final Activity parentActivity = this;
        lsVideoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            startActivity(YouTubeStandalonePlayer.createVideoIntent(
                    parentActivity, Constant.DEVELOPER_KEY,
                    lstVideo.get(position).videoId, 0, true, true));
            }
        });
    }

    @Override
    protected void onDestroy() {
        adapter.releaseLoaders();
        super.onDestroy();
    }

    private void checkYoutubeApi() {
        YouTubeInitializationResult errorReason =
                YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(this);
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
            String errorMsg = "There was an error initializing the YouTubePlayer";
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }
}


