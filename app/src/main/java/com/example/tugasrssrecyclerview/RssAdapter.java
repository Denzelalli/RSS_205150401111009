package com.example.tugasrssrecyclerview;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RssAdapter extends RecyclerView.Adapter<RssAdapter.RssViewHolder> {
    LayoutInflater inflater;
    Context _context;
    ArrayList<Artikel> data;

    public RssAdapter(Context _context, ArrayList<Artikel> data) {
        this._context = _context;
        this.data = data;
        this.inflater = LayoutInflater.from(this._context);
    }

    @NonNull
    @Override
    public RssAdapter.RssViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row,parent,false);
        return new RssViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RssAdapter.RssViewHolder holder, int position) {
        Artikel artikel = data.get(position);

        String judul = artikel.judul;
        String tanggal = artikel.pubDate;
        if(artikel.linkGambar.length() > 5) {
            String gambar = artikel.linkGambar.replace("localhost","10.0.2.2");
            PicassoClient.downloadImage(_context,gambar,holder.ivGambar);
        } else {
            holder.ivGambar.setVisibility(View.GONE);
        }

        holder.tvJudul.setText(judul);
        holder.tvPubDate.setText(tanggal);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class RssViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvJudul, tvPubDate;
        ImageView ivGambar;

        public RssViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvPubDate = itemView.findViewById(R.id.tv_pubdate);
            ivGambar = itemView.findViewById(R.id.iv_gambar);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Intent it = new Intent();
            it.setAction(Intent.ACTION_VIEW);
            it.addCategory(Intent.CATEGORY_BROWSABLE);
            it.setData(Uri.parse(data.get(position).link));
            _context.startActivity(it);
        }
    }
}