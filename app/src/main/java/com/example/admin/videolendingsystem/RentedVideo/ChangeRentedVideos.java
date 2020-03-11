package com.example.admin.videolendingsystem.RentedVideo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.videolendingsystem.AESCrypt;
import com.example.admin.videolendingsystem.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class ChangeRentedVideos extends AppCompatActivity {
    private EditText editTextFullName;
    private EditText editTextVideo;
    private EditText editTextDateRented;
    private EditText editTextExpectedReturnDate;
    private EditText editTextReturnedDate;
    private EditText editTextPenalty;
    private EditText editTextStatus;
    // private EditText editTextUniqueId;

    private Button changerentedbutton;

    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_rented_videos);


        Intent intent = getIntent();
        String fullname = intent.getStringExtra("fullname");
        String video = intent.getStringExtra("video");
        String daterented = intent.getStringExtra("daterented");
        String expectedreturndate = intent.getStringExtra("expectedreturndate");
        String returneddate = intent.getStringExtra("returneddate");
        final String penalty = intent.getStringExtra("penalty");
        String status = intent.getStringExtra("status");
        final String rentedvideokey = intent.getStringExtra("rentedvideokey");

        Button changerentedbutton = (Button)findViewById(R.id.changeRentedButtonSave);
        final EditText editTextFullName = (EditText)findViewById(R.id.changerented_fullname);
        final EditText editTextVideo =  (EditText)findViewById(R.id.changerented_video);
        final EditText editTextDateRented = (EditText)findViewById(R.id.change_daterented);
        final EditText editTextExpectedReturnDate = (EditText)findViewById(R.id.change_ExpectedReturnDate);
        final EditText editTextReturnedDate = (EditText)findViewById(R.id.change_returnedDate);
       final  EditText editTextPenalty = (EditText)findViewById(R.id.change_penalty);
        final EditText editTextStatus = (EditText)findViewById(R.id.change_status);





        editTextFullName.setText(fullname);
        editTextVideo.setText(video);
        editTextDateRented.setText(daterented);
        editTextExpectedReturnDate.setText(expectedreturndate);
        editTextReturnedDate.setText(returneddate);
        editTextPenalty.setText(penalty);

        editTextReturnedDate.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Rentedvideosadd rentedvideosadd = new Rentedvideosadd();
                //Toast.makeText(getApplicationContext(),editTextReturnedDate.getText().toString(), Toast.LENGTH_LONG).show();

                Long days;
                int fine = 2;
                int daynum;
                int penalty;
                String finalPenalty;

                try {
                    SimpleDateFormat format;
                    format = new SimpleDateFormat("dd-mm-yyyy");
                    days = TimeUnit.DAYS.convert(format.parse(editTextReturnedDate.getText().toString()).getTime() - format.parse(editTextExpectedReturnDate.getText().toString()).getTime(), TimeUnit.MILLISECONDS);
                    daynum = Integer.valueOf(days.toString());

                    penalty= fine*daynum;
                    if(penalty<=0){

                        editTextPenalty.setText("0");
                    }else {
                        finalPenalty = Integer.toString(penalty);


                        editTextPenalty.setText(finalPenalty);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // editTextPenalty.setText();
        editTextStatus.setText(status);





        changerentedbutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String videorented = editTextVideo.getText().toString().trim();
                try {
                    String password = "videolendingsystem";
                    AESCrypt aesCrypt = new AESCrypt();
                    videorented= aesCrypt.encrypt(password,videorented);
                }catch (GeneralSecurityException e){
                    //handle error
                }

                Rentedvideosadd rentedvideosadd = new Rentedvideosadd();
                rentedvideosadd.setFullName(editTextFullName.getText().toString());
                rentedvideosadd.setVideoName(videorented);
                rentedvideosadd.setDateRented(editTextDateRented.getText().toString());
                rentedvideosadd.setExpectedReturnDate(editTextExpectedReturnDate.getText().toString());
                rentedvideosadd.setReturnedDate(editTextReturnedDate.getText().toString());
                rentedvideosadd.setPenalty();
                rentedvideosadd.setStatus(editTextStatus.getText().toString());
                rentedvideosadd.setRentedvideokey(rentedvideokey);


               // Toast.makeText(getApplicationContext(),"Penalty is:" +rentedvideosadd.getPenalty(),Toast.LENGTH_LONG).show();


                ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");
                //Firebase ref = new Firebase(Config.FIREBASE_URL);
                DatabaseReference userRef = ref.child("RentedVideos");

                //Firebase userRef = ref.child("Customer");
                userRef.child(rentedvideokey).setValue(rentedvideosadd, new DatabaseReference.CompletionListener() {
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
