package com.example.admin.videolendingsystem.Video;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.videolendingsystem.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EditVideo extends AppCompatActivity {

    ListView allVideosedit;
    ProgressDialog mProgressDialog;



    DatabaseReference ref;

    VideoAdapterListing videoAdapterEdit;
    ArrayList<Videoadd> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_video);


        allVideosedit = (ListView) findViewById(R.id.allvideoedit);
        videoAdapterEdit = new VideoAdapterListing(EditVideo.this, users);
        allVideosedit.setAdapter(videoAdapterEdit);
        getDataFromServer();


        allVideosedit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Intent intent = new Intent(EditVideo.this,VideoChange.class);
                Videoadd videoadd = users.get(position);
                intent.putExtra("videoname",videoadd.getVideoName());
                intent.putExtra("genre",videoadd.getGenre());
                intent.putExtra("director",videoadd.getDirector());
                intent.putExtra("rating",videoadd.getRating());
                intent.putExtra("releasedate",videoadd.getReleaseDate());
                intent.putExtra("custkey", videoadd.getVideoKey());
                startActivity(intent);
            }
        });

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
                        videoAdapterEdit.notifyDataSetChanged();
                    }
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(EditVideo.this, "Error", Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(EditVideo.this);
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
