package com.hktstudio.music.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hktstudio.music.R;
import com.hktstudio.music.activities.MainActivity;
import com.hktstudio.music.adapters.AdapterPlaylist;
import com.hktstudio.music.models.Playlist;

import java.util.List;

/**
 * Created by HOANG on 4/10/2018.
 */

public class FragmentPlaylist extends Fragment {
    RecyclerView rcv_Playlist;
    AdapterPlaylist adapterPlaylist;
    List<Playlist> list;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist,container,false);
        rcv_Playlist = view.findViewById(R.id.rcv_Playlist);
        list = MainActivity.playList;
        adapterPlaylist = new AdapterPlaylist(getContext(),list);
        rcv_Playlist.setAdapter(adapterPlaylist);
        rcv_Playlist.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }

}
