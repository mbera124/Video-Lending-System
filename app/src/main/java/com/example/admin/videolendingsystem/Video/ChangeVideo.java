package com.example.admin.videolendingsystem.Video;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.videolendingsystem.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeVideo extends AppCompatActivity {
    private EditText editTextVideoName;
    private EditText editTextGenre;
    private EditText editTextDirector;
    private EditText editTextRating;
    private EditText editTextReleaseDate;

    private EditText editTextUniqueId;

    private Button savevideobutton;

    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_video);

        Intent intent = getIntent();
        String videoname = intent.getStringExtra("videoname");
        String genre = intent.getStringExtra("genre");
        String director = intent.getStringExtra("director");
        String rating = intent.getStringExtra("rating");
        String releasedate = intent.getStringExtra("releasedate");

        final String custkey = intent.getStringExtra("custkey");

        editTextVideoName = (EditText)findViewById(R.id.cchange_videoName);
        //editTextGenre = (EditText)findViewById(R.id.cchange_genre);
       // editTextDirector = (EditText)findViewById(R.id.cchange_director);
       // editTextRating = (EditText)findViewById(R.id.cchange_rating);
       // editTextReleaseDate = (EditText)findViewById(R.id.cchange_releaseDate);
       // savevideobutton = (Button)findViewById(R.id.cchangeVideoSaveButton);

        editTextVideoName.setText(videoname);
        editTextGenre.setText(genre);
        editTextDirector.setText(director);
        editTextRating.setText(rating);
        editTextReleaseDate.setText(releasedate);
        editTextUniqueId.setText(custkey);



        savevideobutton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Videoadd videoadd = new Videoadd();
                videoadd.setVideoName(editTextVideoName.getText().toString());
                videoadd.setGenre(editTextGenre.getText().toString());
                videoadd.setDirector(editTextDirector.getText().toString());
                videoadd.setRating(editTextRating.getText().toString());
                videoadd.setReleaseDate(editTextReleaseDate.getText().toString());
                videoadd.setVideoKey(custkey);


                ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");
                //Firebase ref = new Firebase(Config.FIREBASE_URL);
                DatabaseReference userRef = ref.child("Video");

                //Firebase userRef = ref.child("Customer");
                userRef.child(custkey).setValue(videoadd, new DatabaseReference.CompletionListener() {
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
