package com.example.bhavyasikka.myfirebaseauth;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
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

public class DisplayActivity extends AppCompatActivity {

    List<UserInformation> userList;
    private FirebaseAuth mfirebaseAuthP;
    private DatabaseReference mdatabaseReference;
    private FirebaseDatabase mfirebaseDatabaseInstance;
    ListView userListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        mfirebaseAuthP= FirebaseAuth.getInstance();

       userListView =(ListView)findViewById(R.id.list1);
        userList=new ArrayList<UserInformation>();

        mfirebaseDatabaseInstance= FirebaseDatabase.getInstance();

        FirebaseUser user= mfirebaseAuthP.getCurrentUser();
        String uniqueUserId=user.getUid();

        mdatabaseReference= mfirebaseDatabaseInstance.getReference(uniqueUserId);
        mdatabaseReference=mfirebaseDatabaseInstance.getReference("Names and age");

    }

    @Override
    protected void onStart() {
        Toast.makeText(DisplayActivity.this,"enterd on start",Toast.LENGTH_SHORT).show();
        super.onStart();

        mdatabaseReference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(DisplayActivity.this,"Entered meethod",Toast.LENGTH_SHORT).show();

                userList.clear();

                Toast.makeText(DisplayActivity.this,"cleared list ",Toast.LENGTH_SHORT).show();

                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    Toast.makeText(DisplayActivity.this,"Entered loop",Toast.LENGTH_SHORT).show();

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
}
