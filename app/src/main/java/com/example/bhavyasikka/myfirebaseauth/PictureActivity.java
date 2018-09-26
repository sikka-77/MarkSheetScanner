package com.example.bhavyasikka.myfirebaseauth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;

public class PictureActivity extends AppCompatActivity {

    /*
            this activity is for uploading a picture
     */
    private Button mSavePhoto;
    private FirebaseAuth mfirebaseAuthP;

    //private ImageView mImage;

    TextView mExamsName;

    private Uri uri;

    private StorageReference mStorageReference;

    private ProgressDialog mprogressDialog;

    private static final int CAMERA_REQUEST_CODE=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        mprogressDialog=new ProgressDialog(this);

        mfirebaseAuthP=FirebaseAuth.getInstance();

        FirebaseUser user= mfirebaseAuthP.getCurrentUser();
        String uniqueUserId=user.getUid();

        mExamsName=(TextView)findViewById(R.id.examsName);



        FloatingActionButton save_new_photo=(FloatingActionButton)findViewById(R.id.savePhoto);

       // mImage=(ImageView)findViewById(R.id.displayImage);

        Intent picIntent=getIntent();

        String new_exam_id=picIntent.getStringExtra(ExamActivity.EXAM_ID);
        String new_exam_name=picIntent.getStringExtra(ExamActivity.EXAM_NAME);
        String new_course_code_id=picIntent.getStringExtra(ExamActivity.NEWCOURSECODEID);

        mExamsName.setText(new_exam_name);

        mStorageReference= FirebaseStorage.getInstance().getReference("Images").child(uniqueUserId)
                .child(new_course_code_id).child(new_exam_id);


        save_new_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(intent,CAMERA_REQUEST_CODE);

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==CAMERA_REQUEST_CODE && resultCode== RESULT_OK) {

            mprogressDialog.setMessage("uploading...");
            mprogressDialog.show();

              //uri=data.getData();

                //code to get camera image
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] dataBAOS = baos.toByteArray();

            //name of the image file (add time to have different files to avoid rewrite on the same file)

            StorageReference imagesRef = mStorageReference.child("filename");
                    //+ new Date().getTime());

            //upload image code

            UploadTask uploadTask = imagesRef.putBytes(dataBAOS);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(getApplicationContext(),"Sending failed", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

            //handle success

                    Toast.makeText(getApplicationContext(),"Sent", Toast.LENGTH_SHORT).show();
                    mprogressDialog.dismiss();
                }
            });
        }

           /* StorageReference filepath=mStorageReference.child("Photos").child(uri.getLastPathSegment());

            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    mprogressDialog.dismiss();

                    Toast.makeText(PictureActivity.this,"upload done!",Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    mprogressDialog.dismiss();
                    Toast.makeText(PictureActivity.this,"can't upload"+ e.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }*/
    }



}
