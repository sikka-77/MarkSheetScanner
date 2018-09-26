package com.example.bhavyasikka.myfirebaseauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /*
            this activity is for new users to register
     */

    private Button registerButton;
    private EditText memailText;
    private EditText mPasswordText;
    private TextView msignInText;
    private ProgressDialog mprogressDialog;
    private FirebaseAuth mfirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mprogressDialog=new ProgressDialog(this);

        mfirebaseAuth=FirebaseAuth.getInstance();

        if(mfirebaseAuth.getCurrentUser()!= null) {
            //user already logged in
            //start display activity
            Toast.makeText(this, "already logged in ", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this,DisplayActivity.class));

        }

        registerButton=(Button)findViewById(R.id.Signup);

        memailText=(EditText)findViewById(R.id.email);

        mPasswordText=(EditText)findViewById(R.id.password);

        msignInText=(TextView)findViewById(R.id.SignIn);

        registerButton.setOnClickListener(this);

        msignInText.setOnClickListener(this);
    }

    private void registerUser()
    {
        String email= memailText.getText().toString().trim();
        String password=mPasswordText.getText().toString().trim();

        if(TextUtils.isEmpty(email)) {

            Toast.makeText(this,"please enter email",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this,"empty password not allowed",Toast.LENGTH_SHORT).show();
            return;
        }
        //if validations okay

        mprogressDialog.setMessage("Registering user..");
        mprogressDialog.show();

        mfirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                mprogressDialog.dismiss();
                if(task.isSuccessful()) {
                   //user successfully registered
                   //start display activity
                    Toast.makeText(MainActivity.this,"registeerd",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(),ProfileActivity.class));


                }

                else
                {
                    Toast.makeText(MainActivity.this, "Can't Register try again ", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public void onClick(View view)
    {
        if(view==registerButton)
        {
            registerUser();
        }
        if(view==msignInText)
        {
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }
    }
}
