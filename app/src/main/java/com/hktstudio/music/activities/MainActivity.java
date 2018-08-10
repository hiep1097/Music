package com.hktstudio.music.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hktstudio.music.R;
import com.hktstudio.music.adapters.AdapterViewPagerMain;
import com.hktstudio.music.controls.Control;
import com.hktstudio.music.dataloaders.AlbumLoader;
import com.hktstudio.music.dataloaders.ArtistLoader;
import com.hktstudio.music.dataloaders.PlaylistLoader;
import com.hktstudio.music.dataloaders.SongLoader;
import com.hktstudio.music.defines.Define;
import com.hktstudio.music.fragments.FragmentDetailAlbum;
import com.hktstudio.music.fragments.FragmentDetailArtist;
import com.hktstudio.music.fragments.FragmentDetailPlaylist;
import com.hktstudio.music.fragments.FragmentSong;
import com.hktstudio.music.models.Album;
import com.hktstudio.music.models.Artist;
import com.hktstudio.music.models.Playlist;
import com.hktstudio.music.models.Song;
import com.hktstudio.music.service.MusicService;

import java.util.ArrayList;
import java.util.List;

import static com.hktstudio.music.service.MusicService.getPos;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static MainActivity instance;
    public static ViewPager viewPager;
    public static TabLayout tabLayout;
    public static FragmentManager fragmentManager;
    public static ImageView image_Song;
    public static TextView tv_Song, tv_Artist;
    public static ImageButton bt_Previous, bt_Play, bt_Next;
    public static List<Song> listSong = new ArrayList<>();
    public static List<Album> listAlbum = new ArrayList<>();
    public static List<Artist> listArtist = new ArrayList<>();
    public static List<Playlist> playList = new ArrayList<>();
    public static LinearLayout bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        addPermission();
    }

    public static void updateList(){
        listSong = SongLoader.getListSongs(instance);
        listAlbum = AlbumLoader.getListAlbums(instance);
        listArtist = ArtistLoader.getListArtist(instance);
        playList = PlaylistLoader.getPlaylist(instance);
    }

    public void setControl(){
        updateList();
        Intent intent = new Intent(this,MusicService.class);
        startService(intent);
        MusicService.list = listSong;
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        fragmentManager = getSupportFragmentManager();
        AdapterViewPagerMain adapterViewPager = new AdapterViewPagerMain(fragmentManager);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOffscreenPageLimit(1);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapterViewPager);
        image_Song = findViewById(R.id.image_Song);
        tv_Song = findViewById(R.id.tv_Song);
        tv_Artist = findViewById(R.id.tv_Artist);
        bt_Previous = findViewById(R.id.bt_Previous);
        bt_Play = findViewById(R.id.bt_Play);
        bt_Next = findViewById(R.id.bt_Next);
        bt_Previous.setOnClickListener(this);
        bt_Play.setOnClickListener(this);
        bt_Next.setOnClickListener(this);
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnClickListener(this);
        updateUI();
    }

    public static void addFragmentDetailAlbum(Bundle bundle){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentDetailAlbum fragmentDetailAlbum = new FragmentDetailAlbum();
        fragmentDetailAlbum.setArguments(bundle);
        transaction.add(R.id.placeHolder,fragmentDetailAlbum);
        transaction.addToBackStack("album");
        transaction.commit();
    }

    public static void addFragmentDetailArtist(Bundle bundle){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentDetailArtist fragmentDetailArtist = new FragmentDetailArtist();
        fragmentDetailArtist.setArguments(bundle);
        transaction.add(R.id.placeHolder,fragmentDetailArtist);
        transaction.addToBackStack("artist");
        transaction.commit();
    }

    public static void addFragmentDetailPlaylist(Bundle bundle){
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentDetailPlaylist fragmentDetailPlaylist = new FragmentDetailPlaylist();
        fragmentDetailPlaylist.setArguments(bundle);
        transaction.add(R.id.placeHolder,fragmentDetailPlaylist);
        transaction.addToBackStack("playlist");
        transaction.commit();
    }

    public static void updateUI(){
        try {
            image_Song.setImageDrawable(Drawable.createFromPath(MusicService.list.get(getPos()).getAlbum_art()));
            tv_Song.setText(MusicService.list.get(getPos()).getName());
            tv_Artist.setText(MusicService.list.get(getPos()).getArtist());
        } catch (IndexOutOfBoundsException e){

        }
    }

    void addPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setControl();

                } else {

                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }

                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, MusicService.class);
        switch (view.getId()){
            case R.id.bt_Previous:
                intent.setAction(Define.actPrevious);
                startService(intent);
                break;
            case R.id.bt_Play:
                intent.setAction(Define.actPlay);
                startService(intent);
                break;
            case R.id.bt_Next:
                intent.setAction(Define.actNext);
                startService(intent);
                break;
            case R.id.bottomBar:
                Intent intent1 = new Intent(MainActivity.this, PlaySongActivity.class);
                startActivity(intent1);
        }
    }
}
