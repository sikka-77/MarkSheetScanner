package com.example.bhavyasikka.myfirebaseauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signInButtonL;
    private EditText memailTextL;
    private EditText mPasswordTextL;
    private TextView mRegisterTextL;
    private ProgressDialog mprogressDialogL;
    private FirebaseAuth mfirebaseAuthL;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mfirebaseAuthL=FirebaseAuth.getInstance();

      if(mfirebaseAuthL.getCurrentUser()!= null) {
            //user already logged in
            //start profile activity
            finish();
            startActivity(new Intent(this,ProfileActivity.class));

        }

        signInButtonL=(Button)findViewById(R.id.Signinside);

        memailTextL=(EditText)findViewById(R.id.emailL);

        mPasswordTextL=(EditText)findViewById(R.id.passwordL);

        mRegisterTextL=(TextView)findViewById(R.id.Register);
        mprogressDialogL= new ProgressDialog(this);

        signInButtonL.setOnClickListener(this);

        mRegisterTextL.setOnClickListener(this);
    }

    private void userLogin() {
        String email=memailTextL.getText().toString().trim();
        String password=mPasswordTextL.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {

            Toast.makeText(this,"please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this,"empty password not allowed",Toast.LENGTH_SHORT).show();
            return;
        }

        mprogressDialogL.setMessage("Signing in...");
        mprogressDialogL.show();

        mfirebaseAuthL.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mprogressDialogL.dismiss();

                        if(task.isSuccessful()) {
                            //start profile activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"can't sign in",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

    public void onClick(View view) {

        if(view==signInButtonL) {

            userLogin();
        }
        if(view==mRegisterTextL) {
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

    }
}
