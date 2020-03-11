package com.example.admin.videolendingsystem.Employee;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.videolendingsystem.AESCrypt;
import com.example.admin.videolendingsystem.R;
//import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class AdapterListing extends BaseAdapter {

   // @Override
    //protected void onCreate(Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_adapter_listing);
   // }


    Context context;
    LayoutInflater layoutInflater;
    ArrayList<Newcustomer> users;

    public AdapterListing(Context con, ArrayList<Newcustomer> users)
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

        Newcustomer user=users.get(position);


        try {
            AESCrypt aesCrypt = new AESCrypt();
            holder.fullname.setText("Name:"+ "\t" + user.getFName()+ " "+ user.getLName());
            //holder.phonenumber.setText("Phone Number:"+ "\t"+ user.getPhoneNumber());
            // holder.natId.setText("National Id:"+ "\t"+ user.getNatId());
            holder.phonenumber.setText("Phone Number:"+ " "+ aesCrypt.decrypt("password",user.getPhoneNumber()));
            holder.natId.setText("National Id:"+ " "+ aesCrypt.decrypt("password",user.getNatId()));
            holder.address.setText("Address:"+ " "+ user.getAddress());
            holder.gender.setText("Gender:"+ " " + user.getGender());
            holder.dateEmployed.setText("Date Employed:"+ " "+ user.getDateEmployed());
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        /*holder.fullname.setText("Name:"+ "\t" + user.getFName()+ " "+ user.getLName());
        holder.phonenumber.setText("Phone Number:"+ " "+ user.getPhoneNumber());
        holder.natId.setText("National Id:"+ "\t"+ user.getNatId());
        holder.address.setText("Address:"+ "\t"+ user.getAddress());
        holder.gender.setText("Gender:"+ "\t" + user.getGender());
        holder.dateEmployed.setText("Date Employed:"+ "\t"+ user.getDateEmployed());
        */
        return convertView;
    }

    public class ViewHolder {
        TextView fullname,phonenumber, natId, address, gender,dateEmployed;;
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
