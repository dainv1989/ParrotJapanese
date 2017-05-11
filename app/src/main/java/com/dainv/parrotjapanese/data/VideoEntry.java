package com.dainv.parrotjapanese.data;

/**
 * Created by dainv on 10/30/2015.
 */
public class VideoEntry {
    public String title;
    public String desc;
    public String videoId;

    public VideoEntry(String title, String desc, String videoId) {
        this.title = title;
        this.desc = desc;
        this.videoId = videoId;
    }

    public VideoEntry() {
        this.title = "";
        this.desc = "";
        this.videoId = "";
    }
}
