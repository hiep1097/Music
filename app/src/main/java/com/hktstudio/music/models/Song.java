package com.hktstudio.music.models;

/**
 * Created by HOANG on 3/20/2018.
 */

public class Song {
    private String id, name, title, album, album_id,artist,artist_id, path, album_art;
    private int duration;
    public Song() {
    }

    public Song(String id, String name, String title, String album, String album_id, String artist, String artist_id, String path, String album_art, int duration) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.album = album;
        this.album_id = album_id;
        this.artist = artist;
        this.artist_id = artist_id;
        this.path = path;
        this.album_art = album_art;
        this.duration = duration;
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

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
