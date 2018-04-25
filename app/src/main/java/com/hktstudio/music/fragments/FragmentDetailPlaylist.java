package com.hktstudio.music.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hktstudio.music.R;
import com.hktstudio.music.activities.MainActivity;
import com.hktstudio.music.adapters.AdapterSong;
import com.hktstudio.music.adapters.AdapterSongForAlbum;
import com.hktstudio.music.adapters.AdapterSongForPlaylist;
import com.hktstudio.music.models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOANG on 4/13/2018.
 */

public class FragmentDetailPlaylist extends Fragment {
    public static FragmentDetailPlaylist instance;
    public static TextView tv_Playlist, tv_Count;
    public static RecyclerView rcv_SongForPlaylist;
    public static AdapterSongForPlaylist adapterSongForPlaylist;
    public static List<Song> list;
    public static List<Integer> listPos = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_playlist,container,false);
        instance = this;
        tv_Playlist = view.findViewById(R.id.tv_Playlist);
        tv_Count = view.findViewById(R.id.tv_Count);
        Bundle bundle = getArguments();
        tv_Playlist.setText(bundle.getString("tv_Playlist"));
        tv_Count.setText(bundle.getInt("tv_Count")+" bài hát");
        rcv_SongForPlaylist = view.findViewById(R.id.rcv_SongForPlaylist);
        listPos = new ArrayList<>();
        list = getListSongForPlaylist(bundle.getLong("playlistID"));
        adapterSongForPlaylist = new AdapterSongForPlaylist(getContext(),list,listPos);
        rcv_SongForPlaylist.setAdapter(adapterSongForPlaylist);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        layoutManager.setAutoMeasureEnabled(true);
        rcv_SongForPlaylist.setLayoutManager(layoutManager);
        rcv_SongForPlaylist.setNestedScrollingEnabled(false);
        rcv_SongForPlaylist.setLayoutManager(layoutManager);
        return view;
    }

    public static List<Song> getListSongForPlaylist(long playlistID){
        List<Song> list = new ArrayList<>();
        Cursor c = makePlaylistSongCursor(instance.getContext(),playlistID);
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String id, name, title, album, album_id, artist, artist_id, path, album_art="";
            int duration;
            id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Playlists.Members.AUDIO_ID));
            name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DISPLAY_NAME));
            title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.TITLE));
            album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM));
            album_id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ALBUM_ID));
            artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST));
            artist_id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.ARTIST_ID));
            duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DURATION));
            path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.AudioColumns.DATA));
            Cursor cursor = instance.getContext().getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                    MediaStore.Audio.Albums._ID+ "=?",
                    new String[] {String.valueOf(album_id)},
                    null);
            if (cursor.moveToFirst()) {
                album_art = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            }
            Song song = new Song(id, name, title, album, album_id, artist,artist_id, path, album_art, duration);
            list.add(song);

        }
        c.close();
        return list;
    }

    public static final Cursor makePlaylistSongCursor(final Context context, final Long playlistID) {
        final StringBuilder mSelection = new StringBuilder();
        mSelection.append(MediaStore.Audio.AudioColumns.IS_MUSIC + "=1");
        mSelection.append(" AND " + MediaStore.Audio.AudioColumns.TITLE + " != ''");
        return context.getContentResolver().query(
                MediaStore.Audio.Playlists.Members.getContentUri("external", playlistID),
                new String[]{
                        MediaStore.Audio.Playlists.Members.AUDIO_ID,
                        MediaStore.Audio.AudioColumns.DISPLAY_NAME,
                        MediaStore.Audio.AudioColumns.TITLE,
                        MediaStore.Audio.AudioColumns.ALBUM,
                        MediaStore.Audio.AudioColumns.ALBUM_ID,
                        MediaStore.Audio.AudioColumns.ARTIST,
                        MediaStore.Audio.AudioColumns.ARTIST_ID,
                        MediaStore.Audio.AudioColumns.DURATION,
                        MediaStore.Audio.AudioColumns.DATA
                }, mSelection.toString(), null,
                MediaStore.Audio.Playlists.Members.DEFAULT_SORT_ORDER);
    }
}
