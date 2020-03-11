package com.example.admin.videolendingsystem.Employee;

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

public class AddEmployee extends AppCompatActivity {
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextAddress;
    private EditText editTextPhoneNumber;
    private EditText editTextNatId;
    private EditText editTextGender;
    private EditText editTextDateEmployed;
   // private Spinner editTextspinner;

    private Button buttonSave;
   // Spinner genderspin;
    //String gender;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);

        buttonSave = (Button) findViewById(R.id.buttonsave);
        editTextFirstName = (EditText) findViewById(R.id.editText_FirstName);
        editTextLastName = (EditText) findViewById(R.id.editText_LastName);
        editTextAddress = (EditText) findViewById(R.id.editText_Address);
        editTextPhoneNumber = (EditText) findViewById(R.id.editText_PhoneNumber);
        editTextNatId = (EditText) findViewById(R.id.editText_NationalID);
        editTextGender = (EditText) findViewById(R.id.editText_Gender);
        editTextDateEmployed = (EditText) findViewById(R.id.editText_DateEmployeed);
        //editTextspinner = (Spinner) findViewById();

       /* genderspin = (Spinner) findViewById(R.id.spinner_customer);
        genderspin.setAdapter(new ArrayAdapter<String>(AddEmployee.this, android.R.layout.simple_spinner_item, new String[]{"Male","Female"}));
        genderspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    gender = "Male";
                }else{

                    gender = "Female";
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        */




        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating firebase object
                //Firebase ref = new Firebase(Config.FIREBASE_URL);


                //Getting values to store
                String firstName = editTextFirstName.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();
                String address = editTextAddress.getText().toString().trim();
                String phoneNumber = editTextPhoneNumber.getText().toString().trim();
                String natId = editTextNatId.getText().toString().trim();
                String gender = editTextGender.getText().toString().trim();
                String dateEmployed = editTextDateEmployed.getText().toString().trim();
                String custkey =  "";

                //Creating Newcustomer object
                Newcustomer newcustomer = new Newcustomer();

                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(getApplicationContext(), "Please enter first name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(getApplicationContext(), "Please enter last name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(address)) {
                    Toast.makeText(getApplicationContext(), "Please enter address", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(getApplicationContext(), "Please enter phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(natId)) {
                    Toast.makeText(getApplicationContext(), "Please enter national Id number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(gender)) {
                    Toast.makeText(getApplicationContext(), "Please enter gender", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(dateEmployed)) {
                    Toast.makeText(getApplicationContext(), "Please enter date employed", Toast.LENGTH_SHORT).show();
                    return;
                }



                try {
                    String password = "password";
                    AESCrypt aesCrypt = new AESCrypt();

                    phoneNumber=aesCrypt.encrypt(password,phoneNumber);
                    natId = aesCrypt.encrypt(password, natId);
                }catch (GeneralSecurityException e){
                    //handle error
                }

                //Adding values
                newcustomer.setFName(firstName);
                newcustomer.setLName(lastName);
                newcustomer.setAddress(address);
                newcustomer.setPhoneNumber(phoneNumber);
                newcustomer.setNatId(natId);
                newcustomer.setGender(gender);
                newcustomer.setDateEmployed(dateEmployed);


//Storing values to firebase

                //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");


                DatabaseReference userRef = ref.child("Employee");
                custkey = userRef.push().getKey();
                newcustomer.setEmployeeKey(custkey);
                DatabaseReference newRef = userRef.child(custkey);
                //Firebase userRef = ref.child("New Customer");
                //Firebase newRef =  userRef.push();
                newRef.setValue(newcustomer, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        if (databaseError != null) {
                            Toast.makeText(getApplicationContext(), "  error ", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data saved successfully!!!", Toast.LENGTH_LONG).show();
                        }

                    }
                });


                //to get the key
                // String key = newRef.getKey();

                // ref.child("New Customer").setValue(newcustomer).push();

                // ref.child("New Customer").push().setValue(newcustomer, new Firebase.CompletionListener() {
                //   @Override
                //    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                //        Toast.makeText(getApplicationContext(), firebaseError.getMessage(),Toast.LENGTH_LONG).show();
                // }
                //});


                //  });
            }
        });
    }
}
