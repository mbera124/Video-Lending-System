package com.example.admin.videolendingsystem.Video;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.videolendingsystem.R;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShowVideos extends AppCompatActivity {

    ListView allVideos;
    ProgressDialog mProgressDialog;


    //Firebase ref = new Firebase(Config.FIREBASE_URL);
    DatabaseReference ref;

    VideoAdapterListing adapter;
    ArrayList<Videoadd> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_videos);

       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
       // ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");
       // Firebase.setAndroidContext(this);

        allVideos = (ListView) findViewById(R.id.allvideos);
        adapter = new VideoAdapterListing(ShowVideos.this, users);
        allVideos.setAdapter(adapter);
        getDataFromServer();



    }


    public void getDataFromServer() {
        showProgressDialog();
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");
        ref.child("Video").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Videoadd videoadd = postSnapShot.getValue(Videoadd.class);
                        users.add(videoadd);
                        adapter.notifyDataSetChanged();
                    }
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowVideos.this, "Error", Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });
    }



    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(ShowVideos.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }


    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    public class VideoAdapterListing extends BaseAdapter {


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


            Videoadd user = users.get(position);
            holder.videoName.setText("Name:"+ "\t" + user.getVideoName());
            holder.genre.setText("Genre:"+ "\t"+ user.getGenre());
            holder.director.setText("Director:"+ "\t"+ user.getDirector());
            holder.rating.setText("Rating:"+ "\t"+ user.getRating());
            holder.releaseDate.setText("Release Date:"+ "\t" + user.getReleaseDate());
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

}
