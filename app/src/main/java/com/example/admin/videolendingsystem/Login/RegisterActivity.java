package com.example.admin.videolendingsystem.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.videolendingsystem.R;
import com.example.admin.videolendingsystem.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailInput;
    private EditText passwordInput;
    private Button registerButton;

    String email ;
    String password;
    ProgressDialog mProgressDialog;
   private  FirebaseAuth mAuth;

   // @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        emailInput = (EditText)findViewById(R.id.registeremail);
        passwordInput = (EditText)findViewById(R.id.registerpassword);
        registerButton = (Button) findViewById(R.id.registerbutton);
        registerButton.setOnClickListener(this);


     //   setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
     //   ActionBar actionBar = getSupportActionBar();
      //  if(actionBar != null){
        //    actionBar.hide();

         //   mAuth = ((FirebaseApplication)getApplication()).getFirebaseAuth();
         //   ((FirebaseApplication)getApplication()).checkUserLogin(RegisterActivity.this);

          //  emailInput = (EditText)findViewById(R.id.editText_email);
            //passwordInput = (EditText)findViewById(R.id.editText_password);

            //registerInput =(TextView)findViewById(R.id.textView_register);

           // signUpText = (TextView)findViewById(R.id.textView_register);
           // signUpText.setOnClickListener(new View.OnClickListener() {
              //  @Override
               // public void onClick(View view) {
                   // Intent signUpIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                  //  startActivity(signUpIntent);
                }
            //});

           // final Button loginButton = (Button)findViewById(R.id.button_login);
           // loginButton.setOnClickListener(new View.OnClickListener() {
             //   @Override
             //   public void onClick(View view) {
             //       String enteredEmail = emailInput.getText().toString();
              //      String enteredPassword = passwordInput.getText().toString();

              //      if(TextUtils.isEmpty(enteredEmail) || TextUtils.isEmpty(enteredPassword)){
                //        Toast.makeText(RegisterActivity.this, "Login fields must be filled",Toast.LENGTH_LONG).show();
                 //       return;
                  //  }
                   // if(!ThemedSpinnerAdapter.Helper.isValidEmail(enteredEmail)){
                     //   ThemedSpinnerAdapter.Helper.displayMessageToast(RegisterActivity.this, "Invalidate email entered");
                    //    return;
                    //}

                   // ((FirebaseApplication)getApplication()).createNewUser(RegisterActivity.this, enteredEmail, enteredPassword, registerInput);
               // }
           // });

       // }
    //}

    private void createAccount(String email, String password) {
        Log.d("TAG", "createAccount:" + email);
        if (!validateForm()) {
            return;
        }
        showProgressDialog();
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this,"User has created",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegisterActivity.this, UserActivity.class));
                            finish();
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                        }else{
                            Log.w("ERROR SIGN UP","" , task.getException());
                            Toast.makeText(RegisterActivity.this,"User not created",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(RegisterActivity.this);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }


    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private boolean validateForm() {
        boolean valid = true;
         email = emailInput.getText().toString();
         password = passwordInput.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailInput.setError("required");
            valid = false;
        } else {
            emailInput.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("required");
            valid = false;
        } else {
            passwordInput.setError(null);
        }

        return  valid;
    }

    @Override
    public void onClick(View v) {

        if (validateForm()){
            int i = v.getId();
            if (i == R.id.registerbutton){
                createAccount(emailInput.getText().toString(),passwordInput.getText().toString());
            }
        }
    }
}
