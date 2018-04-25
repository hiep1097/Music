package com.hktstudio.music.fragments;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hktstudio.music.R;
import com.hktstudio.music.activities.MainActivity;
import com.hktstudio.music.adapters.AdapterAlbum;
import com.hktstudio.music.adapters.AdapterSong;
import com.hktstudio.music.adapters.AdapterSongForAlbum;
import com.hktstudio.music.adapters.AdapterSongForArtist;
import com.hktstudio.music.models.Album;
import com.hktstudio.music.models.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOANG on 4/6/2018.
 */

public class FragmentDetailArtist extends Fragment {
    public static ImageView image_Artist;
    public static TextView tv_Artist;
    public static RecyclerView rcv_AlbumForArtist, rcv_SongForArtist;
    public static AdapterSongForArtist adapterSongForArtist;
    public static AdapterAlbum adapterAlbum;
    public static List<Song> listSong;
    public static List<Album> listAlbum;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_artist,container,false);
        image_Artist = view.findViewById(R.id.image_Artist);
        tv_Artist = view.findViewById(R.id.tv_Artist);
        rcv_AlbumForArtist = view.findViewById(R.id.rcv_AlbumForArtist);
        rcv_SongForArtist = view.findViewById(R.id.rcv_SongForArtist);
        RecyclerView.LayoutManager layoutManagerAlbum = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView.LayoutManager layoutManagerSong = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcv_AlbumForArtist.setLayoutManager(layoutManagerAlbum);
        rcv_SongForArtist.setLayoutManager(layoutManagerSong);
        Bundle bundle = getArguments();
        image_Artist.setImageDrawable(Drawable.createFromPath(bundle.getString("image_Artist")));
        tv_Artist.setText(bundle.getString("tv_Artist"));
        listSong = getListSong(bundle.getString("artistID"));
        listAlbum = getListAlbum(bundle.getString("artistID"));
        adapterAlbum = new AdapterAlbum(getContext(),listAlbum);
        adapterSongForArtist = new AdapterSongForArtist(getContext(),listSong);
        rcv_AlbumForArtist.setAdapter(adapterAlbum);
        rcv_SongForArtist.setAdapter(adapterSongForArtist);
        layoutManagerAlbum.setAutoMeasureEnabled(true);
        layoutManagerSong.setAutoMeasureEnabled(true);
        rcv_SongForArtist.setNestedScrollingEnabled(false);
        rcv_AlbumForArtist.setNestedScrollingEnabled(false);
        return view;
    }

    public List<Song> getListSong(String artistID){
        List<Song> list = new ArrayList<>();
        for (int i = 0; i< MainActivity.listArtist.size(); i++){
            for (int j=0;j<MainActivity.listSong.size();j++)
                if (MainActivity.listArtist.get(i).getId().compareTo(artistID)==0
                        && MainActivity.listArtist.get(i).getId().compareTo(MainActivity.listSong.get(j).getArtist_id())==0){
                    list.add(MainActivity.listSong.get(j));
                }
        }
        return list;
    }
    public List<Album> getListAlbum(String artistID){
        List<Album> list = new ArrayList<>();
        String[] m_data = {MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
        };
        Cursor c = getContext().getContentResolver()
                .query(MediaStore.Audio.Artists.Albums.getContentUri("external", Long.parseLong(artistID)),
                            m_data, null, null, null);

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
        return list;
    }
}
