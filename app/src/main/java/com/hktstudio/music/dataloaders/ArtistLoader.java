package com.hktstudio.music.dataloaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.hktstudio.music.models.Artist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOANG on 4/10/2018.
 */

public class ArtistLoader {
    public static List getListArtist(Context context){
        List<Artist> list = new ArrayList<>();
        Uri uri;
        uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

        String[] m_data = {MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        };
        try {
            Cursor c = context.getContentResolver().query(
                    uri, m_data, null, null, MediaStore.Audio.Artists._ID+" ASC");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                String id, album_art=null, artist, num_of_songs, num_of_albums;
                id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID));
                artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
                num_of_albums = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS))+" album";
                num_of_songs = " "+c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS))+" bài hát";
                Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        new String[] {MediaStore.Audio.Albums.ARTIST, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums.ARTIST+ "=?",
                        new String[] {artist},
                        null);
                if (cursor.moveToFirst()) {
                    album_art = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                }
                cursor.close();
                Artist artists = new Artist(id, album_art, artist, num_of_albums, num_of_songs);
                list.add(artists);
            }
            c.close();
        } catch (NullPointerException e){
        }

        return list;
    }
}
