package com.dainv.parrotjapanese.data;

/**
 * Created by dainv on 11/23/2015.
 */
public class AlphabetItem {
    public String txtJp;
    public String txtEn;

    public AlphabetItem(String jp, String en) {
        this.txtEn = en;
        this.txtJp = jp;
    }

    public AlphabetItem() {
        this.txtJp = "";
        this.txtEn = "";
    }

    public String toString() {
        return this.txtEn;
    }
}
