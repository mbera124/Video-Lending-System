package com.example.admin.videolendingsystem.RentedVideo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.videolendingsystem.R;

import java.util.ArrayList;

public class AdapterListingRentedVideos extends BaseAdapter {


    //String rentedVideoKey;

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Rentedvideosadd> users;

    private AdapterListingRentedVideos(Context con, ArrayList<Rentedvideosadd> users)
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
            convertView = layoutInflater.inflate(R.layout.activity_adapter_listing_rented_videos, null, false);
            holder = new ViewHolder();
            holder.fullname = (TextView) convertView.findViewById(R.id.rented_fullname);
            holder.videoname = (TextView) convertView.findViewById(R.id.rented_video);
            holder.daterented = (TextView) convertView.findViewById(R.id.rented_dateRented);
            holder.expectedreturndate = (TextView) convertView.findViewById(R.id.rented_expRentedDate);
            holder.returneddate = (TextView) convertView.findViewById(R.id.rented_returnedDate);
            holder.penalty = (TextView) convertView.findViewById(R.id.rented_penalty);
            holder.status = (TextView) convertView.findViewById(R.id.rented_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Rentedvideosadd user=users.get(position);
        holder.fullname.setText("Name:"+ "\t" + user.getFullName());
        holder.videoname.setText("Video:"+ "\t"+ user.getVideoName());
        holder.daterented.setText("Date Rented"+ "\t"+ user.getDateRented());
        holder.expectedreturndate.setText("Expected Returned Date:"+ "\t"+ user.getExpectedReturnDate());
        holder.returneddate.setText("Returned Date:"+ "\t" + user.getReturnedDate());
        holder.penalty.setText("Penalty:"+ "\t"+ user.getPenalty());
        holder.status.setText("Status:"+ "\t"+ user.getStatus());
       // rentedVideoKey = user.getRentedvideokey();
        return convertView;
    }

    public class ViewHolder {
        TextView fullname,videoname, daterented, expectedreturndate, returneddate,penalty,status;
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
