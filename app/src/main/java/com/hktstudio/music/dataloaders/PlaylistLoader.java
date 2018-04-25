package com.hktstudio.music.dataloaders;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.MediaController;

import com.hktstudio.music.models.Playlist;
import com.hktstudio.music.models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOANG on 4/10/2018.
 */

public class PlaylistLoader {
    public static List getPlaylist(Context context) {
        List mPlaylist = new ArrayList();
        Uri uri;
        uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;

        String[] m_data = {MediaStore.Audio.Playlists._ID,
                MediaStore.Audio.Playlists.NAME,
                };
        Cursor c = context.getContentResolver().query(
                uri, m_data, null, null,null);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String name;
            long id;
            int count;
            id = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Playlists._ID));
            name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Playlists.NAME));
            count = countPlaylist(context,id);
            Playlist playlist = new Playlist(id,name,count);
            mPlaylist.add(playlist);

        }
        c.close();
        return mPlaylist;
    }
    private static int countPlaylist(final Context context, final long playlistId) {
        Cursor c = context.getContentResolver().query(
                MediaStore.Audio.Playlists.Members.getContentUri("external", playlistId),
                new String[]{MediaStore.Audio.Playlists.Members.AUDIO_ID,}, null, null, null);
        if (c != null) {
            return c.getCount();
        }
        return 0;
    }
}
