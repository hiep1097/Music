package com.hktstudio.music.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.hktstudio.music.adapters.AdapterSong;
import com.hktstudio.music.adapters.AdapterSongForAlbum;
import com.hktstudio.music.models.Song;
import com.hktstudio.music.service.MusicService;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOANG on 4/4/2018.
 */

public class FragmentDetailAlbum extends Fragment{
    public static ImageView image_Album;
    public static TextView tv_Album, tv_Artist;
    public static RecyclerView rcv_AlbumList;
    public static AdapterSongForAlbum adapterSongForAlbum;
    public static List<Song> list;
    public static List<Integer> listPos = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_album,container,false);
        image_Album = view.findViewById(R.id.image_Album);
        tv_Album = view.findViewById(R.id.tv_Album);
        tv_Artist = view.findViewById(R.id.tv_Artist);
        Bundle bundle = getArguments();
        image_Album.setImageDrawable(Drawable.createFromPath(bundle.getString("image_Album")));
        tv_Album.setText(bundle.getString("tv_Album"));
        tv_Artist.setText(bundle.getString("tv_Artist"));
        rcv_AlbumList = view.findViewById(R.id.rcv_AlbumList);
        listPos = new ArrayList<>();
        list = getList(bundle.getString("albumID"));
        adapterSongForAlbum = new AdapterSongForAlbum(getContext(),list,listPos);
        rcv_AlbumList.setAdapter(adapterSongForAlbum);
        rcv_AlbumList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setAutoMeasureEnabled(true);
        rcv_AlbumList.setLayoutManager(layoutManager);
        rcv_AlbumList.setNestedScrollingEnabled(false);
        return view;
    }

    public List<Song> getList(String albumID){
        List<Song> list = new ArrayList<>();
        for (int i = 0; i< MainActivity.listAlbum.size(); i++){
            for (int j=0;j<MainActivity.listSong.size();j++)
                if (MainActivity.listAlbum.get(i).getId().compareTo(albumID)==0
                    && MainActivity.listAlbum.get(i).getId().compareTo(MainActivity.listSong.get(j).getAlbum_id())==0){
                    list.add(MainActivity.listSong.get(j));
                    listPos.add(j);
                    if (j==AdapterSong.getCurrentPos()) AdapterSongForAlbum.setCurrentPos(listPos.size()-1);
                }

        }
        return list;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
