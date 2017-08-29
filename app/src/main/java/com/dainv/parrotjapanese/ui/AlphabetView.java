package com.dainv.parrotjapanese.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dainv.parrotjapanese.R;
import com.dainv.parrotjapanese.data.AlphabetItem;

/**
 * Created by dainv on 11/23/2015.
 */
public class AlphabetView extends LinearLayout {
    public TextView txtJp;
    public TextView txtEn;

    public AlphabetView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.alphabet_cell, this);
        this.txtEn = (TextView)findViewById(R.id.txtEn);
        this.txtJp = (TextView)findViewById(R.id.txtJp);
    }

    public void setItemList(AlphabetItem item) {
        this.txtEn.setText(item.txtEn);
        this.txtJp.setText(item.txtJp);
    }
}
