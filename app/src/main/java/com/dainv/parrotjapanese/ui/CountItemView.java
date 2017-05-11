package com.dainv.parrotjapanese.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dainv.parrotjapanese.data.ListLearnItem;
import com.dainv.parrotjapanese.R;

/**
 * Created by dainv on 10/29/2015.
 */
public class CountItemView extends LinearLayout {
    public TextView number;
    public TextView kanji;
    public TextView romaji;
    private TextView meaning;
    private ImageView speaker;

    public CountItemView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_count_item, this);
        this.number = (TextView)findViewById(R.id.cnt_number);
        this.kanji = (TextView)findViewById(R.id.cnt_hiragana);
        this.romaji = (TextView)findViewById(R.id.cnt_pronun);
        this.meaning = (TextView)findViewById(R.id.cnt_meaning);
        this.speaker = (ImageView)findViewById(R.id.cnt_speaker);
    }

    public void setItemList(ListLearnItem item) {
        this.number.setText(item.number);
        this.kanji.setText(item.kanji);
        this.romaji.setText(item.romaji);
        this.meaning.setText(item.meaning);
    }

    public void setSpeakerVisible(int visibility) {
        this.speaker.setVisibility(visibility);
    }
}
