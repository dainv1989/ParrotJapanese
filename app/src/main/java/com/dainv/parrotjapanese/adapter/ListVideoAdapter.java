package com.dainv.parrotjapanese.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dainv.parrotjapanese.R;
import com.dainv.parrotjapanese.data.Constant;
import com.dainv.parrotjapanese.data.VideoEntry;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dainv on 10/30/2015.
 */
public class ListVideoAdapter extends BaseAdapter {
    private List<VideoEntry> mListVideos;
    private Context mContext;
    private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> mLoaders;
    private ThumbnailListener mListener;

    public ListVideoAdapter(Context context, List<VideoEntry> entries) {
        this.mContext = context;
        this.mListVideos = entries;
        mLoaders = new HashMap<>();
        mListener = new ThumbnailListener();
    }

    public void releaseLoaders() {
        for (YouTubeThumbnailLoader loader: mLoaders.values()) {
            loader.release();
        }
    }

    @Override
    public int getCount() {
        return mListVideos.size();
    }

    @Override
    public VideoEntry getItem(int position) {
        return mListVideos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        VideoEntry entry = mListVideos.get(position);

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_video_item, null);

            YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbail);
            thumbnail.setTag(entry.videoId);
            thumbnail.initialize(Constant.DEVELOPER_KEY, mListener);
        } else {
            YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbail);
            YouTubeThumbnailLoader loader = mLoaders.get(thumbnail);
            if (loader == null) {
                thumbnail.setTag(entry.videoId);
            } else {
                thumbnail.setImageResource(R.mipmap.loading);
                loader.setVideo(entry.videoId);
            }
        }

        TextView title = (TextView)view.findViewById(R.id.videoTitle);
        TextView desc = (TextView)view.findViewById(R.id.videoDesc);
        title.setText(entry.title);
        desc.setText(entry.desc);
        return view;
    }

    private final class ThumbnailListener implements
            YouTubeThumbnailView.OnInitializedListener,
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onInitializationSuccess(YouTubeThumbnailView view,
                                            YouTubeThumbnailLoader loader) {
            loader.setOnThumbnailLoadedListener(this);
            mLoaders.put(view, loader);
            view.setImageResource(R.mipmap.loading);
            String videoID = (String)view.getTag();
            loader.setVideo(videoID);
        }

        @Override
        public void onInitializationFailure(YouTubeThumbnailView view,
                                            YouTubeInitializationResult result) {
            view.setImageResource(R.mipmap.crying);
        }

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView view, String s) {
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView view,
                                     YouTubeThumbnailLoader.ErrorReason errorReason) {
            view.setImageResource(R.mipmap.crying);
        }
    }
}
