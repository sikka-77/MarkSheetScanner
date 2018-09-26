package com.example.bhavyasikka.myfirebaseauth;

import android.content.Intent;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    /*this activity displays the list of all the course codes and their
     respective subject names and semesters
     created by the teacher
     */

    List<UserInformation> userList;
    private FirebaseAuth mfirebaseAuthP;
    private DatabaseReference mdatabaseReference;
    private FirebaseDatabase mfirebaseDatabaseInstance;
    ListView userListView;

    /*
            These two varibales are created so that they can be passes via intent
            These two are to relate the course code with it respective Exam type
            these two will be accepted on the other activity where intent is set
            the value put during initialisation can be anything

     */

    public static final String COURSE_CODE_NAME="courseName";
    public static final String COURSE_CODE_ID="courseCodeId";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mfirebaseAuthP= FirebaseAuth.getInstance();

        /*
            checking if the user is not logged in then login actvity
            will be launched where the user can login again
         */

        if(mfirebaseAuthP.getCurrentUser()==null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        userListView =(ListView)findViewById(R.id.list1);


        userList=new ArrayList<UserInformation>();

        mfirebaseDatabaseInstance= FirebaseDatabase.getInstance();

        /*
                getting the currently logged in user
         */
        FirebaseUser user= mfirebaseAuthP.getCurrentUser();

        /*
                getting the user id from firebase that is automatically generated when user logs in
         */
        String uniqueUserId=user.getUid();

        FloatingActionButton fabButton=(FloatingActionButton)findViewById(R.id.fab);

        /*
                floating button here will opn activity so that
                user can add a new course code
         */
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(DisplayActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        /*
                setting the reference of the databse variable
                the data under this reference will be retrieved from firebase
                here reference is set to Course Code,Semester and Subject and
                in it to the user id that we got above
                This will set refernce to the particular user and course codes created by that
                user will be displayed
         */

        mdatabaseReference=mfirebaseDatabaseInstance.getReference("Course Code,Semester and Subject").child(uniqueUserId);


        /*
                setting clicks on listview
         */

        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*
                        object of user information is created
                        this will be used to get the actual coursecoe id
                        that will be sent via intent
                 */
                UserInformation currentCourse=userList.get(i);

                /*
                        exam activity holds all the exams created by the user under
                        a particular course code
                 */
                Intent intent=new Intent(getApplicationContext(),ExamActivity.class);

                /*
                        getting the actual values of id and name by calling
                          methids on the object created above
                          and sending them through intent that can  be recieved in
                          examActivity class
                 */

                intent.putExtra(COURSE_CODE_ID,currentCourse.getCourseCodeId());
                intent.putExtra(COURSE_CODE_NAME,currentCourse.getCourseCode());

                startActivity(intent);
            }
        });

        /*
                setting the long press on a listview item
                which willenable the diting on a particular course code
         */

        userListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                UserInformation course=userList.get(i);

                showUpdateWindow(course.getCourseCodeId(),course.getCourseCode());

                return false;
            }
        });
    }

    /*
            method to display the update dialog to
            update the course code
     */

    private void showUpdateWindow(final String courseId, String courseName){

        AlertDialog.Builder dialogBuilder= new AlertDialog.Builder(DisplayActivity.this);

        LayoutInflater inflater=getLayoutInflater();

        final View dialogView= inflater.inflate(R.layout.update_dialog,null);

        dialogBuilder.setView(dialogView);

        /*
                recieving all user entered data from the update dialog xml
         */

        final EditText editTextCourse=(EditText)dialogView.findViewById(R.id.updatedCourseCode);

        final EditText editTextSemester=(EditText)dialogView.findViewById(R.id.updatedSemester);

        final EditText editTextSubject=(EditText)dialogView.findViewById(R.id.updatedSubject);

        final Button updateChangesButton=(Button)dialogView.findViewById(R.id.saveChangesButton);

        final Button deleteCourseButton=(Button)dialogView.findViewById(R.id.deleteCourse);



        dialogBuilder.setTitle("Update Course Code  " + courseName);

        final AlertDialog alertDialog= dialogBuilder.create();

        alertDialog.show();

        /*

         */

        updateChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cName=editTextCourse.getText().toString().trim();
                String sem=editTextSemester.getText().toString().trim();
                String sub=editTextSubject.getText().toString().trim();

                if(TextUtils.isEmpty(cName)) {
                    Toast.makeText(DisplayActivity.this, "Please enter course code ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(sem)) {
                    Toast.makeText(DisplayActivity.this, "Please enter semester ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(sub)) {
                    Toast.makeText(DisplayActivity.this, "Please enter subject ", Toast.LENGTH_SHORT).show();
                    return;
                }
                updateCourse(courseId,cName,sem,sub);
                alertDialog.dismiss();

            }
        });

        deleteCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse(courseId);
                alertDialog.dismiss();
            }
        });




    }

            /*
                    this method deletes  data stored in firebase
             */
    private void deleteCourse(String del_c_id) {

        FirebaseUser user= mfirebaseAuthP.getCurrentUser();

        String uniqueUserId=user.getUid();

        DatabaseReference mdatabaseReference= FirebaseDatabase.getInstance().getReference("Course Code,Semester and Subject")
                .child(uniqueUserId).child(del_c_id);

        DatabaseReference mdatabaseReference2 = FirebaseDatabase.getInstance().getReference("Exam Types")
                .child(uniqueUserId).child(del_c_id);

        /*StorageReference mstorageReference = FirebaseStorage.getInstance().getReference("Images")
                .child(uniqueUserId).child(del_c_id); */

        mdatabaseReference.removeValue();
        mdatabaseReference2.removeValue();

    }

        /*
                this method updates data stored in firebase
         */

    private boolean updateCourse(String c_id,String c_name,String sem,String subject) {

        FirebaseUser user= mfirebaseAuthP.getCurrentUser();

        String uniqueUserId=user.getUid();


        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Course Code,Semester and Subject")
                .child(uniqueUserId).child(c_id);

        UserInformation new_course = new UserInformation(c_id,sem,c_name,subject);

        databaseReference.setValue(new_course);

        Toast.makeText(this, "Saved Changes!", Toast.LENGTH_SHORT).show();

        return true;
    }

    @Override
    protected void onStart() {

        // Toast.makeText(DisplayActivity.this,"enterd on start",Toast.LENGTH_SHORT).show();
        super.onStart();

        /*
                displaying course codes in a listview after the user is logged in
         */
        mdatabaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

               // Toast.makeText(DisplayActivity.this,"Entered meethod",Toast.LENGTH_SHORT).show();

                userList.clear();

               // Toast.makeText(DisplayActivity.this,"cleared list ",Toast.LENGTH_SHORT).show();

                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){

                    // Toast.makeText(DisplayActivity.this,"Entered loop",Toast.LENGTH_SHORT).show();

                    UserInformation userInfo=userSnapshot.getValue(UserInformation.class);
                    userList.add(userInfo);
                }
                UserAdapter adapter= new UserAdapter(DisplayActivity.this,userList);
                userListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DisplayActivity.this, "Can't display list", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // return super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.log_off, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId())
        {
            case R.id.signingOut:
                mfirebaseAuthP.signOut();

                finish();
                Intent intent=new Intent(DisplayActivity.this,LoginActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }
}
