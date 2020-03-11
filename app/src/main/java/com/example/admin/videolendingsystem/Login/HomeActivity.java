package com.example.admin.videolendingsystem.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.videolendingsystem.Customer.CustomersActivity;
import com.example.admin.videolendingsystem.Employee.EmployeesActivity;
import com.example.admin.videolendingsystem.R;
import com.example.admin.videolendingsystem.RentedVideo.RentedVideosActivity;
import com.example.admin.videolendingsystem.UserActivity;
import com.example.admin.videolendingsystem.Video.VideosActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mfirebaseuser = mAuth.getCurrentUser();
        if(mfirebaseuser==null){
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        } else if(mfirebaseuser.getEmail().equalsIgnoreCase("administrator@gmail.com")){

        } else {
            Intent intent = new Intent(HomeActivity.this, UserActivity.class);
           startActivity(intent);
            finish();
        }

        TextView textview_employees = (TextView) findViewById(R.id.textView_employees);
        textview_employees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,EmployeesActivity.class);
                startActivity(intent);
            }
        });

        TextView textview_customers = (TextView) findViewById(R.id.textView_customers);
        textview_customers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,CustomersActivity.class);
                startActivity(intent);
            }
        });

        TextView textview_videos = (TextView) findViewById(R.id.textView_videos);
        textview_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,VideosActivity.class);
                startActivity(intent);
            }
        });

        TextView textview_rentedvideos = (TextView) findViewById(R.id.textView_rentedvideos);
        textview_rentedvideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,RentedVideosActivity.class);
                startActivity(intent);
            }
        });

        TextView textview_register = (TextView) findViewById(R.id.textView_register);
        textview_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button button_logout = (Button) findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                mAuth.signOut();

                Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });




    }


}
