package com.example.bhavyasikka.myfirebaseauth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExamTypeActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mExamTypeText;

    private EditText mSectionText;

    private Button mExamTypeSaveButton;

    private FirebaseAuth mfirebaseAuthP;
    private DatabaseReference mdatabaseReference;
    private FirebaseDatabase mfirebaseDatabaseInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_type);

        mExamTypeSaveButton=(Button)findViewById(R.id.examTypeSave);

        mExamTypeText=(EditText)findViewById(R.id.examType);

        mSectionText=(EditText)findViewById(R.id.sectionText);

        mfirebaseAuthP=FirebaseAuth.getInstance();

        mfirebaseDatabaseInstance= FirebaseDatabase.getInstance();

        FirebaseUser user= mfirebaseAuthP.getCurrentUser();
        String uniqueUserId=user.getUid();

        mdatabaseReference=mfirebaseDatabaseInstance.getReference(uniqueUserId);

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

        FirebaseUser user= mfirebaseAuthP.getCurrentUser();

        String id= mdatabaseReference.push().getKey();



        UserInformation userInfo= new UserInformation(examType,section);

        mdatabaseReference.child("Exam Type and Section").child(id).setValue(userInfo);

        Toast.makeText(this, "Stored!", Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        if(view==mExamTypeSaveButton) {
            saveData();
        }

    }
}
