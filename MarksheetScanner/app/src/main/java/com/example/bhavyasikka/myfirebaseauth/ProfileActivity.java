package com.example.bhavyasikka.myfirebaseauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    /*
            this activity is for adding a new course code
            by taking inputs from user
     */

    private FirebaseAuth mfirebaseAuthP;
    private TextView mUsername;
    private Button mLogout;
    private Button msaveButton;

    private EditText mCourseCode;

    private EditText msubject;
    private EditText msemester;
    private DatabaseReference mdatabaseReference;
    private FirebaseDatabase mfirebaseDatabaseInstance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mfirebaseAuthP=FirebaseAuth.getInstance();

       /* if(mfirebaseAuthP.getCurrentUser()==null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }*/

        mfirebaseDatabaseInstance= FirebaseDatabase.getInstance();

        FirebaseUser user= mfirebaseAuthP.getCurrentUser();
        String uniqueUserId=user.getUid();




        mdatabaseReference=mfirebaseDatabaseInstance.getReference("Course Code,Semester and Subject").child(uniqueUserId);



        msubject=(EditText)findViewById(R.id.subjectText);

        mCourseCode =(EditText) findViewById(R.id.courseText);

        msemester=(EditText) findViewById(R.id.semesterText);

        msaveButton=(Button)findViewById(R.id.saveButton);

       /* mUploadButton=(Button)findViewById(R.id.picButton);

        mSeeListButton=(Button)findViewById(R.id.viewList);

        mDisplayButton=(Button)findViewById(R.id.displaynewlist);*/






       // mLogout=(Button)findViewById(R.id.logoutButton);



        msaveButton.setOnClickListener(this);

       /* mUploadButton.setOnClickListener(this);
        mSeeListButton.setOnClickListener(this);
        mDisplayButton.setOnClickListener(this);

        */
       /* mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mfirebaseAuthP.signOut();

                finish();
                Intent intent=new Intent(ProfileActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        }); */
    }

    private void saveInfo() {
        String courseCode=mCourseCode.getText().toString().trim();

        String semester=msemester.getText().toString().trim();

        String subjectName=msubject.getText().toString().trim();

        if(TextUtils.isEmpty(courseCode)) {
            Toast.makeText(this,"Please enter coursecode ",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(semester)) {
            Toast.makeText(this,"Please enter semester ",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(subjectName)) {
            Toast.makeText(this,"Please enter name of subject= ",Toast.LENGTH_SHORT).show();
            return;
        }



        FirebaseUser user=mfirebaseAuthP.getCurrentUser();

        String courseId= mdatabaseReference.push().getKey();

       // UserInformation userInfo = new UserInformation(courseCode,semester,subjectName);

        UserInformation userInfo = new UserInformation(courseId,semester,courseCode,subjectName);

       // mdatabaseReference.child("Course code,Semester and Subject").child(id).setValue(userInfo);

        mdatabaseReference.child(courseId).setValue(userInfo);
        Toast.makeText(this, "Stored!", Toast.LENGTH_SHORT).show();
    }


    public void onClick(View view) {

        if(view==msaveButton)
        {
            saveInfo();
        }

        /*if(view==mUploadButton){
            startActivity(new Intent(this,PictureActivity.class));
        }

       /* if(view==mSeeListButton){
            startActivity(new Intent(this,ExamTypeActivity.class));
        }*/

        /*if(view==mDisplayButton) {
            startActivity(new Intent(this,DisplayActivity.class));
        }*/
    }
}
