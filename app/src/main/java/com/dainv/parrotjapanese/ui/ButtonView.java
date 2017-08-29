package com.dainv.parrotjapanese.ui;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dainv.parrotjapanese.R;
import com.dainv.parrotjapanese.data.ButtonItem;

/**
 * Created by dainv on 10/22/2015.
 */
public class ButtonView extends LinearLayout {
    private ImageView thumb;
    private TextView label;
    private Context context;

    public ButtonView(Context context) {
        super(context);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (inflater != null) {
            inflater.inflate(R.layout.main_view_button, this);

            this.thumb = (ImageView)findViewById(R.id.MainViewButtonImg);
            this.label = (TextView)findViewById(R.id.MainViewButtonTxt);
        }
    }
    
    public void setItem(ButtonItem item) {
        Resources res = context.getResources();
        int photoId = res.getIdentifier(item.photoRes, "mipmap", context.getPackageName());
        Log.v("mainviewbutton", "photo " + item.photoRes.toString() + " id = " + photoId);
        this.thumb.setImageResource(photoId);
        this.label.setText(item.title);
    }
}
