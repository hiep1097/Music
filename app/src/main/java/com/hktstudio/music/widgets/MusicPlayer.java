package com.hktstudio.music.widgets;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.hktstudio.music.activities.MainActivity;
import com.hktstudio.music.fragments.FragmentPlaylist;
import com.hktstudio.music.models.Playlist;

import java.util.List;

/**
 * Created by HOANG on 5/18/2018.
 */

public class MusicPlayer {
    public static void updatePlaylist(){
        List<Playlist> list;
        MainActivity.updateList();
        list = MainActivity.playList;
        FragmentPlaylist.list.clear();
        FragmentPlaylist.list.addAll(list);
        FragmentPlaylist.adapterPlaylist.notifyDataSetChanged();
    }
    public static final long createPlaylist(final Context context, final String name) {
        if (name != null && name.length() > 0) {
            final ContentResolver resolver = context.getContentResolver();
            final String[] projection = new String[]{
                    MediaStore.Audio.PlaylistsColumns.NAME
            };
            final String selection = MediaStore.Audio.PlaylistsColumns.NAME + " = '" + name + "'";
            Cursor cursor = resolver.query(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                    projection, selection, null, null);
            if (cursor.getCount() <= 0) {
                final ContentValues values = new ContentValues(1);
                values.put(MediaStore.Audio.PlaylistsColumns.NAME, name);
                final Uri uri = resolver.insert(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,
                        values);
                return Long.parseLong(uri.getLastPathSegment());
            }
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
            return -1;
        }
        return -1;
    }
    public static final long deletePlaylist(final Context context, final String name) {
        if (name != null && name.length() > 0) {
            final ContentResolver resolver = context.getContentResolver();
            final String[] projection = new String[]{
                    MediaStore.Audio.PlaylistsColumns.NAME
            };
            final String selection = MediaStore.Audio.PlaylistsColumns.NAME + " = '" + name + "'";
            resolver.delete(MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI,selection,null);
        }
        return -1;
    }
}
