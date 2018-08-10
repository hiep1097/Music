package com.hktstudio.music.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hktstudio.music.R;
import com.hktstudio.music.activities.MainActivity;
import com.hktstudio.music.models.Album;
import com.hktstudio.music.models.Playlist;
import com.hktstudio.music.widgets.MusicPlayer;

import java.util.List;

import static com.hktstudio.music.widgets.MusicPlayer.updatePlaylist;

/**
 * Created by HOANG on 4/10/2018.
 */

public class AdapterPlaylist extends RecyclerView.Adapter<AdapterPlaylist.ViewHolder>{
    List<Playlist> list;
    Context context;
    LayoutInflater inflater;
    int pos = 0;
    public static AdapterPlaylist instance;
    public AdapterPlaylist(Context context, List<Playlist> list) {
        instance = this;
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.item_playlist,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_Playlist.setText(list.get(position).getName());
        holder.tv_Count.setText(list.get(position).getSongCount()+" bài hát");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putLong("playlistID",list.get(position).getId());
                bundle.putString("tv_Playlist",list.get(position).getName());
                bundle.putInt("tv_Count",list.get(position).getSongCount());
                MainActivity.addFragmentDetailPlaylist(bundle);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MusicPlayer.deletePlaylist(context,list.get(position).getName());
                updatePlaylist();
                Toast.makeText(context,"Đã xóa playlist",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_Playlist, tv_Count;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_Playlist = itemView.findViewById(R.id.tv_Playlist);
            tv_Count = itemView.findViewById(R.id.tv_Count);
        }
    }
}

