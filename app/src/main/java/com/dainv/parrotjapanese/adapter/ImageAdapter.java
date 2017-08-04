package com.dainv.parrotjapanese.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dainv.parrotjapanese.data.ButtonItem;
import com.dainv.parrotjapanese.ui.ButtonView;

import java.util.List;

/**
 * Created by dainv on 10/23/2015.
 */
public class ImageAdapter extends ArrayAdapter<ButtonItem> {
    private Context mContext;
    private List<ButtonItem> mItems;

    public View getView(int position, View convertView, ViewGroup parent) {
        ButtonView button = new ButtonView(mContext);
        button.setItem(mItems.get(position));
        return button;
    }

    public ImageAdapter(Context context, int resource, List<ButtonItem> objects) {
        super(context, resource, objects);
        this.mItems = objects;
        this.mContext = context;
    }

}
