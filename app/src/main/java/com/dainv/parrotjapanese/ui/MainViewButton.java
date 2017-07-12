package com.dainv.parrotjapanese.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dainv.parrotjapanese.R;
import com.dainv.parrotjapanese.data.ListItem;

/**
 * Created by dainv on 10/22/2015.
 */
public class MainViewButton extends LinearLayout {
    private ImageView thumb;
    private TextView label;
    private Context context;

    public MainViewButton(Context context) {
        super(context);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.main_view_button, this);

            this.thumb = (ImageView)findViewById(R.id.MainViewButtonImg);
            this.label = (TextView)findViewById(R.id.MainViewButtonTxt);
        }
    }
    
    public void setItem(ListItem item) {
        Resources res = context.getResources();
        int photoId = res.getIdentifier(item.photoRes, "mipmap", context.getPackageName());
        Log.v("mainviewbutton", "photo " + item.photoRes.toString() + " id = " + photoId);
        this.thumb.setImageResource(photoId);
        this.label.setText(item.title);
    }
}
