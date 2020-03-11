package com.example.admin.videolendingsystem.RentedVideo;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.admin.videolendingsystem.AESCrypt;
import com.example.admin.videolendingsystem.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class DeleteRentedVideos extends AppCompatActivity {
    ListView allRentedVideosDelete;
    ProgressDialog mProgressDialog;
    String RentedVideosKey;


    DatabaseReference ref;

    // Firebase ref = new ref.getInstance().getReference();
    AdapterListingRentedVideos adapterdelete;
    ArrayList<Rentedvideosadd> users = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_rented_videos);

        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");


        allRentedVideosDelete = (ListView) findViewById(R.id.allrentedvideosdelete);
        adapterdelete = new AdapterListingRentedVideos(DeleteRentedVideos.this, users);
        allRentedVideosDelete.setAdapter(adapterdelete);
        getDataFromServer();



        allRentedVideosDelete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Toast.makeText(DeleteRentedVideos.this, " delete", Toast.LENGTH_LONG);
                AlertDialog.Builder adb = new AlertDialog.Builder(DeleteRentedVideos.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        ref.child("RentedVideos").child(RentedVideosKey).setValue(null);
                        //ref.orderByChild("fname")
                        //    .equalTo((String) allusersdelete.getItemAtPosition(position))
                        //  .addListenerForSingleValueEvent(new ValueEventListener() {


                        adapterdelete.notifyDataSetChanged();
                    }
                });
                android.app.AlertDialog alertDialog = adb.create();
                alertDialog.show();
            }
        });

    }

    public void getDataFromServer() {
        showProgressDialog();
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");
        ref.child("RentedVideos").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Rentedvideosadd rentedvideosadd = postSnapShot.getValue(Rentedvideosadd.class);
                        users.add(rentedvideosadd);
                        adapterdelete.notifyDataSetChanged();
                    }
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DeleteRentedVideos.this, "Error", Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(DeleteRentedVideos.this);
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



    public class AdapterListingRentedVideos extends BaseAdapter {


        String rentedVideoKey;

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
                holder =(ViewHolder) convertView.getTag();
            }

            Rentedvideosadd user=users.get(position);
            AESCrypt aesCrypt = new AESCrypt();
            holder.fullname.setText("Name:"+ " " + user.getFullName());
            try {
                holder.videoname.setText("Video:"+ " "+ aesCrypt.decrypt("videolendingsystem",user.getVideoName()));
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            holder.daterented.setText("Date Rented:"+ " "+ user.getDateRented());
            holder.expectedreturndate.setText("Expected Returned Date:"+ " "+ user.getExpectedReturnDate());
            holder.returneddate.setText("Returned Date:"+ " " + user.getReturnedDate());
            holder.penalty.setText("Penalty:"+ " "+" Ksh"+" "+ user.getPenalty());
            holder.status.setText("Status:"+ " "+ user.getStatus());
            RentedVideosKey = user.getRentedvideokey();
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


}
