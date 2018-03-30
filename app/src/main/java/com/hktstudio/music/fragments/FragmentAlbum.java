package com.hktstudio.music.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hktstudio.music.R;
import com.hktstudio.music.activities.MainActivity;
import com.hktstudio.music.adapters.AdapterAlbum;
import com.hktstudio.music.models.Album;

import java.util.List;

/**
 * Created by HOANG on 3/19/2018.
 */

public class FragmentAlbum extends Fragment {
    RecyclerView rcv_Album;
    AdapterAlbum adapterAlbum;
    List<Album> list;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album,container,false);
        rcv_Album = view.findViewById(R.id.rcv_Album);
        list = MainActivity.listAlbum;
        adapterAlbum = new AdapterAlbum(getContext(),list);
        rcv_Album.setAdapter(adapterAlbum);
        rcv_Album.setLayoutManager(new GridLayoutManager(getContext(),3));
        return view;
    }
}
