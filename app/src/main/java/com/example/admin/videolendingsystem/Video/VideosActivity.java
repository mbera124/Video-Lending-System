package com.example.admin.videolendingsystem.Video;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.admin.videolendingsystem.R;

public class VideosActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);


        TextView textview_videoadd = (TextView) findViewById(R.id.textView_videoadd);
        textview_videoadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideosActivity.this,AddVideo.class);
                startActivity(intent);
            }
        });


        TextView textview_videoview = (TextView) findViewById(R.id.textView_videoview);
        textview_videoview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideosActivity.this,ShowVideos.class);
                startActivity(intent);
            }
        });

        TextView textview_videoedit = (TextView) findViewById(R.id.textView_videoedit);
        textview_videoedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideosActivity.this,EditVideo.class);
                startActivity(intent);
            }
        });

        TextView textview_videodelete = (TextView) findViewById(R.id.textView_videodelete);
        textview_videodelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideosActivity.this,DeleteVideo.class);
                startActivity(intent);
            }
        });

    }
}
