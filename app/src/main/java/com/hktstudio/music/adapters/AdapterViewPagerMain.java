package com.hktstudio.music.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.hktstudio.music.fragments.FragmentAlbum;
import com.hktstudio.music.fragments.FragmentArtist;
import com.hktstudio.music.fragments.FragmentPlaylist;
import com.hktstudio.music.fragments.FragmentSong;

/**
 * Created by HOANG on 3/19/2018.
 */

public class AdapterViewPagerMain extends FragmentStatePagerAdapter {
    public AdapterViewPagerMain(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new FragmentSong();
                break;
            case 1:
                fragment = new FragmentAlbum();
                break;
            case 2:
                fragment = new FragmentArtist();
                break;
            case 3:
                fragment = new FragmentPlaylist();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Song";
                break;
            case 1:
                title = "Album";
                break;
            case 2:
                title = "Artist";
                break;
            case 3:
                title = "Playlist";
                break;
        }
        return title;
    }
}
