package com.example.admin.videolendingsystem.Firebase;

import android.content.Context;

import com.firebase.client.Firebase;



/**
 * Created by ADMIN on 18/05/2017.
 */

public class Config {
    public static final String FIREBASE_URL = "https://video-lending-system.firebaseio.com/";
   /*private Context context;
    private String userKey;
    private DatabaseReference databaseReference;
    private static boolean isPersistenceEnabled = false;
    private static String fixedLocationA = "https://video-lending-system.firebaseio.com/";
    private static String fixedLocationB = "https://video-lending-system.firebaseio.com/";

    public Config(Context context, String userKey){
        this.context = context;
        this.userKey = userKey;

        if (!isPersistenceEnabled){
          FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            isPersistenceEnabled = true;
        }
        databaseReference = FirebaseDatabase.getInstance().getReference().child(userKey);

    }

    public DatabaseReference getRefA(){
        return databaseReference.child(fixedLocationA);
    }

    public DatabaseReference getRefB(){
        return databaseReference.child(fixedLocationB);
    }
*/
}
