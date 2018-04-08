package com.hktstudio.music.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hktstudio.music.R;
import com.hktstudio.music.activities.MainActivity;
import com.hktstudio.music.models.Album;
import com.hktstudio.music.models.Artist;

import java.util.List;

/**
 * Created by HOANG on 3/30/2018.
 */

public class AdapterArtist extends RecyclerView.Adapter<AdapterArtist.ViewHolder>{
    List<Artist> list;
    Context context;
    LayoutInflater inflater;
    int pos = 0;
    public static AdapterArtist instance;
    public AdapterArtist(Context context, List<Artist> list) {
        instance = this;
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = inflater.inflate(R.layout.item_artist,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.image_Artist.setImageDrawable(Drawable.createFromPath(list.get(position).getAlbum_art()));
        holder.tv_Artist.setText(list.get(position).getArtist());
        holder.tv_NumOfAlbums.setText(list.get(position).getNum_of_albums());
        holder.tv_NumOfSongs.setText(list.get(position).getNum_of_songs());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("artistID",list.get(position).getId());
                bundle.putString("image_Artist",list.get(position).getAlbum_art());
                bundle.putString("tv_Artist",list.get(position).getArtist());
                MainActivity.addFragmentDetailArtist(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image_Artist;
        TextView tv_Artist, tv_NumOfAlbums, tv_NumOfSongs;
        public ViewHolder(View itemView) {
            super(itemView);
            image_Artist = itemView.findViewById(R.id.image_Artist);
            tv_Artist = itemView.findViewById(R.id.tv_Artist);
            tv_NumOfAlbums = itemView.findViewById(R.id.tv_NumOfAlbums);
            tv_NumOfSongs = itemView.findViewById(R.id.tv_NumOfSongs);
        }
    }
}

