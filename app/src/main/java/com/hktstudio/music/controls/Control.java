package com.hktstudio.music.controls;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import com.hktstudio.music.R;
import com.hktstudio.music.activities.PlaySongActivity;
import com.hktstudio.music.adapters.AdapterSong;
import com.hktstudio.music.adapters.AdapterSongForAlbum;
import com.hktstudio.music.adapters.AdapterSongForArtist;
import com.hktstudio.music.service.MusicService;
import java.io.IOException;
import static com.hktstudio.music.activities.MainActivity.bt_Play;
import static com.hktstudio.music.activities.MainActivity.updateUI;
import static com.hktstudio.music.service.MusicService.contentView;
import static com.hktstudio.music.service.MusicService.initMedia;
import static com.hktstudio.music.service.MusicService.mediaPlayer;
import static com.hktstudio.music.service.MusicService.newNotification;
import static com.hktstudio.music.service.MusicService.notification;
import static com.hktstudio.music.service.MusicService.pos;
import static com.hktstudio.music.service.MusicService.updateNotification;

/**
 * Created by HOANG on 3/22/2018.
 */

public class Control {
    public static void previous(Context context) {
        initMedia(context);
        if (pos > 0) {
            pos--;
        } else pos = MusicService.list.size() - 1;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            bt_Play.setImageResource(R.drawable.ic_pause_white);
            try {
                PlaySongActivity.bt_Play.setImageResource(R.drawable.ic_pause_white);
            } catch (NullPointerException e) {
            }
            mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
            bt_Play.setImageResource(R.drawable.ic_play_white);
            try {
                PlaySongActivity.bt_Play.setImageResource(R.drawable.ic_play_white);
            } catch (NullPointerException e) {
            }
        }
        updateUI();
        if (notification != null) {
            updateNotification();
        }
        try {
            PlaySongActivity.updateUI();
            PlaySongActivity.viewPager.setCurrentItem(pos, false);
        } catch (NullPointerException e) {
        }
        setPos();
    }

    public static void start(Context context) {
        if (notification == null) {
             newNotification(context);
        }
        contentView.setImageViewResource(R.id.bt_Play, R.drawable.ic_pause_black);
        updateNotification();
        initMedia(context);
        if (mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        bt_Play.setImageResource(R.drawable.ic_pause_white);
        try {
            PlaySongActivity.bt_Play.setImageResource(R.drawable.ic_pause_white);
        } catch (NullPointerException e) {
        }
        mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
        mediaPlayer.start();
        updateUI();
        try {
            PlaySongActivity.updateUI();
        } catch (NullPointerException e) {
        } catch (IndexOutOfBoundsException e){
        }
        setPos();
    }

    public static void play(Context context) {
        initMedia(context);
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            contentView.setImageViewResource(R.id.bt_Play, R.drawable.ic_play_black);
            updateNotification();
            bt_Play.setImageResource(R.drawable.ic_play_white);
            try {
                PlaySongActivity.bt_Play.setImageResource(R.drawable.ic_play_white);
            } catch (NullPointerException e) {
            }
        } else {
            bt_Play.setImageResource(R.drawable.ic_pause_white);
            if (notification == null) {
                newNotification(context);
            }
            contentView.setImageViewResource(R.id.bt_Play, R.drawable.ic_pause_black);
            updateNotification();
            try {
                PlaySongActivity.bt_Play.setImageResource(R.drawable.ic_pause_white);
            } catch (NullPointerException e) {
            }
            mediaPlayer.start();
        }
        setPos();
    }

    public static void next(Context context) {
        initMedia(context);
        if (pos < MusicService.list.size() - 1) {
            pos++;
        } else pos = 0;
        if (mediaPlayer.isPlaying() || PlaySongActivity.complete) {
            mediaPlayer.stop();
            mediaPlayer.release();
            bt_Play.setImageResource(R.drawable.ic_pause_white);
            try {
                PlaySongActivity.bt_Play.setImageResource(R.drawable.ic_pause_white);
            } catch (NullPointerException e) {
            }
            mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
            bt_Play.setImageResource(R.drawable.ic_play_white);
            try {
                PlaySongActivity.bt_Play.setImageResource(R.drawable.ic_play_white);
            } catch (NullPointerException e) {
            }
        }
        updateUI();
        if (notification != null) {
            updateNotification();
        }
        try {
            PlaySongActivity.updateUI();
            PlaySongActivity.viewPager.setCurrentItem(pos, false);
        } catch (NullPointerException e) {
        }
        setPos();
    }

    public static void setPos(){
        if (MusicService.FLAG_LIST_FROM == 0) {
            AdapterSong.setCurrentPos(pos);
            boolean ok = false; //ok: list song for album co bai dang phat cua list song main
            try{
                for (int i=0;i<AdapterSongForAlbum.listPos.size();i++)
                    if (AdapterSongForAlbum.listPos.get(i)==pos) {
                        AdapterSongForAlbum.setCurrentPos(i);
                        ok = true;
                        break;
                    }
                if (!ok) AdapterSongForAlbum.setCurrentPos(-1);
            } catch (NullPointerException e){
            }

            try {
                boolean ok1 = false; //ok1: list song for album co bai dang phat cua list song main
                for (int i=0;i<AdapterSongForArtist.listPos.size();i++)
                    if (AdapterSongForArtist.listPos.get(i)==pos) {
                        AdapterSongForArtist.setCurrentPos(i);
                        ok1 = true;
                        break;
                    }
                if (!ok1) AdapterSongForArtist.setCurrentPos(-1);
            } catch (NullPointerException e){
            }
        } else if (MusicService.FLAG_LIST_FROM == 1){
            AdapterSongForAlbum.setCurrentPos(pos);
            AdapterSong.setCurrentPos(AdapterSongForAlbum.listPos.get(pos));
            try {
                boolean ok = false; //ok: list song for artist co bai dang phat cua list song for album
                for (int i=0;i<AdapterSongForArtist.listPos.size();i++)
                    if (AdapterSongForArtist.listPos.get(i)==AdapterSongForAlbum.listPos.get(pos)) {
                        AdapterSongForArtist.setCurrentPos(i);
                        ok = true;
                        break;
                    }
                if (!ok) AdapterSongForArtist.setCurrentPos(-1);
            } catch (NullPointerException e){
            }

        } else if (MusicService.FLAG_LIST_FROM == 2){
            AdapterSongForArtist.setCurrentPos(pos);
            AdapterSong.setCurrentPos(AdapterSongForArtist.listPos.get(pos));
            try {
                boolean ok = false; //ok: list song for album co bai dang phat cua list song for artist
                for (int i=0;i<AdapterSongForAlbum.listPos.size();i++)
                    if (AdapterSongForAlbum.listPos.get(i)==AdapterSongForArtist.listPos.get(pos)) {
                        AdapterSongForAlbum.setCurrentPos(i);
                        ok = true;
                        break;
                    }
                if (!ok) AdapterSongForAlbum.setCurrentPos(-1);
            } catch (NullPointerException e){
            }
        }
    }

    public static void exit(Context context) {
        MusicService.instance.stopForeground(true);
        MusicService.manager.cancel(1);
        System.exit(1);
    }

}
