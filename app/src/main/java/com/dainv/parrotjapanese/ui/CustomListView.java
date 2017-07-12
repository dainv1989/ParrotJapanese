package com.dainv.parrotjapanese.ui;

import android.app.Service;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dainv.parrotjapanese.R;
import com.dainv.parrotjapanese.data.ListItem;

/**
 * Created by dainv on 10/28/2015.
 */
public class CustomListView extends LinearLayout {
    private Context mContext;
    public ImageView photo;
    public TextView title;
    public TextView desc;

    public CustomListView(Context context) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                Service.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item, this);
        this.photo = (ImageView)findViewById(R.id.itemPhoto);
        this.title = (TextView)findViewById(R.id.itemTitle);
        this.desc = (TextView)findViewById(R.id.itemDesc);
    }

    public void setItemList(ListItem item) {
        Resources res = mContext.getResources();
        int photoResId = res.getIdentifier(item.photoRes, "mipmap", mContext.getPackageName());
        if (photoResId > 0) {
            this.photo.setImageResource(photoResId);
        } else {
            this.photo.setImageResource(R.mipmap.animal_icon);
        }
        this.title.setText(item.title);
        this.desc.setText(item.desc);
    }
}
