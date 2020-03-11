package com.example.admin.videolendingsystem.Customer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

public class AddCustomer extends AppCompatActivity {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextAddress;
    private EditText editTextPhoneNumber;
    private EditText editTextNatId;
    private EditText editTextGender;
    private EditText editTextRegisteredBy;

    private Button customerbuttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        customerbuttonSave = (Button) findViewById(R.id.customerSaveButton);
        editTextFirstName = (EditText) findViewById(R.id.customer_firstName);
        editTextLastName = (EditText) findViewById(R.id.customer_lastName);
        editTextAddress = (EditText) findViewById(R.id.customer_address);
        editTextPhoneNumber = (EditText) findViewById(R.id.acustomer_phonenumber);
        editTextNatId = (EditText) findViewById(R.id.customer_nationalId);
        editTextGender = (EditText) findViewById(R.id.customer_gender);
        editTextRegisteredBy = (EditText) findViewById(R.id.customer_registeredBy);


       // Firebase.setAndroidContext(this);

        customerbuttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Getting values to store
                String firstName = editTextFirstName.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                String natId = editTextNatId.getText().toString().trim();
                String gender = editTextGender.getText().toString().trim();
                String registeredBy = editTextRegisteredBy.getText().toString().trim();
                String custkey =  "";

                //Creating Newcustomer object
                Custadd custadd = new Custadd();

                if (TextUtils.isEmpty(firstName)){
                    Toast.makeText(getApplicationContext(), "Please enter first name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)){
                    Toast.makeText(getApplicationContext(), "Please enter last name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(address)){
                    Toast.makeText(getApplicationContext(), "Please enter address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(getApplicationContext(), "Please enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(natId)){
                    Toast.makeText(getApplicationContext(), "Please enter national Id number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(gender)){
                    Toast.makeText(getApplicationContext(), "Please enter gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(registeredBy)){
                    Toast.makeText(getApplicationContext(), "Please enter gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    String password = "videolendingsystem";
                    AESCrypt aesCrypt = new AESCrypt();
                    phoneNumber= aesCrypt.encrypt(password,phoneNumber);
                    natId = aesCrypt.encrypt(password, natId);
                }catch (GeneralSecurityException e){
                    //handle error
                }

                //Adding values
                custadd.setFName(firstName);
                custadd.setLName(lastName);
                custadd.setAddress(address);
                custadd.setPhoneNumber(phoneNumber);
                custadd.setNatId(natId);
                custadd.setGender(gender);
                custadd.setRegisteredBy(registeredBy);

                //Storing values to firebase

                DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");
                DatabaseReference  userRef = ref.child("Customer");
                custkey = userRef.push().getKey();
                custadd.setCustomerKey(custkey);
                DatabaseReference newRef = userRef.child(custkey);

                newRef.setValue(custadd, new DatabaseReference.CompletionListener() {
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

