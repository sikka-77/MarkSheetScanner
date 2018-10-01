package com.example.bhavyasikka.myfirebaseauth;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExamActivity extends AppCompatActivity {

    List<NewActivity> userList;
    private FirebaseAuth mfirebaseAuthP;
    private DatabaseReference mdatabaseReference;
    private FirebaseDatabase mfirebaseDatabaseInstance;
    TextView mCourseNametoShow;
    ListView userListView;

    public static final String NEWCOURSECODEID="courseidnew";
    public static final String NEW_COURSE_CODE_NAME="courseCodeNamenew";
    public static final String EXAM_ID="newExamId";
    public static final String EXAM_NAME="newExamName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        mfirebaseAuthP= FirebaseAuth.getInstance();

        mCourseNametoShow=(TextView)findViewById(R.id.setCourseName);

        userListView=(ListView)findViewById(R.id.listOfExam);

        userList= new ArrayList<>();

        Intent intent=getIntent();
        final String mCourseidentity=intent.getStringExtra(DisplayActivity.COURSE_CODE_ID);
        final String mCourseNames=intent.getStringExtra(DisplayActivity.COURSE_CODE_NAME);


        mfirebaseDatabaseInstance= FirebaseDatabase.getInstance();

        FirebaseUser user= mfirebaseAuthP.getCurrentUser();
        String uniqueUserId=user.getUid();

        FloatingActionButton fabButton2=(FloatingActionButton)findViewById(R.id.fab2);

        fabButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent examintent = new Intent(getApplicationContext(),ExamTypeActivity.class);

                 examintent.putExtra(NEWCOURSECODEID,mCourseidentity);
                 examintent.putExtra(NEW_COURSE_CODE_NAME,mCourseNames);


                 startActivity(examintent);

            }
        });

        mdatabaseReference=mfirebaseDatabaseInstance.getReference("Exam Types").child(uniqueUserId).child(mCourseidentity);

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                NewActivity currentExam= userList.get(i);

                Intent intent=new Intent(getApplicationContext(),PictureActivity.class);

                intent.putExtra(EXAM_ID,currentExam.getmExamTypeId());
                intent.putExtra(EXAM_NAME,currentExam.getmExamTypeName());
                intent.putExtra(NEWCOURSECODEID,mCourseidentity);


                startActivity(intent);
            }
        });
        mCourseNametoShow.setText(mCourseNames);


    }

    @Override
    protected void onStart() {
        super.onStart();

        mdatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userList.clear();

                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){

                    // Toast.makeText(DisplayActivity.this,"Entered loop",Toast.LENGTH_SHORT).show();

                    NewActivity userInfo=userSnapshot.getValue(NewActivity.class);
                    userList.add(userInfo);
                }

                UserAdapterExamActivity adapter= new UserAdapterExamActivity(ExamActivity.this,userList);
                userListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(ExamActivity.this, "Can't display data right now", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
