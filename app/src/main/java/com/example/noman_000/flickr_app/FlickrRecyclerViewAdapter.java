package com.example.noman_000.flickr_app;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

class FlickrRecyclerViewAdapter extends RecyclerView.Adapter<FlickrRecyclerViewAdapter.FlickrIMageViewHolder>{
    private List<Photo> photos;
    private Context context;
    private LayoutInflater inflater;
    public FlickrRecyclerViewAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    static class FlickrIMageViewHolder extends RecyclerView.ViewHolder{
        ImageView thuMbnail = null;
        TextView title = null;
        FlickrIMageViewHolder(View iteMView) {
            super(iteMView);
            thuMbnail = (ImageView) iteMView.findViewById(R.id.thuMbnail);
            title = (TextView) iteMView.findViewById(R.id.title);
        }
    }

    @NonNull
    @Override
    public FlickrIMageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // called by LayoutManager when new view holder needed
        View view = inflater.inflate(R.layout.browse, parent, false);
        return new FlickrIMageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlickrIMageViewHolder holder, int position) {
        //  called by RecyclerView
        if(photos == null || photos.size() == 0){
            holder.thuMbnail.setImageResource(R.drawable.placeholder);
            holder.title.setText(R.string.no_photo);
        }
        else {
            holder.title.setText(photos.get(position).getTitle());
            Picasso.with(context).load(photos.get(position).getImageUrl()).
                    placeholder(R.drawable.placeholder).error(R.drawable.error).
                    into(holder.thuMbnail);
        }
    }

    @Override
    public int getItemCount() {
        return ((photos != null && photos.size() != 0) ? photos.size() : 1);
    }
    void loadNewData(List<Photo> newPhotos){
        Toast.makeText(context, "loadNew data called", Toast.LENGTH_LONG).show();
        photos = newPhotos;
        if(photos.size() == 0){
            Toast.makeText(context, "no photos found", Toast.LENGTH_LONG).show();
        }
        notifyDataSetChanged();
    }
    Photo getPhoto(int position){
        return (((photos != null) && (photos.size() != 0)) ? photos.get(position) : null);
    }
}
