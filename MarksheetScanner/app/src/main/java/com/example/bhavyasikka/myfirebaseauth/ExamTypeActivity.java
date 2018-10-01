package com.example.bhavyasikka.myfirebaseauth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExamTypeActivity extends AppCompatActivity implements View.OnClickListener {

    /*
            this activity takes input from user fr the type of exam
            under a course code
     */

    private EditText mExamTypeText;

    private TextView mcourseName;
    private EditText mSectionText;

    private Button mExamTypeSaveButton;
    private FirebaseDatabase mfirebaseDatabaseInstance;
    private FirebaseAuth mfirebaseAuthP;
    private DatabaseReference mdatabaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_type);

        mcourseName=(TextView)findViewById(R.id.courseTextdisplay);

        mExamTypeSaveButton=(Button)findViewById(R.id.examTypeSave);

        mExamTypeText=(EditText)findViewById(R.id.examType);

        mSectionText=(EditText)findViewById(R.id.sectionText);

        Intent intent=getIntent();

        String courseId;
        courseId=intent.getStringExtra(ExamActivity.NEWCOURSECODEID);
        String courseName=intent.getStringExtra(ExamActivity.NEW_COURSE_CODE_NAME);



        mfirebaseAuthP=FirebaseAuth.getInstance();



        FirebaseUser user= mfirebaseAuthP.getCurrentUser();
        String uniqueUserId=user.getUid();

        mfirebaseDatabaseInstance= FirebaseDatabase.getInstance();

        mdatabaseReference = mfirebaseDatabaseInstance.getReference("Exam Types").child(uniqueUserId).child(courseId);

        mcourseName.setText(courseName);

        mExamTypeSaveButton.setOnClickListener(this);

    }

    private void saveData() {

        String examType= mExamTypeText.getText().toString().trim();

        String section= mSectionText.getText().toString().trim();


        if(TextUtils.isEmpty(examType)) {
            Toast.makeText(ExamTypeActivity.this,"Please enter examType",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(section)) {
            Toast.makeText(ExamTypeActivity.this,"Please enter section",Toast.LENGTH_SHORT).show();
            return;
        }



        String uniqueExamid= mdatabaseReference.push().getKey();


        NewActivity newExam = new NewActivity(uniqueExamid,examType,section);


        mdatabaseReference.child(uniqueExamid).setValue(newExam);


        Toast.makeText(this, "Stored!", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        if(view==mExamTypeSaveButton) {
            saveData();
        }

    }
}
