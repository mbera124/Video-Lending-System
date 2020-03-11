package com.example.admin.videolendingsystem.RentedVideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.admin.videolendingsystem.AESCrypt;
import com.example.admin.videolendingsystem.Employee.Newcustomer;
import com.example.admin.videolendingsystem.R;
import com.example.admin.videolendingsystem.Video.Videoadd;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.GeneralSecurityException;
import java.util.ArrayList;

public class AddRentedVideos extends AppCompatActivity {


    private EditText editTextDateRented;
    private EditText editTextExpectedReturnDate;
    private EditText editTextReturnedDate;
    private EditText editTextPenalty;
    private EditText editTextStatus;
    private Button videoRentalButtonSave;

    Spinner custnamespin;
    Spinner videonamespin;


    DatabaseReference ref;
    ArrayList<String> empusers = new ArrayList<>();
    ArrayList<String> videonameusers = new ArrayList<>();

    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adaptervideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rented_videos);


        videoRentalButtonSave = (Button) findViewById(R.id.rentedvideos_save);
        editTextDateRented = (EditText) findViewById(R.id.rentedvideos_dateRented);
        editTextExpectedReturnDate = (EditText) findViewById(R.id.rentedvideos_expReturnDate);
        editTextReturnedDate = (EditText) findViewById(R.id.rentedvideos_returnedDate);
        editTextPenalty = (EditText) findViewById(R.id.rentedvideos_penalty);
        editTextStatus = (EditText) findViewById(R.id.rentedvideos_status);

        custnamespin = (Spinner) findViewById(R.id.spinner_customer);
        videonamespin = (Spinner) findViewById(R.id.spinner_video);




        videoRentalButtonSave.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                //Getting values to store
                String fullNamerv = custnamespin.getSelectedItem().toString().trim();
                String videoNamerv = videonamespin.getSelectedItem().toString().trim();
                String dateRented = editTextDateRented.getText().toString().trim();
                String expectedReturnDate = editTextExpectedReturnDate.getText().toString().trim();
                String returnedDate = editTextReturnedDate.getText().toString().trim();
                //String returnedDate = "";
                String status = editTextStatus.getText().toString().trim();
                //String status = "";
                String rentedvideokey =  "";
                String penalty =editTextPenalty.getText().toString().trim();
                //String penalty ="";

                Rentedvideosadd rentedvideosadd = new Rentedvideosadd();


              /*  if (TextUtils.isEmpty(fullNamerv)){
                    Toast.makeText(getApplicationContext(), "Please Select the customer full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(videoNamerv)){
                    Toast.makeText(getApplicationContext(), "Please select the video name", Toast.LENGTH_SHORT).show();
                    return;
                }
                  */
                if (TextUtils.isEmpty(dateRented)){
                    Toast.makeText(getApplicationContext(), "Please enter the date rented", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(expectedReturnDate)){
                    Toast.makeText(getApplicationContext(), "Please enter the expected return date", Toast.LENGTH_SHORT).show();
                    return;
                }


                rentedvideosadd.setFullName(fullNamerv);

                try {
                    String password = "videolendingsystem";
                    AESCrypt aesCrypt = new AESCrypt();
                    videoNamerv= aesCrypt.encrypt(password,videoNamerv);
                }catch (GeneralSecurityException e){
                    //handle error
                }
                rentedvideosadd.setVideoName(videoNamerv);
                rentedvideosadd.setDateRented(dateRented);
                rentedvideosadd.setExpectedReturnDate(expectedReturnDate);
                rentedvideosadd.setReturnedDate(returnedDate);
                rentedvideosadd.setPenalty();
                rentedvideosadd.setStatus(status);


                //Storing values to firebase

                // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
               // DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");

                DatabaseReference  userRef = ref.child("RentedVideos");
                rentedvideokey = userRef.push().getKey();
                rentedvideosadd.setRentedvideokey(rentedvideokey);
                DatabaseReference newRef = userRef.child(rentedvideokey);

                newRef.setValue(rentedvideosadd, new DatabaseReference.CompletionListener() {
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



        adapter = new ArrayAdapter<String>(AddRentedVideos.this, android.R.layout.simple_spinner_item, empusers);

        custnamespin.setAdapter(adapter);

        ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");
        ref.child("Employee").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Newcustomer newcustomer = postSnapShot.getValue(Newcustomer.class);
                        empusers.add(newcustomer.getFName() + " "+newcustomer.getLName());
                        adapter.notifyDataSetChanged();
                    }


                }

                Toast.makeText(getApplicationContext(), "" + empusers.size(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddRentedVideos.this, "Error", Toast.LENGTH_LONG).show();

            }
        });

        custnamespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        adaptervideo = new ArrayAdapter<String>(AddRentedVideos.this, android.R.layout.simple_spinner_item, videonameusers);
        videonamespin.setAdapter(adaptervideo);

        ref.child("Video").addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (com.google.firebase.database.DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
                        Videoadd videoadd = postSnapShot.getValue(Videoadd.class);
                        videonameusers.add(videoadd.getVideoName() + ", "+ videoadd.getGenre());
                        adaptervideo.notifyDataSetChanged();
                    }


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(AddRentedVideos.this, "Error", Toast.LENGTH_LONG).show();

            }
        });

        videonamespin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







    }
}
