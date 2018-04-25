package com.hktstudio.music.dataloaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.hktstudio.music.models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOANG on 4/10/2018.
 */

public class SongLoader {
    public static List getListSongs(Context context) {
        List mListSongs = new ArrayList();
        Uri uri;
        uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        String[] m_data = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA};

        try {
            Cursor c = context.getContentResolver().query(
                    uri, m_data, MediaStore.Audio.Media.IS_MUSIC + "=1", null,
                    MediaStore.Audio.Media.TITLE + " ASC");
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

                String id, name, title, album, album_id, artist, artist_id, path, album_art="";
                int duration;
                id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                album_id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                artist_id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID));
                duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                        new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                        MediaStore.Audio.Albums._ID+ "=?",
                        new String[] {String.valueOf(album_id)},
                        null);
                if (cursor.moveToFirst()) {
                    album_art = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
                }
                Song song = new Song(id, name, title, album, album_id, artist,artist_id, path, album_art, duration);
                mListSongs.add(song);

            }
            c.close();
        } catch (NullPointerException e){
        }
        return mListSongs;
    }
}
