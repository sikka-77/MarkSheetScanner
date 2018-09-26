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

    /*
            this activity starts when a particular course code is clicked from listview
            this activity displays the list of all exam types created by user under a
            particular course code
     */

    List<NewActivity> userList;
    private FirebaseAuth mfirebaseAuthP;
    private DatabaseReference mdatabaseReference;
    private FirebaseDatabase mfirebaseDatabaseInstance;
    TextView mCourseNametoShow;
    ListView userListView;

    /*
            the first two id's are the same id's that were sent by the diplay activity
            these are renamed here for sending them via itent to exam type activity
            in exam type activity thes can't be taken via intent of display activity
            so we have to send them with the intent of this activity
            and so they are given a new name
     */
    public static final String NEWCOURSECODEID="courseidnew";
    public static final String NEW_COURSE_CODE_NAME="courseCodeNamenew";

    /*
            these two id's are created to send to  the picture activity to store a picture under that paritcukar exam
            these are id's of exam type(end sem,sessional) created by the user
     */
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

            /*
                    the id's are recieved that were sent via intent from display activty
                    to relate the exam types to its particular course code
             */
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

                 /*
                        renamed id's sent here
                        as the intent is from this actvity to exam type activity
                        so the id's should be of this activity
                        so new one's are created
                  */
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

                /*
                    id's being sent to picture activity to relate data
                 */

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

        /*
                this will display list of all exam types uner a course code
         */

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
