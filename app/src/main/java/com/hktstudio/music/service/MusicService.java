package com.hktstudio.music.service;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.hktstudio.music.R;
import com.hktstudio.music.activities.MainActivity;
import com.hktstudio.music.activities.PlaySongActivity;
import com.hktstudio.music.controls.Control;
import com.hktstudio.music.defines.Define;
import com.hktstudio.music.models.Song;

import java.io.IOException;
import java.util.List;

import static com.hktstudio.music.activities.MainActivity.bt_Play;
import static com.hktstudio.music.activities.MainActivity.updateUI;

/**
 * Created by HOANG on 3/28/2018.
 */

public class MusicService extends Service{
    public static MusicService instance;
    public static int pos = 0;
    public static List<Song> list;
    public static MediaPlayer mediaPlayer;
    public static NotificationManager manager;
    public static NotificationCompat.Builder builder;
    public static Notification notification;
    public static RemoteViews contentView;
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static void initMedia(Context context) {
        try {
            if (mediaPlayer == null)
                mediaPlayer = MediaPlayer.create(context, Uri.parse(MusicService.list.get(pos).getPath()));
        } catch (IndexOutOfBoundsException e){

        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"start service",Toast.LENGTH_SHORT).show();
        try{
            if (intent.getAction().compareTo(Define.actPrevious)==0){
                Toast.makeText(this,"previous",Toast.LENGTH_SHORT).show();
                Control.previous(this);
            } else if (intent.getAction().compareTo(Define.actPlay)==0){
                Toast.makeText(this,"play",Toast.LENGTH_SHORT).show();
                Control.play(this);
            } else if (intent.getAction().compareTo(Define.actNext)==0){
                Toast.makeText(this,"next",Toast.LENGTH_SHORT).show();
                Control.next(this);
            } else if (intent.getAction().compareTo(Define.actStart)==0){
                Toast.makeText(this,"start",Toast.LENGTH_SHORT).show();
                Control.start(this);
            } else if (intent.getAction().compareTo(Define.actExit)==0){
                Toast.makeText(this,"exit",Toast.LENGTH_SHORT).show();
                Control.exit(this);
            }
        } catch (NullPointerException e){
        }
        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static void newNotification(Context context) {
        manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context, "1234");
        builder.setContentTitle("My Music");
        builder.setContentText("thông báo abcd");
        builder.setSmallIcon(R.drawable.ic_music_default);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                R.drawable.ic_music_default));
        contentView = new RemoteViews(context.getPackageName(), R.layout.notification);
        builder.setCustomContentView(contentView);
        Intent iPrevious = new Intent(Define.actPrevious);
        Intent iNext = new Intent(Define.actNext);
        Intent iPlay = new Intent(Define.actPlay);
        Intent iExit = new Intent(Define.actExit);
        PendingIntent pPrevious = PendingIntent.getBroadcast(context, 0, iPrevious, 0);
        PendingIntent pNext = PendingIntent.getBroadcast(context, 0, iNext, 0);
        PendingIntent pPlay = PendingIntent.getBroadcast(context, 0, iPlay, 0);
        PendingIntent pExit = PendingIntent.getBroadcast(context, 0, iExit, 0);
        contentView.setOnClickPendingIntent(R.id.bt_Previous, pPrevious);
        contentView.setOnClickPendingIntent(R.id.bt_Next, pNext);
        contentView.setOnClickPendingIntent(R.id.bt_Play, pPlay);
        contentView.setOnClickPendingIntent(R.id.bt_Exit, pExit);
        notification = builder.build();
        MusicService.instance.startForeground(1, notification);
    }

    public static void updateNotification() {
        contentView.setTextViewText(R.id.tv_Song, list.get(pos).getName());
        contentView.setTextViewText(R.id.tv_Artist, list.get(pos).getArtist());
        Uri uri = null;
        try {
            uri = Uri.parse(list.get(pos).getAlbum_art());
        } catch (NullPointerException e) {
        }
        if (uri != null)
            contentView.setImageViewUri(R.id.image_Song, uri);
        else contentView.setImageViewResource(R.id.image_Song, R.drawable.ic_music_default);
        manager.notify(1, notification);
    }
    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static void setMediaPlayer(MediaPlayer mediaPlayer) {
        MusicService.mediaPlayer = mediaPlayer;
    }

    public static int getPos() {
        return pos;
    }

    public static void setPos(int pos) {
        MusicService.pos = pos;
    }

}
