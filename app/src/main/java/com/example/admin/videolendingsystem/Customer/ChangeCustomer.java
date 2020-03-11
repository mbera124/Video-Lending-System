package com.example.admin.videolendingsystem.Customer;

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

public class ChangeCustomer extends AppCompatActivity {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextAddress;
    private EditText editTextPhoneNumber;
    private EditText editTextNatId;
    private EditText editTextGender;
    private EditText editTextRegisteredBy;
    private Button savecustomerbutton;

    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_customer);

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");
        String fname = intent.getStringExtra("fname");
        String lname = intent.getStringExtra("lname");
        String registeredby = intent.getStringExtra("registeredby");
        String id = intent.getStringExtra("id");
        String number = intent.getStringExtra("number");
        String gender = intent.getStringExtra("gender");
        final String custkey = intent.getStringExtra("custkey");

        savecustomerbutton = (Button)findViewById(R.id.ccchangeSaveButton);
        editTextAddress = (EditText)findViewById(R.id.ccchange_address);
        editTextFirstName = (EditText) findViewById(R.id.ccchange_firstName);
        editTextLastName = (EditText) findViewById(R.id.ccchange_lastName);
        editTextGender = (EditText) findViewById(R.id.ccchange_gender);
        editTextNatId = (EditText) findViewById(R.id.ccchange_natId);
        editTextPhoneNumber = (EditText) findViewById(R.id.ccchange_phoneNumber);
        editTextRegisteredBy = (EditText) findViewById(R.id.ccchange_registeredBy);

        editTextAddress.setText(address);
        editTextFirstName.setText(fname);
        editTextLastName.setText(lname);
        editTextGender.setText(gender);
        editTextNatId.setText(id);
        editTextPhoneNumber.setText(number);
        editTextRegisteredBy.setText(registeredby);

        savecustomerbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Custadd custadd = new Custadd();

                String PhoneNumber = editTextPhoneNumber.getText().toString().trim();
                String NatId = editTextNatId.getText().toString().trim();
                try {
                    String password = "videolendingsystem";
                    AESCrypt aesCrypt = new AESCrypt();
                    PhoneNumber= aesCrypt.encrypt(password,PhoneNumber);
                    NatId = aesCrypt.encrypt(password, NatId);
                }catch (GeneralSecurityException e){
                    //handle error
                }

                custadd.setRegisteredBy(editTextRegisteredBy.getText().toString());
                custadd.setAddress(editTextAddress.getText().toString());
                custadd.setFName(editTextFirstName.getText().toString());
                custadd.setLName(editTextLastName.getText().toString());
                custadd.setGender(editTextGender.getText().toString());
                custadd.setPhoneNumber(PhoneNumber);
                custadd.setNatId(NatId);
                custadd.setCustomerKey(custkey);

                ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");
                DatabaseReference userRef = ref.child("Customer");
                userRef.child(custkey).setValue(custadd, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if(databaseError != null){
                            Toast.makeText(getApplicationContext(), "  error ", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Data saved successfully!!!", Toast.LENGTH_LONG).show();
                        }

                    }
                });
            }
        });
    }
}
