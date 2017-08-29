package com.dainv.parrotjapanese.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.dainv.parrotjapanese.data.ButtonItem;
import com.dainv.parrotjapanese.ui.CustomListView;

import java.util.List;

/**
 * Created by dainv on 10/28/2015.
 */
public class ButtonAdapter extends ArrayAdapter<ButtonItem> {
    private List<ButtonItem> mButtonItems;
    private Context mContext;

    public ButtonAdapter(Context context, int resource, List<ButtonItem> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mButtonItems = objects;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomListView view = new CustomListView(mContext);
        view.setItemList(mButtonItems.get(position));
        return view;
    }

    @Override
    public int getCount() {
        return mButtonItems.size();
    }
}
