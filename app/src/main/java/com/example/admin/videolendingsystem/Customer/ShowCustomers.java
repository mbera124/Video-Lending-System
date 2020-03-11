package com.example.admin.videolendingsystem.Customer;

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

import com.example.admin.videolendingsystem.AESCrypt;
import com.example.admin.videolendingsystem.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class ShowCustomers extends AppCompatActivity {

    ListView allcustomers;
    ProgressDialog mProgressDialog;
    DatabaseReference ref;
    CustomerAdapterListing adapter;
    ArrayList<Custadd> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_customers);

        allcustomers = (ListView) findViewById(R.id.allcustomers);
        adapter = new CustomerAdapterListing(ShowCustomers.this, users);
        allcustomers.setAdapter(adapter);
        getDataFromServer();

    }
    public void getDataFromServer() {
        showProgressDialog();
        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");
        ref.child("Customer").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Custadd custadd = postSnapShot.getValue(Custadd.class);
                        users.add(custadd);
                        adapter.notifyDataSetChanged();
                    }
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ShowCustomers.this, "Error", Toast.LENGTH_LONG).show();
                hideProgressDialog();
            }
        });
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(ShowCustomers.this);
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



    public class CustomerAdapterListing extends BaseAdapter {

        Context context;
        LayoutInflater layoutInflater;
        ArrayList<Custadd> users;

        private CustomerAdapterListing(Context con, ArrayList<Custadd> users)
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
                convertView = layoutInflater.inflate(R.layout.activity_customer_adapter_listing, null, false);
                holder = new ViewHolder();
                holder.fullname = (TextView) convertView.findViewById(R.id.acustomer_fullname);
                holder.phonenumber = (TextView) convertView.findViewById(R.id.acustomer_phonenumber);
                holder.natId = (TextView) convertView.findViewById(R.id.acustomer_natId);
                holder.address = (TextView) convertView.findViewById(R.id.acustomer_address);
                holder.gender = (TextView) convertView.findViewById(R.id.acustomer_gender);
                holder.registeredBy = (TextView) convertView.findViewById(R.id.acustomer_registeredBy);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Custadd user=users.get(position);
            AESCrypt aesCrypt = new AESCrypt();
            try {

                holder.fullname.setText("Name:"+ " "  + user.getFName()+  " "+ user.getLName());
                holder.phonenumber.setText("Phone Number:"+ "\t"+ aesCrypt.decrypt("videolendingsystem",user.getPhoneNumber()));

                holder.address.setText("Address:"+ "\t"+ user.getAddress());
                holder.gender.setText("Gender:"+ "\t" + user.getGender());
                holder.registeredBy.setText("Registered By:"+ "\t"+ user.getRegisteredBy());
                holder.natId.setText("National Id:"+ "\t"+ aesCrypt.decrypt("videolendingsystem",user.getNatId()));
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
            }
            return convertView;
        }

        public class ViewHolder {
            TextView fullname,phonenumber, natId, address, gender,registeredBy;
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
