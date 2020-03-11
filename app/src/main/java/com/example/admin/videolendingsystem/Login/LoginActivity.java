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

import com.example.admin.videolendingsystem.Employee.EmployeesActivity;
import com.example.admin.videolendingsystem.R;
import com.example.admin.videolendingsystem.UserActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog mProgressDialog;
    private EditText emailInput;
    private EditText passwordInput;
    private Button loginbutton;

    String email ;
    String password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        emailInput = (EditText)findViewById(R.id.loginemail);
        passwordInput = (EditText)findViewById(R.id.loginpassword);
        loginbutton = (Button) findViewById(R.id.loginButton);

        loginbutton.setOnClickListener(this);
    }
/*      //  LoginButton();
        //final Button loginButton = (Button) findViewById(R.id.button_login);
        //loginButton.setOnClickListener(new View.OnClickListener() {

                }




    public void LoginButton() {
        email = (EditText) findViewById(R.id.registereditText_email);
        password = (EditText) findViewById(R.id.registereditText_password);
        login_button = (Button) findViewById(R.id.registerbutton_login);

        login_button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (email.getText().toString().equals("user") &&
                                password.getText().toString().equals("pass")) {

                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Email and password is NOT correct",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }

 **/


    private void signIn(final String email, String password) {
        Log.d("", "signIn:" + email);
        if (!validateForm()) {
            return;
        }
        showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            if(email.equalsIgnoreCase("administrator1@gmail.com")){
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }else {

                            startActivity(new Intent(LoginActivity.this, UserActivity.class));
                           finish();}
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                        }
                        else{
                            Log.w("", "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "SignIn Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }


    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(LoginActivity.this);
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
            if (i == R.id.loginButton){
                signIn(emailInput.getText().toString(),passwordInput.getText().toString());
            }
        }
    }

}



