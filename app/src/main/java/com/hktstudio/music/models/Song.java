package com.hktstudio.music.models;

/**
 * Created by HOANG on 3/20/2018.
 */

public class Song {
    private String id, name, title, album, album_id,artist, path, album_art;
    private int duration;
    public Song() {
    }

    public Song(String id, String name, String title, String album,
                String album_id, String artist, String path,int duration,String album_art) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.album = album;
        this.album_id = album_id;
        this.artist = artist;
        this.path = path;
        this.duration = duration;
        this.album_art = album_art;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }
}
