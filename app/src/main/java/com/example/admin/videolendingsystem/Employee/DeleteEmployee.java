package com.example.admin.videolendingsystem.Employee;

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
//import com.scottyab.aescrypt.AESCrypt;


import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class DeleteEmployee extends AppCompatActivity {
    ListView allusersdelete;
    ProgressDialog mProgressDialog;
    String CustKey;

    //Firebase ref = new Firebase(Config.FIREBASE_URL);
    DatabaseReference ref;
    // Firebase ref = new ref.getInstance().getReference();
    AdapterListing adapterdelete;
    ArrayList<Newcustomer> users = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employee);

        //Firebase.setAndroidContext(this);

        allusersdelete = (ListView) findViewById(R.id.allusersdelete);
        adapterdelete = new AdapterListing(DeleteEmployee.this, users);
        allusersdelete.setAdapter(adapterdelete);
        getDataFromServer();



        allusersdelete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                Toast.makeText(DeleteEmployee.this, " delete", Toast.LENGTH_LONG);
                AlertDialog.Builder adb = new AlertDialog.Builder(DeleteEmployee.this);
                adb.setTitle("Delete?");
                adb.setMessage("Are you sure you want to delete " + position);
                adb.setNegativeButton("Cancel", null);
                adb.setPositiveButton("Ok", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // new Firebase("https://MY-FIREBASE-APP.firebaseio.com/todoItems")
                        //Firebase ref = new Firebase(Config.FIREBASE_URL);
                        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");
                        ref.child("Employee").child(CustKey).setValue(null);
                       // ref.child("New Customer").child("-KkpsHokNQWYuPL5yHEW").removeValue();
                        //ref.orderByChild("fname")
                            //    .equalTo((String) allusersdelete.getItemAtPosition(position))
                              //  .addListenerForSingleValueEvent(new ValueEventListener() {
                                //    @Override
                                  //  public void onDataChange(DataSnapshot dataSnapshot) {
                                    //    if (dataSnapshot.hasChildren()) {
                                     //       DataSnapshot firstChild = dataSnapshot.getChildren().iterator().next();
                                     //       String key = firstChild.getKey();
                                      //      firstChild.getRef().child("New Customer").child(key).removeValue();
                                       //     Toast.makeText(DeleteEmployee.this,key,Toast.LENGTH_LONG ).show();
                                        //}
                                    //}



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
        ref.child("Employee").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Newcustomer newcustomer = postSnapShot.getValue(Newcustomer.class);
                        users.add(newcustomer);
                        adapterdelete.notifyDataSetChanged();
                    }
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(DeleteEmployee.this, "Error", Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });
    }

  /*
    public void getDataFromServer() {
        showProgressDialog();
        ref.child("Employee").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Newcustomer newcustomer = postSnapShot.getValue(Newcustomer.class);
                        users.add(newcustomer);
                        adapterdelete.notifyDataSetChanged();
                    }
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                hideProgressDialog();
            }

            //   @Override
            // public void onCancelled(FirebaseError FirebaseError) {
            //     Toast.makeText(ShowEmployees.this, "Error", Toast.LENGTH_LONG).show();
            // }
        });
    }
    */

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(DeleteEmployee.this);
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
                holder.fullname.setText("Name:"+ "\t" + user.getFName()+ "\t"+ user.getLName());
                //holder.phonenumber.setText("Phone Number:"+ "\t"+ user.getPhoneNumber());
                //holder.natId.setText("National Id:"+ "\t"+ user.getNatId());
                holder.phonenumber.setText("Phone Number:"+ " "+ aesCrypt.decrypt("password",user.getPhoneNumber()));
                holder.natId.setText("National Id:"+ " "+ aesCrypt.decrypt("password",user.getNatId()));
                holder.address.setText("Address:"+ "\t"+ user.getAddress());
                holder.gender.setText("Gender:"+ "\t" + user.getGender());
                holder.dateEmployed.setText("Date Employed:"+ "\t"+ user.getDateEmployed());
                CustKey = user.getEmployeeKey();
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
