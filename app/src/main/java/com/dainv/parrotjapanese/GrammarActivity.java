package com.dainv.parrotjapanese;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dainv.parrotjapanese.adapter.ListVideoAdapter;
import com.dainv.parrotjapanese.data.AppData;
import com.dainv.parrotjapanese.data.LearnItem;
import com.dainv.parrotjapanese.data.VideoEntry;
import com.dainv.parrotjapanese.util.TextLoader;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
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

    private TextView tvTitle;

    private InterstitialAd fullScrVideosAds;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        /* set screen title */
        Resources res = getResources();
        tvTitle = (TextView)findViewById(R.id.txtVocabTitle);
        tvTitle.setText(res.getString(R.string.title_grammar));

        adView = (AdView)findViewById(R.id.adsVocabBanner);
        adView.setVisibility(View.GONE);

        /* ads implementation start */
        fullScrVideosAds = new InterstitialAd(this);
        fullScrVideosAds.setAdUnitId(res.getString(R.string.fullscreen_video_ads_id));
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        fullScrVideosAds.loadAd(adRequest);
        /* ads implementation end */

        /** load YouTube video list from a resource file */
        if (lstVideo == null) {
            lstVideo = new ArrayList<>();
            final List<LearnItem> lstItem = new ArrayList<LearnItem>();
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

        adapter = new ListVideoAdapter(getApplicationContext(), lstVideo);
        ListView lsVideoView = (ListView)findViewById(R.id.listVocabulary);
        lsVideoView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lsVideoView.setAdapter(adapter);
        checkYoutubeApi();

         lsVideoView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int video_index = position;

                fullScrVideosAds.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        /* load the next ads content */
                        fullScrVideosAds.loadAd(new AdRequest.Builder()
                                .build());
                        startYoutubePlayer(video_index);
                    }
                });

                if(fullScrVideosAds.isLoaded())
                    fullScrVideosAds.show();
                else
                    startYoutubePlayer(video_index);
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
            String errorMsg = "There was an error initializing the YouTube Player";
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @param position      video index in video list
     */
    private void startYoutubePlayer(int position) {
        Intent intent = YouTubeStandalonePlayer.createVideoIntent(
                this, AppData.DEVELOPER_KEY,
                lstVideo.get(position).videoId, 0, true, true);
        startActivity(intent);
    }
}