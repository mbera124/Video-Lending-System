package com.example.admin.videolendingsystem.Customer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.admin.videolendingsystem.R;

public class CustomersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customers);

        TextView textview_customersadd = (TextView) findViewById(R.id.textView_customeradd);
        textview_customersadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomersActivity.this,AddCustomer.class);
                startActivity(intent);
            }
        });

        TextView textview_customersview = (TextView) findViewById(R.id.textView_customerview);
        textview_customersview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomersActivity.this,ShowCustomers.class);
                startActivity(intent);
            }
        });

        TextView textview_customersedit = (TextView) findViewById(R.id.textView_customeredit);
        textview_customersedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomersActivity.this,EditCustomer.class);
                startActivity(intent);
            }
        });


        TextView textview_customersdelete = (TextView) findViewById(R.id.textView_customerdelete);
        textview_customersdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomersActivity.this,DeleteCustomer.class);
                startActivity(intent);
            }
        });
    }
}
