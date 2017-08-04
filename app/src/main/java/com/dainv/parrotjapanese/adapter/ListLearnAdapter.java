package com.dainv.parrotjapanese.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dainv.parrotjapanese.data.LearnItem;
import com.dainv.parrotjapanese.ui.ListLearnView;

import java.util.List;

/**
 * Created by dainv on 10/29/2015.
 */
public class ListLearnAdapter extends ArrayAdapter<LearnItem> {
    private List<LearnItem> mListItems;
    private Context mContext;

    public ListLearnAdapter(Context context, int resource, List<LearnItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mListItems = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListLearnView view = new ListLearnView(mContext);
        LearnItem item = mListItems.get(position);

        if (isSoundAvailable(item.getSoundFileName())) {
            view.setSpeakerVisible(View.VISIBLE);
        } else {
            view.setSpeakerVisible(View.INVISIBLE);
        }
        view.setItemList(item);
        return view;
    }

    private boolean isSoundAvailable(String soundFileName) {
        boolean isAvailble = false;
        Resources res = mContext.getResources();
        int soundId = res.getIdentifier(soundFileName, "raw", mContext.getPackageName());
        if (soundId > 0)
            isAvailble = true;

        //Log.v("ListLearnAdapter", soundFileName + ":" + isAvailble);
        return isAvailble;
    }

    public void playSound(int position) {
        LearnItem item = mListItems.get(position);
        String soundFileName;
        Resources res = mContext.getResources();
        int soundFileId = 0;

        soundFileName = item.getSoundFileName();
        if (isSoundAvailable(soundFileName)) {
            soundFileId = res.getIdentifier(soundFileName, "raw", mContext.getPackageName());
            MediaPlayer player = MediaPlayer.create(mContext, soundFileId);
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
    }
}
