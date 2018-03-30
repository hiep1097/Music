package com.hktstudio.music.models;

/**
 * Created by HOANG on 3/30/2018.
 */

public class Artist {
    private String id, album_art, artist, num_of_albums, num_of_songs;

    public Artist(String id, String album_art, String artist, String num_of_albums, String num_of_songs) {
        this.id = id;
        this.album_art = album_art;
        this.artist = artist;
        this.num_of_albums = num_of_albums;
        this.num_of_songs = num_of_songs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNum_of_albums() {
        return num_of_albums;
    }

    public void setNum_of_albums(String num_of_albums) {
        this.num_of_albums = num_of_albums;
    }

    public String getNum_of_songs() {
        return num_of_songs;
    }

    public void setNum_of_songs(String num_of_songs) {
        this.num_of_songs = num_of_songs;
    }
}
