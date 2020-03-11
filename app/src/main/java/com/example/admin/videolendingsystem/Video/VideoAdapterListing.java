package com.example.admin.videolendingsystem.Video;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.videolendingsystem.R;

import java.util.ArrayList;

public class VideoAdapterListing extends BaseAdapter {
    String CustKey;

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Videoadd> users;

    private VideoAdapterListing(Context con, ArrayList<Videoadd> users)
    {
        context=con;
        layoutInflater = LayoutInflater.from(context);
        this.users=users;
    }


    @Override
    public int getCount() {
        return users.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.activity_video_adapter_listing, null, false);
            holder = new ViewHolder();
            holder.videoName = (TextView) convertView.findViewById(R.id.avideo_videoName);
            holder.genre = (TextView) convertView.findViewById(R.id.avideo_genre);
            holder.director = (TextView) convertView.findViewById(R.id.avideo_director);
            holder.rating = (TextView) convertView.findViewById(R.id.avideo_rating);
            holder.releaseDate = (TextView) convertView.findViewById(R.id.avideo_releaseDate);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        Videoadd user=users.get(position);
        holder.videoName.setText("Name:"+ "\t" + user.getVideoName());
        holder.genre.setText("Genre:"+ "\t"+ user.getGenre());
        holder.director.setText("Director:"+ "\t"+ user.getDirector());
        holder.rating.setText("Rating:"+ "\t"+ user.getRating());
        holder.releaseDate.setText("Release Date:"+ "\t" + user.getReleaseDate());
        CustKey = user.getVideoKey();
        return convertView;
    }

    public class ViewHolder {
        TextView videoName,genre, director, rating, releaseDate;
    }
    @Override
    public Object getItem(int position) {
        return users.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

}
