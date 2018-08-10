package com.hktstudio.music.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.hktstudio.music.R;
import com.hktstudio.music.activities.MainActivity;
import com.hktstudio.music.adapters.AdapterArtist;
import com.hktstudio.music.adapters.AdapterPlaylist;
import com.hktstudio.music.fragments.FragmentPlaylist;
import com.hktstudio.music.models.Playlist;
import com.hktstudio.music.models.Song;
import com.hktstudio.music.widgets.MusicPlayer;

import java.util.List;

import static com.hktstudio.music.widgets.MusicPlayer.updatePlaylist;

/**
 * Created by HOANG on 5/12/2018.
 */

public class CreatePlaylistDialog extends DialogFragment {
    public static CreatePlaylistDialog instance;

    public CreatePlaylistDialog(){
        instance = this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialDialog.Builder(instance.getContext())
                .title("Tạo danh sách")
                .inputRange(1,50)
                .positiveText("Tạo")
                .negativeText("Thoát")
                .input("", "Danh sách phát...", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        // Do something
                        long playistId = MusicPlayer.createPlaylist(getActivity(), input.toString());
                        updatePlaylist();
                    }
                }).show();
    }
}
