package com.example.admin.videolendingsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.videolendingsystem.Customer.CustomersActivity;
import com.example.admin.videolendingsystem.Employee.EmployeesActivity;
import com.example.admin.videolendingsystem.Login.HomeActivity;
import com.example.admin.videolendingsystem.Login.LoginActivity;
import com.example.admin.videolendingsystem.RentedVideo.RentedVideosActivity;
import com.example.admin.videolendingsystem.Video.VideosActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class UserActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mfirebaseuser = mAuth.getCurrentUser();
        if(mfirebaseuser==null){
            Intent intent = new Intent(UserActivity.this,LoginActivity.class);
            startActivity(intent);
        }

        TextView textview_usercustomer = (TextView) findViewById(R.id.user_customer);
        textview_usercustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, CustomersActivity.class);
                startActivity(intent);
            }
        });

        TextView textview_uservideo = (TextView) findViewById(R.id.user_videos);
        textview_uservideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, VideosActivity.class);
                startActivity(intent);
            }
        });

        TextView textview_userrentedvideos = (TextView) findViewById(R.id.user_rentedVideos);
        textview_userrentedvideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, RentedVideosActivity.class);
                startActivity(intent);
            }
        });

        Button button_userlogout = (Button) findViewById(R.id.user_logout);
        button_userlogout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mAuth.signOut();

                Intent intent = new Intent(UserActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });


    }
}
