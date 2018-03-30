package com.hktstudio.music.models;

/**
 * Created by HOANG on 3/30/2018.
 */

public class Album {
    private String id, album, album_art, artist, number_of_songs;

    public Album(String id, String album, String album_art, String artist, String number_of_songs) {
        this.id = id;
        this.album = album;
        this.album_art = album_art;
        this.artist = artist;
        this.number_of_songs = number_of_songs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getNumber_of_songs() {
        return number_of_songs;
    }

    public void setNumber_of_songs(String number_of_songs) {
        this.number_of_songs = number_of_songs;
    }
}
