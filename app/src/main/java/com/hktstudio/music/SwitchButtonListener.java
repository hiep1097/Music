package com.hktstudio.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hktstudio.music.controls.Control;
import com.hktstudio.music.defines.Define;
import com.hktstudio.music.service.MusicService;

/**
 * Created by HOANG on 3/22/2018.
 */

public class SwitchButtonListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().compareTo(Define.actPrevious)==0){
            Toast.makeText(context,"previous",Toast.LENGTH_SHORT).show();
            Control.previous(context);
        } else if (intent.getAction().compareTo(Define.actPlay)==0){
            Toast.makeText(context,"play",Toast.LENGTH_SHORT).show();
            Control.play(context);
        } else if (intent.getAction().compareTo(Define.actNext)==0){
            Toast.makeText(context,"next",Toast.LENGTH_SHORT).show();
            Control.next(context);
        } else if (intent.getAction().compareTo(Define.actStart)==0){
            Toast.makeText(context,"start",Toast.LENGTH_SHORT).show();
            Control.start(context);
        } else if (intent.getAction().compareTo(Define.actExit)==0){
            Toast.makeText(context,"exit",Toast.LENGTH_SHORT).show();
            Control.exit(context);
        }
    }
}
