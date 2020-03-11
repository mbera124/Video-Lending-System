package com.example.admin.videolendingsystem.Employee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.admin.videolendingsystem.R;

public class EmployeesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employees);

        TextView textview_employees = (TextView) findViewById(R.id.textView_adde);
        textview_employees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeesActivity.this,AddEmployee.class);
                startActivity(intent);
            }
        });


        TextView textview_employeesv = (TextView) findViewById(R.id.textView_viewe);
        textview_employeesv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeesActivity.this,ShowEmployees.class);

                startActivity(intent);
            }
        });


        TextView textview_employeese = (TextView) findViewById(R.id.textView_edite);
        textview_employeese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeesActivity.this,EditEmployees.class);
                startActivity(intent);

            }
        });


        TextView textview_employeesd = (TextView) findViewById(R.id.textView_deletee);
        textview_employeesd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeesActivity.this,DeleteEmployee.class);
                startActivity(intent);

            }
        });
    }
}
