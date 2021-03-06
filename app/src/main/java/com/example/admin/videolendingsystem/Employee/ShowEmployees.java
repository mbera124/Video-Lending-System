package com.example.admin.videolendingsystem.Employee;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View;
import android.content.Context;
import android.widget.Toast;

import com.example.admin.videolendingsystem.AESCrypt;
import com.example.admin.videolendingsystem.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.scottyab.aescrypt.AESCrypt;


import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class ShowEmployees extends AppCompatActivity {

    ListView allusers;
    ProgressDialog mProgressDialog;



//Config config = new Config(this, "New Customer");
 //   Firebase ref = new ref.getInstance().getReference();

    AdapterListing adapter;
    ArrayList<Newcustomer> users = new ArrayList<>();

    DatabaseReference ref;
   // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
   // Firebase ref = new Firebase(Config.FIREBASE_URL);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_employees);

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);


       // Firebase.setAndroidContext(this);

        allusers = (ListView) findViewById(R.id.allusers);
        adapter = new AdapterListing(ShowEmployees.this, users);
        allusers.setAdapter(adapter);
        getDataFromServer();


    }


    public void getDataFromServer() {
        showProgressDialog();
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");
        ref.child("Employee").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Newcustomer newcustomer = postSnapShot.getValue(Newcustomer.class);
                        users.add(newcustomer);
                        adapter.notifyDataSetChanged();
                    }
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowEmployees.this, "Error", Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(ShowEmployees.this);
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

    private class AdapterListing extends BaseAdapter {
        Context context;
        LayoutInflater layoutInflater;
        ArrayList<Newcustomer> users;

        public AdapterListing(Context con, ArrayList<Newcustomer> users) {
            context = con;
            layoutInflater = LayoutInflater.from(context);
            this.users = users;
        }

        @Override
        public int getCount() {
            return users.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.activity_adapter_listing, null, false);
                holder = new ViewHolder();
                holder.fullname = (TextView) convertView.findViewById(R.id.user_fullname);
                holder.phonenumber = (TextView) convertView.findViewById(R.id.user_phonenumber);
                holder.natId = (TextView) convertView.findViewById(R.id.user_natId);
                holder.address = (TextView) convertView.findViewById(R.id.user_address);
                holder.gender = (TextView) convertView.findViewById(R.id.user_gender);
                holder.dateEmployed = (TextView) convertView.findViewById(R.id.user_dateemployed);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Newcustomer user = users.get(position);
            AESCrypt aesCrypt = new AESCrypt();

            try {
                holder.fullname.setText("Name:"+ " " + user.getFName()+ " "+ user.getLName());
                holder.phonenumber.setText("Phone Number:"+ " "+ aesCrypt.decrypt("password",user.getPhoneNumber()));
                holder.natId.setText("National Id:"+ " "+ aesCrypt.decrypt("password",user.getNatId()));
                holder.address.setText("Address:"+ " "+ user.getAddress());
                holder.gender.setText("Gender:"+ " " + user.getGender());
                holder.dateEmployed.setText("Date Employed:"+ " "+ user.getDateEmployed());
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }

            return convertView;
        }

        public class ViewHolder {
            TextView fullname, phonenumber, natId, address, gender,dateEmployed;
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
