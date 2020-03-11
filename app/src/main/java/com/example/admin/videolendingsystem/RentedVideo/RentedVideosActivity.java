package com.example.admin.videolendingsystem.RentedVideo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.admin.videolendingsystem.Customer.ShowCustomers;
import com.example.admin.videolendingsystem.R;

public class RentedVideosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rented_videos);


        TextView textview_rentedvideoadd = (TextView) findViewById(R.id.rentedvideo_add);
        textview_rentedvideoadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentedVideosActivity.this,AddRentedVideos.class);
                startActivity(intent);
            }
        });

        TextView textview_rentedvideoview = (TextView) findViewById(R.id.rentedvideo_view);
        textview_rentedvideoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentedVideosActivity.this, ShowRentedVideos.class);
                startActivity(intent);
            }
        });

        TextView textview_rentedvideoedit = (TextView) findViewById(R.id.rentedvideo_edit);
        textview_rentedvideoedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentedVideosActivity.this,EditRentedVideos.class);
                startActivity(intent);
            }
        });

        TextView textview_rentedvideodelete = (TextView) findViewById(R.id.rentedvideo_delete);
        textview_rentedvideodelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RentedVideosActivity.this,DeleteRentedVideos.class);
                startActivity(intent);
            }
        });

    }
}
