package com.dainv.parrotjapanese.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dainv.parrotjapanese.R;
import com.dainv.parrotjapanese.data.ListLearnItem;

/**
 * Created by dainv on 10/29/2015.
 */
public class ListLearnView extends LinearLayout {
    private Context context;
    private TextView number;
    private TextView kanji;
    private TextView romaji;
    private TextView meaning;
    private ImageView speaker;

    public ListLearnView(Context context) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_learn_item, this);
        this.number = (TextView)findViewById(R.id.vcb_number);
        this.kanji = (TextView)findViewById(R.id.vcb_kanji);
        this.romaji = (TextView)findViewById(R.id.vcb_romaji);
        this.meaning = (TextView)findViewById(R.id.vcb_meaning);
        this.speaker = (ImageView)findViewById(R.id.speaker);
    }

    public void setItemList(ListLearnItem item) {
        this.number.setText(item.number + ".");
        this.kanji.setText(item.kanji);
        this.romaji.setText(item.romaji);
        this.meaning.setText(item.meaning);
    }

    public void setSpeakerVisible(int visibility) {
        this.speaker.setVisibility(visibility);
    }
}
