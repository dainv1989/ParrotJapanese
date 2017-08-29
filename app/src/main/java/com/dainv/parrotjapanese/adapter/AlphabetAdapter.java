package com.dainv.parrotjapanese.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dainv.parrotjapanese.data.AlphabetItem;
import com.dainv.parrotjapanese.ui.AlphabetView;

import java.util.List;

/**
 * Created by dainv on 11/23/2015.
 */
public class AlphabetAdapter extends ArrayAdapter<AlphabetItem> {
    private List<AlphabetItem> mListItems;
    private Context mContext;

    public AlphabetAdapter(Context context, int resource, List<AlphabetItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mListItems = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlphabetView view = new AlphabetView(this.mContext);
        view.setItemList(mListItems.get(position));
        return view;
    }
}
