package com.hktstudio.music.dataloaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.hktstudio.music.models.Album;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOANG on 4/10/2018.
 */

public class AlbumLoader {
    public static List getListAlbums(Context context){
        List<Album> list = new ArrayList<>();
        Uri uri;
        uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        String[] m_data = {MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
        };


        try{
            Cursor c = context.getContentResolver().query(
                    uri, m_data, null, null, MediaStore.Audio.Albums._ID+" ASC");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                String id, album, album_art, artist, number_of_songs;
                id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums._ID));
                album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM));
                album_art = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM_ART));
                artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST));
                number_of_songs = " ("+c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS))+")";
                Album albums = new Album(id, album, album_art, artist, number_of_songs);
                list.add(albums);

            }
            c.close();
        } catch (NullPointerException e){
        }
        return list;
    }

}
