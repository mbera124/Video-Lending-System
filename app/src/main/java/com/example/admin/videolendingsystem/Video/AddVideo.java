package com.example.admin.videolendingsystem.Video;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.videolendingsystem.R;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddVideo extends AppCompatActivity {
    private EditText editTextVideoName;
    private EditText editTextGenre;
    private EditText editTextDirector;
    private EditText editTextRating;
    private EditText editTextReleaseDate;

    private Button videobuttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);


        videobuttonSave = (Button) findViewById(R.id.VideoSaveButton);
        editTextVideoName = (EditText) findViewById(R.id.video_name);
        editTextGenre = (EditText) findViewById(R.id.video_genre);
        editTextDirector = (EditText) findViewById(R.id.video_director);
        editTextRating = (EditText) findViewById(R.id.video_rating);
        editTextReleaseDate = (EditText) findViewById(R.id.video_releaseDate);

       //Firebase.setAndroidContext(this);


        videobuttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating firebase object

               // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
               // DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");


                //Getting values to store
                String videoName = editTextVideoName.getText().toString().trim();
                String genre = editTextGenre.getText().toString().trim();
                String director = editTextDirector.getText().toString().trim();
                String rating = editTextRating.getText().toString().trim();
                String releaseDate = editTextReleaseDate.getText().toString().trim();
                String custkey =  "";


                //Creating Videoadd object
                Videoadd videoadd = new Videoadd();

                if (TextUtils.isEmpty(videoName)) {
                    Toast.makeText(getApplicationContext(), "Please enter video name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(genre)) {
                    Toast.makeText(getApplicationContext(), "Please enter genre", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(director)) {
                    Toast.makeText(getApplicationContext(), "Please enter director", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(rating)) {
                    Toast.makeText(getApplicationContext(), "Please enter rating", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(releaseDate)) {
                    Toast.makeText(getApplicationContext(), "Please enter release date", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Adding values
                videoadd.setVideoName(videoName);
                videoadd.setGenre(genre);
                videoadd.setDirector(director);
                videoadd.setRating(rating);
                videoadd.setReleaseDate(releaseDate);


                //Storing values to firebase

               // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
                DatabaseReference ref = FirebaseDatabase.getInstance().getReferenceFromUrl("https://video-lending-system.firebaseio.com/");

                DatabaseReference userRef = ref.child("Video");
                custkey = userRef.push().getKey();
                videoadd.setVideoKey(custkey);
                DatabaseReference newRef = userRef.child(custkey);


                newRef.setValue(videoadd, new DatabaseReference.CompletionListener() {
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


