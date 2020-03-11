package com.example.admin.videolendingsystem.Customer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.videolendingsystem.AESCrypt;
import com.example.admin.videolendingsystem.Customer.Custadd;
import com.example.admin.videolendingsystem.R;
//import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class CustomerAdapterListing extends BaseAdapter {
    String CustKey;

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
                holder.natId.setText("National Id:"+ " "+ aesCrypt.decrypt("password",user.getNatId()));
                holder.fullname.setText("Name:"+ " " + user.getFName()+ " "+ user.getLName());
                holder.phonenumber.setText("Phone Number:"+ " "+ aesCrypt.decrypt("password",user.getPhoneNumber()));
                holder.address.setText("Address:"+ " "+ user.getAddress());
                holder.gender.setText("Gender:"+ " " + user.getGender());
                holder.registeredBy.setText("Registered By:"+ " "+ user.getRegisteredBy());
                CustKey = user.getCustomerKey();
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
