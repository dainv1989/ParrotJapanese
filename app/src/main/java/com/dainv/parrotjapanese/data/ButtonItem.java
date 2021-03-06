package com.dainv.parrotjapanese.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dainv on 10/28/2015.
 */
public class ButtonItem {
    public String title;        // menu item title
    public String desc;         // menu item description
    public String photoRes;     // menu item photo resource file name
    public String dataRes;      // menu item data resource file name
    public List<LearnItem> learnItems;

    public ButtonItem(String title, String desc, String dataFile, String photo) {
        this.title = title;
        this.desc = desc;
        this.photoRes = photo;
        this.dataRes = dataFile;
        this.learnItems = new ArrayList<>();
    }

    public ButtonItem() {
        this.title = "";
        this.desc = "";
        this.photoRes = "";
        this.dataRes = "";
        this.learnItems = new ArrayList<>();
    }
}
