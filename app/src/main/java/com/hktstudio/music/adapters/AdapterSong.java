package com.hktstudio.music.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hktstudio.music.R;
import com.hktstudio.music.controls.Control;
import com.hktstudio.music.defines.Define;
import com.hktstudio.music.models.Song;
import com.hktstudio.music.service.MusicService;

import java.util.List;

import static com.hktstudio.music.service.MusicService.setPos;

/**
 * Created by HOANG on 3/20/2018.
 */

public class AdapterSong extends RecyclerView.Adapter<AdapterSong.ViewHolder>{
    List<Song> list;
    Context context;
    LayoutInflater inflater;
    int pos = 0;
    public static AdapterSong instance;
    public AdapterSong(Context context, List<Song> list) {
        instance = this;
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.item_song,parent,false);
        return new ViewHolder(item);
    }

    public static void setCurrentPos(int pos){
        instance.pos = pos;
        instance.notifyDataSetChanged();
    }

    public int getCurrentPos(){
        return pos;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.image_Song.setImageDrawable(Drawable.createFromPath(list.get(position).getAlbum_art()));
        holder.tv_Song.setText(list.get(position).getName());
        holder.tv_Artist.setText(list.get(position).getArtist());
        if (pos == position){
            holder.tv_Song.setTextColor(Color.MAGENTA);
            holder.tv_Artist.setTextColor(Color.MAGENTA);
        } else {
            holder.tv_Song.setTextColor(Color.BLACK);
            holder.tv_Artist.setTextColor(Color.BLACK);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pos = position;
                notifyDataSetChanged();
                setPos(position);
                Intent intent = new Intent(context, MusicService.class);
                intent.setAction(Define.actStart);
                context.startService(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image_Song;
        TextView tv_Song, tv_Artist;
        public ViewHolder(View itemView) {
            super(itemView);
            image_Song = itemView.findViewById(R.id.image_Song);
            tv_Song = itemView.findViewById(R.id.tv_Song);
            tv_Artist = itemView.findViewById(R.id.tv_Artist);
        }
    }
}
