package com.dainv.parrotjapanese.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dainv.parrotjapanese.data.ListItem;
import com.dainv.parrotjapanese.ui.CustomListView;

import java.util.List;

/**
 * Created by dainv on 10/28/2015.
 */
public class CustomListAdapter extends ArrayAdapter<ListItem> {
    private List<ListItem> mListItems;
    private Context mContext;

    public CustomListAdapter(Context context, int resource, List<ListItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mListItems = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomListView view = new CustomListView(mContext);
        view.setItemList(mListItems.get(position));
        return view;
    }

    @Override
    public int getCount() {
        return mListItems.size();
    }
}
