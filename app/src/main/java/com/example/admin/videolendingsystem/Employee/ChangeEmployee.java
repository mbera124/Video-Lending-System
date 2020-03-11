package com.example.admin.videolendingsystem.Employee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.videolendingsystem.AESCrypt;
import com.example.admin.videolendingsystem.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class ChangeEmployee extends AppCompatActivity {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextAddress;
    private EditText editTextPhoneNumber;
    private EditText editTextNatId;
    private EditText editTextGender;
    private EditText editTextDateEmployed;

    private Button saveemployeebutton;

    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_employee);

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String fname = intent.getStringExtra("fname");
        String lname = intent.getStringExtra("lname");
        String date = intent.getStringExtra("date");
        String id = intent.getStringExtra("id");
        String number = intent.getStringExtra("number");
        String gender = intent.getStringExtra("gender");
        final String custkey = intent.getStringExtra("custkey");


       saveemployeebutton = (Button)findViewById(R.id.changeSaveButton);
        editTextAddress = (EditText)findViewById(R.id.change_address);
        editTextFirstName = (EditText) findViewById(R.id.change_firstName);
        editTextLastName = (EditText) findViewById(R.id.change_lastName);
        editTextGender = (EditText) findViewById(R.id.change_gender);
        editTextNatId = (EditText) findViewById(R.id.change_NatId);
        editTextPhoneNumber = (EditText) findViewById(R.id.change_PhoneNumber);
        editTextDateEmployed = (EditText) findViewById(R.id.change_dateEmployed);

        editTextAddress.setText(address);
        editTextFirstName.setText(fname);
        editTextLastName.setText(lname);
        editTextGender.setText(gender);
        editTextNatId.setText(id);
        editTextPhoneNumber.setText(number);
        editTextDateEmployed.setText(date);

        saveemployeebutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Newcustomer newcustomer = new Newcustomer();
                String PhoneNumber = editTextPhoneNumber.getText().toString().trim();
                String NatId = editTextNatId.getText().toString().trim();
                try {
                    String password = "password";
                    AESCrypt aesCrypt = new AESCrypt();
                    PhoneNumber= aesCrypt.encrypt(password,PhoneNumber);
                    NatId = aesCrypt.encrypt(password, NatId);
                }catch (GeneralSecurityException e){
                    //handle error
                }
                newcustomer.setDateEmployed(editTextDateEmployed.getText().toString());
                newcustomer.setAddress(editTextAddress.getText().toString());
                newcustomer.setFName(editTextFirstName.getText().toString());
                newcustomer.setLName(editTextLastName.getText().toString());
                newcustomer.setGender(editTextGender.getText().toString());
                newcustomer.setPhoneNumber(PhoneNumber);
                newcustomer.setNatId(NatId);

                //newcustomer.setPhoneNumber(editTextPhoneNumber.getText().toString());
                //newcustomer.setNatId(editTextNatId.getText().toString());
                newcustomer.setEmployeeKey(custkey);


              //  Firebase ref = new Firebase(Config.FIREBASE_URL);
                ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");

                DatabaseReference userRef = ref.child("Employee");
                userRef.child(custkey).setValue(newcustomer, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(getApplicationContext(), "  error ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data saved successfully!!!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
    }

}
