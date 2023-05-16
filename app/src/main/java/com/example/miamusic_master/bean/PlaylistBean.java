package com.example.miamusic_master.bean;

/**
 * 用于Adapter的歌单Bean
 */
public class PlaylistBean {

    private String picUrl;

    private String name;

    public String getPlaylistCoverUrl() {
        return picUrl;
    }

    public void setPlaylistCoverUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPlaylistName() {
        return name;
    }

    public void setPlaylistName(String name) {
        this.name = name;
    }
}
