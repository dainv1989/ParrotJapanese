package com.dainv.parrotjapanese.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dainv.parrotjapanese.data.ListItem;
import com.dainv.parrotjapanese.ui.MainViewButton;

import java.util.List;

/**
 * Created by dainv on 10/23/2015.
 */
public class ImageAdapter extends ArrayAdapter<ListItem> {
    private Context mContext;
    private List<ListItem> mItems;

    public View getView(int position, View convertView, ViewGroup parent) {
        MainViewButton button = new MainViewButton(mContext);
        button.setItem(mItems.get(position));
        return button;
    }

    public ImageAdapter(Context context, int resource, List<ListItem> objects) {
        super(context, resource, objects);
        this.mItems = objects;
        this.mContext = context;
    }

}
