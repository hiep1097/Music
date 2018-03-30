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
import com.hktstudio.music.defines.Define;
import com.hktstudio.music.models.Album;
import com.hktstudio.music.models.Artist;
import com.hktstudio.music.models.Song;
import com.hktstudio.music.service.MusicService;

import java.util.ArrayList;
import java.util.List;

import static com.hktstudio.music.service.MusicService.getPos;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ViewPager viewPager;
    TabLayout tabLayout;
    FragmentManager fragmentManager;
    public static ImageView image_Song;
    public static TextView tv_Song, tv_Artist;
    public static ImageButton bt_Previous, bt_Play, bt_Next;
    public static List<Song> listSong = new ArrayList<>();
    public static List<Album> listAlbum = new ArrayList<>();
    public static List<Artist> listArtist = new ArrayList<>();
    LinearLayout bottomBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listSong = getListSongs(this);
        listAlbum = getListAlbums(this);
        listArtist = getListArtist(this);
        addPermission();
        Intent intent = new Intent(this,MusicService.class);
        startService(intent);
    }
    public void setControl(){
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);
        fragmentManager = getSupportFragmentManager();
        AdapterViewPagerMain adapterViewPager = new AdapterViewPagerMain(fragmentManager);
        viewPager.setAdapter(adapterViewPager);
        viewPager.setOffscreenPageLimit(3);
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

    public static void updateUI(){
        try {
            image_Song.setImageDrawable(Drawable.createFromPath(listSong.get(getPos()).getAlbum_art()));
            tv_Song.setText(listSong.get(getPos()).getName());
            tv_Artist.setText(listSong.get(getPos()).getArtist());
        } catch (IndexOutOfBoundsException e){

        }

    }

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
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA};

        Cursor c = context.getContentResolver().query(
                uri, m_data, MediaStore.Audio.Media.IS_MUSIC + "=1", null,
                MediaStore.Audio.Media.TITLE + " ASC");

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            String id, name, title, album, album_id, artist, path, album_art="";
            int duration;
            id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
            name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
            title = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
            album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
            album_id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
            artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
            duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
            path = c.getString(c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    new String[] {MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART},
                    MediaStore.Audio.Albums._ID+ "=?",
                    new String[] {String.valueOf(album_id)},
                    null);
            if (cursor.moveToFirst()) {
                album_art = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            }
            Song song = new Song(id, name, title, album, album_id, artist, path, duration,album_art);
            mListSongs.add(song);

        }
        return mListSongs;
    }

    public static List getListAlbums(Context context){
        List<Album> list = new ArrayList<>();
        Uri uri;
        uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;

        String[] m_data = {MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM,
                MediaStore.Audio.Albums.ALBUM_ART,
                MediaStore.Audio.Albums.ARTIST,
                MediaStore.Audio.Albums.NUMBER_OF_SONGS,
                };

        Cursor c = context.getContentResolver().query(
                uri, m_data, null, null, MediaStore.Audio.Albums._ID+" ASC");

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

    public static List getListArtist(Context context){
        List<Artist> list = new ArrayList<>();
        Uri uri;
        uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;

        String[] m_data = {MediaStore.Audio.Artists._ID,
                MediaStore.Audio.Artists.ARTIST,
                MediaStore.Audio.Artists.NUMBER_OF_ALBUMS,
                MediaStore.Audio.Artists.NUMBER_OF_TRACKS
        };

        Cursor c = context.getContentResolver().query(
                uri, m_data, null, null, MediaStore.Audio.Artists._ID+" ASC");

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            String id, album_art=null, artist, num_of_songs, num_of_albums;
            id = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Artists._ID));
            artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
            num_of_albums = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_ALBUMS))+" album";
            num_of_songs = " "+c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS))+" bài hát";
            Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                    new String[] {MediaStore.Audio.Albums.ARTIST, MediaStore.Audio.Albums.ALBUM_ART},
                    MediaStore.Audio.Albums.ARTIST+ "=?",
                    new String[] {artist},
                    null);
            if (cursor.moveToFirst()) {
                album_art = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            }
            Artist artists = new Artist(id, album_art, artist, num_of_albums, num_of_songs);
            list.add(artists);

        }
        return list;
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
